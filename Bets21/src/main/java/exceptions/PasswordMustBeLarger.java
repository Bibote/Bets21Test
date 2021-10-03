package exceptions;

public class PasswordMustBeLarger extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordMustBeLarger() {
		super();
	}

	public PasswordMustBeLarger(String message) {
		super(message);
		
	}



}
