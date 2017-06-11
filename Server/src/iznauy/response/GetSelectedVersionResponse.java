package iznauy.response;


/**
 * Created by iznauy on 2017/6/11.
 */
public class GetSelectedVersionResponse extends Response {

    private String content;

    public GetSelectedVersionResponse(String content) {
        super();
        this.content = content;
        this.setResponseType(Response.GET_SELECTED_VERSION);
    }

    public GetSelectedVersionResponse(String content, String status) {
        this(content);
        this.setStatus(status);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
