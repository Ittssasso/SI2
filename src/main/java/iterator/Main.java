package iterator;

import java.text.SimpleDateFormat;
import java.util.Date;

import businessLogic.BLFacade;
import configuration.ConfigXML;
import domain.Event;
import factory.BLFactory;

public class Main {
	public static void main(String[] args) {
		ConfigXML c = ConfigXML.getInstance();

		try {
			// Data prestatu
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse("17/12/2021");

			// Facade objektua lortu lehendabiziko ariketa erabiliz
			BLFacade facadeInterface = (new BLFactory()).getBusinessLogic(c);
			ExtendedIterator<Event> i = facadeInterface.getEvents(date);
			Event ev;

			System.out.println("______");
			System.out.println("GERTAERAK ATZETIK AURRERA:");
			System.out.println("______");
			System.out.println();
			i.goLast();
			while (i.hasPrevious()) {
				ev = i.previous();
				System.out.println(ev.toString());
			}

			System.out.println();
			System.out.println("______");
			System.out.println("GERTAERAK AURRETIK ATZERA:");
			System.out.println("______");
			System.out.println();
			// Nahiz eta suposatu hasierara ailegatu garela, eragiketa egiten dugu.
			i.goFirst();
			while (i.hasNext()) {
				ev = i.next();
				System.out.println(ev.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}