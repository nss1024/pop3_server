package command;

import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;

public class NoopCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {
        return Pop3Response.ok("noop");
    }
}
