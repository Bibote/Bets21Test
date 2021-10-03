package exceptions;

public class NoCardsStored extends Exception {
	
	private static final long serialVersionUID = 1L;
	 
	 public NoCardsStored()
	  {
	    super();
	  }
	  /**This exception is triggered if the user hasn't credit cards stored
	  *@param s String of the exception
	  */
	  public NoCardsStored(String s)
	  {
	    super(s);
	  }

}
