package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Bet;
import domain.Prediction;
import domain.Question;
import domain.RegisteredClient;
import exceptions.EventFinished;
import iterator.ExtendedIterator;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;

public class ToBetGUI extends JFrame{

	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 
	private final JLabel jLabelPredictions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Predictions"));
	private JLabel jLabel_error;
	private JLabel lblLabelBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MoneyBet"));
	private JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblNewLabel_1 = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblNewLabel_3 = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblNewLabel_4 = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblNewLabel_5 = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblNewLabel_6 = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblNewLabel_7 = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
	
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("goBack"));
	private JButton jButton_PutResult = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ToBet"));
	private JButton jButtonAddPred = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddPrediction")); //$NON-NLS-1$ //$NON-NLS-2$
	
	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPanePredictions = new JScrollPane();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tablePredictions = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelPredictions;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private String[] columnNamesPredictions = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("PredictionN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Prediction")

	};
	
	private JTextField textFieldBet = new JTextField();
	
	private Vector<Prediction> preds = new Vector<Prediction>();

	private Prediction lastPred = null;
	

	public ToBetGUI(JFrame previousFrame, RegisteredClient u)
	{
		
		try
		{
			jbInit(previousFrame, u);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit(JFrame previousFrame, RegisteredClient u) throws Exception
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(705, 656));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ToBet"));

		jLabelEventDate.setBounds(new Rectangle(40, 25, 140, 14));
		jLabelQueries.setBounds(292, 204, 382, 14);
		jLabelEvents.setBounds(292, 24, 225, 16);
		jLabelPredictions.setBounds(40,203,200,16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		this.getContentPane().add(jLabelPredictions);
		
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				BLFacade facade=MainGUI.getBusinessLogic();
				JFrame a = new RegisteredClientGUI((RegisteredClient) facade.getRegisteredClient(u));
				a.setVisible(true);
			}
		});

		jButtonClose.setBounds(new Rectangle(416, 547, 154, 30));
		this.getContentPane().add(jButtonClose, null);

		jButton_PutResult.setBounds(new Rectangle(416, 497, 154, 30));
		getContentPane().add(jButton_PutResult);
		jButton_PutResult.setEnabled(false);
		
		jLabel_error = new JLabel();
		jLabel_error.setBounds(178, 587, 327, 20);
		getContentPane().add(jLabel_error);
		
		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					 
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													
					

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=MainGUI.getBusinessLogic();

//						Vector<domain.Event> events=facade.getEvents(firstDay);
						ExtendedIterator<domain.Event> events=facade.getEvents(firstDay);

						events.goLast();
						domain.Event ev;
						if(!events.hasPrevious()) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						while (events.hasPrevious()) {
							ev = events.previous();
							Vector<Object> row = new Vector<Object>();
							System.out.println("Events "+ev);
							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
//						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
//						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
//						for (domain.Event ev:events){
//							Vector<Object> row = new Vector<Object>();
//
//							System.out.println("Events "+ev);
//
//							row.add(ev.getEventNumber());
//							row.add(ev.getDescription());
//							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
//							tableModelEvents.addRow(row);
//						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(292, 229, 346, 150));
		scrollPanePredictions.setBounds(new Rectangle(40, 230, 225, 149));
		
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jLabel_error.setText("");
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3);

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q);
					tableModelQueries.addRow(row);
				}

				tableQueries.setModel(tableModelQueries);
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2));
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);


		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		tableQueries.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Question#", "Question"
				}
			));
			tableQueries.getColumnModel().getColumn(0).setPreferredWidth(66);
			tableQueries.getColumnModel().getColumn(0).setMinWidth(25);
			tableQueries.getColumnModel().getColumn(1).setPreferredWidth(250);
		
		
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jLabel_error.setText("");
				int i=tableQueries.getSelectedRow();
				domain.Question q=(domain.Question)tableModelQueries.getValueAt(i,2);
				Vector<Prediction> predictions=q.getPredictions();

				tableModelPredictions.setDataVector(null, columnNamesPredictions);
				tableModelPredictions.setColumnCount(3);
				
				for (domain.Prediction p:predictions){
					Vector<Object> row = new Vector<Object>();

					row.add(p.getPredictionNumber());
					row.add(p.getPrediction());
					row.add(p);
					tableModelPredictions.addRow(row);	
				}
				tablePredictions.setModel(tableModelPredictions);
				tablePredictions.getColumnModel().getColumn(0).setPreferredWidth(25);
				tablePredictions.getColumnModel().getColumn(1).setPreferredWidth(150);
				tablePredictions.getColumnModel().removeColumn(tablePredictions.getColumnModel().getColumn(2));
			}
		});
		
		scrollPanePredictions.setViewportView(tablePredictions);
		tableModelPredictions = new DefaultTableModel(null, columnNamesPredictions);
		tablePredictions.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Prediction#", "Prediction"
				}
			));
		tablePredictions.getColumnModel().getColumn(0).setPreferredWidth(70);
		tablePredictions.getColumnModel().getColumn(1).setPreferredWidth(150);
		

		tablePredictions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableQueries.getSelectedRow();
				domain.Question q=(domain.Question)tableModelQueries.getValueAt(i,2);
				if(q.getPredictions()!=null) {
					jButtonAddPred.setEnabled(true);
					lastPred = (domain.Prediction)tableModelPredictions.getValueAt(tablePredictions.getSelectedRow(),2);
					
				}
					
			}
		});
		
		jButton_PutResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(tableEvents.getSelectedRow(),2);
				int i=tableQueries.getSelectedRow();
				domain.Question q=(domain.Question)tableModelQueries.getValueAt(i,2);
				int i2=tablePredictions.getSelectedRow();
				domain.Prediction p=(domain.Prediction)tableModelPredictions.getValueAt(i2,2);
				if (textFieldBet.getText()!=null) {
					try {
					Float money = Float.valueOf(textFieldBet.getText());
						if(u.getBalance()>=money) {
							 if (facade.toBet(preds, money, u, ev.getEventDate(), ev.getDescription())) {
								jLabel_error.setForeground(Color.BLACK);
								jLabel_error.setText(ResourceBundle.getBundle("Etiquetas").getString("BetDone"));
								preds = new Vector<Prediction>();
								lblNewLabel_3.setText("");
								lblNewLabel_4.setText("");
								lblNewLabel_5.setText("");
								lblNewLabel_6.setText("");
								lblNewLabel_7.setText("");
								lblNewLabel_1.setText("0");
								jButtonAddPred.setEnabled(false);
								jButton_PutResult.setEnabled(false);
							 } else {
								 jLabel_error.setForeground(Color.RED);
									jLabel_error.setText(ResourceBundle.getBundle("Etiquetas").getString("BetMoneyLower"));
							 }					
						} else {
							jLabel_error.setForeground(Color.RED);
							jLabel_error.setText(ResourceBundle.getBundle("Etiquetas").getString("NotEnoughMoney"));
						}
					}catch (EventFinished eventFinished) {
						jLabel_error.setForeground(Color.RED);
						jLabel_error.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
					}
					
				}
				
			}
		});
		
		
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPanePredictions, null);
		
		
		
		textFieldBet.setBounds(442, 434, 96, 19);
		getContentPane().add(textFieldBet);
		textFieldBet.setColumns(10);
		
		lblLabelBet.setBounds(395, 411, 230, 13);
		getContentPane().add(lblLabelBet);
		
		
		lblNewLabel.setBounds(414, 474, 91, 13);
		getContentPane().add(lblNewLabel);
		
		
		lblNewLabel_1.setBounds(493, 474, 45, 13);
		getContentPane().add(lblNewLabel_1);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		lblNewLabel_3.setBounds(40, 411, 303, 13);
		getContentPane().add(lblNewLabel_3);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		
		lblNewLabel_4.setBounds(40, 437, 303, 13);
		getContentPane().add(lblNewLabel_4);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		
		lblNewLabel_5.setBounds(40, 463, 303, 13);
		getContentPane().add(lblNewLabel_5);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		
		lblNewLabel_6.setBounds(40, 486, 303, 13);
		getContentPane().add(lblNewLabel_6);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		
		lblNewLabel_7.setBounds(40, 509, 303, 13);
		getContentPane().add(lblNewLabel_7);
		
		jButtonAddPred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (preds.size()>=5) {
					jLabel_error.setForeground(Color.RED);
					jLabel_error.setText(ResourceBundle.getBundle("Etiquetas").getString("ToMuchPredictions"));
				} else {
					if (preds.contains(lastPred)) {
						jLabel_error.setForeground(Color.RED);
						jLabel_error.setText(ResourceBundle.getBundle("Etiquetas").getString("PredictionAlreadySelected"));	
					} else {
						jLabel_error.setForeground(Color.BLACK);
						jLabel_error.setText("");
						preds.add(lastPred);
						String t = lastPred.getQuestion().getEvent().getDescription() 
								  + "; " + lastPred.getQuestion().getQuestion() + "; " + lastPred.getPrediction();
						switch(preds.size()) {
						  case 1:
							  lblNewLabel_3.setText(t);
						    break;
						  case 2:
							  lblNewLabel_4.setText(t);
						    break;
						  case 3:
							  lblNewLabel_5.setText(t);
						    break;
						  case 4:
							  lblNewLabel_6.setText(t);
							break;
						  case 5:
							  lblNewLabel_7.setText(t);
				  		    break;
						  default:
						}
						float minBet = 0;
						for (Prediction pi : preds) {
							minBet = minBet + pi.getQuestion().getBetMinimum();
						}
						lblNewLabel_1.setText(String.valueOf(minBet));
						jButton_PutResult.setEnabled(true);
					}
				}
					
				
			}
		});
		jButtonAddPred.setBounds(95, 547, 145, 30);
		getContentPane().add(jButtonAddPred);
		jButtonAddPred.setEnabled(false);
		
		JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Bets")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_2.setBounds(39, 388, 45, 13);
		getContentPane().add(lblNewLabel_2);
		
		

	}
}
