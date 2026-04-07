package command;

import mailbox.MailMessage;
import mailbox.Mailbox;
import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;
import session.SessionState;

import java.util.ArrayList;
import java.util.List;

public class UidlCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {


        if (context.getSessionState() != SessionState.TRANSACTION) {
            return Pop3Response.err("not in transaction state");
        }

        String arg = request.getArgument();
        Mailbox mailbox = context.getMailbox();

        if (arg == null || arg.isBlank()) {
            return handleAll(mailbox);
        } else {
            return handleSingle(mailbox, arg);
        }
    }

    private Pop3Response handleAll(Mailbox mailbox) {
        List<String> lines = new ArrayList<>();
        lines.add("+OK");

        for (MailMessage msg : mailbox.getMessages()) {
            if (msg.isDeleted()) continue;

            lines.add(msg.getIndex() + " " + extractUid(msg));
        }

        lines.add(".");
        return Pop3Response.multiLine(lines);
    }

    private Pop3Response handleSingle(Mailbox mailbox, String arg) {
        int index;

        try {
            index = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return Pop3Response.err("invalid message number");
        }

        MailMessage msg = mailbox.getMessage(index);

        if (msg == null || msg.isDeleted()) {
            return Pop3Response.err("no such message");
        }

        return Pop3Response.ok(index + " " + extractUid(msg));
    }

    private String extractUid(MailMessage msg) {
        String filename = msg.getFilePath().getFileName().toString();

        return filename.contains(".")
                ? filename.substring(0, filename.lastIndexOf('.'))
                : filename;
    }

}
