package iznauy.response;

public class OpenFileResponse extends Response {

    private String content;

    public OpenFileResponse(String content) {
        super();
        this.content = content;
        this.setResponseType(Response.OPEN_FILE);
    }

    public OpenFileResponse(String content, String status) {
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
