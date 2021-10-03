package exceptions;
public class PredictionAlreadyExists extends Exception {
 private static final long serialVersionUID = 1L;
 
 public PredictionAlreadyExists()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public PredictionAlreadyExists(String s)
  {
    super(s);
  }
}