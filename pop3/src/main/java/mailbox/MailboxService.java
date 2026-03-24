package mailbox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class MailboxService {

public static List<MailMessage> getMessages(Path userFolder) throws IOException {
    List<MailMessage> messages = new ArrayList<>();

    try (Stream<Path> paths = Files.list(userFolder)) {
        Iterator<Path> it = paths.iterator();
        int index = 1;
        while (it.hasNext()) {
            Path file = it.next();

            long size = Files.size(file);

            messages.add(new MailMessage(
                    index++,
                    file.getFileName().toString(),
                    file,
                    size,
                    false
            ));
        }
    }catch(Exception e){
        System.out.println("Failed to get mailbox files");
    }
    return messages;
}

}
