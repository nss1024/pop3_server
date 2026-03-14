package server;

import session.Pop3SessionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pop3Server {

    private final int port;
    private final ExecutorService connectionPool;

    public Pop3Server(int port) {
        this.port = port;
        this.connectionPool = Executors.newFixedThreadPool(20);
    }


    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("POP3 server listening on " + port);

        while (true) {

            Socket socket = serverSocket.accept();

            connectionPool.submit(new Pop3SessionHandler(socket));
        }
    }
}
