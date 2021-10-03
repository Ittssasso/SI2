package exceptions;

public class BetIsLocked extends Exception {
	
	private static final long serialVersionUID = 1L;
	 
	 public BetIsLocked()
	  {
	    super();
	  }
	  
	 /**This exception is triggered if the bet is multiple
	  *@param s String of the exception
	  */
	 
	  public BetIsLocked(String s)
	  {
	    super(s);
	  }
}
