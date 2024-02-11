package bus;

public class NotInRangeException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotInRangeException(String message) {
        super(message);
    }
}