package exceptions;

public class WrongEmailPattern extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongEmailPattern() {
		super();
	}
	
	public WrongEmailPattern(String e) {
		super(e);
	}

}
