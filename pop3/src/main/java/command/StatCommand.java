package command;

import mailbox.MailMessage;
import mailbox.Mailbox;
import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;
import session.SessionState;

public class StatCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {
        if (context.getSessionState() != SessionState.TRANSACTION) {
            return Pop3Response.err("not in transaction state");
        }
        Mailbox mailbox = context.getMailbox();

        int count = 0;
        long totalSize = 0;

        for (MailMessage msg : mailbox.getMessages()) {
            if (!msg.isDeleted()) {
                count++;
                totalSize += msg.getSize();
            }
        }

        return Pop3Response.ok(count + " " + totalSize);

    }
}
