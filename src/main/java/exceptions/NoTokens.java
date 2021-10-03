package exceptions;

public class NoTokens extends Exception {
	
	private static final long serialVersionUID = 1L;
	 
	 public NoTokens()
	  {
	    super();
	  }
	  
	 /**This exception is triggered if the client has no tokens
	  *@param s String of the exception
	  */
	  public NoTokens(String s)
	  {
	    super(s);
	  }
	
}
