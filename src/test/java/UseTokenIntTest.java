import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Event;
import domain.Prediction;
import domain.Question;
import domain.RegisteredClient;
import exceptions.BetIsLocked;
import exceptions.BetIsMultiple;
import exceptions.NoTokens;
import test.businessLogic.TestFacadeImplementation;

public class UseTokenIntTest {

	static BLFacadeImplementation sut;
	static TestFacadeImplementation testBL;

	private RegisteredClient rC;
	private Event ev;
	private Bet b;
	private Question q;
	private Prediction p;

	@BeforeClass
	public static void setUpClass() {
		
		DataAccess da = new DataAccess(false);

		sut = new BLFacadeImplementation(da);

		testBL = new TestFacadeImplementation();
	}
	
	@Test
	// sut.UseToken: The bet and the registered client are in the database.
	public void test1() throws BetIsMultiple, NoTokens, BetIsLocked {
		try {

			System.out.println("TEST1:");
			System.out.println("");

			// Define parameters.
			String name = "Alex";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "client1@email.com";
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

			ev = testBL.addEvent(event, date);
			q = testBL.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testBL.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testBL.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testBL.addBet(prediction, 17, rC, locked);

			int esperoEm = 4;
			//Invoke sut.
			int em=sut.useToken(b, rC);
			//Verify.
			assertEquals(esperoEm,em);
			

			boolean exist = testBL.existRegisteredClient(rC);
			boolean exist1 = testBL.existBet(b);

			// Verify.
			assertTrue(exist);
			assertTrue(exist1);

		} finally {
			// Remove the created objects in the database (cascade removing)
			boolean registeredClient = testBL.removeRegisteredClient(rC);
			boolean bet = testBL.removedBet(b);
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}
	}
	
	@Test
	// sut.UseToken: The bet is not in the database.
	public void test2() {
		try {

			System.out.println("TEST2:");
			System.out.println("");

			// Define parameters.
			String name = "Nerea";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "client2@email.com";
			String password = "12345678";
			String currentAcount = "1234567812345678";
			boolean replicable = true;
			int tokensAmount = 5;
			int money = 27;

			String event = "Barça - Sevilla";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = sdf.parse("10/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ev = testBL.addEvent(event, date);
			q = testBL.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testBL.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testBL.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = rC.addBet(money, prediction);
			
			//Invoke sut.
			sut.useToken(b, rC);
			fail();

		} catch (Exception e) {
			assertTrue(true);
		} finally {
			// Remove the created objects in the database (cascade removing)
			boolean registeredClient = testBL.removeRegisteredClient(rC);
			System.out.println("Registered client ondo ezabatu da: " + registeredClient);
		}
	}

	@Test
	// sut.UseToken: The registered client is not in the database.
	public void test3() {
		try {

			System.out.println("TEST3:");
			System.out.println("");

			// Define parameters.
			String name = "Enara";
			String surname = "Bela";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "client3@email.com";
			String password = "12345678";
			String currentAcount = "1234567812345678";
			boolean replicable = true;

			rC = new RegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable);

			//Invoke sut.
			sut.useToken(b, rC);
			fail();

		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	// sut.UseToken: Bet is null.
	public void test4() {
		try {

			System.out.println("TEST4");
			System.out.println("");

			// Define paramaters
			String name = "Ander";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "client4@email.com";
			String password = "12345678";
			String currentAcount = "1234567812345678";
			boolean replicable = true;
			int tokensAmount = 5;

			String event = "Barça - Sevilla";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = sdf.parse("10/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ev = testBL.addEvent(event, date);
			q = testBL.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testBL.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testBL.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			
			//Invoke sut.
			sut.useToken(null, rC);
			fail();

		} catch (Exception e) {
			assertTrue(true);
		} finally {
			// Remove the created objects in the database (cascade removing)
			boolean registeredClient = testBL.removeRegisteredClient(rC);
			System.out.println("Registered client ondo ezabatu da: " + registeredClient);
		}

	}

	@Test
	// sut.UseToken: Registered client is null.
	public void test5() {
		try {

			System.out.println("");
			System.out.println("TEST5:");

			sut.useToken(b, null);
			fail();

		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	//sut.UseToken: NoTokens exception. (tokensAmount=-7).
	public void test6() {
		try {

			System.out.println("");
			System.out.println("TEST6:");

			//Define parameters.
			String name = "Alexandra";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "client6@email.com";
			String password = "12345678";
			String currentAcount = "1234567812345678";
			boolean replicable = true;
			int tokensAmount = -7;
			boolean locked = false;

			String event = "Barça - Sevilla";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = sdf.parse("10/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ev = testBL.addEvent(event, date);
			q = testBL.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testBL.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testBL.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testBL.addBet(prediction, 17, rC, locked);
			
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
			boolean registeredClient = testBL.removeRegisteredClient(rC);
			boolean bet = testBL.removedBet(b);
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}
	}

	@Test
	//sut.UseToken: BetIsLocked exception. (locked==true).
	public void test7() {
		try {

			System.out.println("");
			System.out.println("TEST7:");

			//Define parameters.
			String name = "Alex";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "client18@email.com";
			String password = "12345678";
			String currentAcount = "1234567812345678";
			boolean replicable = true;
			int tokensAmount = 3;
			boolean locked = false;

			String event = "Barça - Sevilla";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = sdf.parse("10/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ev = testBL.addEvent(event, date);
			q = testBL.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testBL.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testBL.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testBL.addBet(prediction, 17, rC, locked);
			
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
			boolean registeredClient = testBL.removeRegisteredClient(rC);
			boolean bet = testBL.removedBet(b);
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}

	}

	@Test
	//sut.UseToken: BetIsMultiple exception. (prediction.size()>1).
	public void test8() {
		try {

			System.out.println("");
			System.out.println("TEST8:");

			//Define parameters.
			String name = "Nerea";
			String surname = "Garcia";
			Date birthDate = new Date();
			String dNI = "12345678X";
			String email = "client4@email.com";
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

			ev = testBL.addEvent(event, date);
			q = testBL.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testBL.addPrediction(q, "Barça", (float) 1.5);
			Prediction p1 = testBL.addPrediction(q, "Sevilla", (float) 2);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			prediction.add(p1);
			rC = testBL.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testBL.addBet(prediction, 17, rC, locked);
			
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
			boolean registeredClient = testBL.removeRegisteredClient(rC);
			boolean bet = testBL.removedBet(b);
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}
	}

	// MUGA BALIOAK: Tokens atributuarekin.

	@Test
	//sut.UseToken: rc.Tokens=-1.
	public void test9() {
		try {

			System.out.println("");
			System.out.println("TEST9:");

			//Define parameters.
			String name = "Alexandra";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "client6@email.com";
			String password = "12345678";
			String currentAcount = "1234567812345678";
			boolean replicable = true;
			int tokensAmount = -1;
			boolean locked = false;

			String event = "Barça - Sevilla";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = sdf.parse("10/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ev = testBL.addEvent(event, date);
			q = testBL.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testBL.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testBL.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testBL.addBet(prediction, 17, rC, locked);

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
			boolean registeredClient = testBL.removeRegisteredClient(rC);
			boolean bet = testBL.removedBet(b);
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}
	}

	@Test
	//sut.UseToken: rc.Tokens=0.
	public void test10() {
		try {

			System.out.println("");
			System.out.println("TEST10:");

			//Define parameters.
			String name = "Alex";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "client2@email.com";
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

			ev = testBL.addEvent(event, date);
			q = testBL.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testBL.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testBL.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testBL.addBet(prediction, 17, rC, locked);
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
			boolean registeredClient = testBL.removeRegisteredClient(rC);
			boolean bet = testBL.removedBet(b);
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}

	}

	@Test
	//sut.UseToken: rc.Tokens=1.
	public void test11() {
		try {

			System.out.println("");
			System.out.println("TEST11:");

			//Define parameters.
			String name = "Idoia";
			String surname = "Merino";
			Date birthDate = new Date();
			String dNI = "11111111X";
			String email = "clien4t@email.com";
			String password = "12345678";
			String currentAcount = "1234567812345678";
			boolean replicable = true;
			int tokensAmount = 1;
			boolean locked = false;

			String event = "Barça - Sevilla";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = sdf.parse("10/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ev = testBL.addEvent(event, date);
			q = testBL.addQuestion(ev, "Nork irabaziko du?", 3);
			p = testBL.addPrediction(q, "Barça", (float) 1.5);
			Vector<Prediction> prediction = new Vector<Prediction>();
			prediction.add(p);
			rC = testBL.addRegisteredClient(name, surname, birthDate, dNI, email, password, currentAcount, replicable,
					tokensAmount);
			b = testBL.addBet(prediction, 17, rC, locked);

			int esperoEm = 0;
			//Invoke sut.
			int sutEm = sut.useToken(b, rC);
			
			//Verify.
			assertEquals(esperoEm, sutEm);

		} catch (NoTokens e) {
			fail();
		} catch (BetIsLocked e) {
			fail();
		} catch (BetIsMultiple e) {
			fail();
		} finally {
			// Remove the created objects in the database (cascade removing)
			boolean registeredClient = testBL.removeRegisteredClient(rC);
			boolean bet = testBL.removedBet(b);
			System.out.println(
					"Bet ondo ezabatu da: " + bet + ", Registered client ondo ezabatu da: " + registeredClient);
		}
	}

}


	