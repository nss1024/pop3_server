package session;

import authenticate.AuthService;
import mailbox.MailboxManager;

import java.net.Socket;

public class SessionContext {

    private String userName;
    private String pass;

    private AuthService authService;
    private MailboxManager mailboxManager;
    private Socket socket;


    public SessionContext(AuthService authService, MailboxManager mailboxManager, Socket socket) {
        this.authService = authService;
        this.mailboxManager = mailboxManager;
        this.socket=socket;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Socket getSocket() {
        return socket;
    }



}
