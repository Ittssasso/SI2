package exceptions;

public class NotReplicable extends Exception{

	 public NotReplicable()
	  {
	    super();
	  }
	  /**This exception is triggered if the event already exists 
	  *@param s String of the exception
	  */
	  public NotReplicable(String s) {
	    super(s);
	  }
}