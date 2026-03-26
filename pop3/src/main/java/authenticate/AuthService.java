package authenticate;

public class AuthService {


    public static boolean authenticate (String username, String pass){
        System.out.println("Authenticating user "+username+" using password: "+pass );
        return true;
    }

}
