package iznauy.request;

import iznauy.utils.User;

/**
 * Created by iznauy on 2017/6/7.
 */
public class LoginRequest extends Request {

    public LoginRequest(String userName, String password) {
        super(userName, password);
        this.setRequestType(Request.LOGIN);
    }

    public LoginRequest(User user) {
        this(user.getUserName(), user.getPassword());
    }

}