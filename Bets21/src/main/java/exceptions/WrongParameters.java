package exceptions;

public class WrongParameters extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongParameters() {

	}

	public WrongParameters(String message) {
		super(message);

	}

}
