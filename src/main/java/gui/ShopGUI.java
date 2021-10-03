package gui;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Person;
import domain.RegisteredClient;
import exceptions.NotEnoughMoney;

import javax.swing.event.ChangeEvent;
import java.awt.Label;
import java.awt.Font;
import java.awt.Scrollbar;
import java.awt.Button;
import java.awt.Color;

import javax.swing.JSpinner;
import java.awt.Panel;

public class ShopGUI extends JFrame {

	private JPanel contentPane;
	private JButton jButtonGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("goBack"));
	private RegisteredClient logged_rc;
	
	public ShopGUI(JFrame previousFrame, RegisteredClient logged) {
		try {
			jbInit(previousFrame, logged);
			logged_rc=logged;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void jbInit(JFrame previousFrame, RegisteredClient logged) throws Exception {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("shop"));
		
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
		
		jButtonGoBack.setBounds(new Rectangle(54, 264, 89, 23));
		jButtonGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				RegisteredClientGUI a= new RegisteredClientGUI(logged_rc);
				a.setVisible(true);
			}
		});
		
		 BoundedRangeModel bRangeModel = 
			      new DefaultBoundedRangeModel(10, 0, 5, 150);
		JSlider slider = new JSlider(bRangeModel);
		slider.setValue(5);
		
	    
	    JLabel valueLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MoneyToInsert")+slider.getValue()+"€");
		valueLabel.setBounds(32, 67, 240, 14);
		contentPane.add(valueLabel);
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				valueLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyToInsert")+ slider.getValue()+"€");
			}
		});
		
		slider.setBounds(32, 99, 200, 38);
		contentPane.add(slider);
		
		JButton btnNewButton = new JButton("Insert money");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				RegisteredClient rc = facade.insertMoney(slider.getValue(), logged_rc);
				logged_rc=rc;
			}
		});
		btnNewButton.setBounds(54, 148, 117, 28);
		contentPane.add(btnNewButton);
		
		JLabel lblBuyTokens = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("quantityTokens"));
		lblBuyTokens.setBounds(330, 67, 233, 14);
		contentPane.add(lblBuyTokens);
		
		JLabel lblPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ShopGUI_price")+ " 5€");
		lblPrice.setBounds(494, 103, 88, 14);
		contentPane.add(lblPrice);
		
		JSpinner spinner = new JSpinner();
		spinner.setValue(1);
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblPrice.setText(ResourceBundle.getBundle("Etiquetas").getString("ShopGUI_price")+" "+ (int)spinner.getValue()*5 +" €");
			}
		});
		spinner.setBounds(414, 99, 48, 23);
		contentPane.add(spinner);
		
		JLabel lblbuyTokens = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NotEnoughMoney"));
		lblbuyTokens.setBounds(388, 200, 175, 14);
		contentPane.add(lblbuyTokens);
		lblbuyTokens.setForeground(Color.RED);
		lblbuyTokens.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblbuyTokens.setVisible(false);
		
		JButton btnBuyTokens = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ShopGUI_buyTokens_button")); 
		btnBuyTokens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				boolean success=true;
				try {
					RegisteredClient rc = facade.buyTokens((int)spinner.getValue(), (RegisteredClient)logged_rc);
					logged_rc=rc;
				}catch(NotEnoughMoney a) {
					success=false;
					lblbuyTokens.setVisible(true);
					lblbuyTokens.setForeground(Color.RED);
					lblbuyTokens.setFont(new Font("Calibri", Font.PLAIN, 13));
					lblbuyTokens.setText(ResourceBundle.getBundle("Etiquetas").getString("NotEnoughMoney"));
				}finally {
					if(success) {
						lblbuyTokens.setVisible(true);
						lblbuyTokens.setForeground(Color.GREEN);
						lblbuyTokens.setFont(new Font("Calibri", Font.BOLD, 13));
						lblbuyTokens.setText(ResourceBundle.getBundle("Etiquetas").getString("SuccessBuyingTokens"));
					}
				}
			}
		});
		btnBuyTokens.setBounds(388, 148, 107, 28);
		contentPane.add(btnBuyTokens);
		
		
		
		
	}
}
