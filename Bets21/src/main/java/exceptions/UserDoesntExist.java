package exceptions;

public class UserDoesntExist extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDoesntExist() {
		super();
	}

	public UserDoesntExist(String message) {
		super(message);
	
	}


}
