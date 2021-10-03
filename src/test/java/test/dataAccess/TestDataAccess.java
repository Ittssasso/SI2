package test.dataAccess;

import java.util.Date;


import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Bet;
import domain.Event;
import domain.Movements;
import domain.Prediction;
import domain.Question;
import domain.RegisteredClient;

public class TestDataAccess {
	protected EntityManager db;
	protected EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public TestDataAccess() {

		System.out.println("Creating TestDataAccess instance");

		open();

	}

	public void open() {

		System.out.println("Opening TestDataAccess instance ");

		String fileName = c.getDbFilename();

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

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		if (ev != null) {
			try{
				db.getTransaction().begin();
				Event e = db.find(Event.class, ev.getEventNumber());
				db.remove(e);
				db.getTransaction().commit();
			}catch(Exception x) {x.printStackTrace(); }
			return true;
		} else
			return false;
	}
	
	public boolean removeRegisteredClient(RegisteredClient ev) {
		System.out.println(">> DataAccessTest: removeClient");
		if (ev != null) {
			try{
			db.getTransaction().begin();
			RegisteredClient e = db.find(RegisteredClient.class, ev.getEmail());
			
			db.remove(e);
			db.getTransaction().commit();
			}catch(Exception e2) {e2.printStackTrace();}

			return true;
		} else
			return false;
	}
	
	public boolean removeMovement(RegisteredClient ev) {
		System.out.println(">> DataAccessTest: removeMovement");
		RegisteredClient e = db.find(RegisteredClient.class, ev.getEmail());
		Vector<Movements> m = e.getMovements();
		if (m != null) {
			for(Movements mo : m) {
				db.getTransaction().begin();
				db.remove(mo);
				db.getTransaction().commit();
			}
			return true;
		} else
			return false;
	}

	public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
		System.out.println(">> DataAccessTest: addEvent");
		Event ev = null;
		db.getTransaction().begin();
		try {
			ev = new Event(desc, d);
			ev.addQuestion(question, qty);
			db.persist(ev);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}

	public Event addEvent(String desc, Date d) {
		System.out.println(">> DataAccessTest: addEvent");
		Event ev = null;
		db.getTransaction().begin();
		try {
			ev = new Event(desc, d);
			db.persist(ev);
			db.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ev;
	}

	public Question addQuestion(Event e, String question, float money) {
		System.out.println(">> DataAccessTest: addQuestion to an event");
		Event ev = null;
		Question q = null;
		db.getTransaction().begin();
		try {
			ev = db.find(Event.class, e.getEventNumber());
			q = ev.addQuestion(question, money);
			db.persist(q);
			db.persist(ev);
			db.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return q;
	}
	
	public Prediction addPrediction(Question q, String prediction, float fee) {
		System.out.println(">> DataAccessTest: addPrediction to a question");
		Question qu = null;
		Prediction p = null;
		db.getTransaction().begin();
		try {
			qu = db.find(Question.class, q.getQuestionNumber());
			p = qu.addPrediction(prediction, fee);
			db.persist(qu);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public Bet addBet(Vector<Prediction> p, float money, RegisteredClient rc, boolean locked) {
		System.out.println(">> DataAccessTest: addBet to a/some prediction(s)");
		Bet bet = null;
		Prediction pi2;
		db.getTransaction().begin();
		try {
			
			RegisteredClient rc1 = db.find(RegisteredClient.class, rc.getEmail());
			
			Vector<Prediction> pLag = new Vector<Prediction>();
			for (Prediction pi : p) {
				pi2 = db.find(Prediction.class, pi.getPredictionNumber());
				pLag.add(pi2);
			}
			
			//b = new Bet(money, pLag);
			//rc1.addBet(b);
			
			bet = rc1.addBet(money, pLag);
			bet.setLocked(locked);

			//b.setClient(rc1);

			for (Prediction pi : p) {
				pi = db.find(Prediction.class, pi.getPredictionNumber());
				pi.addBet(bet);
				//db.persist(pi);
			}


			db.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bet;
	}
	
	public RegisteredClient addRegisteredClient(String name, String surname, Date birthDate, String dNI, String email, String password, String currentAccount, boolean replicable, int tokensAmount) {
		System.out.println(">> DataAccessTest: addRegisteredClient");
		RegisteredClient rC = null;
		db.getTransaction().begin();
		try{
			rC = new RegisteredClient(name, surname, birthDate, dNI, email, password, currentAccount, true);
			rC.setTokens(tokensAmount);
			db.persist(rC);
			db.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return rC;
		
	}
	
	public boolean existEvent(Event ev) {
		System.out.println(">> DataAccessTest: existEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e != null) {
			return true;
		} else
			return false;
	}
	
	public boolean existQuestion(Event ev, Question q) {
		System.out.println(">> DataAccessTest: existQuestion");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e != null) {
			return e.DoesQuestionExists(q.getQuestion());
		} else
			return false;
	}
	
	public boolean existPrediction(Prediction p) {
		System.out.println(">> DataAccessTest: existPrediction");
		Prediction pr = db.find(Prediction.class, p.getPredictionNumber());
		if (pr!=null)
			return true;
		else
			return false;
	}
	

	public boolean existBet(Bet b) {
		System.out.println(">> DataAccessTest: existPrediction");
		Bet bet = db.find(Bet.class, b.getBetNumber());
		if (bet!=null)
			return true;
		else
			return false;
	}
	
	public boolean existRegisteredClient(RegisteredClient rC) {
		System.out.println(">> DataAccessTest: existPrediction");
		RegisteredClient rc = db.find(RegisteredClient.class, rC.getEmail());
		if (rc!=null)
			return true;
		else
			return false;
	}
	
	public boolean existsMovement(RegisteredClient rc, String d, float m, String n) {
		System.out.println(">> DataAccessTest: existsMovement");
		RegisteredClient rcl = db.find(RegisteredClient.class, rc.getEmail());
		Vector<Movements> move = rcl.getMovements();
		if(move!=null) {
			for(Movements ms: move) {
				if(ms.getDescription().equals(d) && ms.getMoney()==m && ms.getNumber().equals(n))
					return true;
			}
		}
		return false;
	}
	
	public boolean removedEvent(Event ev) {
		System.out.println(">> DataAccessTest: removedEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e != null) {
			return false;
		} else
			return true;
	}
	public boolean removedQuestion(Question ev) {
		System.out.println(">> DataAccessTest: removedQuestion");
		Question e = db.find(Question.class, ev.getQuestionNumber());
		if (e != null) {
			return false;
		} else
			return true;
	}
	public boolean removedPrediction(Prediction ev) {
		System.out.println(">> DataAccessTest: removedPrediction");
		Prediction e = db.find(Prediction.class, ev.getPredictionNumber());
		if (e != null) {
			return false;
		} else
			return true;
	}
	public boolean removedBet(Bet ev) {
		System.out.println(">> DataAccessTest: removedBet");
		Bet e = db.find(Bet.class, ev.getBetNumber());
		if (e != null) {
			return false;
		} else
			return true;
	}
	
	public RegisteredClient getRegisteredClient(String email) {
		System.out.println(">> DataAccessTest: getClient");
		RegisteredClient e = db.find(RegisteredClient.class, email);
		if (e != null) {
			return e;
		} else
			return null;
	}
	
	public boolean clientsBalance(RegisteredClient rc, float b) {
		System.out.println(">> DataAccessTest: getClient");
		RegisteredClient e = db.find(RegisteredClient.class, rc.getEmail());
		if (e != null) {
			if(e.getBalance()==b)
				return true;
		}
		return false;
	}

}
