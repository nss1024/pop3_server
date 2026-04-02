package command;

import mailbox.MailMessage;
import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;
import session.SessionState;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class RetrCommand implements Pop3Command{

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

        try {
            Files.lines(message.getFilePath())
                    .forEach(lines::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lines.add(".");

        return Pop3Response.multiLine(lines);

    }
}
