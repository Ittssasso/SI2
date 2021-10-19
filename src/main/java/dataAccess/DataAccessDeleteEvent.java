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
public class DataAccessDeleteEvent {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccessDeleteEvent(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccessDeleteEvent() {
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

				q5.addPrediction("Malaga", (float) 1.5);
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
			RegisteredClient p4 = new RegisteredClient("p1", "p2", newDate(2000, 9, 15), "11111111X", "a", "a",
					"1234567812345678", true);
			p3.setBalance(40);
			p4.setBalance(40);

			Vector<Prediction> pred1 = new Vector<Prediction>();
			pred1.addElement(pr1);
			Vector<Prediction> pred2 = new Vector<Prediction>();
			pred2.addElement(pr2);
			Vector<Prediction> pred3 = new Vector<Prediction>();
			pred3.addElement(pr3);
			pred3.addElement(pr1);
			// para probar replicateUser con apuesta mltiple:
			Event ev70 = new Event(70, "khazix vs yuumi", UtilDate.newDate(year, month, 17));
			Question q700 = new Question(700, "Who would win?", 1, ev70);
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
			p4.addMovements2(
					ResourceBundle.getBundle("Etiquetas").getString("ToBet") + ": "
							+ b3.getPredictions().get(0).getQuestion().getEvent().getDescription(),
					-b3.getMoney(), UtilDate.newDate(2021, 3, 2), "x");

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
			p3.addMovements2(
					ResourceBundle.getBundle("Etiquetas").getString("ToBet") + ": "
							+ b.getPredictions().get(0).getQuestion().getEvent().getDescription(),
					-b.getMoney(), UtilDate.newDate(2021, 3, 2), "-");
			p3.addMovements2(
					ResourceBundle.getBundle("Etiquetas").getString("ToBet") + ": "
							+ b1.getPredictions().get(0).getQuestion().getEvent().getDescription(),
					-b1.getMoney(), UtilDate.newDate(2021, 2, 25), "-");
			p3.addMovements2(
					ResourceBundle.getBundle("Etiquetas").getString("ToBet") + ": "
							+ b2.getPredictions().get(0).getQuestion().getEvent().getDescription(),
					-b2.getMoney(), UtilDate.newDate(2021, 0, 17), "x");

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
	 * This method closes the database
	 */
	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}


	/**
	 * This method delete a event
	 * 
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
					if (bet.getPredictions().size() == 1) {
						Movements m = rc1.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent") + ": " + bet.getPredictions().get(0).getQuestion().getEvent().getDescription(), +bet.getMoney(), new Date(), "-");
						db.persist(m);
					} else {
						Movements m2 = rc1.addMovements2(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent") + ": " + bet.getPredictions().get(0).getQuestion().getEvent().getDescription(), +bet.getMoney(), new Date(), "x");
						db.persist(m2);
					}
					rc1.setBalance(rc1.getBalance() + extra);
					// db.persist(rc1);
				}
			}
		}
		// delete the event
		db.remove(ev);
		db.getTransaction().commit();
	}
	
	@WebMethod
	public Date newDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}
