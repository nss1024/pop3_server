import server.Pop3Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Pop3Server server = new Pop3Server(110);

        try {
            server.start(8110);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
