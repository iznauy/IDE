package iznauy.exception;

public class StackOutBoundException extends Exception {

	public StackOutBoundException() {
		super();
	}

	public StackOutBoundException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 9211738937883149956L;

	public static final String OUT_BOUDN = "Error: Stack out bound!";

}
