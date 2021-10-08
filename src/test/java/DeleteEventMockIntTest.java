import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeleteEventMockIntTest {

	DataAccess dataAccess = Mockito.mock(DataAccess.class);

	@InjectMocks
	BLFacade sut = new BLFacadeImplementation(dataAccess);

	@Test
	// sut.deleteEvent: The event is in the database.
	public void test1() {
		try {

			// Define paramaters.
			String eventText = "Osasuna -Alaves";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = null;

			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Event ev = new Event(eventText, oneDate);

			// Invoke System Under Test (sut).
			sut.deleteEvent(ev);
			
			Mockito.verify(dataAccess, Mockito.timeout(1)).deleteEvent(Mockito.any(Event.class));
			
			ArgumentCaptor<Event> evCaptor = ArgumentCaptor.forClass(Event.class);
			Mockito.verify(dataAccess, Mockito.times(1)).deleteEvent(evCaptor.capture());

			// Verify the results.
			assertEquals(evCaptor.getValue(), ev);
			
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// sut.deleEvent: The event is null.
	public void test2() {
		
		//Configure Mock.
		Mockito.doThrow(new NullPointerException()).when(dataAccess).deleteEvent(Mockito.any(Event.class));
		
		try {
			//Invoke System Under Test (sut).
			sut.deleteEvent(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	// sut.deleEvent: The event is not in the database.
	public void test3() {

		//Define parameters.
		String eventText = "Osasuna -Alaves";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate = null;

		try {
			oneDate = sdf.parse("05/10/2022");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Event ev2 = new Event(eventText, oneDate);

		// Configure Mock.
		Mockito.doThrow(new RuntimeException()).when(dataAccess).deleteEvent(Mockito.any(Event.class));

		try {
			//Invoke System Under Test (sut).
			sut.deleteEvent(ev2);
			fail();
		} catch (RuntimeException e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

}