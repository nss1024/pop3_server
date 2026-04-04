package command;

import authenticate.AuthState;
import mailbox.MailMessage;
import mailbox.Mailbox;
import mailbox.MailboxManager;
import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;
import session.SessionState;

import java.io.IOException;
import java.nio.file.Files;

public class QuitCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {
        MailboxManager mailboxManager = context.getMailboxManager();

        if (context.getAuthState() == AuthState.LOGGED_OUT) {
            return Pop3Response.ok("");
        }

        if (context.getSessionState() == SessionState.TRANSACTION) {
            handleTransactionQuit(context);
            mailboxManager.unlock(context.getUserName(), context.getSessionId());
        }

        return Pop3Response.ok("bye");

    }

private void handleTransactionQuit(SessionContext context) {
    Mailbox mailbox = context.getMailbox();

    for (MailMessage msg : mailbox.getMessages()) {
        if (msg.isDeleted()) {
            try {
                Files.deleteIfExists(msg.getFilePath());
            } catch (IOException e) {
                // log it, but don't keep QUIT moving
                System.err.println("Failed to delete: " + msg.getFilePath());
            }
        }
    }


}
}

