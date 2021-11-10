package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import adapter.UserAdapter;
import domain.RegisteredClient;

public class AdapterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("goBack"));
	/**
	 * Create the frame.
	 */
	public AdapterGUI(RegisteredClient logged) {
		try {
			jbInit(logged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void jbInit(RegisteredClient logged) throws Exception {
		
		UserAdapter model= new UserAdapter(logged);

		JFrame j=new JFrame();
		JTable table = new JTable(model);
		j.add(new JScrollPane(table)); 
		j.setTitle(logged.getName()+"k egin dituen apustuak: ");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.pack();
		j.setVisible(true);
		
	}

}
