package iznauy.utils;

/**
 * Created by iznauy on 2017/6/7.
 */
public class User {

    public User(String userName, String password) {
        this.password = password;
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName = null;

    private String password = null;

}
