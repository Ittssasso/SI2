import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import dataAccess.DataAccess;
import domain.Event;
import test.dataAccess.TestDataAccess;

public class DeleteEventDABTest {

	// sut:system under test
	static DataAccess sut = new DataAccess(true);

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	private Event ev;

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
			testDA.open();
			ev = testDA.addEvent(eventText, oneDate);
			testDA.close();

			// invoke System Under Test (sut)
			sut.deleteEvent(ev);
			
			// verify the results
			testDA.open();
			testDA.removedEvent(ev);
			assertTrue(testDA.removedEvent(ev));
			testDA.close();

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// sut.deleteEvent: The event is null.
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
	// sut.deleteEvent: The event is not in the database.
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
