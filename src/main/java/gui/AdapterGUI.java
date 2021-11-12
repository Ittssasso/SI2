package gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import adapter.RegisteredClientModelAdapter;
import domain.RegisteredClient;

public class AdapterGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public AdapterGUI(RegisteredClient logged) {
		try {
			jbInit(logged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void jbInit(RegisteredClient logged) throws Exception {

		RegisteredClientModelAdapter model = new RegisteredClientModelAdapter(logged);

		JFrame j = new JFrame();
		JTable table = new JTable(model);
		j.add(new JScrollPane(table));
		j.setTitle(logged.getName() + "k egin dituen apustuak: ");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.pack();
		j.setVisible(true);

	}

}


