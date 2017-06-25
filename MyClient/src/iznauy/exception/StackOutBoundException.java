package iznauy.exception;

/**
 * 当用户执行bf/Ook代码时栈下溢出时抛出
 * @author iznauy
 *
 */
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
