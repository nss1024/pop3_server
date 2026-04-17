package command;

import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;

import java.io.OutputStream;

public interface Pop3Command {

    Pop3Response execute(Pop3Request request, SessionContext context);

    default boolean handlesOwnResponse() {
        return false;
    }

    default void writeDirect(OutputStream out,
                             Pop3Request request,
                             SessionContext context) {

    }



}
