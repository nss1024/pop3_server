package authenticate;

public class AuthService {


    public boolean authenticate (String username, String pass){
        System.out.println("Authenticating user "+username+" using password: "+pass );
        return true;
    }

}
