package dataAccess;

import java.util.Calendar;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Bet;
import exceptions.BetIsLocked;
import domain.BetPredictionQuestionEventContainer;
import domain.Event;
import domain.Movements;
import domain.Person;
import domain.Prediction;
import domain.Question;
import domain.RegisteredClient;
import domain.ReplicatedProperties;
import domain.Worker;
import exceptions.BetIsMultiple;
import exceptions.EventAlreadyExists;
import exceptions.EventFinished;
import exceptions.NoTokens;
import exceptions.NotEnoughMoney;
import exceptions.PredictionAlreadyExists;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));

			Event ev21 = new Event(21, "Athletic-Real Sociedad", UtilDate.newDate(2021, 3, 3));
			Event ev22 = new Event(22, "Barcelona-Celta", UtilDate.newDate(2021, 2, 27));
			Event ev23 = new Event(23, "Alavs-Cdiz", UtilDate.newDate(2021, 0, 18));
			Event ev24 = new Event(24, "Barcelona-Real Sociedad", UtilDate.newDate(2021, month, 30));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
			Question q7;
			Question q8;
			Question q9;
			Question q10;

			Prediction pr1;
			Prediction pr2;
			Prediction pr3;
			

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion(" Quién ganará el partido?", 1);
				q1.setEvent(ev1);
				q2 = ev1.addQuestion(" Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion(" Quién ganará el partido?", 1);
				q4 = ev11.addQuestion(" Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion(" Quién ganará el partido?", 1);
				q6 = ev17.addQuestion(" Habrá goles en la primera parte?", 2);

				q7 = ev21.addQuestion(" Quién ganará el partido?", 1);
				q7.setEvent(ev21);

				q8 = ev22.addQuestion(" Quién meterá el primer gol?", 2);
				q8.setEvent(ev22);

				q9 = ev23.addQuestion(" Cuántos goles se marcarán?", 2);
				q9.setEvent(ev23);
				
				q10 = ev24.addQuestion(" Cuántos goles se marcarán?", 2);
				q10.setEvent(ev24);

				q1.addPrediction("Atletico", (float) 1.5);
				q1.addPrediction("Athletic", (float) 1.1);
				q1.addPrediction("Draws", (float) 1.2);

				q7.addPrediction("Real Sociedad", (float) 1.5);
				q7.addPrediction("Athletic", (float) 1.1);
				q7.addPrediction("Draws", (float) 1.2);

				q8.addPrediction("Barcelona", (float) 2.0);
				q8.addPrediction("Celta", (float) 3.0);
				q8.addPrediction("Draws", (float) 3.5);

				q9.addPrediction("Alavs", (float) 1.5);
				q9.addPrediction("Cdiz", (float) 2.5);
				q9.addPrediction("Draws", (float) 2.0);
				
				q10.addPrediction("0", (float) 1.5);
				q10.addPrediction("1", (float) 1.3);
				q10.addPrediction("2", (float) 1.2);
				q10.addPrediction("3", (float) 1.1);
				q10.setBetMinimum((float) 2.2);

				pr1 = new Prediction("Real Sociedad", (float) 1.5, q7);
				pr2 = new Prediction("Celta", (float) 3.0, q8);
				pr3 = new Prediction("Cdiz", (float) 2.5, q9);
			} else if (Locale.getDefault().equals(new Locale("en"))) {

				q1 = ev1.addQuestion("Who will win the match?", 1);
				q1.setEvent(ev1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);

				q7 = ev21.addQuestion("Who will win the match?", 1);
				q7.setEvent(ev21);

				q8 = ev22.addQuestion("Who will score first?", 2);
				q8.setEvent(ev22);

				q9 = ev23.addQuestion("How many goals will be scored in the match?", 2);
				q9.setEvent(ev23);
				
				q10 = ev24.addQuestion("How many goals will be scored in the match?", 2);
				q10.setEvent(ev24);

				q1.addPrediction("Atletico", (float) 1.5);
				q1.addPrediction("Athletic", (float) 1.1);
				q1.addPrediction("Draws", (float) 1.2);

				q7.addPrediction("Real Sociedad", (float) 1.5);
				q7.addPrediction("Athletic", (float) 1.1);
				q7.addPrediction("Draws", (float) 1.2);

				q8.addPrediction("Barcelona", (float) 2.0);
				q8.addPrediction("Celta", (float) 3.0);
				q8.addPrediction("Draws", (float) 3.5);

				q9.addPrediction("Alavs", (float) 1.5);
				q9.addPrediction("Cdiz", (float) 2.5);
				q9.addPrediction("Draws", (float) 2.0);
				
				q10.addPrediction("0", (float) 1.5);
				q10.addPrediction("1", (float) 1.3);
				q10.addPrediction("2", (float) 1.2);
				q10.addPrediction("3", (float) 1.1);
				q10.setBetMinimum((float) 2.2);

				pr1 = new Prediction("Real Sociedad", (float) 1.5, q7);
				pr2 = new Prediction("Celta", (float) 3.0, q8);
				pr3 = new Prediction("Cdiz", (float) 2.5, q9);

			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q1.setEvent(ev1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

				q7 = ev21.addQuestion("Zeinek irabaziko du partidua?", 1);
				q7.setEvent(ev21);

				q8 = ev22.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q8.setEvent(ev22);

				q9 = ev23.addQuestion("Zenbat gol sartuko dira?", 2);
				q9.setEvent(ev23);
				
				q10 = ev24.addQuestion("Zenbat gol sartuko dira?", 2);
				q10.setEvent(ev24);

				q1.addPrediction("Atletico", (float) 1.5);
				q1.addPrediction("Athletic", (float) 1.1);
				q1.addPrediction("Draws", (float) 1.2);
				
				q5.addPrediction("Malaga",(float) 1.5);
				q5.addPrediction("Valencia", (float) 1.1);
				q5.addPrediction("Draws", (float) 1.0);

				q7.addPrediction("Real Sociedad", (float) 1.5);
				q7.addPrediction("Athletic", (float) 1.1);
				q7.addPrediction("Draws", (float) 1.2);

				q8.addPrediction("Barcelona", (float) 2.0);
				q8.addPrediction("Celta", (float) 3.0);
				q8.addPrediction("Draws", (float) 3.5);

				q9.addPrediction("Alavs", (float) 1.5);
				q9.addPrediction("Cdiz", (float) 2.5);
				q9.addPrediction("Draws", (float) 2.0);
				
				q10.addPrediction("0", (float) 1.5);
				q10.addPrediction("1", (float) 1.3);
				q10.addPrediction("2", (float) 1.2);
				q10.addPrediction("3", (float) 1.1);
				q10.setBetMinimum((float) 2.2);

				pr1 = new Prediction("Real Sociedad", (float) 1.5, q7);
				pr2 = new Prediction("Celta", (float) 3.0, q8);
				pr3 = new Prediction("Cdiz", (float) 2.5, q9);

			}
			
			q7.setResult(pr1.getPrediction());
			q8.setResult(pr2.getPrediction());
			q9.setResult("Draws");

			Person p1 = new Admin("admin", "bat", newDate(2001, 9, 15), "11111111X", "admin@email.com", "12345678",
					"1234567812345678");
			Person p2 = new Worker("langile", "bat", newDate(2001, 9, 18), "11111111X", "worker@email.com", "12345678",
					"1234567812345678");
			RegisteredClient p3 = new RegisteredClient("p1", "p2", newDate(2000, 9, 15), "11111111X",
					"client@email.com", "12345678", "1234567812345678", true);
			RegisteredClient p4= new RegisteredClient("p1", "p2", newDate(2000, 9, 15), "11111111X",
					"a", "a", "1234567812345678", true);
			p3.setBalance(40);
			p4.setBalance(40);

			Vector<Prediction> pred1 = new Vector<Prediction>();
			pred1.addElement(pr1);
			Vector<Prediction> pred2 = new Vector<Prediction>();
			pred2.addElement(pr2);
			Vector<Prediction> pred3 = new Vector<Prediction>();
			pred3.addElement(pr3);
			pred3.addElement(pr1);
			//para probar replicateUser con apuesta mltiple:
			Event ev70= new Event(70, "khazix vs yuumi", UtilDate.newDate(year, month, 17));
			Question q700= new Question(700, "Who would win?",  1, ev70);
			Prediction pr6 = new Prediction("Khazix", (float) 1.1, q700);
			Prediction pr7 = new Prediction("Yuumi", 3, q700);
			Vector<Prediction> predictionsKY = new Vector<Prediction>();
			predictionsKY.add(pr6);
			predictionsKY.add(pr7);
			q700.setPredictions(predictionsKY);
			Vector<Prediction> predic4 = new Vector<Prediction>();
			predic4.add(pr1);
			predic4.add(pr2);
			predic4.add(pr3);
			predic4.add(pr7);
			Bet b3 = new Bet(50, predic4);
			b3.setClient(p4);
			b3.setWin(true);
			p4.addBet(b3);
			p4.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("ToBet")+": "+b3.getPredictions().get(0).getQuestion().getEvent().getDescription(), -b3.getMoney(),  UtilDate.newDate(2021, 3, 2), "x");

			
			Bet b = new Bet(3, pred1);
			Bet b1 = new Bet(2, pred2);
			Bet b2 = new Bet(4, pred3);
			Vector<Prediction> predic10 = new Vector<Prediction>();
			predic10.add(pr7);
			Bet b10 = new Bet(5, predic10);
			b10.setClient(p3);
			p3.addBet(b10);
			b.setClient(p3);
			b.setWin(true);
			b1.setClient(p3);
			b1.setWin(true);
			b2.setClient(p3);
			b2.setWin(false);
			p3.addBet(b);
			p3.addBet(b1);
			p3.addBet(b2);
			p3.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("ToBet")+": "+b.getPredictions().get(0).getQuestion().getEvent().getDescription(), -b.getMoney(),  UtilDate.newDate(2021, 3, 2),"-");
			p3.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("ToBet")+": "+b1.getPredictions().get(0).getQuestion().getEvent().getDescription(), -b1.getMoney(),   UtilDate.newDate(2021, 2, 25), "-");
			p3.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("ToBet")+": "+b2.getPredictions().get(0).getQuestion().getEvent().getDescription(), -b2.getMoney(),   UtilDate.newDate(2021, 0, 17), "x");
			
			
			q7.setResult(pr1.getPrediction());
			q8.setResult(pr2.getPrediction());
			q9.setResult(pr3.getPrediction());

			db.persist(p1);
			db.persist(p2);
			db.persist(p3);
			db.persist(p4);

			db.persist(pr1);
			db.persist(pr2);
			db.persist(pr3);
			db.persist(pr7);



			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);
			db.persist(q7);
			db.persist(q8);
			db.persist(q9);
			db.persist(q700);
			db.persist(q10);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);
			db.persist(ev21);
			db.persist(ev22);
			db.persist(ev23);
			db.persist(ev70);
			
			db.persist(ev24);
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);

		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
						// property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	/**
	 * This method searches the person who is trying to log in in the database
	 * 
	 * @param email from the person who is trying to log in
	 * @param pw    password for that account
	 * @return the person who logged in, or null in case the email is not found or
	 *         the password is incorrect
	 */
	public Person isLogin(String email, String pw) {
		System.out.println(">> DataAccess: getEvents");
		TypedQuery<Person> query = db.createQuery("SELECT p FROM Person p WHERE p.email=?1 AND p.password=?2",
				Person.class);
		query.setParameter(1, email);
		query.setParameter(2, pw);
		List<Person> p = query.getResultList();
		if (p.isEmpty())
			return null;
		else {
			return p.get(0);
		}
	}

	/**
	 * This method stores the client into the system or database
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
	public boolean storeRegisteredClient(String name, String surname, Date birthDate, String DNI, String email,
			String password, String currentAccount, boolean replicable) {

		db.getTransaction().begin();
		TypedQuery<Person> query = db.createQuery("SELECT p FROM Person p WHERE p.email=?1 OR p.DNI=?2", Person.class);
		query.setParameter(1, email);
		query.setParameter(2, DNI);
		List<Person> p = query.getResultList();
		Person rc = null;
		if (p.isEmpty()) {
			rc = new RegisteredClient(name, surname, birthDate, DNI, email, password, currentAccount, replicable);
			db.persist(rc);
		}
		db.getTransaction().commit();
		return rc==null;
	}

	/**
	 * This method creates a new event, with two teams and a date, storing it in the
	 * database
	 * 
	 * @param t1 home-playing team
	 * @param t2 outsider team
	 * @param d  game's date
	 */
	public Event createEvent(String teams, Date eventDate) throws EventAlreadyExists {
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, eventDate);
		List<Event> events = query.getResultList();
		boolean doesExists = false;
		int i = 0;
		while (i < events.size() && !doesExists) {
			String s = events.get(i).getDescription();
			if (s.equalsIgnoreCase(teams)) {
				doesExists = true;
			}
			i++;
		}
		if (!doesExists) {
			db.getTransaction().begin();
			Integer numberMax = (Integer) db.createQuery("SELECT MAX(eventNumber) From Event").getSingleResult();
			Event ev = new Event(numberMax + 1, teams, eventDate);
			db.persist(ev);
			db.getTransaction().commit();
			return ev;
		} else
			throw new EventAlreadyExists();
		
	}

	/**
	 * This method opens the database so it can be updated
	 * 
	 * @param initializeMode
	 */
	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	/**
	 * This method creates a new prediction for a question
	 * 
	 * @param question
	 * @param prediction
	 * @param fee
	 */
	public void createPrediction(Question question, String prediction, float fee) throws PredictionAlreadyExists {
		boolean exists = question.doesPredictionExist(prediction);
		if (!exists) {
			db.getTransaction().begin();
			Question q = db.find(Question.class, question.getQuestionNumber());
			q.addPrediction(prediction, fee);
			db.persist(q);
			db.getTransaction().commit();
		} else
			throw new PredictionAlreadyExists(
					ResourceBundle.getBundle("Etiquetas").getString("ErrorPredictionAlreadyExists"));
	}

	/**
	 * This method puts the result (the right prediction) of a question
	 * 
	 * @param prediction
	 */
	public void putResult(Question question, Prediction prediction) {
		db.getTransaction().begin();
		Question q = db.find(Question.class, question.getQuestionNumber());
		q.setResult(prediction.getPrediction());
		Vector<Bet> bets = prediction.getBets();
		for (int i = 0; i < bets.size(); i++) { //predikzioko apustu bakoitzeko
			Bet b = db.find(Bet.class, bets.get(i).getBetNumber());
			RegisteredClient rc = db.find(RegisteredClient.class, b.getClient().getEmail());
			float money = b.getMoney(); //apustatutako ditu kantitatea
			Vector<Prediction> ps = b.getPredictions();
			RegisteredClient replicatedClient=null;
			if(b.getReplicatedClient()!=null) {//apustua erreplikatutako bezero baten erreplika bada
				replicatedClient = b.getReplicatedClient();
			}
			if(b.getMultiple()) { //apustu anitza bada
				boolean multipleWin = true;
				float totalFee=1;
				for(int j=0; j<ps.size(); j++) {
					if(ps.get(j).getQuestion().getResult()==null || !ps.get(j).getQuestion().getResult().equals(ps.get(j).getPrediction())) multipleWin=false;
					totalFee=totalFee*ps.get(j).getFee();
				}
				if (multipleWin) { //apustu anitza irabazi badu
					if(replicatedClient!=null) { //apustua erreplikatua bazen, bezero originalari portzentai bat eman
						replicatedClient.setBalance(replicatedClient.getBalance() + money*totalFee*(float)(0.1));
						rc.setBalance(rc.getBalance() + money*totalFee*(float)(0.9));
						rc.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("BetWon")+": "+b.getPredictions().get(0).getQuestion().getEvent().getDescription(), rc.getBalance() + money*totalFee*(float)(0.9), new Date(), "x");
						replicatedClient.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("BetWon")+": "+b.getPredictions().get(0).getQuestion().getEvent().getDescription(), replicatedClient.getBalance() + money*totalFee*(float)(0.1), new Date(), "x");
					}else{
						rc.setBalance(rc.getBalance() + money*totalFee);
						rc.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("BetWon")+": "+b.getPredictions().get(0).getQuestion().getEvent().getDescription(), rc.getBalance() + money*totalFee, new Date(), "x");

					}
					b.setWin(true);
				}
			} else { //apustu sinplea
				float new_balance = money * prediction.getFee() * b.getFeeMultiplier();
				if(replicatedClient!=null) { //apustua erreplikatua bazen, bezero originalari portzentai bat eman
					replicatedClient.setBalance(replicatedClient.getBalance() + new_balance*(float)(0.1));
					rc.setBalance(rc.getBalance() + new_balance*(float)(0.9));
					rc.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("BetWon")+": "+b.getPredictions().get(0).getQuestion().getEvent().getDescription(),rc.getBalance() + new_balance*(float)(0.9), new Date(), "-");
					replicatedClient.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("BetWon")+": "+b.getPredictions().get(0).getQuestion().getEvent().getDescription(), replicatedClient.getBalance() + money*(float)(0.1), new Date(), "-");

				}else{
					rc.setBalance(rc.getBalance() + new_balance);
					rc.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("BetWon")+": "+b.getPredictions().get(0).getQuestion().getEvent().getDescription(),rc.getBalance() + new_balance, new Date(), "-");

				}
				b.setWin(true);
			}
			db.persist(rc);
			db.persist(b);
		}
		db.persist(q);
		db.getTransaction().commit();
	}

	
	/**
	 * This method create a bet from a prediction
	 * @param p prediction of question
	 * @param m money to bet with
	 * @param u registered client who bet
	 * @param evdescription event description
	 * @return boolean, true if the bet has been created successfully, false if not
	 * @throws EventFinished 
	 */
	@WebMethod
	public boolean createBet(Vector<Prediction> p, float m, RegisteredClient u, String evdescription) {
		Boolean r = false;
		db.getTransaction().begin();
		Vector<Prediction> pLag = new Vector<Prediction>();
		for (Prediction pi : p) {
			pi = db.find(Prediction.class, pi.getPredictionNumber());
			pLag.add(pi);
		}
		Bet b = new Bet(m, pLag);
		RegisteredClient rG = db.find(RegisteredClient.class, u.getEmail());
		rG.setBalance(rG.getBalance() - m);
		rG.addBet(b);
		if(b.getPredictions().size()==1) {		
			rG.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("ToBet")+": "+b.getPredictions().get(0).getQuestion().getEvent().getDescription(), -b.getMoney(), new Date(), "-");
		}else {
			for(Prediction pre: b.getPredictions()) {
			rG.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("ToBet")+": "+pre.getQuestion().getEvent().getDescription(), -b.getMoney(), new Date(), "x");
			}
		}

		b.setClient(rG);
		for (Prediction pi : p) {
			pi = db.find(Prediction.class, pi.getPredictionNumber());
			pi.addBet(b);
			db.persist(pi);
		}
		r = true;
		System.out.println(db.find(RegisteredClient.class, u.getEmail()).getMovements().size());
		db.getTransaction().commit();
		HashMap<RegisteredClient, ReplicatedProperties> h = rG.getHmap();
		Set<Entry<RegisteredClient, ReplicatedProperties>> it = h.entrySet();
		RegisteredClient rC;
		Bet bR;
		float mR;
		for(Entry<RegisteredClient, ReplicatedProperties> en : it) {
			rC = db.find(RegisteredClient.class, en.getKey().getEmail());
			mR = m*en.getValue().getPercentage()/100;
			bR = new Bet(mR, p);
			rC.setBalance(rC.getBalance() - mR);
			rC.addBet(bR);
			bR.setClient(rC);
			for (Prediction pi : p) {
				pi = db.find(Prediction.class, pi.getPredictionNumber());
				pi.addBet(bR);
				db.persist(pi);
			}
		}
		return r;
	}

	/**
     * This method delete a bet from a prediction
     * @param b
     * @param u
     * @return boolean, true if the bet has been deleted successfully, false if not
     */
	@WebMethod
	public boolean deleteBet(Bet b, RegisteredClient u) {
		Boolean r = false;
		db.getTransaction().begin();
		Bet bet = db.find(Bet.class, b.getBetNumber());
		db.remove(bet);
		RegisteredClient rG = db.find(RegisteredClient.class, u.getEmail());
		rG.setBalance(rG.getBalance() + b.getMoney());
		if(bet.getPredictions().size()==1) {		
			rG.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("deleteBet")+": "+bet.getPredictions().get(0).getQuestion().getEvent().getDescription(), +bet.getMoney(), new Date(), "-");
		}else {
			for(Prediction pre: bet.getPredictions())
				rG.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("deleteBet")+": "+pre.getQuestion().getEvent().getDescription(), +bet.getMoney(), new Date(), "x");
		}
		db.persist(rG);
		r = true;
		db.getTransaction().commit();
		return r;
	}

	/**
	 * This method closes the database
	 */
	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	/**
	 * This method search if question exists
	 * @param event
	 * @param question
	 * @return true if question exists, else false.
	 */
	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);
	}

	/**
	 * This method get registered client
	 * @param u
	 * @return the registered client who wanted to obtain
	 */
	public RegisteredClient getRegisteredClient(RegisteredClient rG) {
		db.getTransaction().begin();
		RegisteredClient r = db.find(RegisteredClient.class, rG.getEmail());
		System.out.println(r.getMovements().size());
		db.getTransaction().commit();
		return r;
	}

	/**
	 * This method delete a event
	 * @param event to be deleted
	 */
	public void deleteEvent(Event event) {
		db.getTransaction().begin();
		Event ev = db.find(Event.class, event.getEventNumber());
		Vector<Question> questions = event.getQuestions();
		for (Question quest : questions) {
			for (Prediction predict : quest.getPredictions()) {
				for (Bet bet : predict.getBets()) {
					// return the money if any person has bet in any deleted question
					Bet b1 = db.find(Bet.class, bet.getBetNumber());
					RegisteredClient rc1 = db.find(RegisteredClient.class, b1.getClient().getEmail());
					float extra = bet.getMoney();
					if(bet.getPredictions().size()==1) {
						Movements m=rc1.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent")+": "+bet.getPredictions().get(0).getQuestion().getEvent().getDescription(), +bet.getMoney(), new Date(), "-");
						db.persist(m);
					}else {
						Movements m2=rc1.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent")+": "+bet.getPredictions().get(0).getQuestion().getEvent().getDescription(), +bet.getMoney(), new Date(), "x");
						db.persist(m2);
					}
					rc1.setBalance(rc1.getBalance() + extra);
					//db.persist(rc1);
				}
			}
		}
		// delete the event
		db.remove(ev);
		db.getTransaction().commit();
	}

	/**
	 * This method insert money to a registered client
	 * @param money that goes to insert
	 * @param logged registered client to whom the money is going to be put
	 * @return the registered client to whom the money has been put
	 */
	public RegisteredClient insertMoney(float money, Person registeredClient) {
		db.getTransaction().begin();
		RegisteredClient rc = db.find(RegisteredClient.class, registeredClient.getEmail());
		rc.setBalance(rc.getBalance() + money);
		rc.addMovements(ResourceBundle.getBundle("Etiquetas").getString("InsertMoney"), +money);
		db.persist(rc);
		db.getTransaction().commit();
		return rc;
	}
	
	/**
	 * This method invokes the data access to buy tokens.
	 * @param quantityTokens, the quantity of tokens that the user wants to buy.
	 * @param logged, the current logged registered client.
	 * @return the logged registered client
	 */
	public RegisteredClient buyTokens(int quantityTokens, Person registeredClient) throws NotEnoughMoney {
		db.getTransaction().begin();
		RegisteredClient rc = db.find(RegisteredClient.class, registeredClient.getEmail());
		if(rc.getBalance()<(quantityTokens*5)) {
			throw new NotEnoughMoney();
		}else {
			rc.setTokens(rc.getTokens() + quantityTokens);
			rc.setBalance(rc.getBalance()-(quantityTokens*5));
			java.util.Date dToday= new java.util.Date();
			rc.addMovements1(ResourceBundle.getBundle("Etiquetas").getString("ShopGUI_buyTokens_button")+": "+quantityTokens, -quantityTokens*(float)5, dToday);
			db.persist(rc);
		}
		db.getTransaction().commit();
		return rc;
	}
/**
	 * This method returns a registered client passing his/her email.
	 * @param email
	 * @return RegisteredClient
	 */
	public RegisteredClient getRegisteredClientByEmail(String email) {
		return db.find(RegisteredClient.class, email);
	}
	
	/**
	 * This method replicates all the future bets (bets that haven't happened yet) that have done a specific user.
	 * @param percentage, in which percentage we want to pay what the other user has paid.
	 * @param registeredClientJ, the client who wants to replicate the bets.
	 * @param bets, all the bets that will be replicated.
	 * @param email, the email of the user from who we want to replicate.
	 */
	public void replicateUser(int percentage, RegisteredClient registeredClientJ, Vector<BetPredictionQuestionEventContainer> bets, String email) {
		db.getTransaction().begin();
		RegisteredClient rc = getRegisteredClientByEmail(email);
		RegisteredClient registeredClient = db.find(RegisteredClient.class, registeredClientJ.getEmail());
		ReplicatedProperties percentageValue = new ReplicatedProperties(percentage);
		rc.addHash(registeredClient, percentageValue);
		Vector<Prediction> pVi;
		Prediction pi;
		Bet bet;
		for(int i=0; i<bets.size(); i++) {
			registeredClient.setBalance(registeredClient.getBalance()-bets.get(i).getBet().getMoney()*percentage/100);
			System.out.println(registeredClient.getBalance());
			pVi = db.find(Bet.class, bets.get(i).getBet().getBetNumber()).getPredictions();
			bet = new Bet(bets.get(i).getBet().getMoney()*percentage/100, pVi);
			bet.setClient(registeredClient);
			bet.setReplicatedClient(rc);
			registeredClient.addBet(bet);
			for(int j=0; j<bets.get(i).getPredictions().size(); j++) {
				Vector<Prediction> prediction = bets.get(i).getPredictions();
				pi = db.find(Prediction.class, prediction.get(j).getPredictionNumber());
				java.util.Date dToday= new java.util.Date();
				registeredClient.addMovements1(ResourceBundle.getBundle("Etiquetas").getString("ToBet")+": "+bet.getPredictions().get(j).getQuestion().getEvent().getDescription(), -bet.getMoney(),  dToday);
				pi.addBet(bet);
				db.persist(pi);
			}
			db.persist(bet);
			db.persist(registeredClient);
		}
		db.persist(percentageValue);
		db.getTransaction().commit();
	}

	
	@WebMethod
	public Date newDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * This method get the bet
	 * @param bet who wants to get
	 * @return the bet who wants
	 */
	public Bet getBet(Bet bet) {
		db.getTransaction().begin();
		Bet b = db.find(Bet.class, bet.getBetNumber());
		db.getTransaction().commit();
		return b;
	}
	
	/**
	 * This method looks for the tokens of the client to be used in a bet
	 * @param b Bet where the token is wanted to be used
	 * @param rc the client who wants to use a token
	 * @throws BetIsMultiple if the bet where the token is wanted to be used is multiple
	 * @throws NoTokens if the client has no tokens
	 */
	public int useToken(Bet b, RegisteredClient rc) throws BetIsMultiple, NoTokens, BetIsLocked {

		db.getTransaction().begin();
		Bet bet = db.find(Bet.class, b.getBetNumber());
		RegisteredClient client = db.find(RegisteredClient.class, rc.getEmail());
		int tokenAmount = client.getTokens();
		if (tokenAmount <= 0) {
			db.getTransaction().commit();
			throw new NoTokens();
		} else if (bet.getLocked()) {
			db.getTransaction().commit();
			throw new BetIsLocked();
		} else if (bet.getMultiple()) {
			db.getTransaction().commit();
			throw new BetIsMultiple();
		} else {
			bet.setFeeMultiplier();
			client.setTokens(tokenAmount - 1);
			db.persist(client);
			db.persist(bet);
			db.getTransaction().commit();
			return client.getTokens();
		}

	}

	public Prediction getPrediction(Prediction pred) {
		db.getTransaction().begin();
		Prediction p = db.find(Prediction.class, pred.getPredictionNumber());
		db.getTransaction().commit();
		return p;
	}
}
