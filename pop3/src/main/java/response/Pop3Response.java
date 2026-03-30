package response;

import java.util.List;
import java.util.stream.Collectors;

public class Pop3Response {

    private final String message;
    private final Boolean ok;


    public Pop3Response(Boolean b, String message) {
        this.message=message;
        this.ok=b;
    }

    public static Pop3Response ok(String message) {
        return new Pop3Response(true, message);
    }

    public static Pop3Response err(String message) {
        return new Pop3Response(false, message);
    }

    public static Pop3Response multiLine(List<String> lines) {
        return new Pop3Response(null,Pop3Response.linesToString(lines));
    }

    @Override
    public String toString() {
        if(ok!=null){
            return (ok ? "+OK " : "-ERR ") + message + "\r\n";
        }else {
            return message;
        }

    }

    private static String linesToString(List<String> lines){
        return lines.stream()
                .map(line -> line + "\r\n")
                .collect(Collectors.joining());
    }

}
