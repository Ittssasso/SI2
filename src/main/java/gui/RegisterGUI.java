package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Window;

import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.RegisteredClient;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JPasswordField; 
import java.util.regex.Matcher; 
import java.util.regex.Pattern;
import java.awt.Checkbox;

public class RegisterGUI extends JFrame{
	
private static final long serialVersionUID=1L;
	
	private JFrame RegisterFrame=this;
	private JTextField DNI;
	private JTextField inputName;
	private JTextField inputSurnames;
	private JTextField inputEmail;
	private JTextField inputCurrentAccount;
	
	Integer selectedDay, selectedMonth, selectedYear;
	boolean selected;
	boolean dateSelected;
	private JPasswordField passwordField;
	private JPasswordField confirmPassword;
	private boolean permission;
	
//	private Date selectedDate;
	
	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RegisterGUI window = new RegisterGUI(previousFrame);
//					window.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public RegisterGUI(JFrame previousFrame) {
		try {
			selected=false;
		initialize(previousFrame);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Date newDate(int year,int month,int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day,0,0,0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
	 
	/**
	 * Initialize the contents of the RegisterGUI
	 */
	private void initialize(JFrame previousFrame) throws Exception{
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 476, 331);
		getContentPane().setLayout(null);
		//YEARS
		JComboBox<Integer> year = new JComboBox<Integer>();
		DefaultComboBoxModel<Integer> years= new DefaultComboBoxModel<Integer>();
		//MONTHS
		JComboBox<String> month = new JComboBox<String>();
		DefaultComboBoxModel<String> months= new DefaultComboBoxModel<String>();
		 //DAYS
		 JComboBox<Integer> day = new JComboBox<Integer>();
		 DefaultComboBoxModel<Integer> days= new DefaultComboBoxModel<Integer>();
		 
		month.setEnabled(false);
		day.setEnabled(false);
		
		DNI = new JTextField();
		DNI.setToolTipText("");
		DNI.setForeground(SystemColor.desktop);
		DNI.setBounds(219, 8, 145, 20);
		getContentPane().add(DNI);
		DNI.setColumns(10);
		
		JLabel LabelDNI = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DNINumber"));
		//LabelDNI.setText(ResourceBundle.getBundle("Etiquetas").getString("DNINumber"));
		LabelDNI.setBounds(57, 11, 119, 14);
		getContentPane().add(LabelDNI);
		
		
		inputName = new JTextField();
		inputName.setBounds(219, 31, 145, 20);
		getContentPane().add(inputName);
		inputName.setColumns(10);
		
		JLabel LabelName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Name"));
		LabelName.setBounds(57, 34, 119, 14);
		getContentPane().add(LabelName);
		
		inputSurnames = new JTextField();
		inputSurnames.setBounds(219, 54, 145, 20);
		getContentPane().add(inputSurnames);
		inputSurnames.setColumns(10);
		
		JLabel LabelSurnames = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Surnames"));
		LabelSurnames.setBounds(57, 57, 119, 14);
		getContentPane().add(LabelSurnames);
		
		inputEmail = new JTextField();
		inputEmail.setBounds(219, 107, 176, 20);
		getContentPane().add(inputEmail);
		inputEmail.setColumns(10);
		
		passwordField = new JPasswordField();
		//passwordField.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.passwordField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		passwordField.setBounds(219, 132, 162, 20);
		getContentPane().add(passwordField);
		
		confirmPassword = new JPasswordField();
		//confirmPassword.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.passwordField_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		confirmPassword.setBounds(219, 156, 162, 20);
		getContentPane().add(confirmPassword);
		//YEARS
		//Take the actual date
		Calendar c2 = new GregorianCalendar();
		int actualDay= c2.get(Calendar.DATE);
		int actualMonth = c2.get(Calendar.MONTH);
		int actualYear = c2.get(Calendar.YEAR);

		//calculate who has 18 years old
		int moreThan18Years=actualYear-18;
				for(int i=moreThan18Years;i>=1910;i--) {
					years.addElement(i);
				}
				year.setModel(years);
				year.setToolTipText("");
				year.setBounds(219, 78, 56, 22);
				getContentPane().add(year);
				
				
				year.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						 selectedYear = (Integer) year.getSelectedItem();
						 dateSelected=false;
						 months.removeAllElements();
						 month.setEnabled(true);
						 if(selectedYear==moreThan18Years) {
							 switch (actualMonth) 
						        {
						            case 0:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
						                     break;
						            case 1:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
						                     break;
						            case 2:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
						                     break;
						            case 3:	 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 	 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
										 	 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
										 	 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
						            		 break;
						            case 4:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
						                     break;
						            case 5:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
						                     break;
						            case 6:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 	 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
										 	 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
										 	 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
										 	 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
										 	 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
										 	 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
						                     break;
						            case 7:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
											 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
						                     break;
						            case 8:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
									 		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
									 		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
									 		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
									 		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
									 		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
									 		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
									 		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
									 		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
									 		 break;
						            case 9:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
						            		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
						            		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
						            		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
						            		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
						            		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
						            		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
						            		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
						            		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
						            		 months.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
						            		 break;
						            case 10:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
							 		 		  break;
						            case 11:  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
							 		 		  months.addElement(ResourceBundle.getBundle("Etiquetas").getString("December"));
							 		 		  break;
						            default:
						            		  break;
						             
						        }															
													 
						 }else {
							    months.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
								months.addElement(ResourceBundle.getBundle("Etiquetas").getString("December"));
						 }
						 month.setModel(months);
						 month.setBounds(281, 78, 90, 22);
						 getContentPane().add(month);
						 
						 //System.out.println((Integer) year.getSelectedItem());
						 
					}
				});

				month.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						 selectedMonth = month.getSelectedIndex();
						 dateSelected=false;
						 days.removeAllElements();
						 day.setEnabled(true);
						 GregorianCalendar calendar = new GregorianCalendar();
						 if(selectedYear==moreThan18Years && selectedMonth==actualMonth) {
							 switch(selectedMonth+1){
				             case 1:
				             case 3:
				             case 5:
				             case 7:
				             case 8:
				             case 10:
				             case 12:
				            	 int i=1;
				            	 while(i<=actualDay && i<=31) {
				            		 days.addElement(i);
				            		 i++;
				            	 }
				                 break;
				             case 4:
				             case 6:
				             case 9:
				             case 11:
				            	 int y=1;
				            	 while(y<=actualDay && y<=30) {
				            		 days.addElement(y);
				            		 y++;
				            	 }
				                 break;
				             case 2:
				                 if(calendar.isLeapYear(actualYear)){
				                	 int z=1;
					            	 while(z<=actualDay && z<=29) {
					            		 days.addElement(z);
					            		 z++;
					            	 }
				                 }else{
				                	//days if the year is not leap
				                	 int j=1;
					            	 while(j<=actualDay && j<=28) {
					            		 days.addElement(j);
					            		 j++;
					            	 }
				                 }
				                 break;
				             default:
				            	 break;
				         }
						 }else {
						         switch(selectedMonth+1){
						             case 1:
						             case 3:
						             case 5:
						             case 7:
						             case 8:
						             case 10:
						             case 12:
											for(int i=1;i<=31;i++) {
												days.addElement(i);
											}
						                 break;
						             case 4:
						             case 6:
						             case 9:
						             case 11:
						            	//days
											for(int i=1;i<=30;i++) {
												days.addElement(i);
											}
						                 break;
						             case 2:
						                 if(calendar.isLeapYear(actualYear)){
						                	//days if the year is leap
												for(int i=1;i<=29;i++) {
													days.addElement(i);
												}
						                 }else{
						                	//days if the year is not leap
												for(int i=1;i<=28;i++) {
													days.addElement(i);
												}
						                 }
						                 break;
						             default:
						            	 break;
						         }
						 }
						         day.setModel(days);
									day.setBounds(378, 78, 38, 22);
									getContentPane().add(day);
									
									//System.out.println(month.getSelectedIndex()+1);
					}
				});
				day.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dateSelected=true;
						 selectedDay = (Integer) day.getSelectedItem();
//						 System.out.println((Integer) day.getSelectedItem());
					}
				});
//				System.out.println(selectedYear);

		
		JLabel lblNewLabel_3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BirthDate"));
		lblNewLabel_3.setBounds(57, 82, 119, 14);
		getContentPane().add(lblNewLabel_3);
				
		
		JLabel lblNewLabel_4 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("E-mail"));
		lblNewLabel_4.setBounds(57, 110, 98, 14);
		getContentPane().add(lblNewLabel_4);
		
		inputCurrentAccount = new JTextField();
		inputCurrentAccount.setBounds(219, 179, 176, 20);
		getContentPane().add(inputCurrentAccount);
		inputCurrentAccount.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblNewLabel_5.setBounds(57, 135, 119, 14);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ConfirmPassword"));
		lblNewLabel_6.setBounds(57, 159, 131, 14);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CurrentAccount"));
		lblNewLabel_7.setBounds(57, 182, 119, 14);
		getContentPane().add(lblNewLabel_7);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox(ResourceBundle.getBundle("Etiquetas").getString("TermsConditions"));
		chckbxNewCheckBox.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
		            selected=true;
		        } else {//checkbox has been deselected
		            selected=false;
		        };
		    }
			});
		chckbxNewCheckBox.setBounds(82, 202, 313, 23);
		
		JLabel errorLabel = new JLabel("");
		errorLabel.setFont(new Font("Tahoma", Font.PLAIN, 8));
		errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(46, 263, 212, 33);
		getContentPane().add(errorLabel);
		errorLabel.setVisible(true);
		
		getContentPane().add(chckbxNewCheckBox);
		
		JCheckBox chckbxReplicable = new JCheckBox(ResourceBundle.getBundle("Etiquetas").getString("checkbox_replicable")); //$NON-NLS-1$ //$NON-NLS-2$
		
		chckbxReplicable.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
		            permission=true;
		        } else {//checkbox has been deselected
		        	permission=false;
		        };
		    }
			});
		chckbxReplicable.setBounds(275, 231, 145, 23);
		getContentPane().add(chckbxReplicable);
		
		JButton RegisterButton = new JButton("Register");
		RegisterButton.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterButton"));
		RegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorLabel.setForeground(Color.RED);
				//if there is any empty field
				if(DNI.getText().equals("") || inputName.getText().equals("") || inputSurnames.getText().equals("")
						|| inputEmail.getText().equals("") || passwordField.getText().equals("") || confirmPassword.getText().equals("")
						|| inputCurrentAccount.getText().equals("")) {
					errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel9"));
				}else {	
				//this checks if the inserted DNI Number is correct or not
				
				//if length of the DNI is lower or longer
				//OR
				//if the last character is not really a character
				String nanzbk = DNI.getText();
				if(DNI.getText().length()!=9 || Character.isLetter(DNI.getText().charAt(8))==false) {
					//ERROR
					errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel4"));
				}else {
					//if the letter is upperCase or not
					String upperCaseChar= DNI.getText().substring(8).toUpperCase();
					
					if(DNIChar(nanzbk).equals(upperCaseChar)==false) {
						errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel3"));
					}else if(!onlyNumbers(nanzbk)){
						//the numbers are not correct
						errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel5"));
					}else if(!checkName(inputName.getText())){
						errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel12"));
						}else if(!checkName(inputSurnames.getText())){
							errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel13"));
						}else if(!emailIsValid(inputEmail.getText())) {
							errorLabel.setText("asdasdasdaadsdasd");
							}else if (inputEmail.getText().equals(passwordField.getText())) {
								errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel6"));
							}else {
								if(!passwordField.getText().equals(confirmPassword.getText())) {
									if(passwordField.getText().length()<8) {
										errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel1"));
									}else {
										errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel2"));
									}
								}else if(!dateSelected) {
											errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel10"));
									}else if(selected) {
								
											BLFacade facade = MainGUI.getBusinessLogic();
											
											//System.out.println((Integer) year.getSelectedItem());
											//System.out.println(month.getSelectedIndex()+1);
											//System.out.println((Integer) day.getSelectedItem());
											
											Date d = newDate((Integer) year.getSelectedItem(), month.getSelectedIndex(), (Integer) day.getSelectedItem());
										
											
											
											Boolean alreadyStored = facade.storeRegisteredClient(inputName.getText(), inputSurnames.getText(), 
												d, DNI.getText(), inputEmail.getText(), passwordField.getText(), inputCurrentAccount.getText(), permission);
											if (alreadyStored) { 
												errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel11"));
												
											} else {
												errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel8"));
												errorLabel.setForeground(Color.GREEN);
												RegisterFrame.setVisible(false);
												RegisteredClient logged=(RegisteredClient) facade.isLogin(inputEmail.getText(), passwordField.getText());
												JFrame a = new RegisteredClientGUI(logged);
												a.setVisible(true);
											}
										}else {
											errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("errorLabel7"));
										}
									}
//									GO ON FOR storeRegisteredClient in the database
//									System.out.println(inputName.getText());
//									System.out.println(inputSurnames.getText());
//									System.out.println(selectedDate);
//									System.out.println(DNI.getText());
//									System.out.println(inputEmail.getText());
//									System.out.println(passwordField.getText());
//									System.out.println(inputCurrentAccount.getText());
									
									//System.out.println("passwords correct");
									}
								}
				
							}
						
		});
		RegisterButton.setBounds(82, 231, 152, 23);
		getContentPane().add(RegisterButton);
		
		JButton btnGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("goBack"));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousFrame.setVisible(true);
				setVisible(false);
			}
		});
		btnGoBack.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnGoBack.setBounds(352, 270, 89, 14);
		getContentPane().add(btnGoBack);
		
	}

	/**
	 * this returns actually which is the letter associated to the numbers 
	 * @param dni
	 * @return the letter associated
	 */
	private String DNIChar(String dni) {
		int myDNI = Integer.parseInt(dni.substring(0,8));
		int rest=0;
		String myChar="";
		String[] assignChar= {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};
		rest = myDNI%23;
		myChar= assignChar[rest];
		return myChar;
	}
	/**
	 * this checks if all 8 first characters are numbers given a DNI
	 * @param dni
	 * @return boolean
	 */
	private boolean onlyNumbers(String dni) {
		int i, j=0;
		String number="";
		String myDNI="";
		String[] zeroToNine= {"0","1","2","3","4","5","6","7","8","9"};
		for(i=0; i < dni.length()-1; i++) {
			number= dni.substring(i,i+1);
			
			for(j=0;j<zeroToNine.length; j++) {
				if(number.equals(zeroToNine[j])) {
					myDNI+=zeroToNine[j];
				}
			}
		}
		if(myDNI.length()!=8) {
			return false;
		}else {
			return true;
		}
	}
	
	private boolean checkName(String name) {
	      for (int i = 0; i < name.length(); i++) {
	         // checks whether the character is not a letter
	         // if it is not a letter ,it will return false
	    	 String str= " a";
	         if ((Character.isLetter(name.charAt(i)) == false)) {
	        	 if(!((name.charAt(i)==str.charAt(0)))) {
	        		 return false;
	        	 }
	         }
	      }
	      return true;
	}
	
	public static boolean emailIsValid(String email) 
    { 
        String emailRegex = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";                    
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 
}



