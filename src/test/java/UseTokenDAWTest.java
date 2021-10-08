import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import org.junit.Test;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Event;
import domain.Prediction;
import domain.Question;
import domain.RegisteredClient;
import exceptions.BetIsLocked;
import exceptions.BetIsMultiple;
import exceptions.NoTokens;
import test.dataAccess.TestDataAccess;

public class UseTokenDAWTest {

	// sut:system under test
	static DataAccess sut = new DataAccess(true);

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	private RegisteredClient rC;
	private Event ev;
	private Bet b;
	private Question q;
	private Prediction p;


	@Test
	//sut.UseToken: NoTokens exception. (tokensAmount=0).
	public void test1() {
		try {

			System.out.println("");
			System.out.println("TEST1:");

			//Define parameters.
			String name = "Alex";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "client1@email.com";
			String password = "12345678";
			String currentAcount = "1234567812345678";
			boolean replicable = true;
			int tokensAmount = 0;
			boolean locked = false;

			String event = "Barça - Sevilla";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = sdf.parse("10/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			testDA.open();
			ev = testDA.addEvent(event, date);
			q = testDA.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testDA.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testDA.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testDA.addBet(prediction, 17, rC, locked);
			testDA.close();

			//Invoke sut.
			sut.useToken(b, rC);

		} catch (NoTokens e) {
			assertTrue(true);
		} catch (BetIsLocked e) {
			fail();
		} catch (BetIsMultiple e) {
			fail();
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean registeredClient = testDA.removeRegisteredClient(rC);
			boolean bet = testDA.removedBet(b);
			testDA.close();
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}

	}

	
	@Test
	//sut.UseToken: BetIsLocked exception. (locked=true).
	public void test2() {
		try {

			System.out.println("");
			System.out.println("TEST2");

			//Define parameters.
			String name = "Maitena";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "client2@email.com";
			String password = "12345678";
			String currentAcount = "1234567812345678";
			boolean replicable = true;
			int tokensAmount = 5;
			boolean locked = true;

			String event = "Barça - Sevilla";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = sdf.parse("10/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			testDA.open();
			ev = testDA.addEvent(event, date);
			q = testDA.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testDA.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testDA.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testDA.addBet(prediction, 17, rC, locked);
			testDA.close();
			
			//Invoke sut.
			sut.useToken(b, rC);

		} catch (NoTokens e) {
			fail();
		} catch (BetIsLocked e) {
			assertTrue(true);
		} catch (BetIsMultiple e) {
			fail();
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean registeredClient = testDA.removeRegisteredClient(rC);
			boolean bet = testDA.removedBet(b);
			testDA.close();
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}

	}

	
	@Test
	//sut.UseToken: BetIsMultiple exception. (prediction.size()>1).
	public void test3() {
		try {

			System.out.println("");
			System.out.println("TEST3:");

			//Define parameters.
			String name = "Nerea";
			String surname = "Garcia";
			Date birthDate = new Date();
			String dNI = "12345678X";
			String email = "client3@email.com";
			String password = "12335678";
			String currentAcount = "1234537812345678";
			boolean replicable = true;
			int tokensAmount = 5;
			boolean locked = false;

			String event = "Barça - Sevilla";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = sdf.parse("10/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			testDA.open();
			ev = testDA.addEvent(event, date);
			q = testDA.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testDA.addPrediction(q, "Barça", (float) 1.5);
			Prediction p1 = testDA.addPrediction(q, "Sevilla", (float) 2);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			prediction.add(p1);
			rC = testDA.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testDA.addBet(prediction, 17, rC, locked);
			testDA.close();
			
			//Invoke sut.
			sut.useToken(b, rC);

		} catch (NoTokens e) {
			fail();
		} catch (BetIsLocked e) {
			fail();
		} catch (BetIsMultiple e) {
			assertTrue(true);
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean registeredClient = testDA.removeRegisteredClient(rC);
			boolean bet = testDA.removedBet(b);
			testDA.close();
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}
	}

	
	@Test
	//sut.UseToken: Token bat kendu registered client-eri. (UseToken metodoa ondo dabil).
	public void test4() throws NoTokens, BetIsMultiple {
		try {

			System.out.println("");
			System.out.println("TEST4:");

			//Define parameters.
			String name = "Idoia";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "clien4t@email.com";
			String password = "12345678";
			String currentAcount = "1234567812345678";
			boolean replicable = true;
			int tokensAmount = 5;
			boolean locked = false;

			String event = "Barça - Sevilla";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = sdf.parse("10/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			testDA.open();
			ev = testDA.addEvent(event, date);
			q = testDA.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testDA.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testDA.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testDA.addBet(prediction, 17, rC, locked);
			testDA.close();

			int esperoEm = 4;
			//Invoke sut.
			int sutEm = sut.useToken(b, rC);
			
			//Verify the result.
			assertEquals(esperoEm, sutEm);

		} catch (NoTokens e) {
			fail();
		} catch (BetIsLocked e) {
			fail();
		} catch (BetIsMultiple e) {
			fail();
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean registeredClient = testDA.removeRegisteredClient(rC);
			boolean bet = testDA.removedBet(b);
			testDA.close();
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}
	}
}
