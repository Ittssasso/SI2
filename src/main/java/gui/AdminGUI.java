package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class AdminGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private JButton button_logout= new JButton (ResourceBundle.getBundle("Etiquetas").getString("LogOut"));

	AdminGUI adminFrame = this;
	
	public AdminGUI() {
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void jbInit() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(null);
		setBounds(100, 100, 450, 300); 
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Admin"));
		button_logout.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		button_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MainGUI();
				a.setVisible(true);
				adminFrame.setVisible(false);
			}
		});
		button_logout.setBounds(169, 190, 112, 23);
		getContentPane().add(button_logout);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(182, 96, 150, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_1.setBounds(10, 11, 46, 14);
		getContentPane().add(lblNewLabel_1);
	}
}
