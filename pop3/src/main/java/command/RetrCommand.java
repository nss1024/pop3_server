package command;

import mailbox.MailMessage;
import mailbox.Mailbox;
import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;
import session.SessionState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class RetrCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {

        return null;

    }

    @Override
    public boolean handlesOwnResponse() {
        return true;
    }

    @Override
    public void writeDirect(OutputStream out, Pop3Request request, SessionContext context) {

        if (context.getSessionState() != SessionState.TRANSACTION) {
            writeError(out, "not in transaction state");
            return;
        }

        String arg = request.getArgument();

        if (arg == null || arg.isBlank()) {
            writeError(out, "missing message number");
            return;
        }

        int index;

        try {
            index = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            writeError(out, "invalid message number");
            return;
        }

        MailMessage message = context.getMailbox().getMessage(index);

        if (message == null || message.isDeleted()) {
            writeError(out, "no such message");
            return;
        }

        try {
            out.write("+OK\r\n".getBytes(StandardCharsets.UTF_8));

            try (BufferedReader reader =
                         Files.newBufferedReader(message.getFilePath())) {

                String line;

                while ((line = reader.readLine()) != null) {

                    if (line.startsWith(".")) {
                        line = "." + line;
                    }

                    out.write(line.getBytes(StandardCharsets.UTF_8));
                    out.write("\r\n".getBytes(StandardCharsets.UTF_8));
                }
            }

            out.write(".\r\n".getBytes(StandardCharsets.UTF_8));
            out.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void writeError(OutputStream out, String message){
        try{
            String errMsg = "-ERR"+message+"\r\n";
            out.write(errMsg.getBytes(StandardCharsets.UTF_8));
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }


}
