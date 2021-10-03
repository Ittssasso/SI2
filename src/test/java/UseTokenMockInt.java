import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import org.junit.Test;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Event;
import domain.Prediction;
import domain.Question;
import domain.RegisteredClient;
import exceptions.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UseTokenMockInt {
	
     DataAccess dataAccess=Mockito.mock(DataAccess.class);
	
	@InjectMocks
	 BLFacade sut=new BLFacadeImplementation(dataAccess);

	@Test
	// sut.UseToken: The bet and registeredClient are in the database.
	public void test1() throws BetIsMultiple, NoTokens, BetIsLocked {
	
		//Define parameters.
		String event = "Barça - Sevilla";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Event ev = new Event (event, date);
		Question q = ev.addQuestion("Nork irabaziko du?", 2);
		Prediction p = new Prediction("Barca", (float) 1.5, q);
		Prediction p1 = new Prediction("Sevilla", (float) 1.5, q);
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		//rc.tokens=4.
		rC.setTokens(4);
		Vector<Prediction> prediction= new Vector<Prediction>();
		prediction.add(p);
		prediction.add(p1);
		Bet bet= rC.addBet(3, prediction);
		
		//Configure Mock.
		Mockito.doReturn(3).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));
		
		
		try {
		 //Invoke sut.
		 sut.useToken(bet,rC);
		 
		 //Verify the results.
		 ArgumentCaptor<RegisteredClient> rCaptor = ArgumentCaptor.forClass(RegisteredClient.class);
		 ArgumentCaptor<Bet> bCaptor = ArgumentCaptor.forClass(Bet.class);
		 Mockito.verify(dataAccess,Mockito.times(1)).useToken(bCaptor.capture(), rCaptor.capture());
		 
		//If the program goes to this point OK.
		 assertEquals(rCaptor.getValue().getTokens(), rC.getTokens());
		 	
		} catch (NoTokens e) {
			fail();
		} catch (BetIsLocked e) {
			fail();
		} catch (BetIsMultiple e) {
			fail();	
		}
	}
	
	@Test
	// sut.UseToken: The bet is not in the database.
	public void test2() throws BetIsMultiple, NoTokens, BetIsLocked {
	
		//Define parameters.
		String event = "Barça - Sevilla";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Event ev = new Event (event, date);
		Question q = ev.addQuestion("Nork irabaziko du?", 2);
		Prediction p = new Prediction("Barca", (float) 1.5, q);
		Prediction p1 = new Prediction("Sevilla", (float) 1.5, q);
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		Vector<Prediction> prediction= new Vector<Prediction>();
		prediction.add(p);
		prediction.add(p1);
		Bet bet= rC.addBet(3, prediction);
		
		//Configure Mock.
		Mockito.doThrow(new RuntimeException()).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));

		try {
			//Invoke sut.
			sut.useToken(bet,rC);			
			fail();
		} catch (RuntimeException e) {
			//If the program goes to this point OK.
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	// sut.UseToken: The registered client is not in the database.
	public void test3() throws BetIsMultiple, NoTokens, BetIsLocked {
	
		String event = "Barça - Sevilla";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Event ev = new Event (event, date);
		Question q = ev.addQuestion("Nork irabaziko du?", 2);
		Prediction p = new Prediction("Barca", (float) 1.5, q);
		Prediction p1 = new Prediction("Sevilla", (float) 1.5, q);
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		Vector<Prediction> prediction= new Vector<Prediction>();
		prediction.add(p);
		prediction.add(p1);
		Bet bet= rC.addBet(3, prediction);
		
		//Configure Mock
		Mockito.doThrow(new RuntimeException()).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));
		
		try {
			//Invoke sut.
			sut.useToken(bet,rC);
			fail();
		} catch (Exception e) {
			//If the program goes to this point OK.
			assertTrue(true);
		}
	}
	
	@Test
	// sut.UseToken: The bet is null.
	public void test4() throws BetIsMultiple, NoTokens, BetIsLocked {
		
		//Define parameters.
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		
		//Configure Mock.
		Mockito.doThrow(new NullPointerException("Null")).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));
	
		try {
			//Invoke sut.
			sut.useToken(null,rC);
			fail();
		} catch (NullPointerException e) {
			//If the program goes to this point OK.
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	// sut.UseToken: The RegisteredClient is null.
	public void test5() throws BetIsMultiple, NoTokens, BetIsLocked {
	
		//Define parameters.
		String event = "Barça - Sevilla";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Event ev = new Event (event, date);
		Question q = ev.addQuestion("Nork irabaziko du?", 2);
		Prediction p = new Prediction("Barca", (float) 1.5, q);
		Prediction p1 = new Prediction("Sevilla", (float) 1.5, q);
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		Vector<Prediction> prediction= new Vector<Prediction>();
		prediction.add(p);
		prediction.add(p1);
		Bet bet= rC.addBet(3, prediction);
		
		//Configure Mock.
		Mockito.doThrow(new NullPointerException("Null")).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));
		
		try {
			//Invoke sut.
			sut.useToken(bet,null);
			fail();
		} catch (NullPointerException e) {
			//If the program goes to this point OK.
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	// sut.UseToken: Registered client don't have tokens.
	public void test6() throws BetIsMultiple, NoTokens, BetIsLocked {
	
		//Define parameters.
		String event = "Barça - Sevilla";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Event ev = new Event (event, date);
		Question q = ev.addQuestion("Nork irabaziko du?", 2);
		Prediction p = new Prediction("Barca", (float) 1.5, q);
		Prediction p1 = new Prediction("Sevilla", (float) 1.5, q);
		//rC.tokens=-7
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		Vector<Prediction> prediction= new Vector<Prediction>();
		prediction.add(p);
		prediction.add(p1);
		Bet bet= rC.addBet(3, prediction);
		
		//Configure Mock.
		Mockito.doThrow(new NoTokens()).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));
		try {
			//Invoke Mock.
			sut.useToken(bet,rC);
			fail();
		} catch (NoTokens e) {
			//If the program goes to this point OK.
			assertTrue(true);
		} catch (BetIsLocked e) {
			fail();
		} catch (BetIsMultiple e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	// sut.UseToken: Bet is locked.
	public void test7() throws BetIsMultiple, NoTokens, BetIsLocked {
	
		//Define parameters.
		String event = "Barça - Sevilla";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Event ev = new Event (event, date);
		Question q = ev.addQuestion("Nork irabaziko du?", 2);
		Prediction p = new Prediction("Barca", (float) 1.5, q);
		Prediction p1 = new Prediction("Sevilla", (float) 1.5, q);
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		Vector<Prediction> prediction= new Vector<Prediction>();
		prediction.add(p);
		prediction.add(p1);
		//b.isLocked==true.
		Bet bet= rC.addBet(3, prediction);
		bet.setLocked(true);
		
		//Configure Mock.
		Mockito.doThrow(new BetIsLocked()).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));
	
		try {
			//Invoke sut.
			sut.useToken(bet,rC);
			fail();
		} catch (NoTokens e) {
			fail();
		} catch (BetIsLocked e) {
			//If the program goes to this point OK.
			assertTrue(true);		
		} catch (BetIsMultiple e) {
			fail();
		} catch (Exception e) {
			fail();
		}	
	}
	
	@Test
	// sut.UseToken: Bet is multiple.
	public void test8() throws BetIsMultiple, NoTokens, BetIsLocked {
	
		//Define parameters.
		String event = "Barça - Sevilla";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Event ev = new Event (event, date);
		Question q = ev.addQuestion("Nork irabaziko du?", 2);
		Prediction p = new Prediction("Barca", (float) 1.5, q);
		Prediction p1 = new Prediction("Sevilla", (float) 1.5, q);
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		Vector<Prediction> prediction= new Vector<Prediction>();
		//Bet.isMultiple==true.
		prediction.add(p);
		prediction.add(p1);
		Bet bet= rC.addBet(3, prediction);
		
		//Configure Mock
		Mockito.doThrow(new BetIsMultiple()).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));
		
		try {
			//Invoke sut.
			sut.useToken(bet,rC);
			fail();
		} catch (NoTokens e) {
			fail();
		} catch (BetIsLocked e) {
			fail();
		} catch (BetIsMultiple e) {
			//If the program goes to this point OK.
			assertTrue(true);	
		} catch (Exception e) {
			fail();
		}
	}
	
	//MUGA BALIOAK: Tokens atributuarekin.
	
	@Test
	// sut.UseToken: rC.tokens=-1
	public void test9() throws BetIsMultiple, NoTokens, BetIsLocked {
	
		String event = "Barça - Sevilla";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Event ev = new Event (event, date);
		Question q = ev.addQuestion("Nork irabaziko du?", 2);
		Prediction p = new Prediction("Barca", (float) 1.5, q);
		Prediction p1 = new Prediction("Sevilla", (float) 1.5, q);
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		//rC.tokens=-1
		rC.setTokens(-1);
		Vector<Prediction> prediction= new Vector<Prediction>();
		prediction.add(p);
		prediction.add(p1);
		Bet bet= rC.addBet(3, prediction);
	
		//Configure Mock.
		Mockito.doThrow(new NoTokens()).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));
		
		try {
			//Invoke Mock.
			sut.useToken(bet,rC);
			fail();
		} catch (NoTokens e) {
			//If the program goes to this point OK.
			assertTrue(true);
		} catch (BetIsLocked e) {
			fail();
		} catch (BetIsMultiple e) {
			fail();
		} catch (Exception e) {
			fail();
		}	
	}
	
	@Test
	// sut.UseToken: rC.tokens=0
	public void test10() throws BetIsMultiple, NoTokens, BetIsLocked {
	
		String event = "Barça - Sevilla";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Event ev = new Event (event, date);
		Question q = ev.addQuestion("Nork irabaziko du?", 2);
		Prediction p = new Prediction("Barca", (float) 1.5, q);
		Prediction p1 = new Prediction("Sevilla", (float) 1.5, q);
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		//rC.tokens=-1
		rC.setTokens(-1);
		rC.setTokens(4);
		Vector<Prediction> prediction= new Vector<Prediction>();
		prediction.add(p);
		prediction.add(p1);
		Bet bet= rC.addBet(3, prediction);
	
		//Configure Mock.
		Mockito.doThrow(new NoTokens()).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));
				
		try {
			//Invoke Mock.
			sut.useToken(bet,rC);
			fail();
		} catch (NoTokens e) {
			//If the program goes to this point OK.
			assertTrue(true);
		} catch (BetIsLocked e) {
			fail();
		} catch (BetIsMultiple e) {
			fail();
		} catch (Exception e) {
			fail();
		}	
	}
	
	@Test
	// sut.UseToken: rC.tokens=1
	public void test11() throws BetIsMultiple, NoTokens, BetIsLocked {
	
		String event = "Barça - Sevilla";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse("10/11/2021");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Event ev = new Event (event, date);
		Question q = ev.addQuestion("Nork irabaziko du?", 2);
		Prediction p = new Prediction("Barca", (float) 1.5, q);
		Prediction p1 = new Prediction("Sevilla", (float) 1.5, q);
		RegisteredClient rC= new RegisteredClient("p1", "p2", date, "11111111X",
						"a", "a", "1234567812345678", true);
		//rC.tokens=1
		rC.setTokens(1);
		Vector<Prediction> prediction= new Vector<Prediction>();
		prediction.add(p);
		prediction.add(p1);
		Bet bet= rC.addBet(3, prediction);
	
		Mockito.doReturn(0).when(dataAccess).useToken(Mockito.any(Bet.class), Mockito.any(RegisteredClient.class));
		
		try {
			//Invoke sut
			sut.useToken(bet,rC);
			
			//Verify the results.
			ArgumentCaptor<RegisteredClient> rCaptor = ArgumentCaptor.forClass(RegisteredClient.class);
			ArgumentCaptor<Bet> bCaptor = ArgumentCaptor.forClass(Bet.class);
			Mockito.verify(dataAccess,Mockito.times(1)).useToken(bCaptor.capture(), rCaptor.capture());
			
			// if the program goes to this point OK
			assertEquals(rCaptor.getValue().getTokens(), rC.getTokens());
		 	
		} catch (NoTokens e) {
			fail();
		} catch (BetIsLocked e) {
			fail();
		} catch (BetIsMultiple e) {
			fail();	
		} catch (Exception e) {
			fail();
		}
	}
	

}