package iznauy.response;

/**
 * Created by iznauy on 2017/7/1.
 */
public class DebugResponse extends Response{


    private String output;

    public String getOutput() {
            return output;
        }

    public void setOutput(String output) {
            this.output = output;
        }

    public DebugResponse(String output) {
        super();
        this.setStatus(Response.SUCCESS);
        this.setResponseType(Response.DEBUG);
        this.setOutput(output);
    }

    public DebugResponse(String status, String output) {
        super();
        this.setStatus(status);
        this.setResponseType(Response.DEBUG);
        this.setOutput(output);
    }


}