package exceptions;

public class MaxUsed extends Exception {
	private static final long serialVersionUID = 1L;
	 
	 public MaxUsed()
	  {
	    super();
	  }
	  /**This exception is triggered if the event has already finished
	  *@param s String of the exception
	  */
	  public MaxUsed(String s)
	  {
	    super(s);
	  }
}

