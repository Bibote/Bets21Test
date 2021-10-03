package exceptions;

public class DifferentPasswords extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DifferentPasswords() {
		super();
	}

	public DifferentPasswords(String message) {
		super(message);
		
	}



}
