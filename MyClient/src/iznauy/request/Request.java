package iznauy.request;

import iznauy.utils.User;

public class Request {
	
	private String userName = null;
	
	private String password = null;
	
	private String requestType = null;
	
	public static final String LOGIN = "LOGIN";
	
	public static final String REGISTER = "REGISTER";
	
	public static final String EXECUTE = "EXECUTE";
	
	public static final String DEFAULT = "DEFAULT";
	
	public static final String NEW_FILE = "NEW_FILE";
	
	public static final String OPEN_FILE = "OPEN_FILE";
	
	public static final String SAVE_FILE = "SAVE_FILE";
	
	public static final String GET_FILE_LIST = "GET_FILE_LIST";
	
	public static final String GET_FILE_VERSION_LIST = "GET_FILE_VERSION_LIST";
	
	public static final String GET_SELECTED_VERSION_LIST = "GET_SELECTED_VERSION_REQUEST";
	
	public static final String OOK = "OOK";
	
	public static final String BRAIN_FUCK = "BRAIN_FUCK";
	
	public Request(String userName, String password) {
		this.setPassword(password);
		this.setUserName(userName);
		this.requestType = DEFAULT;
	}
	
	public Request(User user) {
		this(user.getUserName(), user.getPassword());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

}
