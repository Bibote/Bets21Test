package exceptions;

public class BoletoNoExiste extends Exception {
	private static final long serialVersionUID = 1L;

	public BoletoNoExiste() {

	}

	public BoletoNoExiste(String message) {
		super(message);

	}

}
