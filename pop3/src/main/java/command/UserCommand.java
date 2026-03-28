package command;

import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;

public class UserCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {
        System.out.println("User command ");
        String username = request.getArgument();

        if (username == null || username.isBlank()) {
            return Pop3Response.err("missing username");
        }

        context.setUserName(username);

        return Pop3Response.ok("user accepted");
    }
}
