package session;

import java.net.Socket;

public class Pop3SessionHandler implements Runnable{

    Socket socket;

    public Pop3SessionHandler(Socket s){
        this.socket=s;
    }

    @Override
    public void run() {
        
    }
}
