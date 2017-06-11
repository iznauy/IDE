package iznauy.response;

public class SaveFileResponse extends Response {

    public static final String OK = "创建成功";

    public static final String FAIL = "服务器走丢了！";

    public SaveFileResponse() {
        this.setResponseType(Response.SAVE_FILE);
        this.setStatus(OK);
    }

    public SaveFileResponse(String status) {
        this();
        this.setStatus(status);
    }

}