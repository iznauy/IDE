package iznauy.exception;

public class SyntaxError extends Exception {

    private static final long serialVersionUID = -8402552871238251799L;

    public final static String UNEXPECTED_FRONT_FRAME = "Syntax Error: Unexpected front frame";

    public final static String UNEXPECTED_BACK_FRAME = "Syntax Error: Unexpected back frame";

    public final static String UNBLOCKED_FRONT_FRAME = "Syntax Error: Unblocked front frame";
    
    public final static String INVALID_NAME = "Syntax Error: invalid code";

    public SyntaxError() {
        super();
    }

    public SyntaxError(String message) {
        super(message);
    }
    
}
