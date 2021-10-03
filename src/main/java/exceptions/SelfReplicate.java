package exceptions;

public class SelfReplicate extends Exception {

	private static final long serialVersionUID = 1L;
	 
	 public SelfReplicate()
	  {
	    super();
	  }
	  
	 /**This exception is triggered if the bet is multiple
	  *@param s String of the exception
	  */
	 
	  public SelfReplicate(String s)
	  {
	    super(s);
	  }
}
