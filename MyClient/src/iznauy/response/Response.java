package iznauy.response;

/**
 * Response类。是服务器端和客户端交互的模板类。
 * @author iznauy
 *
 */
public class Response {

	public static final String LOGIN = "LOGIN";

	public static final String REGISTER = "REGISTER";

	public static final String EXECUTE = "EXECUTE";

	public static final String DEFAULT = "DEFAULT";
	
	public static final String NEW_FILE = "NEW_FILE";
	
	public static final String SAVE_FILE = "SAVE_FILE";
	
	public static final String GET_FILE_LIST = "GET_FILE_LIST";

    public static final String OPEN_FILE = "OPEN_FILE";

    public static final String GET_FILE_VERSION_LIST = "GET_FILE_VERSION_LIST";

    public static final String GET_SELECTED_VERSION = "GET_SELECT_VERSION";

	private String responseType = DEFAULT;
	
	public static final String DEBUG = "DEBUG";

	private String status = null;

	public static final String SUCCESS = "SUCCESS";

	public static final String UNKNOWN_REASON = "unknown reason";

	public Response() {
		this.status = Response.SUCCESS;
	}

	public Response(String status) {
		this.status = status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

}
