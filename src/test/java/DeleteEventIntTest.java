import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import test.businessLogic.TestFacadeImplementation;

public class DeleteEventIntTest {

	static BLFacadeImplementation sut;
	static TestFacadeImplementation testBL;

	private Event ev;

	@BeforeClass
	public static void setUpClass() {
		
		DataAccess da = new DataAccess(false);

		sut = new BLFacadeImplementation(da);

		testBL = new TestFacadeImplementation();
	}
	
	@Test
	// sut.deleteEvent: The event is in the database
	public void test1() {
		try {
			
			// define paramaters
			String eventText = "Osasuna - Alavés";

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = null;

			try {
				oneDate = sdf.parse("12/11/2021");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// configure the state of the system (create object in the dabatase)
			ev = testBL.addEvent(eventText, oneDate);

			// invoke System Under Test (sut)
			sut.deleteEvent(ev);
			
			// verify the results
			assertTrue(testBL.removedEvent(ev));

		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	// sut.createQuestion: The event is null.
	public void test2() {
		try {

			// invoke System Under Test (sut)
			sut.deleteEvent(null);

			fail();

		} catch (Exception e) {
			
			assertTrue(true);
		
		}
	}

	@Test
	// sut.createQuestion: The event is not in the database.
	public void test3() {
		try {

			// define paramaters
			String eventText = "Osasuna - Alavés";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = null;
			
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ev = new Event(eventText, oneDate);

			// invoke System Under Test (sut)
			sut.deleteEvent(ev);

			fail();
			
		} catch (Exception e) {
			
			assertTrue(true);
			
		}
	}
}
