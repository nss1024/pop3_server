package command;

import mailbox.Mailbox;
import mailbox.MailboxService;
import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;
import session.SessionState;

import java.io.IOException;

public class PassCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {
        System.out.println("Executing PASS command");
        String password = request.getArgument();
        String username = context.getUserName();

        if (username == null) {
            return Pop3Response.err("USER required before PASS");
        }

        if (!context.getAuthService().authenticate(username, password)) {
            return Pop3Response.err("invalid credentials");
        }

        if (!context.getMailboxManager().lock(username, context.getSessionId())) {
            return Pop3Response.err("mailbox locked");
        }

        Mailbox mailbox = null;
        try {
            mailbox = new Mailbox(MailboxService.getMessages(username));
        } catch (IOException e) {
            System.out.println("Failed to get mailbox");
            throw new RuntimeException(e);
        }

        context.setMailbox(mailbox);
        context.setSessionState(SessionState.TRANSACTION);

        return Pop3Response.ok("mailbox locked and ready");
    }
}
