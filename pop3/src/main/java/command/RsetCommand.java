package command;

import mailbox.MailMessage;
import mailbox.Mailbox;
import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;
import session.SessionState;

public class RsetCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {
        if (context.getSessionState() != SessionState.TRANSACTION) {
            return Pop3Response.err("not in transaction state");
        }

        Mailbox mailbox = context.getMailbox();

        for (MailMessage msg : mailbox.getMessages()) {
            if (msg.isDeleted()) {
                msg.setDeleted(false);
            }
        }

        return Pop3Response.ok("maildrop has been reset");

    }
}
