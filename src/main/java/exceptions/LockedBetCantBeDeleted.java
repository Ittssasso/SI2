package exceptions;

public class LockedBetCantBeDeleted extends Exception {
	private static final long serialVersionUID = 1L;
	 
	 public LockedBetCantBeDeleted()
	  {
	    super();
	  }
	  /**This exception is triggered if the question already exists 
	  *@param s String of the exception
	  */
	  public LockedBetCantBeDeleted(String s)
	  {
	    super(s);
	  }
}
