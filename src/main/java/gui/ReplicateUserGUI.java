package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import businessLogic.BLFacade;
import domain.RegisteredClient;
import domain.ReplicatedProperties;
import exceptions.NotReplicable;
import exceptions.AlreadyReplicated;
import exceptions.NoUserFound;
import exceptions.ReplicableNoBets;
import exceptions.SelfReplicate;

import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.awt.Color;

public class ReplicateUserGUI extends JFrame {

	private JPanel contentPane;
	private RegisteredClient logged_rc;
	private JButton jButtonGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("goBack"));
	private JTextField textField_email;
	private int percentage=0;
	
	/**
	 * Create the frame.
	 */
	public ReplicateUserGUI(JFrame previousFrame, RegisteredClient logged) {
		try {
			jbInit(previousFrame, logged);
			logged_rc=logged;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void jbInit(JFrame previousFrame, RegisteredClient logged) throws Exception {
		percentage=0;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("button_replicateUser"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(604, 370));
		getContentPane().setLayout(null);
		getContentPane().add(jButtonGoBack);
		
		jButtonGoBack.setBounds(new Rectangle(40, 281, 89, 23));
		jButtonGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				BLFacade facade = MainGUI.getBusinessLogic();
				RegisteredClientGUI a= new RegisteredClientGUI(facade.getRegisteredClient(logged_rc));
				a.setVisible(true);
			}
		});
		
		textField_email = new JTextField();
		textField_email.setBounds(69, 66, 208, 20);
		contentPane.add(textField_email);
		textField_email.setColumns(10);
		
		JLabel label_email = new JLabel("Email:");
		label_email.setForeground(Color.BLACK);
		label_email.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_email.setBounds(69, 41, 48, 14);
		contentPane.add(label_email);
		
		
		JLabel label_percentage = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("percentage"));
		label_percentage.setForeground(Color.BLACK);
		label_percentage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_percentage.setBounds(69, 97, 90, 14);
		contentPane.add(label_percentage);
		
		
		JSpinner spinner_percentage = new JSpinner();
		spinner_percentage.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				percentage=(int) spinner_percentage.getValue();
			}
		});
		spinner_percentage.setBounds(69, 122, 48, 20);
		contentPane.add(spinner_percentage);
	
		JLabel replicateUser_error = new JLabel();
		
		JButton button_replicate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("button_replicateUser")); //$NON-NLS-1$ //$NON-NLS-2$
		button_replicate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				boolean success=true;
				//error label
				replicateUser_error.setBounds(69, 214, 258, 14);
				contentPane.add(replicateUser_error);
				replicateUser_error.setVisible(false);
				replicateUser_error.setForeground(Color.RED);
				replicateUser_error.setFont(new Font("Calibri", Font.BOLD, 13));
				if(percentage<=0) {
					replicateUser_error.setVisible(true);
					replicateUser_error.setText(ResourceBundle.getBundle("Etiquetas").getString("replicateUser_invalidValue"));
				}else {
					try {
					facade.replicateUser(textField_email.getText(), (int)spinner_percentage.getValue(), logged_rc);
					}catch(NoUserFound a) {
						success=false;
						replicateUser_error.setText(ResourceBundle.getBundle("Etiquetas").getString("replicateUser_noUserFound"));
						replicateUser_error.setVisible(true);
					}catch(SelfReplicate b) {
						success=false;
						replicateUser_error.setText(ResourceBundle.getBundle("Etiquetas").getString("replicateUser_selfReplicate"));
						replicateUser_error.setVisible(true);
					}catch(ReplicableNoBets c) {
						success=false;
						replicateUser_error.setVisible(true);
						replicateUser_error.setText(ResourceBundle.getBundle("Etiquetas").getString("replicateUser_noBets"));
					}catch(NotReplicable d) {
						success=false;
						replicateUser_error.setVisible(true);
						replicateUser_error.setText(ResourceBundle.getBundle("Etiquetas").getString("replicateUser_notReplicable"));
					}catch(AlreadyReplicated f){
						success=false;
						replicateUser_error.setVisible(true);
						replicateUser_error.setText(ResourceBundle.getBundle("Etiquetas").getString("replicateUser_alreadyReplicated"));
					}finally {
						if(success) {
							replicateUser_error.setVisible(true);
							replicateUser_error.setForeground(Color.GREEN);
							replicateUser_error.setFont(new Font("Calibri", Font.PLAIN, 13));
							replicateUser_error.setText(ResourceBundle.getBundle("Etiquetas").getString("replicateUser_success"));
						}
					}
				}
				logged_rc = (RegisteredClient) facade.isLogin(logged_rc.getEmail(), logged_rc.getPassword());
				}
		});
		button_replicate.setBounds(69, 166, 151, 23);
		contentPane.add(button_replicate);

	}
}
