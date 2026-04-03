package session;

import authenticate.AuthService;
import authenticate.AuthState;
import mailbox.Mailbox;
import mailbox.MailboxManager;

import java.net.Socket;
import java.util.UUID;

public class SessionContext {

    private String userName;
    private String pass;

    private AuthService authService;
    private MailboxManager mailboxManager;
    private Socket socket;
    String sessionId = UUID.randomUUID().toString();
    Mailbox mailbox;
    SessionState sessionState;
    AuthState authState;


    public SessionContext(AuthService authService, MailboxManager mailboxManager, Socket socket) {
        this.authService = authService;
        this.mailboxManager = mailboxManager;
        this.socket=socket;
        this.authState=AuthState.LOGGED_OUT;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    public SessionState getSessionState() {
        return sessionState;
    }

    public void setSessionState(SessionState sessionState) {
        this.sessionState = sessionState;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return pass;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public MailboxManager getMailboxManager() {
        return mailboxManager;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setAuthState(AuthState authState) {
        this.authState = authState;
    }

    public AuthState getAuthState() {
        return authState;
    }
}
