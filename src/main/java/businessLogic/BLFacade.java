package businessLogic;

import java.util.Vector;
import java.util.Date;

import domain.Question;
import domain.RegisteredClient;
import domain.Bet;
import domain.BetPredictionQuestionEventContainer;
import domain.Event;
import domain.Person;
import domain.Prediction;
import exceptions.AlreadyReplicated;
import exceptions.BetIsLocked;
import exceptions.BetIsMultiple;
import exceptions.EventAlreadyExists;
import exceptions.EventFinished;
import exceptions.LockedBetCantBeDeleted;
import exceptions.NoTokens;
import exceptions.NoUserFound;
import exceptions.NotEnoughMoney;
import exceptions.NotReplicable;
import exceptions.PredictionAlreadyExists;
import exceptions.QuestionAlreadyExist;
import exceptions.ReplicableNoBets;
import exceptions.SelfReplicate;
import iterator.ExtendedIterator;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public ExtendedIterator<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	/**
	 * This method calls the data access to search the person who is trying to log in in the database
	 * 
	 * @param email from the person who is trying to log in
	 * @param pw password for that account
	 * @return the person who logged in, or null in case the email is not found or the password is incorrect
	 */
	@WebMethod public Person isLogin(String email, String pw);
	
	/**
	 * This method invokes the data access to register the client into the system or database
	 * 
	 * @param name
	 * @param surname
	 * @param birthDate
	 * @param DNI
	 * @param email
	 * @param password
	 * @param currentAccount
	 * @return boolean, true if the client has been registered successfully, false if there is already someone registered with that email
	 */
	@WebMethod public boolean storeRegisteredClient(String name, String surname, Date birthDate, String DNI, String email, String password, String currentAccount, boolean replicable);

	
	/**
	 * This method invokes the data access to create a new event, with two teams and a date
	 * 
	 * @param t1 home-playing team
	 * @param t2 outsider team
	 * @param d game's date
	 */
	@WebMethod public void createEvent(String t1, String t2, Date d) throws EventAlreadyExists;
	
	/**
	 * This method invokes the data access to create a new prediction for a question
	 * @param question
	 * @param prediction
	 * @param fee
	 */
	@WebMethod public void createPrediction(Question question, String prediction, float fee) throws PredictionAlreadyExists;
	
	/**
	 * This method invokes the data access to put the result (the right prediction) of a question
	 * @param prediction
	 */
	@WebMethod public void putResult(Question question, Prediction prediction);
	
//    @WebMethod public boolean toBet(Prediction p, float m, Person u, Date d, String evdescription) throws EventFinished;
    
	/**
	 * This method invokes the data access to create a bet from a prediction
	 * @param p prediction of question
	 * @param m money to bet with
	 * @param u registered client who bet
	 * @param d date of event
	 * @param evdescription event description
	 * @return boolean, true if the bet has been created successfully, false if not
	 * @throws EventFinished 
	 */
    @WebMethod public boolean toBet(Vector<Prediction> p, float m, Person u, Date d, String evdescription) throws EventFinished;
    
    /**
     * This method invokes the data access to delete a bet from a prediction
     * @param b
     * @param u
     * @return boolean, true if the bet has been deleted successfully, false if not
     */ 
	@WebMethod public boolean deleteBet(Bet b, Person u) throws LockedBetCantBeDeleted;
	
	/**
	 * This method create a new date
	 * @param year
	 * @param month
	 * @param day
	 * @return the date created
	 */
	@WebMethod public Date newDate(int year,int month,int day);

	/**
	 * This method invokes the data access to get registered client
	 * @param u
	 * @return the registered client who wanted to obtain
	 */
	@WebMethod public RegisteredClient getRegisteredClient(RegisteredClient u);

	/**
	 * This method invokes the data access to insert money to a registered client
	 * @param money
	 * @param logged
	 * @return the registered client to whom the money has been put
	 */
	@WebMethod public RegisteredClient insertMoney(float money, Person logged);
	
	/**
	 * This method invokes the data access to delete a event
	 * @param event to be deleted
	 */
	@WebMethod public void deleteEvent(Event event);
	
	/**
	 * 
	 * This method show all the bets that the registered client has made without any date range
	 * @param rC Registered client who wants to see their bets
	 * @return all the bets that the user has made without putting a date range
	 */
	@WebMethod public Vector<BetPredictionQuestionEventContainer> viewAllBets(RegisteredClient rC);
	
	/**
	 * This method shows all the bets that the registered client has made in a range of dates
	 * @param rC Registered client who wants to see their bets
	 * @param date1 Start date
	 * @param date2 End date
	 * @return all the bets that the user has made by placing a range of dates
	 */
	@WebMethod public Vector<BetPredictionQuestionEventContainer> viewBets(RegisteredClient rC, Date date1, Date date2);

	/**
	 * This method show all the profits that the registered client has made without any date range
	 * @param rC Registered client who wants to see their bets
	 * @return all the profits that the user has obtained without putting a date range
	 */
	@WebMethod public Vector<BetPredictionQuestionEventContainer> viewAllProfits(RegisteredClient rC);
	
	/**
	 * This method shows all the profits that the registered client has made in a range of dates
	 * @param rC Registered client who wants to see their bets
	 * @param date1 Start date
	 * @param date2 End date
	 * @return all the profits that the user has obtained by placing a range of dates
	 */
	@WebMethod public Vector<BetPredictionQuestionEventContainer> viewProfits(RegisteredClient rC, Date date1, Date date2); 
	
	/**
	 * This method show all future bets that the registered client has made
	 * @param rC Registered client who wants to see their bets
	 * @return the future bets
	 */
	@WebMethod public Vector<BetPredictionQuestionEventContainer> viewFutureBets(RegisteredClient rC);
	
	/**
	 * This method invokes data access to replicate all the future bets (bets that haven't happened yet) that have done a specific user.
	 * @param email, the email of the user from who we want to replicate.
	 * @param percentage, in which percentage we want to pay what the other user has paid.
	 * @param registeredClient, the client who wants to replicate the bets.
	 * @throws NoUserFound: if there is no user with selected email, 
	 * 		   ReplicableNoBets: if the user that we want to replicate has no bets,
	 * 		   NotReplicable: if the user that we want to replicate is not replicable.
	 * 		   SelfReplicable: if the user tries to replicate herself/himself.
	 * 		   AlreadyReplicated: if the user has the same bets as the other.
	 */
	@WebMethod public void replicateUser(String email, int percentage, RegisteredClient registeredClient) throws NoUserFound, ReplicableNoBets, NotReplicable, SelfReplicate, AlreadyReplicated;


	/**
	 * This method invokes the data access to buy tokens.
	 * @param quantityTokens
	 * @param logged
	 * @return the logged registered client
	 * @throws NotEnoughMoney if the client hasn't has enough money.
	 */
	@WebMethod public RegisteredClient buyTokens(int quantityTokens, Person logged) throws NotEnoughMoney;

	/**
	 * This method invokes the data access so a client can use a token to bet
	 * @param b Bet where the token is wanted to be used
	 * @param rc the client who wants to use a token
	 */
	@WebMethod
	public int useToken(Bet b, RegisteredClient rc) throws NoTokens, BetIsMultiple, BetIsLocked;
	
}
