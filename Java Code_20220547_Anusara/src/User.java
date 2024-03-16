import java.util.List;

//User class to store user details
public class User {

    //Encapsulated variables to store user details
    private String username;
    private String password;

    //Constructor to initialize a user account with details
    public User(String username,String password) {
        this.username = username;
        this.password = password;
    }


    //To get username
    public String getUsername() {
        return username;
    }

    //To get password
    public String getPassword() {
        return password;
    }

    //To set password
    public void setPassword(String password) {
        this.password = password;
    }

    //To set username
    public void setUsername(String username) {
        this.username = username;
    }
}
