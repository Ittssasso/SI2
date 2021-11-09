package adapter;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import domain.Bet;
import domain.RegisteredClient;

public class UserAdapter extends AbstractTableModel{

	private String[] columnNames = {"Event", "Question", "Event Date", "Bet"};
	private Vector<Bet> bets;
	private RegisteredClient rC;
	
	public UserAdapter(RegisteredClient rc) {
		rC=rc;
		bets=rC.getMovementsBet();	
//		System.out.println(bets.size());
//		System.out.println(bets.get(1).getMoney());
//		System.out.println(bets.get(0).getPredictions().get(0).getQuestion().getEvent().getDescription());
//		System.out.println(bets.get(1).getPredictions().get(0).getQuestion().getEvent().getDescription());
	}
	
	@Override
	public int getRowCount() {
		int size;
		if(bets == null)
			return 0;
		else
//			System.out.println("size:"+bets.size());
			size=bets.size();
		return size;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object temp = null;
//		System.out.println("row"+rowIndex);
//		System.out.println(columnIndex);
		if(columnIndex==0) {
			temp = bets.get(rowIndex).getPredictions().get(0).getQuestion().getEvent().getDescription();
		}
		else if(columnIndex==1) {
			temp = bets.get(rowIndex).getPredictions().get(0).getQuestion().toString();
		}
		else if(columnIndex==2) {
			temp =bets.get(rowIndex).getPredictions().get(0).getQuestion().getEvent().getEventDate();
		}
		else if(columnIndex==3) {
			temp = bets.get(rowIndex).getMoney();
		}
		return temp;
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}


}
