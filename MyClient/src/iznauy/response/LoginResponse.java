package iznauy.response;

public class LoginResponse extends Response {

    public static final String WRONG_PASSWORD = "wrong password!";

    public static final String NON_EXIST = "unexpected User";

    public LoginResponse() {
        super();
        this.setResponseType(Response.LOGIN);
    }

    public LoginResponse(String status) {
        super(status);
        this.setResponseType(Response.LOGIN);
    }

}
