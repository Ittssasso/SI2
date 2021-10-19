
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;
import org.junit.Test;
import dataAccess.DataAccessDeleteEvent;
import domain.Bet;
import domain.Event;
import domain.Prediction;
import domain.Question;
import domain.RegisteredClient;
import test.dataAccess.TestDataAccess;
public class DeleteEventDAW2Test {

	// sut:system under test
	static DataAccessDeleteEvent sut = new DataAccessDeleteEvent(true);

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	private Event ev, ev2;
	private RegisteredClient rc;

	@Test
	// sut.deleteEvent: The event has a question, this one a prediction, and this
	// one a bet. The bet is not multiple.
	public void test1() {
		try {
			System.out.println("TEST 1:");
			
			String event1 = "Real Sociedad - Athletic";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = null;
			Date otherDate = null;
			try {
				oneDate = sdf.parse("10/11/2021");
				otherDate = sdf.parse("12/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// configure the state of the system (create object in the dabatase)
			testDA.open();
			ev = testDA.addEvent(event1, oneDate);
			ev2 = testDA.addEvent("Osasuna - Alav�s", otherDate);
			Question q = testDA.addQuestion(ev,"Zenbat gol?", 5);
			Prediction p, p1;
			p = testDA.addPrediction(q, "3", new Float(1.3));

			p1 = testDA.addPrediction(q, "1", new Float(1.1));

			Vector<Prediction> pX = new Vector<Prediction>();
			pX.add(p1);
			Vector<Prediction> pY = new Vector<Prediction>();
			pY.add(p);
			rc = testDA.addRegisteredClient("oihane", "zaldua", oneDate, "dni", "email", "pasahitza", "kontua", false, 0);
			Bet b = testDA.addBet(pX, 10, rc, false);
			Bet b1 = testDA.addBet(pY, 6, rc, false);
			testDA.close();

			// invoke System Under Test (sut)
			sut.deleteEvent(ev);
			
			//ev, q, ... ez daude datu-basean. ev2 bai, baita movement berri bat ere.
			testDA.open();
			
			assertTrue(testDA.removedEvent(ev));
			assertTrue(testDA.removedQuestion(q));
			assertTrue(testDA.removedPrediction(p));
			assertTrue(testDA.removedPrediction(p1));
			assertTrue(testDA.removedBet(b));
			assertTrue(testDA.removedBet(b1));
			assertTrue(testDA.existEvent(ev2));
			assertTrue(testDA.existsMovement(rc, ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent") + ": " + event1, 10, "-"));
			assertTrue(testDA.existsMovement(rc, ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent") + ": " + event1, 6, "-"));
			assertTrue(testDA.clientsBalance(rc, 16));
			testDA.close();

		} catch (Exception e) {

			System.out.println(e.getMessage());
			fail();
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeEvent(ev2);
			boolean b2 = testDA.removeRegisteredClient(rc);

			testDA.close();
			System.out.println("Finally " + b + " " + b2);
		}
	}
	
//	@Test
//	// sut.deleteEvent: The event has a question, this one a prediction, and this
//	// one a bet. The bet is multiple.
//	public void test2() {
//		try {
//			System.out.println("TEST 2:");
//			
//			String event1 = "Real Sociedad - Athletic";
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			Date oneDate = null;
//			Date otherDate = null;
//			try {
//				oneDate = sdf.parse("10/11/2021");
//				otherDate = sdf.parse("12/11/2021");
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//
//			// configure the state of the system (create object in the dabatase)
//			testDA.open();
//			ev = testDA.addEvent(event1, oneDate);
//			ev2 = testDA.addEvent("Osasuna - Alav�s", otherDate);
//			Question q, q2;
//			q = testDA.addQuestion(ev,"Zenbat gol?", 5);
//			q2 = testDA.addQuestion(ev2, "Zenabt gol?", 3);
//			Prediction p, p1, p2;
//			p = testDA.addPrediction(q, "3", new Float(1.3));
//			p1 = testDA.addPrediction(q, "1", new Float(1.1));
//			p2 = testDA.addPrediction(q2, "0", new Float(1.2));
//			Vector<Prediction> pX = new Vector<Prediction>();
//			pX.add(p1);
//			pX.add(p2);
//			Vector<Prediction> pY = new Vector<Prediction>();
//			pY.add(p);
//			rc = testDA.addRegisteredClient("oihane", "zaldua", oneDate, "dni", "emaila", "pasahitza", "kontua", false, 0);
//			Bet b = testDA.addBet(pX, 10, rc, false);
//			Bet b1 = testDA.addBet(pY, 6, rc, false);
//			testDA.close();
//			
//			// invoke System Under Test (sut)
//			sut.deleteEvent(ev);
//			
//			//ev, q, ... ez daude datu-basean. ev2, q2 eta p2 bai, baita movement berri bat ere.
//			testDA.open();
//			assertTrue(testDA.removedEvent(ev));
//			assertTrue(testDA.removedQuestion(q));
//			assertTrue(testDA.removedPrediction(p));
//			assertTrue(testDA.removedPrediction(p1));
//			assertTrue(testDA.removedBet(b));
//			assertTrue(testDA.removedBet(b1));
//			assertTrue(testDA.existEvent(ev2));
//			assertTrue(testDA.existQuestion(ev2, q2));
//			assertTrue(testDA.existPrediction(p2));
//			assertTrue(testDA.existsMovement(rc, ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent") + ": " + event1, 10, "x"));
//			assertTrue(testDA.existsMovement(rc, ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent") + ": " + event1, 6, "-"));
//			assertTrue(testDA.clientsBalance(rc, 16));
//			testDA.close();
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail();
//		} finally {
//			// Remove the created objects in the database (cascade removing)
//			testDA.open();
//			//boolean b = testDA.removeEvent(ev2);
//			boolean b2 = testDA.removeRegisteredClient(rc);
//			testDA.close();
//			System.out.println("Finally " + b2);
//		}
//	}
//	
	@Test
	// sut.deleteEvent: The event has a question and this one a prediction.
	public void test3() {
		try {
			System.out.println("TEST 3:");
			
			String event1 = "Real Sociedad - Athletic";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = null;
			Date otherDate = null;
			try {
				oneDate = sdf.parse("10/11/2021");
				otherDate = sdf.parse("12/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// configure the state of the system (create object in the dabatase)
			testDA.open();
			ev = testDA.addEvent(event1, oneDate);
			ev2 = testDA.addEvent("Osasuna - Alav�s", otherDate);
			Question q;
			q = testDA.addQuestion(ev,"Zenbat gol?", 5);
			Prediction p, p1;
			p = testDA.addPrediction(q, "3", new Float(1.3));
			p1 = testDA.addPrediction(q, "1", new Float(1.1));
			testDA.close();

			// invoke System Under Test (sut)
			sut.deleteEvent(ev);
			
			//ev, q, ... ez daude datu-basean. ev2, q2 eta p2 bai, baita movement berri bat ere.
			testDA.open();
			assertTrue(testDA.removedEvent(ev));
			assertTrue(testDA.removedQuestion(q));
			assertTrue(testDA.removedPrediction(p));
			assertTrue(testDA.removedPrediction(p1));
			assertTrue(testDA.existEvent(ev2));
			testDA.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeEvent(ev2);
			testDA.close();
			System.out.println("Finally " + b);
		}
	}
	
	@Test
	// sut.deleteEvent: The event has a question
	public void test4() {
		try {
			System.out.println("TEST 4:");
			
			String event1 = "Real Sociedad - Athletic";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = null;
			Date otherDate = null;
			try {
				oneDate = sdf.parse("10/11/2021");
				otherDate = sdf.parse("12/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// configure the state of the system (create object in the dabatase)
			testDA.open();
			ev = testDA.addEvent(event1, oneDate);
			ev2 = testDA.addEvent("Osasuna - Alav�s", otherDate);
			Question q;
			q = testDA.addQuestion(ev,"Zenbat gol?", 5);
			testDA.close();

			// invoke System Under Test (sut)
			sut.deleteEvent(ev);
			
			//ev, q, ... ez daude datu-basean. ev2, q2 eta p2 bai, baita movement berri bat ere.
			testDA.open();
			assertTrue(testDA.removedEvent(ev));
			assertTrue(testDA.removedQuestion(q));
			assertTrue(testDA.existEvent(ev2));
			testDA.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeEvent(ev2);
			testDA.close();
			System.out.println("Finally " + b);
		}
	}
	@Test
	// sut.deleteEvent: The event has no question
	public void test5() {
		try {
			System.out.println("TEST 5:");
			
			String event1 = "Real Sociedad - Athletic";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = null;
			Date otherDate = null;
			try {
				oneDate = sdf.parse("10/11/2021");
				otherDate = sdf.parse("12/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// configure the state of the system (create object in the dabatase)
			testDA.open();
			ev = testDA.addEvent(event1, oneDate);
			ev2 = testDA.addEvent("Osasuna - Alav�s", otherDate);
			testDA.close();

			// invoke System Under Test (sut)
			sut.deleteEvent(ev);
			
			//ev, q, ... ez daude datu-basean. ev2, q2 eta p2 bai, baita movement berri bat ere.
			testDA.open();
			assertTrue(testDA.removedEvent(ev));
			assertTrue(testDA.existEvent(ev2));
			testDA.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeEvent(ev2);
			testDA.close();
			System.out.println("Finally " + b);
		}
	}
}