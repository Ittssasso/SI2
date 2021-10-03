package exceptions;

public class EventAlreadyExists extends Exception{

	 public EventAlreadyExists()
	  {
	    super();
	  }
	  /**This exception is triggered if the event already exists 
	  *@param s String of the exception
	  */
	  public EventAlreadyExists(String s) {
	    super(s);
	  }
}