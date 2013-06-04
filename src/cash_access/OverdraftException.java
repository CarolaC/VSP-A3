package cash_access;

public class OverdraftException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public OverdraftException(String message) {
		super(message);
	}

}
