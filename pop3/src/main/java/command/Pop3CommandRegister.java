package command;

import java.util.HashMap;

public class Pop3CommandRegister {
    private static HashMap<String, Pop3Command> pop3CommandHashMap = new HashMap<>();

    Pop3CommandRegister(){
        loadMap();
    }

    public Pop3Command getCommand (String name){
        return pop3CommandHashMap.get(name.toUpperCase());
    }

    private void loadMap(){
        pop3CommandHashMap.put("APOP",new ApopCommand());
        pop3CommandHashMap.put("DELET",new DeleCommand());
        pop3CommandHashMap.put("LIST",new ListCommand());
        pop3CommandHashMap.put("NOOP",new NoopCommand());
        pop3CommandHashMap.put("PASS",new PassCommand());
        pop3CommandHashMap.put("QUIT",new QuitCommand());
        pop3CommandHashMap.put("RETR",new RetrCommand());
        pop3CommandHashMap.put("RSET",new RsetCommand());
        pop3CommandHashMap.put("STAT",new StatCommand());
        pop3CommandHashMap.put("TOP",new TopCommand());
        pop3CommandHashMap.put("UIDL",new UidlCommand());
        pop3CommandHashMap.put("USER",new UserCommand());
    }

}
