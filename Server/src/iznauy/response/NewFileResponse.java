package iznauy.response;


/**
 * Created by iznauy on 2017/6/11.
 */
public class NewFileResponse extends Response {

    public static final String OK = "创建成功";

    public static final String EXIST = "文件已存在！";

    public static final String FAIL = "服务器走丢了！";

    public NewFileResponse() {
        this.setResponseType(Response.NEW_FILE);
        this.setStatus(OK);
    }

    public NewFileResponse(String status) {
        this();
        this.setStatus(status);
    }

}
