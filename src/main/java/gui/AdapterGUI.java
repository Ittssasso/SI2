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
	public AdapterGUI(JFrame previousFrame, RegisteredClient logged) {
		try {
			jbInit(previousFrame, logged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void jbInit(JFrame previousFrame,RegisteredClient logged) throws Exception {
		
		UserAdapter model= new UserAdapter(logged);

		JFrame j=new JFrame();
		JTable table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		j.add(new JScrollPane(table)); 
		j.setTitle(logged.getName()+"k egin dituen apustuak: ");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.pack();
		j.setVisible(true);
		
		btnGoBack.setBounds(10, 199, 89, 23);
		btnGoBack .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				previousFrame.setVisible(true);
			}
		});
		getContentPane().add(btnGoBack);
		
	}

}
