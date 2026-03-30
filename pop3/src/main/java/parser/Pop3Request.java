package parser;

public class Pop3Request {
    private final String command;
    private final String argument;

    public Pop3Request(String command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    public String getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }



    @Override
    public String toString() {
        return "Pop3Request{" +
                "command='" + command + '\'' +
                ", argument='" + argument + '\'' +
                '}';
    }
}
