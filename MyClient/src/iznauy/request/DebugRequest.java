package iznauy.request;

import iznauy.utils.User;

public class DebugRequest extends Request {
	
	private String type = NewFileRequest.BRAIN_FUCK;
	
	private int count;

	private String rawSource = null;

    private String input = null;

    public DebugRequest(String userName, String password, String rawSource, String input, int count) {
        super(userName, password);
        this.setRequestType(Request.DEBUG);
        this.rawSource = rawSource;
        this.input = input;
        this.setCount(count);
    }

    public DebugRequest(User user, String rawSource, String input, int count) {
        this(user.getUserName(), user.getPassword(), rawSource, input, count);
    }

    public DebugRequest(String userName, String password, String rawSource, String input, String type, int count) {
        super(userName, password);
        this.setRequestType(Request.DEBUG);
        this.rawSource = rawSource;
        this.input = input;
        this.setType(type);
        this.setCount(count);
    }

    public DebugRequest(User user, String rawSource, String input, String type, int count) {
        this(user.getUserName(), user.getPassword(), rawSource, input, type, count);
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


}

