package command;

import mailbox.MailMessage;
import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;
import session.SessionState;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TopCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {

        if (context.getSessionState() != SessionState.TRANSACTION) {
            return Pop3Response.err("not in transaction state");
        }
        String arg = request.getArgument();

        if (arg == null || arg.isBlank()) {
            return Pop3Response.err("missing message number");
        }

        int index;
        try {
            index = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return Pop3Response.err("invalid message number");
        }

        MailMessage message = context.getMailbox().getMessage(index);

        if (message == null || message.isDeleted()) {
            return Pop3Response.err("no such message");
        }

        List<String> lines = new ArrayList<>();
        lines.add("+OK");

        boolean inBody = false;
        int bodyCount = 0;

        try (BufferedReader reader = Files.newBufferedReader(message.getFilePath())) {

            String line;

            while ((line = reader.readLine()) != null) {

                // dot-stuffing
                if (line.startsWith(".")) {
                    line = "." + line;
                }

                if (!inBody) {
                    lines.add(line);

                    if (line.isEmpty()) {
                        inBody = true;
                    }

                } else {
                    if (bodyCount < 5) {
                        lines.add(line);
                        bodyCount++;
                    } else {
                        break;
                    }
                }
            }

        } catch (IOException e) {
            return Pop3Response.err("failed to read message");
        }

        return Pop3Response.multiLine(lines);

    }
}
