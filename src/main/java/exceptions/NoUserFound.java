package exceptions;

public class NoUserFound extends Exception{

	 public NoUserFound()
	  {
	    super();
	  }
	  /**This exception is triggered if the event already exists 
	  *@param s String of the exception
	  */
	  public NoUserFound(String s) {
	    super(s);
	  }
}