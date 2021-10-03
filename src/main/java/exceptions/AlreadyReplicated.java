package exceptions;

public class AlreadyReplicated extends Exception {

	private static final long serialVersionUID = 1L;
	 
	 public AlreadyReplicated()
	  {
	    super();
	  }
	  
	 /**This exception is triggered if the bet is multiple
	  *@param s String of the exception
	  */
	 
	  public AlreadyReplicated(String s)
	  {
	    super(s);
	  }
}
