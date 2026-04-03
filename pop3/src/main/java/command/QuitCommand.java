package command;

import authenticate.AuthState;
import mailbox.MailboxManager;
import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;

public class QuitCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {

              
        if(context.getAuthState()== AuthState.LOGGED_OUT){
            return Pop3Response.ok("");
        }



        return null;
    }
}
