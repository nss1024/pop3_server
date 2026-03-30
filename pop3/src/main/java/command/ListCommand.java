package command;

import mailbox.MailMessage;
import mailbox.Mailbox;
import parser.Pop3Request;
import response.Pop3Response;
import session.SessionContext;
import session.SessionState;

import java.util.ArrayList;
import java.util.List;

public class ListCommand implements Pop3Command{

    @Override
    public Pop3Response execute(Pop3Request request, SessionContext context) {
        if (context.getSessionState() != SessionState.TRANSACTION) {
            return Pop3Response.err("not in transaction state");
        }

        System.out.println("LIST request args:"+request.getArgument());

        Mailbox mailbox = context.getMailbox();

        if(request.getArgument()==null || request.getArgument().isBlank()){
            return noArgsList(mailbox);
        }else{
            return argsList(mailbox,request);
        }
    }

    private Pop3Response noArgsList(Mailbox mailbox){
        List<String> lines = new ArrayList<>();
        lines.add("+OK");

        for (MailMessage msg : mailbox.getMessages()) {
            if (!msg.isDeleted()) {
                lines.add(msg.getIndex() + " " + msg.getSize());
            }
        }

        lines.add(".");

        return Pop3Response.multiLine(lines);
    }

    private Pop3Response argsList(Mailbox mailbox, Pop3Request request){
        String listArgument = request.getArgument();

        //Ensure argument is number
        int index =0;
        try {
            index = Integer.parseInt(listArgument);
        }catch (NumberFormatException e){
            return Pop3Response.err("invalid message number");
        }
        MailMessage message = mailbox.getMessage(index);
        if(message!=null && !message.isDeleted()){
                return Pop3Response.ok(message.getIndex()+" "+message.getSize());
        } else{
            return  Pop3Response.err("invalid message number");
        }
    }

}
