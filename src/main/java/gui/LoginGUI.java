package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Person;
import domain.RegisteredClient;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public class LoginGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JTextField textField_email;
	private JLabel error_message = new JLabel("");
	private JPasswordField passwordField;

	LoginGUI loginFrame = this;
	
	ImageIcon logo;
	Icon icon2;
	
	public LoginGUI(JFrame previousFrame) {
		try
		{
			jbInit(previousFrame);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void jbInit(JFrame previousFrame) throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 450, 300); 
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Login"));

		textField_email = new JTextField();
		textField_email.setBounds(123, 77, 196, 20);
		getContentPane().add(textField_email);
		textField_email.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(123, 108, 196, 20);
		getContentPane().add(passwordField);

		JButton btnLogIn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		btnLogIn.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				Person logged_person = facade.isLogin(textField_email.getText(), passwordField.getText());
				if (logged_person==null) {
					error_message.setForeground(Color.RED);
					error_message.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginError"));
				}
				else {
					JFrame a;
					error_message.setText("");
					if (logged_person instanceof domain.Worker) {
						a = new WorkerGUI();
						a.setVisible(true);
					}
					else if (logged_person instanceof domain.RegisteredClient) {
						RegisteredClient p = (RegisteredClient) logged_person;
						a = new RegisteredClientGUI(p);
						a.setVisible(true);
					}
					else if (logged_person instanceof domain.Admin) {
						a = new AdminGUI();
						a.setVisible(true);
					}
					loginFrame.setVisible(false);
				}
			}
		});
		btnLogIn.setBounds(156, 164, 111, 23);
		getContentPane().add(btnLogIn);

		JLabel lblEmail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("E-mail"));
		lblEmail.setBounds(44, 80, 46, 14);
		getContentPane().add(lblEmail);

		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPassword.setBounds(44, 111, 79, 14);
		getContentPane().add(lblPassword);

		

		error_message.setFont(new Font("Tahoma", Font.PLAIN, 9));
		error_message.setBounds(91, 139, 333, 14);
		getContentPane().add(error_message);
		
		JButton btnGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("goBack")); //$NON-NLS-1$ //$NON-NLS-2$
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				previousFrame.setVisible(true);
			}
		});
		btnGoBack.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnGoBack.setBounds(323, 223, 89, 14);
		getContentPane().add(btnGoBack);
		
		
		
		JButton button = new JButton(); //$NON-NLS-1$ //$NON-NLS-2
		button.setBounds(20, 25, 73, 44);
		getContentPane().add(button);
		
		//LOGO BETS21
		logo = new ImageIcon("src/main/resources/images/logo1.png");
		icon2= new ImageIcon(logo.getImage().getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH));
		button.setIcon(icon2);
		button.setContentAreaFilled(false);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setFocusPainted(false);
	}
}

