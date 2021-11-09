package adapter;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import configuration.UtilDate;
import domain.Bet;
import domain.Event;
import domain.Prediction;
import domain.Question;
import domain.RegisteredClient;

public class Main {

	public static void main(String[]args) {
		RegisteredClient p3 = new RegisteredClient("Itsaso", "p2", UtilDate.newDate(2022, 12, 17), "11111111X",
				"client@email.com", "12345678", "1234567812345678", true);
		Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(2021, 11, 17));
		Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(2022, 12, 17));
		Question q1 = new Question(3,"Quien ganara el partido?",3, ev1);
		Question q2 = new Question(2,"Quien metera el primer gol?", 4, ev2);
		ev1.addQuestion(" Quién ganará el partido?", 1);
		q1.setEvent(ev1);
		ev2.addQuestion(" Quién meterá el primer gol?", 2);
		q2.setEvent(ev2);
		q1.addPrediction("Atletico", (float) 1.5);
		q1.addPrediction("Athletic", (float) 1.1);
		q1.addPrediction("Draws", (float) 1.2);
		q2.addPrediction("Eibar", (float) 1.5);
		q2.addPrediction("Barcelona", (float) 1.1);
		q2.addPrediction("Draws", (float) 1.2);
		Prediction pr1 = new Prediction("Athletic", (float) 1.5, q1);
		Vector<Prediction> pred1 = new Vector<Prediction>();
		Prediction pr2 = new Prediction("Eibar", (float) 3.0, q2);
		pred1.addElement(pr1);
		pred1.addElement(pr2);
		Vector<Prediction> pred2 = new Vector<Prediction>();
		pred2.addElement(pr2);
		
		Bet b = new Bet(3, pred1);
		Bet b1 = new Bet(2, pred2);
		b.setClient(p3);
		p3.addBet(b);
		p3.addBet(b1);
		System.out.println(b);
		System.out.println(b1);
		
		UserAdapter model= new UserAdapter(p3);

		JFrame j=new JFrame();
		JTable table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		j.add(new JScrollPane(table)); 
		j.setTitle(p3.getName()+"k egin dituen apustuak: ");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.pack();
		j.setVisible(true);
		
	}
}
