package exceptions;

public class BetIsMultiple extends Exception{

	private static final long serialVersionUID = 1L;
	 
	 public BetIsMultiple()
	  {
	    super();
	  }
	  
	 /**This exception is triggered if the bet is multiple
	  *@param s String of the exception
	  */
	 
	  public BetIsMultiple(String s)
	  {
	    super(s);
	  }
}
