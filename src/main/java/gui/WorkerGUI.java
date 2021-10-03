package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import domain.Event;
import java.awt.Font;

public class WorkerGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton button_queryquestions = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
	private JButton button_createquestion=new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
	private JButton button_createevent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
	private JButton button_logout= new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
	private JButton button_createprediction = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreatePredictionGUI.lblNewLabel.text"));
	private JButton btnPutResult = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PutResultGUI.jButtonPutResult.text"));
	WorkerGUI workerFrame = this;
	
	public WorkerGUI() {
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void jbInit() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(null);
		setBounds(100, 100, 450, 300); 
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Worker"));
		
		button_queryquestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindQuestionsGUI(workerFrame);
				a.setVisible(true);
				workerFrame.setVisible(false);
			}
		});
		button_queryquestions.setBounds(10, 75, 182, 37);
		getContentPane().add(button_queryquestions);

		button_createquestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateQuestionGUI(new Vector<Event>(), workerFrame);
				a.setVisible(true);
				workerFrame.setVisible(false);
			}
		});
		button_createquestion.setBounds(10, 123, 182, 37);
		getContentPane().add(button_createquestion);
		
		button_createevent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateEventGUI(workerFrame);
				a.setVisible(true);
				workerFrame.setVisible(false);
			}
		});
		button_createevent.setBounds(10, 171, 182, 37);
		getContentPane().add(button_createevent);
		
		JLabel lblNewLabel = new JLabel("Bets21");
		lblNewLabel.setBounds(10, 11, 46, 14);
		getContentPane().add(lblNewLabel);
		button_logout.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		button_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MainGUI();
				a.setVisible(true);
				workerFrame.setVisible(false);
			}
		});
		button_logout.setBounds(168, 219, 114, 23);
		getContentPane().add(button_logout);
		
		btnPutResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new PutResultGUI(workerFrame);
				a.setVisible(true);
				workerFrame.setVisible(false);
			}
		});
		
		btnPutResult.setBounds(246, 123, 182, 37);
		getContentPane().add(btnPutResult);
		
		JButton btnDeleteEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDeleteEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new DeleteEventGUI(workerFrame);
				a.setVisible(true);
				workerFrame.setVisible(false);
			}
		});
		btnDeleteEvent.setBounds(246, 171, 182, 37);
		getContentPane().add(btnDeleteEvent);
		
		button_createprediction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreatePredictionGUI(workerFrame);
				a.setVisible(true);
				workerFrame.setVisible(false);
			}
		});
		button_createprediction.setBounds(246, 75, 182, 37);
		
		getContentPane().add(button_createprediction);
	}
}
