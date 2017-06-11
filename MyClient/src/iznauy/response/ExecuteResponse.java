package iznauy.response;

public class ExecuteResponse extends Response {

    private String output;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public ExecuteResponse(String output) {
        super();
        this.setStatus(Response.SUCCESS);
        this.setResponseType(Response.EXECUTE);
        this.setOutput(output);
    }

    public ExecuteResponse(String status, String output) {
        super();
        this.setStatus(status);
        this.setResponseType(Response.EXECUTE);
        this.setOutput(output);
    }
    
}