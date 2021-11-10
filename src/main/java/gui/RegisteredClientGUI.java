package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import domain.RegisteredClient;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;


public class RegisteredClientGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton button_queryquestions= new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
	private JButton button_logout= new JButton (ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
	private JButton button_Bet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
	JButton button_deleteBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("editBet"));

	JFrame clientFrame = this;

	ImageIcon logo;
	Icon icon2;
	
	public RegisteredClientGUI(RegisteredClient logged) {
		try
		{
			jbInit(logged);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void jbInit(RegisteredClient logged) throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(null);
		setBounds(100, 100, 450, 300); 
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisteredClient"));
		
		button_queryquestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindQuestionsGUI(clientFrame);
				a.setVisible(true);
				clientFrame.setVisible(false);
			}
		});
		button_queryquestions.setBounds(10, 66, 182, 28);
		getContentPane().add(button_queryquestions);
		
		JLabel lblNewLabel = new JLabel("Bets21");
		
		Font font = lblNewLabel.getFont();
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		
		lblNewLabel.setForeground(Color.CYAN);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 17));
		lblNewLabel.setFont(font.deriveFont(attributes));
		lblNewLabel.setBounds(103, 11, 71, 14);
		getContentPane().add(lblNewLabel);
		button_logout.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		button_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MainGUI();
				a.setVisible(true);
				clientFrame.setVisible(false);
			}
		});
		button_logout.setBounds(167, 227, 113, 23);
		getContentPane().add(button_logout);
		button_Bet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ToBetGUI(clientFrame, logged);
				a.setVisible(true);
				clientFrame.setVisible(false);
			}
		});
		
		
		button_Bet.setBounds(10, 144, 182, 28);
		getContentPane().add(button_Bet);
		button_deleteBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(logged.getMovements().size());
				JFrame a = new EditBetGUI(clientFrame, logged);
				a.setVisible(true);
				clientFrame.setVisible(false);
			}
		});
		
		
		JButton btnViewMovementsBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("viewMovementsBet")); 

		RegisteredClient rc= (RegisteredClient) logged;
				btnViewMovementsBet.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JFrame a = new ViewBetsGUI(clientFrame, rc);
						a.setVisible(true);
						clientFrame.setVisible(false);
					}
				});
				btnViewMovementsBet.setBounds(10, 183, 182, 29);
				getContentPane().add(btnViewMovementsBet);
			
		JButton button_shop = new JButton(ResourceBundle.getBundle("Etiquetas").getString("shop")); //$NON-NLS-1$ //$NON-NLS-2$
		button_shop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ShopGUI(clientFrame, rc);
				a.setVisible(true);
				clientFrame.setVisible(false);
			}
		});
		button_shop.setBounds(10, 105, 182, 28);
		getContentPane().add(button_shop);
		
		JLabel lblBalance = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("balance") + " " +String.valueOf(rc.getBalance())+"€"); //$NON-NLS-1$ //$NON-NLS-2$
		lblBalance.setBounds(103, 25, 143, 14);
		getContentPane().add(lblBalance);
		
		JLabel blbTokens = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("tokens") + " " +String.valueOf(rc.getTokens())); //$NON-NLS-1$ //$NON-NLS-2$
		blbTokens.setBounds(103, 41, 143, 14);
		getContentPane().add(blbTokens);
	
		button_deleteBet.setBounds(244, 144, 182, 28);
		getContentPane().add(button_deleteBet);
		
		JButton viewProfits = new JButton(ResourceBundle.getBundle("Etiquetas").getString("viewProfits"));
		viewProfits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ViewProfitsGUI(clientFrame, rc);
				a.setVisible(true);
				clientFrame.setVisible(false);
			}
		});
		viewProfits.setBounds(244, 66, 182, 28);
		getContentPane().add(viewProfits);
		
		JButton btnViewMovements = new JButton(ResourceBundle.getBundle("Etiquetas").getString("viewMovements")); //$NON-NLS-1$ //$NON-NLS-2$
		btnViewMovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a=new ViewMovementsGUI(clientFrame, rc);
				a.setVisible(true);
				clientFrame.setVisible(false);
			}
		});
		btnViewMovements.setBounds(244, 105, 182, 28);
		getContentPane().add(btnViewMovements);
		
		JButton button_replicateUser = new JButton(ResourceBundle.getBundle("Etiquetas").getString("button_replicateUser")); //$NON-NLS-1$ //$NON-NLS-2$
		button_replicateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ReplicateUserGUI(clientFrame, rc);
				a.setVisible(true);
				clientFrame.setVisible(false);
			}
		});
		button_replicateUser.setBounds(244, 183, 182, 29);
		getContentPane().add(button_replicateUser);
		
		
		
		JButton button = new JButton(); //$NON-NLS-1$ //$NON-NLS-2
		button.setBounds(20, 11, 73, 44);
		getContentPane().add(button);
		
		//LOGO BETS21
		logo = new ImageIcon("src/main/resources/images/logo1.png");
		icon2= new ImageIcon(logo.getImage().getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH));
		button.setIcon(icon2);
		button.setContentAreaFilled(false);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setFocusPainted(false);
		
		JButton btnNewButton = new JButton(rc.getName()); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AdapterGUI( rc);
			}
		});
		btnNewButton.setBounds(189, 11, 237, 49);
		getContentPane().add(btnNewButton);
		
		ImageIcon token_background = new ImageIcon("src/main/resources/images/token.png");
		Icon icon3= new ImageIcon(token_background.getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH));
		
		
		

	}
}
