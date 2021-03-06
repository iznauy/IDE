package iznauy.request;

import iznauy.utils.User;

public class RegisterRequest extends Request {

	public RegisterRequest(String userName, String password) {
		super(userName, password);
		this.setRequestType(Request.REGISTER);
	}
	
	public RegisterRequest(User user) {
		this(user.getUserName(), user.getPassword());
	}

}
