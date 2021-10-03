package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;

import javax.swing.JLabel;

public class DeleteEventGUI extends JFrame {
	
	private JCalendar calendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;
	private JButton deleteEventButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent"));
	private JLabel chooseEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("chooseDate"));
	private JLabel errorText = new JLabel(); 
	private JButton jButtonGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("goBack"));
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	private JPanel contentPane=null;
	
	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	
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
	/**
	 * Create the frame.
	 */
	public DeleteEventGUI(JFrame previousFrame) {
		try {
			jbInit(previousFrame);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void jbInit(JFrame previousFrame) throws Exception {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(604, 370));
		getContentPane().setLayout(null);
		//setBounds(100, 100, 450, 300);
		
		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(248, 53, 266, 20));
		
//		JLabel lblNewLabel = new JLabel("Bets21");
//		lblNewLabel.setBounds(10, 11, 49, 14);
//		contentPane.add(lblNewLabel);
		
		calendar.setBounds(10, 63, 200, 121);
		chooseEvent.setBounds(22, 21, 200, 14);

		errorText.setHorizontalAlignment(SwingConstants.CENTER);
		errorText.setFont(new Font("Tahoma", Font.PLAIN, 9));
		errorText.setForeground(Color.RED);
		errorText.setBounds(133, 239, 293, 14);

		getContentPane().add(calendar);
		getContentPane().add(chooseEvent);
		getContentPane().add(jButtonGoBack);
		getContentPane().add(deleteEventButton);
		getContentPane().add(errorText);
		getContentPane().add(jComboBoxEvents, null);


		deleteEventButton.setBounds(317, 230, 135, 23);
		deleteEventButton.setEnabled(false);
		deleteEventButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//deleteEventButton_actionPerformed(e);
				domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());
				
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.deleteEvent(event);
				
				Date firstDay = UtilDate.trim(calendarAct.getTime());
				Vector<domain.Event> events = facade.getEvents(firstDay);
				jComboBoxEvents.removeAllItems();
				System.out.println("Events " + events);
				for (domain.Event ev : events)
					modelEvents.addElement(ev);
				jComboBoxEvents.repaint();
				if (events.size() == 0)
					deleteEventButton.setEnabled(false);
				else
					deleteEventButton.setEnabled(true);
				
			}
		});
		deleteEventButton.setEnabled(false);

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
					//BLFacade facade = MainGUI.getBusinessLogic();
					datesWithEventsCurrentMonth=facade.getEventsMonth(calendar.getDate());
					deleteEventButton.setEnabled(true);
					paintDaysWithEvents(calendar,datesWithEventsCurrentMonth);
					Date firstDay = UtilDate.trim(calendarAct.getTime());
					try {
						BLFacade facade = MainGUI.getBusinessLogic();
						Vector<domain.Event> events = facade.getEvents(firstDay);
//						if (events.isEmpty())
//							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
//									+ ": " + dateformat1.format(calendarAct.getTime()));
//						else
//							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
//									+ dateformat1.format(calendarAct.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);
						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();
						if (events.size() == 0)
							deleteEventButton.setEnabled(false);
						else
							deleteEventButton.setEnabled(true);
					} catch (Exception e1) {
						//jLabelError.setText(e1.getMessage());
						System.out.println("error");
					}
				}
			}

		});

		jButtonGoBack.setBounds(new Rectangle(72, 230, 89, 23));
		jButtonGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				previousFrame.setVisible(true);
			}
		});
	}

}
