package test.businessLogic;

import java.util.Date;
import java.util.Vector;

import configuration.ConfigXML;
import domain.Bet;
import domain.Event;
import domain.Prediction;
import domain.Question;
import domain.RegisteredClient;
import test.dataAccess.TestDataAccess;

public class TestFacadeImplementation {
	TestDataAccess dbManagerTest;

	public TestFacadeImplementation() {

		System.out.println("Creating TestFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();
		dbManagerTest = new TestDataAccess();
		dbManagerTest.close();
	}

	public Event addEvent(String d, Date date) {
		dbManagerTest.open();
		Event ev = dbManagerTest.addEvent(d, date);
		dbManagerTest.close();
		return ev;
	}

	public boolean removeEvent(Event ev) {
		dbManagerTest.open();
		boolean b = dbManagerTest.removeEvent(ev);
		dbManagerTest.close();
		return b;

	}

	public Event addEventWithQuestion(String desc, Date d, String q, float qty) {
		dbManagerTest.open();
		Event o = dbManagerTest.addEventWithQuestion(desc, d, q, qty);
		dbManagerTest.close();
		return o;

	}
	
	public boolean removedEvent(Event ev) {
		dbManagerTest.open();
		boolean b = dbManagerTest.removedEvent(ev);
		dbManagerTest.close();
		return b;
	}
	
	public boolean removedBet(Bet ev) {
		dbManagerTest.open();
		boolean b = dbManagerTest.removedBet(ev);
		dbManagerTest.close();
		return b;
	}
	public boolean removeRegisteredClient(RegisteredClient ev) {
		dbManagerTest.open();
		boolean rc= dbManagerTest.removeRegisteredClient(ev);
		dbManagerTest.close();
		return rc;
	}
	public RegisteredClient addRegisteredClient(String name, String surname, Date birthDate, String dNI, String email, String password, String currentAccount, boolean replicable, int tokensAmount) {
		dbManagerTest.open();
		RegisteredClient o = dbManagerTest.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAccount, replicable, tokensAmount);
		dbManagerTest.close();
		return o;
	}
	
	public Question addQuestion(Event e, String question, float money) {
		dbManagerTest.open();
		Question q= dbManagerTest.addQuestion(e, question, money);
		dbManagerTest.close();
		return q;
	}
	
	public Prediction addPrediction(Question q, String prediction, float fee) {
		dbManagerTest.open();
		Prediction p= dbManagerTest.addPrediction(q, prediction, fee);
		dbManagerTest.close();
		return p;
	}
	
	public Bet addBet(Vector<Prediction> p, float money, RegisteredClient rc, boolean locked) {
		dbManagerTest.open();
		Bet b= dbManagerTest.addBet(p, money, rc, locked);
		dbManagerTest.close();
		return b;
	}
	
	public boolean existBet(Bet b) {
		dbManagerTest.open();
		boolean bet = dbManagerTest.existBet(b);
		dbManagerTest.close();
		return bet;
	}
	
	public boolean existRegisteredClient(RegisteredClient rC) {
		dbManagerTest.open();
		boolean rc = dbManagerTest.existRegisteredClient(rC);
		dbManagerTest.close();
		return rc;
	}
}

