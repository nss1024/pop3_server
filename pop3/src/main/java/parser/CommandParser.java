package parser;


public class CommandParser {


    public static Pop3Request parseCommand (String line){
        String[] parts = line.split(" ", 2);
        String command = parts[0].toUpperCase();
        String argument = parts.length > 1 ? parts[1] : null;


        return new Pop3Request(command, argument);
    }

}
