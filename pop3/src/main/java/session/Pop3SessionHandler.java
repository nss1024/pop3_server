package session;

import authenticate.AuthService;
import command.Pop3Command;
import command.Pop3CommandRegister;
import mailbox.MailboxManager;
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
    MailboxManager mailboxManager;
    AuthService authService;

    public Pop3SessionHandler(Socket s, Pop3CommandRegister register, AuthService authService, MailboxManager mailboxManager){

        this.socket=s;
        this.pop3CommandRegister=register;
        this.authService = authService;
        this.mailboxManager = mailboxManager;

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
            context = new SessionContext(authService,mailboxManager,socket);
            writer.write("+OK pop3 server ready \r\n");
            writer.flush();
            String line;
            Pop3Request request;
            while(true){
                    line = reader.readLine();
                System.out.println("Received: "+line);
                if (line == null || line.isBlank()) {
                    writer.write(Pop3Response.err("unknown command!").toString());
                    writer.flush();;
                    continue;
                }


                request= CommandParser.parseCommand(line);

               //Pop3Response response =  pop3CommandRegister.getCommand(request.getCommand()).execute(request,context);

               Pop3Command command = pop3CommandRegister.getCommand(request.getCommand());
               if(command.handlesOwnResponse()){
                   command.writeDirect(socket.getOutputStream(),request,context);
               }else {
                   Pop3Response response = command.execute(request, context);
                   writer.write(response.toString());
                   writer.flush();
               }
                if ("QUIT".equalsIgnoreCase(request.getCommand())) {
                    break;
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
