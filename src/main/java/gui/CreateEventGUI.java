package gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JCalendar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import businessLogic.BLFacade;
import exceptions.EventAlreadyExists;
import exceptions.QuestionAlreadyExist;

import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Font;
import javax.swing.SwingConstants;


public class CreateEventGUI extends JFrame {

	private JTextField txtTeam1 = new JTextField();
	private JTextField txtTeam2 = new JTextField();
	private JCalendar calendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;
	private JButton createEventButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
	private JLabel chooseEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("chooseDate"));
	private JLabel vsEvent = new JLabel("VS");
	private JLabel errorText = new JLabel(); 
	private JButton jButtonGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("goBack"));
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();


	/**
	 * Create the frame.
	 */
	public CreateEventGUI(JFrame previousFrame) {
		try {
			jbInit(previousFrame);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(JFrame previousFrame) throws Exception{
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().setLayout(null);
		setBounds(100, 100, 450, 300);

		calendar.setBounds(10, 63, 200, 121);
		chooseEvent.setBounds(22, 21, 200, 14);
		vsEvent.setBounds(300, 105, 18, 14);
		txtTeam1.setBounds(220, 63, 167, 20);
		txtTeam2.setBounds(220, 143, 167, 20);
		errorText.setHorizontalAlignment(SwingConstants.CENTER);
		errorText.setFont(new Font("Tahoma", Font.PLAIN, 9));
		errorText.setForeground(Color.RED);
		errorText.setBounds(133, 239, 293, 14);

		getContentPane().add(calendar);
		getContentPane().add(chooseEvent);
		getContentPane().add(vsEvent);
		getContentPane().add(txtTeam1);
		getContentPane().add(txtTeam2);
		getContentPane().add(jButtonGoBack);
		getContentPane().add(createEventButton);
		getContentPane().add(errorText);

		txtTeam1.setColumns(10);
		txtTeam2.setColumns(10);

		createEventButton.setBounds(236, 203, 135, 23);
		createEventButton.setEnabled(false);
		createEventButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createEventButton_actionPerformed(e);
			}
		});
		createEventButton.setEnabled(false);


		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(calendar.getDate());
		paintDaysWithEvents(calendar,datesWithEventsCurrentMonth);

		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("locale")) {
					calendar.setLocale((Locale) evt.getNewValue());
				} else if (evt.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) evt.getOldValue();
					calendarAct = (Calendar) evt.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar est√É ° 30 de enero y se avanza al mes siguiente, devolver√É ≠a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este c√É ≥digo se dejar√É ° como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						calendar.setCalendar(calendarAct);						

					}
					BLFacade facade = MainGUI.getBusinessLogic();
					datesWithEventsCurrentMonth=facade.getEventsMonth(calendar.getDate());
					createEventButton.setEnabled(true);
					paintDaysWithEvents(calendar,datesWithEventsCurrentMonth);
				}
			}

		});

		jButtonGoBack.setBounds(new Rectangle(73, 203, 89, 23));
		jButtonGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				previousFrame.setVisible(true);
			}
		});

	}

	public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		//Yo esto lo borrarÌa
		// For each day with events in current month, the background color for that day is changed.
		Calendar calendar = jCalendar.getCalendar();
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);
//		if (Locale.getDefault().equals(new Locale("es")))
//			offset += 4;
//		else
			offset += 5;
		for (Date d:datesWithEventsCurrentMonth){
			calendar.setTime(d);
			System.out.println(d);
			//Yo esto lo borrarÌa
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			//				    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}
		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
	}
	private void createEventButton_actionPerformed(ActionEvent e) {
		try{
			BLFacade appFacadeInterface =MainGUI.getBusinessLogic();;
			Date d=calendar.getDate();
			java.util.Date dtoday= new java.util.Date();
			if(d.after(dtoday)) {
				String t1= txtTeam1.getText();
				System.out.println(t1);
				String t2= txtTeam2.getText();
				System.out.print(t2);
				errorText.setForeground(Color.RED);
				if (t1==null || t2==null) {
					errorText.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsError1"));
				} else {
					appFacadeInterface.createEvent(t1,t2,d);
					errorText.setForeground(Color.GREEN);
					errorText.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsGood"));
				}
				
			}else {
				createEventButton.setEnabled(false);
				errorText.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsError2"));
			}
		} catch (EventAlreadyExists e1) {
			errorText.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsError3"));
		}
	}

}
