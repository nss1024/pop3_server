package response;

public class Pop3Response {

    private final String message;
    private final boolean ok;

    public Pop3Response(boolean b, String message) {
        this.message=message;
        this.ok=b;
    }

    public static Pop3Response ok(String message) {
        return new Pop3Response(true, message);
    }

    public static Pop3Response err(String message) {
        return new Pop3Response(false, message);
    }

    @Override
    public String toString() {
        return (ok ? "+OK " : "-ERR ") + message + "\r\n";
    }

}
