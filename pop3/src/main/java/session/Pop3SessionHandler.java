package session;

import command.Pop3Command;
import command.Pop3CommandRegister;
import parser.CommandParser;
import parser.Pop3Request;
import response.Pop3Response;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Pop3SessionHandler implements Runnable{

    Socket socket;
    Pop3CommandRegister pop3CommandRegister;
    SessionContext context;

    public Pop3SessionHandler(Socket s){
        this.socket=s;
    }

    @Override
    public void run() {


        System.out.println("Connected to "+socket.getInetAddress());


        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
                );
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)
                )
        ) {
            String line;
            Pop3Request request;
            while(true){
                    line = reader.readLine();
                if (line == null || line.isBlank()) {
                    writer.write(Pop3Response.err("unknown command!").toString());
                    writer.flush();;
                    continue;
                }
                 request= CommandParser.parseCommand(line);
                 pop3CommandRegister.getCommand(request.getCommand()).execute(request,context);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
