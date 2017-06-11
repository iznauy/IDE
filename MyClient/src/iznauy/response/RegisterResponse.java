package iznauy.response;

public class RegisterResponse extends Response {

    public static final String HAS_REGISTER = "账号已注册";

    public RegisterResponse() {
        super();
        this.setResponseType(Response.REGISTER);
    }

    public RegisterResponse(String status) {
        super(status);
        this.setResponseType(Response.REGISTER);
    }

}