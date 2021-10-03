package gui;

import java.util.Calendar;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import domain.BetPredictionQuestionEventContainer;
import domain.Prediction;
import domain.RegisteredClient;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import configuration.UtilDate;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import java.awt.Color;


public class ViewBetsGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel Fromtext = new JLabel("From:");
	JLabel toText = new JLabel("To:");
	JComboBox<Integer> year1 = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> years1 = new DefaultComboBoxModel<Integer>();
	JComboBox<String> month1 = new JComboBox<String>();
	DefaultComboBoxModel<String> months1 = new DefaultComboBoxModel<String>();
	JComboBox<Integer> day1 = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> days1 = new DefaultComboBoxModel<Integer>();
	JComboBox<Integer> year2 = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> years2 = new DefaultComboBoxModel<Integer>();
	JComboBox<String> month2 = new JComboBox<String>();
	DefaultComboBoxModel<String> months2 = new DefaultComboBoxModel<String>();
	JComboBox<Integer> day2 = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> days2 = new DefaultComboBoxModel<Integer>();
	Calendar calendar = new GregorianCalendar();
	private int muga;
	private Integer selectedMonth1;
	private Integer selectedYear1;
	private Integer selectedMonth2; 
	private Integer selectedYear2;
	private int y1,m1,d1,y2,m2,d2;
	private JButton btnGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("goBack")); //$NON-NLS-1$ //$NON-NLS-2$
	private JButton btnViewMovements = new JButton(ResourceBundle.getBundle("Etiquetas").getString("viewMovementsBet")); //$NON-NLS-1$ //$NON-NLS-2$
	private Vector<BetPredictionQuestionEventContainer> betsCollection;
	private JScrollPane scrollPane = new JScrollPane();
	private JTable table= new JTable();
	private DefaultTableModel tableModel;
	private RegisteredClient logged_rc;
	private JRadioButton allMovements = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("allBets")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NoBets")); //$NON-NLS-1$ //$NON-NLS-2$
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"), 
			ResourceBundle.getBundle("Etiquetas").getString("Prediction"),
			ResourceBundle.getBundle("Etiquetas").getString("Money"),
			ResourceBundle.getBundle("Etiquetas").getString("Multiple"),
	};
	
	/**
	 * Create the frame.
	 */
	public ViewBetsGUI(JFrame previousFrame, RegisteredClient logged) {
		try {
			jbInit(previousFrame, logged);
			logged_rc=logged;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	private void jbInit(JFrame previousFrame, RegisteredClient logged) throws Exception {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("viewMovementsBet"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 300);
		getContentPane().setLayout(null);
		Fromtext.setBounds(10, 28, 46, 14);
		
			getContentPane().add(Fromtext);
			toText.setBounds(10, 90, 46, 14);
			getContentPane().add(toText);
			year1.setBounds(10, 53, 58, 20);
			getContentPane().add(year1);
			month1.setBounds(78, 53, 119, 20);
			getContentPane().add(month1);
			day1.setBounds(207, 53, 46, 20);
			getContentPane().add(day1);
			year2.setBounds(10, 115, 58, 20);
			getContentPane().add(year2);
			day2.setBounds(207, 115, 46, 20);
			getContentPane().add(day2);
			
		
			int actualYear = calendar.get(Calendar.YEAR);
			int actualMonth = calendar.get(Calendar.MONTH);
			int actualDay = calendar.get(Calendar.DATE);

			muga = actualYear - 5;
			for (int i = actualYear; i >= muga; i--) {
				years1.addElement(i);
				years2.addElement(i);
			}
			year1.setModel(years1);
			year2.setModel(years2);
			
			year1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedYear1 = (Integer) year1.getSelectedItem();
					months1.removeAllElements();
					allMovements.setEnabled(false);
					 if(selectedYear1==actualYear) {
						 switch (actualMonth) 
					        {
					            case 0:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
					                     break;
					            case 1:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
					                     break;
					            case 2:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
					                     break;
					            case 3:	 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
									 	 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
									 	 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
									 	 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
					            		 break;
					            case 4:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
					                     break;
					            case 5:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
					                     break;
					            case 6:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
									 	 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
									 	 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
									 	 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
									 	 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
									 	 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
									 	 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
					                     break;
					            case 7:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
										 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
					                     break;
					            case 8:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
								 		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
								 		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
								 		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
								 		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
								 		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
								 		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
								 		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
								 		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
								 		 break;
					            case 9:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
					            		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
					            		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
					            		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
					            		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
					            		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
					            		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
					            		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
					            		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
					            		 months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
					            		 break;
					            case 10:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
						 		 		  break;
					            case 11:  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
						 		 		  months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("December"));
						 		 		  break;
					             
					        }															
												 
					 }else {
						    months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
							months1.addElement(ResourceBundle.getBundle("Etiquetas").getString("December"));
					 }
					 month1.setModel(months1);
				}
			});

			
			year2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedYear2 = (Integer) year2.getSelectedItem();
					months2.removeAllElements();
					 if(selectedYear2==actualYear) {
						 switch (actualMonth) 
					        {
					            case 0:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
					                     break;
					            case 1:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
					                     break;
					            case 2:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
					                     break;
					            case 3:	 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
									 	 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
									 	 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
									 	 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
					            		 break;
					            case 4:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
					                     break;
					            case 5:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
					                     break;
					            case 6:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
									 	 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
									 	 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
									 	 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
									 	 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
									 	 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
									 	 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
					                     break;
					            case 7:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
										 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
					                     break;
					            case 8:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
								 		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
								 		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
								 		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
								 		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
								 		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
								 		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
								 		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
								 		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
								 		 break;
					            case 9:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
					            		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
					            		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
					            		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
					            		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
					            		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
					            		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
					            		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
					            		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
					            		 months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
					            		 break;
					            case 10:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
						 		 		  break;
					            case 11:  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
						 		 		  months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("December"));
						 		 		  break;
					             
					        }															
												 
					 }else {
						    months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
							months2.addElement(ResourceBundle.getBundle("Etiquetas").getString("December"));
					 }
					 month2.setModel(months2);
				}
			});
			
			month1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					selectedMonth1 = month1.getSelectedIndex();
					selectedYear1 = (Integer) year1.getSelectedItem();
					days1.removeAllElements();
					GregorianCalendar calendar = new GregorianCalendar();
					if (selectedMonth1 == actualMonth && selectedYear1==actualYear) {
						switch (selectedMonth1 + 1) {
						case 1:
						case 3:
						case 5:
						case 7:
						case 8:
						case 10:
						case 12:
							int i = 1;
							while (i <= actualDay && i <= 31) {
								days1.addElement(i);
								i++;
							}
							break;
						case 4:
						case 6:
						case 9:
						case 11:
							int y = 1;
							while (y <= actualDay && y <= 30) {
								days1.addElement(y);
								y++;
							}
							break;
						case 2:
							if (calendar.isLeapYear(actualYear)) {
								int z = 1;
								while (z <= actualDay && z <= 29) {
									days1.addElement(z);
									z++;
								}
							} else {
								// days if the year is not leap
								int j = 1;
								while (j <= actualDay && j <= 28) {
									days1.addElement(j);
									j++;
								}
							}
							break;
						}
					} else {
						switch (selectedMonth1 + 1) {
						case 1:
						case 3:
						case 5:
						case 7:
						case 8:
						case 10:
						case 12:
							for (int i = 1; i <= 31; i++) {
								days1.addElement(i);
							}
							break;
						case 4:
						case 6:
						case 9:
						case 11:
							// days
							for (int i = 1; i <= 30; i++) {
								days1.addElement(i);
							}
							break;
						case 2:
							if (calendar.isLeapYear(actualYear)) {
								// days if the year is leap
								for (int i = 1; i <= 29; i++) {
									days1.addElement(i);
								}
							} else {
								// days if the year is not leap
								for (int i = 1; i <= 28; i++) {
									days1.addElement(i);
								}
							}
							break;
						}
					}
					day1.setModel(days1);
				}
			});
			month2.setBounds(78, 115, 119, 20);
			
		
			
			month2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedMonth2 = month2.getSelectedIndex();
					selectedYear2 = (Integer) year2.getSelectedItem();
					days2.removeAllElements();
					GregorianCalendar calendar = new GregorianCalendar();
					if (selectedMonth2 == actualMonth && selectedYear2==actualYear) {
						switch (selectedMonth2 + 1) {
						case 1:
						case 3:
						case 5:
						case 7:
						case 8:
						case 10:
						case 12:
							int i = 1;
							while (i <= actualDay && i <= 31) {
								days2.addElement(i);
								i++;
							}
							break;
						case 4:
						case 6:
						case 9:
						case 11:
							int y = 1;
							while (y <= actualDay && y <= 30) {
								days2.addElement(y);
								y++;
							}
							break;
						case 2:
							if (calendar.isLeapYear(actualYear)) {
								int z = 1;
								while (z <= actualDay && z <= 29) {
									days2.addElement(z);
									z++;
								}
							} else {
								// days if the year is not leap
								int j = 1;
								while (j <= actualDay && j <= 28) {
									days2.addElement(j);
									j++;
								}
							}
							break;
						}
					} else {
						switch (selectedMonth1 + 1) {
						case 1:
						case 3:
						case 5:
						case 7:
						case 8:
						case 10:
						case 12:
							for (int i = 1; i <= 31; i++) {
								days2.addElement(i);
							}
							break;
						case 4:
						case 6:
						case 9:
						case 11:
							// days
							for (int i = 1; i <= 30; i++) {
								days2.addElement(i);
							}
							break;
						case 2:
							if (calendar.isLeapYear(actualYear)) {
								// days if the year is leap
								for (int i = 1; i <= 29; i++) {
									days2.addElement(i);
								}
							} else {
								// days if the year is not leap
								for (int i = 1; i <= 28; i++) {
									days2.addElement(i);
								}
							}
							break;
						}
					}
					day2.setModel(days2);
					btnViewMovements.setEnabled(true);
				}
			});
			getContentPane().add(month2);
			
			btnViewMovements.setBounds(102, 199, 188, 23);
			
			btnViewMovements.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(year1.getSelectedIndex()!=-1 && year2.getSelectedIndex()!=-1) {
					y1=(int) year1.getSelectedItem();
					m1= month1.getSelectedIndex();
					d1=(int) day1.getSelectedItem();
					y2=(int) year2.getSelectedItem();
					m2=month2.getSelectedIndex();
					d2=(int) day2.getSelectedItem();
					Date date1= UtilDate.newDate(y1,m1,d1);
					Date date2= UtilDate.newDate(y2,m2,d2);
					tableModel.setDataVector(null, columnNamesEvents);
					tableModel.setColumnCount(5);
					
					BLFacade facade = MainGUI.getBusinessLogic();
					betsCollection = facade.viewBets(logged_rc, date1, date2);					
					int kont=0;
					allMovements.setEnabled(false);
					if(betsCollection.size()==0) {
						lblNewLabel.setForeground(Color.RED);
						lblNewLabel.setBounds(300, 236, 563, 14);
						lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("NoBets"));
					}else {	
					for(BetPredictionQuestionEventContainer bet: betsCollection) {
						for(Prediction p: bet.getPredictions()) {
							if(bet.getPredictions().size()==1) {
								if((date1.before(date2) || date1.equals(date2)) && (p.getQuestion().getEvent().getEventDate().after(date1) || p.getQuestion().getEvent().getEventDate().equals(date1)) && (p.getQuestion().getEvent().getEventDate().before(date2)|| p.getQuestion().getEvent().getEventDate().equals(date2))) {
									Vector<Object> row = new Vector<Object>();
									System.out.println("Predicciones:"+p.getPrediction());
									row.add(p.getQuestion().getEvent().getDescription());
									row.add(p.getQuestion().getQuestion());
									row.add(p.getPrediction()+" x "+p.getFee());
									row.add(bet.getBet().getMoney());
									row.add("-");
									tableModel.addRow(row);
									kont++;
								}
							}else {
									if((date1.before(date2) || date1.equals(date2)) && (p.getQuestion().getEvent().getEventDate().after(date1) || p.getQuestion().getEvent().getEventDate().equals(date1)) && (p.getQuestion().getEvent().getEventDate().before(date2)|| p.getQuestion().getEvent().getEventDate().equals(date2))) {
										Vector<Object> row = new Vector<Object>();
										System.out.println("Predicciones:"+p.getPrediction());
										row.add(p.getQuestion().getEvent().getDescription());
										row.add(p.getQuestion().getQuestion());
										row.add(p.getPrediction()+" x "+p.getFee());
										row.add(bet.getBet().getMoney());
										row.add("Nº "+bet.getBet().getBetNumber());
										tableModel.addRow(row);
										kont++;
										
									}
								}
						
					}
						
						lblNewLabel.setForeground(Color.black);
						lblNewLabel.setBounds(300, 236, 563, 14);
						lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AmountOfBets")+kont);
						
					}
					table.getColumnModel().getColumn(0).setPreferredWidth(158);
					table.getColumnModel().getColumn(1).setPreferredWidth(280); 
					table.getColumnModel().getColumn(2).setPreferredWidth(130);
					table.getColumnModel().getColumn(3).setPreferredWidth(45);
					table.getColumnModel().getColumn(4).setPreferredWidth(45);
					}
					allMovements.setEnabled(true);
					allMovements.setSelected(false);
				}
			}
		});
			
			getContentPane().add(lblNewLabel);
			getContentPane().add(btnViewMovements);
			
			
	        scrollPane.setBounds(300, 53, 563, 169);
	       
	     
		
	        scrollPane.setViewportView(table);
	        tableModel= new DefaultTableModel(null, columnNamesEvents);
	    	table.setModel(tableModel);
	    	table.getColumnModel().getColumn(0).setPreferredWidth(158);
			table.getColumnModel().getColumn(1).setPreferredWidth(280); 
			table.getColumnModel().getColumn(2).setPreferredWidth(130);
			table.getColumnModel().getColumn(3).setPreferredWidth(45);
			table.getColumnModel().getColumn(4).setPreferredWidth(45);
	     
	        
			getContentPane().add(scrollPane);
			
			btnGoBack.setBounds(10, 199, 89, 23);
			btnGoBack .addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					previousFrame.setVisible(true);
				}
			});
	
			getContentPane().add(btnGoBack);
			allMovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(allMovements.isSelected()==true) {
					tableModel.setDataVector(null, columnNamesEvents);
					tableModel.setColumnCount(5);
					int kont=0;
					BLFacade facade = MainGUI.getBusinessLogic();
					betsCollection = facade.viewAllBets(logged_rc);
					java.util.Date dtoday= new java.util.Date();
					for(BetPredictionQuestionEventContainer bet: betsCollection) { 
							for(Prediction p: bet.getPredictions()) {
								if(bet.getPredictions().size()==1) {
									if(p.getQuestion().getEvent().getEventDate().before(dtoday)|| p.getQuestion().getEvent().getEventDate().equals(dtoday)) {
										Vector<Object> row = new Vector<Object>();
										System.out.println("Predicciones:"+p.getPrediction());
										row.add(p.getQuestion().getEvent().getDescription());
										row.add(p.getQuestion().getQuestion());
										row.add(p.getPrediction()+" x "+p.getFee());
										row.add(bet.getBet().getMoney());
										row.add("-");
										tableModel.addRow(row);
										kont++;
									}
								}else {
									if(p.getQuestion().getEvent().getEventDate().before(dtoday)|| p.getQuestion().getEvent().getEventDate().equals(dtoday)) {
										Vector<Object> row = new Vector<Object>();
										System.out.println("Predicciones:"+p.getPrediction());
										row.add(p.getQuestion().getEvent().getDescription());
										row.add(p.getQuestion().getQuestion());
										row.add(p.getPrediction()+" x "+p.getFee());
										row.add(bet.getBet().getMoney());
										row.add("Nº "+bet.getBet().getBetNumber());
										tableModel.addRow(row);
										kont++;
										}
								}
								
								
							
						}
						
						lblNewLabel.setForeground(Color.black);
						lblNewLabel.setBounds(300, 236, 563, 14);
						lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AmountOfBets")+kont);
					}
					table.getColumnModel().getColumn(0).setPreferredWidth(158);
					table.getColumnModel().getColumn(1).setPreferredWidth(280); 
					table.getColumnModel().getColumn(2).setPreferredWidth(130);
					table.getColumnModel().getColumn(3).setPreferredWidth(45);
					table.getColumnModel().getColumn(4).setPreferredWidth(45);
					month1.removeAllItems();
					month2.removeAllItems();
					day1.removeAllItems();
					day2.removeAllItems();
				}
				btnViewMovements.setEnabled(false);
			}
			});
			
			
			allMovements.setBounds(10, 158, 223, 23);
			getContentPane().add(allMovements);
			
			
			
		}
}
