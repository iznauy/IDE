package iznauy.exception;

/**
 * Created by iznauy on 2017/6/7.
 */
public class StackOutBoundException extends Exception {

    private static final long serialVersionUID = 9211738937883149956L;

    public static final String OUT_BOUDN = "Error: Stack out bound!";

    public StackOutBoundException() {
        super();
    }

    public StackOutBoundException(String message) {
        super(message);
    }

}
