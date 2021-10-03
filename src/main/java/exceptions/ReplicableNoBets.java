package exceptions;

public class ReplicableNoBets extends Exception{

	 public ReplicableNoBets()
	  {
	    super();
	  }
	  /**This exception is triggered if the event already exists 
	  *@param s String of the exception
	  */
	  public ReplicableNoBets(String s) {
	    super(s);
	  }
}