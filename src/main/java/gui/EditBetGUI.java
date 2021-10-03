package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bet;
import domain.BetPredictionQuestionEventContainer;
import domain.RegisteredClient;
import exceptions.BetIsLocked;
import exceptions.BetIsMultiple;
import exceptions.LockedBetCantBeDeleted;
import exceptions.NoTokens;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class EditBetGUI extends JFrame {
	
	private static final String ETIQUETAS= "etiquetas";
	private static final String NOBET= "noBet";
	private static final String SELECTBET= "selectBet";
	private static final String SELECTMULTIPLE= "selectMultipleBet";

	private JPanel contentPane;
	
	private JComboBox<BetPredictionQuestionEventContainer> comboBoxBets = new JComboBox<BetPredictionQuestionEventContainer>();
	private JComboBox<BetPredictionQuestionEventContainer> comboBoxMultiple = new JComboBox<BetPredictionQuestionEventContainer>();

	private DefaultComboBoxModel<BetPredictionQuestionEventContainer> betsInfo = new DefaultComboBoxModel<BetPredictionQuestionEventContainer>();
	private DefaultComboBoxModel<BetPredictionQuestionEventContainer> multipleInfo = new DefaultComboBoxModel<BetPredictionQuestionEventContainer>();
	
	private Vector<BetPredictionQuestionEventContainer> betsCollection; 
	
	private JButton buttonDeleteBet = new JButton(ResourceBundle.getBundle(ETIQUETAS).getString("deleteBet"));
	private JButton buttonGoBack = new JButton(ResourceBundle.getBundle(ETIQUETAS).getString("goBack"));
	private JButton btnUseTokens = new JButton(ResourceBundle.getBundle(ETIQUETAS).getString("useToken"));
	private JButton btnDeleteMultiple = new JButton(ResourceBundle.getBundle(ETIQUETAS).getString("deleteMultipleBet"));
	
	private JLabel lblNewLabelMessages = new JLabel("");
	private JLabel lblNewLabelErrors = new JLabel("");

	private JLabel lblNewLabelMessagesM = new JLabel("");
	private JLabel lblNewLabelErrorsM = new JLabel("");
	
	private Bet selectedBet;
	private Bet selectedMultiple;
	
	
	
	public EditBetGUI(JFrame previousFrame, RegisteredClient u) {
		try
		{
			jbInit(previousFrame, u);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void jbInit(JFrame previousFrame, RegisteredClient u) throws Exception {
		this.setTitle(ResourceBundle.getBundle(ETIQUETAS).getString("editBet"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 823, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		betsInfo.removeAllElements();
		multipleInfo.removeAllElements();
		BLFacade facade = MainGUI.getBusinessLogic();
		betsCollection = facade.viewFutureBets(u);
//		if (u.getMovements()==null) System.out.println("prueba");
		selectedBet=null;
		
		for (BetPredictionQuestionEventContainer b : betsCollection) { 
//			System.out.println(b.toString());
			if(!b.getBet().getMultiple())
				betsInfo.addElement(b); 
			else
				multipleInfo.addElement(b);
		}
		if (betsInfo.getSize()==0) lblNewLabelMessages.setText(ResourceBundle.getBundle(ETIQUETAS).getString(NOBET));
		 else lblNewLabelMessages.setText(ResourceBundle.getBundle(ETIQUETAS).getString(SELECTBET));
		if (multipleInfo.getSize()==0) lblNewLabelMessagesM.setText(ResourceBundle.getBundle(ETIQUETAS).getString("noMultipleBet"));
		 else lblNewLabelMessagesM.setText(ResourceBundle.getBundle(ETIQUETAS).getString(SELECTMULTIPLE));
		

		buttonDeleteBet.setEnabled(false);
		btnUseTokens.setEnabled(false);
		btnDeleteMultiple.setEnabled(false);
		comboBoxBets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BetPredictionQuestionEventContainer cont = (BetPredictionQuestionEventContainer) comboBoxBets.getSelectedItem();
				if (cont!=null) {
					selectedBet = cont.getBet();
					buttonDeleteBet.setEnabled(true);
					btnUseTokens.setEnabled(true);
				}
				else selectedBet = null;
			}
		});
		
		comboBoxBets.setBounds(36, 43, 763, 21);
		contentPane.add(comboBoxBets);
		comboBoxBets.setModel(betsInfo);
		
		comboBoxMultiple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BetPredictionQuestionEventContainer contM = (BetPredictionQuestionEventContainer) comboBoxMultiple.getSelectedItem();
				if (contM!=null) {
					selectedMultiple = contM.getBet();
					btnDeleteMultiple.setEnabled(true);
				}
				else selectedMultiple = null;
			}
		});
		comboBoxMultiple.setBounds(36,155,763,20);
		contentPane.add(comboBoxMultiple);
		comboBoxMultiple.setModel(multipleInfo);
		
		buttonDeleteBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedBet!=null) {
					lblNewLabelErrors.setText("");
					BLFacade facade=MainGUI.getBusinessLogic();
					try {
						facade.deleteBet(selectedBet, u);
						betsInfo.removeAllElements();
						RegisteredClient rG = facade.getRegisteredClient(u);
						betsCollection = facade.viewFutureBets(rG);
						
						for (BetPredictionQuestionEventContainer b : betsCollection) { 
//							System.out.println(b.toString());
							if(!b.getBet().getMultiple())
								betsInfo.addElement(b); 
						}
					} catch(LockedBetCantBeDeleted eL1) {
						lblNewLabelErrors.setForeground(Color.RED);
						lblNewLabelErrors.setText(ResourceBundle.getBundle(ETIQUETAS).getString("isLocked"));
					}
					comboBoxBets.setModel(betsInfo);
					selectedBet=null;
					if (betsCollection.isEmpty()) lblNewLabelMessages.setText(ResourceBundle.getBundle(ETIQUETAS).getString(NOBET));
					 else lblNewLabelMessages.setText(ResourceBundle.getBundle(ETIQUETAS).getString(SELECTBET));
					
					buttonDeleteBet.setEnabled(false);
					btnUseTokens.setEnabled(false);
				} else {
					lblNewLabelErrors.setForeground(Color.RED);
					lblNewLabelErrors.setText(ResourceBundle.getBundle(ETIQUETAS).getString(SELECTBET));
				}	
			}
		});
		buttonDeleteBet.setBounds(241, 99, 129, 32);
		contentPane.add(buttonDeleteBet);
		
		
		buttonGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				BLFacade facade=MainGUI.getBusinessLogic();
				JFrame a = new RegisteredClientGUI((RegisteredClient) facade.getRegisteredClient(u));
				a.setVisible(true);
			}
		});
		buttonGoBack.setBounds(571, 303, 117, 21);
		contentPane.add(buttonGoBack);
		
		
		lblNewLabelMessages.setBounds(36, 23, 480, 13);
		contentPane.add(lblNewLabelMessages);
		
		lblNewLabelErrors.setBounds(211, 75, 588, 13);
		contentPane.add(lblNewLabelErrors);
		
		lblNewLabelMessagesM.setBounds(36, 141, 780, 13);
		contentPane.add(lblNewLabelMessagesM);
		
		lblNewLabelErrorsM.setBounds(59, 185, 740, 13);
		contentPane.add(lblNewLabelErrorsM);
		
		btnUseTokens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try{
					facade.useToken(selectedBet, u);
					betsInfo.removeAllElements();
					RegisteredClient rG = facade.getRegisteredClient(u);
					betsCollection = facade.viewFutureBets(rG);
					for (BetPredictionQuestionEventContainer b : betsCollection) { 
//						System.out.println(b.toString());
						if(!b.getBet().getMultiple())
							betsInfo.addElement(b); 
					}
					comboBoxBets.setModel(betsInfo);
					selectedBet=null;
					if (betsCollection.isEmpty()) lblNewLabelMessages.setText(ResourceBundle.getBundle(ETIQUETAS).getString(NOBET));
					 else lblNewLabelMessages.setText(ResourceBundle.getBundle(ETIQUETAS).getString(SELECTBET));
					buttonDeleteBet.setEnabled(false);
					btnUseTokens.setEnabled(false);
					lblNewLabelErrors.setForeground(Color.BLACK);
					lblNewLabelErrors.setText(ResourceBundle.getBundle(ETIQUETAS).getString("TokenUsedCorrectly"));
				}catch(NoTokens e1) {
					lblNewLabelErrors.setForeground(Color.RED);
					lblNewLabelErrors.setText(ResourceBundle.getBundle(ETIQUETAS).getString("NoTokens"));
				}catch(BetIsMultiple e2) {
					lblNewLabelErrors.setForeground(Color.RED);
					lblNewLabelErrors.setText(ResourceBundle.getBundle(ETIQUETAS).getString("BetIsMultiple"));
				}catch(BetIsLocked e2) {
				lblNewLabelErrors.setForeground(Color.RED);
				lblNewLabelErrors.setText(ResourceBundle.getBundle(ETIQUETAS).getString("BetIsLocked"));
				}
				
				buttonDeleteBet.setEnabled(false);
				btnUseTokens.setEnabled(false);
			}
		});
		btnUseTokens.setBounds(451, 99, 129, 32);
		contentPane.add(btnUseTokens);
			
		btnDeleteMultiple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if (selectedMultiple!=null) {
					lblNewLabelErrors.setText("");
					BLFacade facade=MainGUI.getBusinessLogic();
					try {
						facade.deleteBet(selectedMultiple, u);
					} catch(LockedBetCantBeDeleted eL) {
						
					}
					multipleInfo.removeAllElements();
					RegisteredClient rG = facade.getRegisteredClient(u);
					betsCollection = facade.viewFutureBets(rG);
					
					for (BetPredictionQuestionEventContainer b : betsCollection) { 
//						System.out.println(b.toString());
						if(b.getBet().getMultiple())
							multipleInfo.addElement(b); 
					}
					comboBoxMultiple.setModel(multipleInfo);
					selectedMultiple=null;
					if (betsCollection.isEmpty()) lblNewLabelMessagesM.setText(ResourceBundle.getBundle(ETIQUETAS).getString("noMultipleBet"));
					 else lblNewLabelMessagesM.setText(ResourceBundle.getBundle(ETIQUETAS).getString(SELECTMULTIPLE));
					
					btnDeleteMultiple.setEnabled(false);
				} else {
					lblNewLabelErrorsM.setForeground(Color.RED);
					lblNewLabelErrorsM.setText(ResourceBundle.getBundle(ETIQUETAS).getString(SELECTMULTIPLE));
				}	
			}
		});
		btnDeleteMultiple.setBounds(332, 208, 184, 32);
		contentPane.add(btnDeleteMultiple);
	}
}
