package response;

public class Pop3Response {

    public static String ok(String message) {
        return "+OK " + message + "\r\n";
    }

    public static String err(String message) {
        return "-ERR " + message + "\r\n";
    }

}
