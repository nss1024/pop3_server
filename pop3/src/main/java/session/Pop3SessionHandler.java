package session;

import response.Pop3Response;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Pop3SessionHandler implements Runnable{

    Socket socket;

    public Pop3SessionHandler(Socket s){
        this.socket=s;
    }

    @Override
    public void run() {

        try {
            OutputStream out = socket.getOutputStream();
            out.write(Pop3Response.ok("POP3 server ready").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Connected to "+socket.getInetAddress());
    }
}
