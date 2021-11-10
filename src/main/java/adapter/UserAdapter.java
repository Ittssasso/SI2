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
	}
	
	@Override
	public int getRowCount() {
		int size;
		if(bets == null)
			return 0;
		else
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
