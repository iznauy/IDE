package iznauy.request;

import iznauy.utils.User;

public class LoginRequest extends Request {

	public LoginRequest(String userName, String password) {
		super(userName, password);
		this.setRequestType(Request.LOGIN);
	}
	
	public LoginRequest(User user) {
		this(user.getUserName(), user.getPassword());
	}

}
