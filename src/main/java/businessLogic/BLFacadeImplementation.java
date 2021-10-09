package businessLogic;

import java.util.Calendar;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.RegisteredClient;
import domain.Bet;
import domain.BetPredictionQuestionEventContainer;
import domain.Event;
import domain.Person;
import domain.Prediction;
import domain.PredictionQuestionEventContainer;
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

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
		}

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager = da;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	/**
	 * This method calls the data access to search the person who is trying to log
	 * in in the database
	 * 
	 * @param email from the person who is trying to log in
	 * @param pw    password for that account
	 * @return the person who logged in, or null in case the email is not found or
	 *         the password is incorrect
	 */
	@WebMethod
	public Person isLogin(String email, String pw) {
		dbManager.open(false);
		Person p = dbManager.isLogin(email, pw);
		dbManager.close();
		return p;
	}

	/**
	 * This method invokes the data access to register the client into the system or
	 * database
	 * 
	 * @param name
	 * @param surname
	 * @param birthDate
	 * @param DNI
	 * @param email
	 * @param password
	 * @param currentAccount
	 * @return boolean, true if the client has been registered successfully, false
	 *         if there is already someone registered with that email
	 */

	@WebMethod
	public boolean storeRegisteredClient(String name, String surname, Date birthDate, String DNI, String email,
			String password, String currentAccount, boolean replicable) {
		boolean isRegistered = false;
		RegisteredClient rC = new RegisteredClient(name, surname, birthDate, DNI, email, password, currentAccount,
				replicable);
		dbManager.open(false);
		isRegistered = dbManager.storeRegisteredClient(rC);
		dbManager.close();
		return isRegistered;
	}

	/**
	 * This method invokes the data access to create a new event, with two teams and
	 * a date
	 * 
	 * @param t1 home-playing team
	 * @param t2 outsider team
	 * @param d  game's date
	 */
	@WebMethod
	public void createEvent(String t1, String t2, Date d) throws EventAlreadyExists {
		dbManager.open(false);
		String teams = t1 + " - " + t2;
		dbManager.createEvent(teams, d);
		dbManager.close();
	}

	/**
	 * This method invokes the data access to create a new prediction for a question
	 * 
	 * @param question
	 * @param prediction
	 * @param fee
	 */
	@WebMethod
	public void createPrediction(Question question, String prediction, float fee) throws PredictionAlreadyExists {
		if (prediction != null && fee > 0) {
			dbManager.open(false);
			dbManager.createPrediction(question, prediction, fee);
			dbManager.close();
		}
	}

	/**
	 * This method invokes the data access to put the result (the right prediction)
	 * of a question
	 * 
	 * @param prediction
	 */
	@WebMethod
	public void putResult(Question question, Prediction prediction) {
		if (prediction != null) {
			dbManager.open(false);
			dbManager.putResult(question, prediction);
			dbManager.close();
		}
	}

//	@WebMethod
//	public boolean toBet(Prediction p, float m, Person u, Date d, String evdescription) throws EventFinished {
//		boolean r = false;
//		java.util.Date dtoday= new java.util.Date();
//		if (d.before(dtoday) || d.equals(dtoday)) throw new EventFinished();
//		else {
//			if (p!=null && u.getClass().getSimpleName().equals("RegisteredClient")) {
//				RegisteredClient rG = (RegisteredClient) u;
//				if (p.getFee()<=m && m<=rG.getBalance()) {
//					dbManager.open(false);
//					r = dbManager.createBet(p, m, rG, evdescription);
//					dbManager.close();
//					return r;
//				} else return false;
//			} else return false;
//		}
//	}

	/**
	 * This method invokes the data access to create a bet from a prediction
	 * 
	 * @param p
	 * @param m
	 * @param u
	 * @param d
	 * @param evdescription
	 * @return boolean, true if the bet has been created successfully, false if not
	 * @throws EventFinished
	 */
	@WebMethod
	public boolean toBet(Vector<Prediction> p, float m, Person u, Date d, String evdescription) throws EventFinished {
		boolean r = false;
		java.util.Date dtoday = new java.util.Date();
		dbManager.open(false);
		PredictionQuestionEventContainer pLag = null;
		dbManager.open(false);
		pLag = new PredictionQuestionEventContainer(dbManager.getPrediction(p.get(0)));
		dbManager.close();
		java.util.Date dlast = pLag.getEvent().getEventDate();
		for (Prediction pi : p) {
			dbManager.open(false);
			pLag = new PredictionQuestionEventContainer(dbManager.getPrediction(pi));
			dbManager.close();
			if (pLag.getEvent().getEventDate().before(dlast))
				dlast = pLag.getEvent().getEventDate();
		}
		if (dlast.before(dtoday) || dlast.equals(dtoday)) {
			throw new EventFinished();
		} else {
			if (p != null && p.size() > 0 && u instanceof domain.RegisteredClient) {
				RegisteredClient rG = (RegisteredClient) u;
				float sum = 0;
				for (Prediction pi : p) {
					dbManager.open(false);
					pLag = new PredictionQuestionEventContainer(dbManager.getPrediction(pi));
					dbManager.close();
					sum = sum + pLag.getQuestion().getBetMinimum();
				}
				if (sum <= m && m <= rG.getBalance()) {
					dbManager.open(false);
					r = dbManager.createBet(p, m, rG, evdescription);
					dbManager.close();
					return r;
				} else
					return false;
			} else
				return false;
		}
	}

	/**
	 * This method invokes the data access to delete a bet from a prediction
	 * 
	 * @param b
	 * @param u
	 * @return boolean, true if the bet has been deleted successfully, false if not
	 */
	@WebMethod
	public boolean deleteBet(Bet b, Person u) throws LockedBetCantBeDeleted {
		boolean r = false;
		if (b.getLocked())
			throw new LockedBetCantBeDeleted();
		else {
			if (u instanceof domain.RegisteredClient) {
				dbManager.open(false);
				r = dbManager.deleteBet(b, (RegisteredClient) u);
				dbManager.close();
				return r;
			} else
				return false;
		}
	}

	/**
	 * This method invokes the data access to get registered client
	 * 
	 * @param u
	 * @return the registered client who wanted to obtain
	 */
	@WebMethod
	public RegisteredClient getRegisteredClient(RegisteredClient rG) {
		dbManager.open(false);
		RegisteredClient r = dbManager.getRegisteredClient(rG);
		dbManager.close();
		return r;
	}

	/**
	 * This method invokes the data access to insert money to a registered client
	 * 
	 * @param money
	 * @param logged
	 * @return the registered client to whom the money has been put
	 */
	@WebMethod
	public RegisteredClient insertMoney(float money, Person registeredClient) {
		dbManager.open(false);
		RegisteredClient rc = dbManager.insertMoney(money, registeredClient);
		dbManager.close();
		return rc;
	}

	/**
	 * This method invokes the data access to delete a event
	 * 
	 * @param event
	 */
	@WebMethod
	public void deleteEvent(Event event) {
		dbManager.open(false);
		dbManager.deleteEvent(event);
		dbManager.close();
	}

	/**
	 * 
	 * This method show all the bets that the registered client has made without any
	 * date range
	 * 
	 * @param rC Registered client who wants to see their bets
	 * @return all the bets that the user has made without putting a date range
	 */
	@WebMethod
	public Vector<BetPredictionQuestionEventContainer> viewAllBets(RegisteredClient rC) {
		Vector<BetPredictionQuestionEventContainer> betMovements = new Vector<BetPredictionQuestionEventContainer>();
		for (int i = 0; i < rC.getMovementsBet().size(); i++) {
			dbManager.open(false);
			BetPredictionQuestionEventContainer betCont = new BetPredictionQuestionEventContainer(
					dbManager.getBet(rC.getMovementsBet().get(i)));
			dbManager.close();

			betMovements.add(betCont);
		}
		return betMovements;
	}

	/**
	 * This method show all future bets that the registered client has made
	 * 
	 * @param rC Registered client who wants to see their bets
	 * @return the future bets
	 */
	@WebMethod
	public Vector<BetPredictionQuestionEventContainer> viewFutureBets(RegisteredClient rC) {
		Vector<BetPredictionQuestionEventContainer> betMovements = new Vector<BetPredictionQuestionEventContainer>();
		java.util.Date dtoday = new java.util.Date();
		Date dfrom;
		boolean gehitu;
		for (int i = 0; i < rC.getMovementsBet().size(); i++) {
			dbManager.open(false);
			BetPredictionQuestionEventContainer betCont = new BetPredictionQuestionEventContainer(
					dbManager.getBet(rC.getMovementsBet().get(i)));
			dbManager.close();
			gehitu = true;
			for (Prediction p : betCont.getPredictions()) {
				dfrom = p.getQuestion().getEvent().getEventDate();
				if (!dtoday.before(dfrom)) {
					gehitu = false;
				}
			}
			if (gehitu)
				betMovements.add(betCont);
		}
		return betMovements;
	}

	/**
	 * This method shows all the bets that the registered client has made in a range
	 * of dates
	 * 
	 * @param rC    Registered client who wants to see their bets
	 * @param date1 Start date
	 * @param date2 End date
	 * @return all the bets that the user has made by placing a range of dates
	 */
	@WebMethod
	public Vector<BetPredictionQuestionEventContainer> viewBets(RegisteredClient rC, Date date1, Date date2) {
		Vector<BetPredictionQuestionEventContainer> betMovements = new Vector<BetPredictionQuestionEventContainer>();
		for (int i = 0; i < rC.getMovementsBet().size(); i++) {
			dbManager.open(false);
			BetPredictionQuestionEventContainer betCont = new BetPredictionQuestionEventContainer(
					dbManager.getBet(rC.getMovementsBet().get(i)));
			dbManager.close();
			betMovements.add(betCont);
		}
		return betMovements;
	}

	/**
	 * This method show all the profits that the registered client has made without
	 * any date range
	 * 
	 * @param rC Registered client who wants to see their bets
	 * @return all the profits that the user has obtained without putting a date
	 *         range
	 */
	@WebMethod
	public Vector<BetPredictionQuestionEventContainer> viewAllProfits(RegisteredClient rC) {
		Vector<BetPredictionQuestionEventContainer> betMovements = new Vector<BetPredictionQuestionEventContainer>();
		for (int i = 0; i < rC.getMovementsBet().size(); i++) {
			dbManager.open(false);
			BetPredictionQuestionEventContainer betCont = new BetPredictionQuestionEventContainer(
					dbManager.getBet(rC.getMovementsBet().get(i)));
			dbManager.close();
			if (betCont.getBet().isWin() == true)
				betMovements.add(betCont);
		}
		return betMovements;
	}

	/**
	 * This method shows all the profits that the registered client has made in a
	 * range of dates
	 * 
	 * @param rC    Registered client who wants to see their bets
	 * @param date1 Start date
	 * @param date2 End date
	 * @return all the profits that the user has obtained by placing a range of
	 *         dates
	 */
	@WebMethod
	public Vector<BetPredictionQuestionEventContainer> viewProfits(RegisteredClient rC, Date date1, Date date2) {
		Vector<BetPredictionQuestionEventContainer> betMovements = new Vector<BetPredictionQuestionEventContainer>();
		for (int i = 0; i < rC.getMovementsBet().size(); i++) {
			dbManager.open(false);
			BetPredictionQuestionEventContainer betCont = new BetPredictionQuestionEventContainer(
					dbManager.getBet(rC.getMovementsBet().get(i)));
			dbManager.close();
			if (rC.getMovementsBet().get(i).isWin() == true) {
				betMovements.add(betCont);
			}
		}
		return betMovements;
	}

	/**
	 * This method invokes the data access to buy tokens.
	 * 
	 * @param quantityTokens, the quantity of tokens that the user wants to buy.
	 * @param logged,         the current logged registered client.
	 * @return the logged registered client
	 */
	@WebMethod
	public RegisteredClient buyTokens(int quantityTokens, Person registeredClient) throws NotEnoughMoney {
		dbManager.open(false);
		RegisteredClient rc = dbManager.buyTokens(quantityTokens, registeredClient);
		dbManager.close();
		return rc;
	}

	/**
	 * This method replicates all the future bets (bets that haven't happened yet)
	 * that have done a specific user.
	 * 
	 * @param email,            the email of the user from who we want to replicate.
	 * @param percentage,       in which percentage we want to pay what the other
	 *                          user has paid.
	 * @param registeredClient, the client who wants to replicate the bets.
	 * @throws NoUserFound: if there is no user with selected email,
	 *                      ReplicableNoBets: if the user that we want to replicate
	 *                      has no bets, NotReplicable: if the user that we want to
	 *                      replicate is not replicable. SelfReplicable: if the user
	 *                      tries to replicate herself/himself. AlreadyReplicated:
	 *                      if the user has the same bets as the other.
	 */
	@WebMethod
	public void replicateUser(String email, int percentage, RegisteredClient registeredClient)
			throws NoUserFound, ReplicableNoBets, NotReplicable, SelfReplicate, AlreadyReplicated {
		if (email.equals(registeredClient.getEmail())) {
			throw new SelfReplicate(); // You can't replicate yourself.
		} else {
			dbManager.open(false);
			RegisteredClient rc = dbManager.getRegisteredClientByEmail(email);
			RegisteredClient rG = dbManager.getRegisteredClientByEmail(registeredClient.getEmail());
			dbManager.close();
			if (rc == null) {
				throw new NoUserFound(); // There is no user with that email.
			} else if (rc.getHmap().containsKey(rG)) {
				throw new AlreadyReplicated(); // You have the same bets as the person u want to replicate.
			} else if (rc.getReplicable()) {
				Vector<BetPredictionQuestionEventContainer> bets = viewFutureBets(rc);
				if (bets.isEmpty()) {
					throw new ReplicableNoBets();// The user that we want to replicate has no bets.
				} else {
					dbManager.open(false);
					dbManager.replicateUser(percentage, registeredClient, bets, email);
					dbManager.close();// there is no error
				}
			} else {
				throw new NotReplicable(); // the user that we want to replicate is not replicable.
			}
		}
	}

	/**
	 * This method invokes the data access so a client can use a token to bet
	 * 
	 * @param b  Bet where the token is wanted to be used
	 * @param rc the client who wants to use a token
	 */
	@WebMethod
	public int useToken(Bet b, RegisteredClient rc) throws NoTokens, BetIsMultiple, BetIsLocked {
		try {
			dbManager.open(false);
			int us = dbManager.useToken(b, rc);
			dbManager.close();
			return us;
		} catch (NoTokens e) {
			throw new NoTokens();
		} catch (BetIsMultiple e) {
			throw new BetIsMultiple();
		} catch (BetIsLocked e) {
			throw e;
		}
	}

	/**
	 * This method create a new date
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return the date created
	 */
	@WebMethod
	public Date newDate(int year, int month, int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
}
