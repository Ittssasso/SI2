package gui;

import businessLogic.BLFacade;

import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.Question;
import domain.Prediction;
import exceptions.PredictionAlreadyExists;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;


public class CreatePredictionGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 
	private final JLabel jLabelPredictions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Predictions"));
	private final JLabel jLabel_newPrediction = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NewPrediction"));
	private final JLabel jLabel_newPredictionFee = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Fee"));
	private JLabel jLabel_error;
	
	private JTextField textField_Prediction;
	private JTextField textField_Fee;
	
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("goBack"));
	private JButton jButton_CreatePrediction = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreatePredictionGUI.lblNewLabel.text"));
	
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

	public CreatePredictionGUI(JFrame previousFrame)
	{
		try
		{
			jbInit(previousFrame);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit(JFrame previousFrame) throws Exception
	{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreatePredictionGUI.lblNewLabel.text"));

		jLabelEventDate.setBounds(new Rectangle(40, 25, 140, 14));
		jLabelQueries.setBounds(292, 204, 382, 14);
		jLabelEvents.setBounds(292, 24, 225, 16);
		jLabelPredictions.setBounds(40,203,200,16);
		jLabel_newPrediction.setFont(new Font("Tahoma", Font.PLAIN, 10));
		jLabel_newPrediction.setBounds(10, 337, 225, 14);
		jLabel_newPredictionFee.setFont(new Font("Tahoma", Font.PLAIN, 10));
		jLabel_newPredictionFee.setBounds(62, 362, 49, 14);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		this.getContentPane().add(jLabelPredictions);
		this.getContentPane().add(jLabel_newPrediction);
		this.getContentPane().add(jLabel_newPredictionFee);
		
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				previousFrame.setVisible(true);
			}
		});

		jButtonClose.setBounds(new Rectangle(414, 420, 154, 30));
		this.getContentPane().add(jButtonClose, null);

		jButton_CreatePrediction.setBounds(new Rectangle(86, 420, 154, 30));
		getContentPane().add(jButton_CreatePrediction);
		jButton_CreatePrediction.setEnabled(false);

		textField_Prediction = new JTextField();
		textField_Prediction.setBackground(new Color(255, 255, 255));
		textField_Prediction.setBounds(101, 334, 181, 20);
		getContentPane().add(textField_Prediction);
		textField_Prediction.setColumns(10);
		
		textField_Fee = new JTextField();
		textField_Fee.setBounds(101, 359, 181, 20);
		getContentPane().add(textField_Fee);
		textField_Fee.setColumns(10);
		
		jLabel_error = new JLabel();
		jLabel_error.setBounds(40, 390, 225, 20);
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
				jLabel_error.setText("");
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

						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
						
						jButton_CreatePrediction.setEnabled(false);
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(292, 229, 346, 116));
		scrollPanePredictions.setBounds(new Rectangle(40, 230, 225, 96));
		
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jLabel_error.setText("");
				
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelPredictions.setRowCount(0);
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
				
				jButton_CreatePrediction.setEnabled(false);
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
				tableModelPredictions.setColumnCount(2);
				
				for (domain.Prediction p:predictions){
					Vector<Object> row = new Vector<Object>();

					row.add(p.getPredictionNumber());
					row.add(p.getPrediction());
					tableModelPredictions.addRow(row);
				}
				
				tablePredictions.setModel(tableModelPredictions);
				tablePredictions.getColumnModel().getColumn(0).setPreferredWidth(25);
				tablePredictions.getColumnModel().getColumn(1).setPreferredWidth(150);
				jButton_CreatePrediction.setEnabled(true);
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
		
		jButton_CreatePrediction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					jLabel_error.setText("");
					int i=tableQueries.getSelectedRow();
					domain.Question q=(domain.Question)tableModelQueries.getValueAt(i,2);
						if(textField_Prediction.getText()!=null) {
						try {
							float fee = Float.parseFloat(textField_Fee.getText());
							if (fee>0) {
								facade.createPrediction(q, textField_Prediction.getText(), fee);
								jLabel_error.setText(ResourceBundle.getBundle("Etiquetas").getString("PredictionCreated"));
								
								//Taulak berritu
								Date day = jCalendar1.getDate();
								Vector<domain.Event> events = facade.getEvents(day);
								Vector<Prediction> predictions = new Vector<Prediction>();
								int i2=tableEvents.getSelectedRow();
								domain.Event event=(domain.Event)tableModelEvents.getValueAt(i2,2);
								
								for(domain.Event ev: events) {
									if(ev.getEventNumber().equals(event.getEventNumber())) {
										Vector<domain.Question> questions = ev.getQuestions();
										tableModelQueries.setRowCount(0);
										for (domain.Question question:questions){
											if(q.getQuestionNumber().equals(question.getQuestionNumber())) {
												predictions = question.getPredictions();
											}
											Vector<Object> row = new Vector<Object>();
											row.add(question.getQuestionNumber());
											row.add(question.getQuestion());
											row.add(question);
											tableModelQueries.addRow(row);
										}
										tableQueries.setModel(tableModelQueries);
										tableQueries.setRowSelectionInterval(i, i);
										
										break;
									}
								}
								
								tableModelPredictions.setRowCount(0);
								for (domain.Prediction pred:predictions){
									Vector<Object> row = new Vector<Object>();

									row.add(pred.getPredictionNumber());
									row.add(pred.getPrediction());
									tableModelPredictions.addRow(row);
								}
								tablePredictions.setModel(tableModelPredictions);
								//.
								textField_Prediction.setText(null);
								textField_Fee.setText(null);
							}
						}catch(NumberFormatException ex) {
							jLabel_error.setText(ResourceBundle.getBundle("Etiquetas").getString("CreatePrediction_IntegerError"));
						}
					} else
						jLabel_error.setText(ResourceBundle.getBundle("Etiquetas").getString("CreatePrediction_nullError"));
				}catch(PredictionAlreadyExists pe) {
					jLabel_error.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorPredictionAlreadyExists"));
					textField_Prediction.setText(null);
					textField_Fee.setText(null);
				}
			}
		});
		
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPanePredictions, null);

	}
}
