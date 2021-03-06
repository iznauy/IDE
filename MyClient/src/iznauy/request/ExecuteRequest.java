package iznauy.request;

import iznauy.utils.User;

public class ExecuteRequest extends Request {

	/**
	 * 执行的文件种类，默认是BF
	 */
    private String type = Request.OOK;

    private String rawSource = null;

    private String input = null;

    public ExecuteRequest(String userName, String password, String rawSource, String input) {
        super(userName, password);
        this.setRequestType(Request.EXECUTE);
        this.rawSource = rawSource;
        this.input = input;
    }

    public ExecuteRequest(User user, String rawSource, String input) {
        this(user.getUserName(), user.getPassword(), rawSource, input);
    }

    public ExecuteRequest(String userName, String password, String rawSource, String input, String type) {
        super(userName, password);
        this.setRequestType(Request.EXECUTE);
        this.rawSource = rawSource;
        this.input = input;
        this.setType(type);
    }

    public ExecuteRequest(User user, String rawSource, String input, String type) {
        this(user.getUserName(), user.getPassword(), rawSource, input, type);
    }

    public String getRawSource() {
        return rawSource;
    }

    public void setRawSource(String rawSource) {
        this.rawSource = rawSource;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
