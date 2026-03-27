package command;

import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;

public interface Pop3Command {

    Pop3Response execute(Pop3Request request, SessionContext context);

}
