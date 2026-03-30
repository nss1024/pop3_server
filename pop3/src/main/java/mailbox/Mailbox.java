package mailbox;

import java.util.List;

public class Mailbox {

List<MailMessage> messages;

public Mailbox(List<MailMessage> messages){
    this.messages=messages;
}

    public List<MailMessage> getMessages() {
        return messages;
    }

    public MailMessage getMessage(int index){
        if (index < 1 || index > messages.size()) {
            return null;
        }
        return messages.get(index-1);
    }

}
