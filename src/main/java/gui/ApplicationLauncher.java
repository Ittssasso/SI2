package gui;

import java.awt.Color;
import java.util.Locale;

import javax.swing.UIManager;

import configuration.ConfigXML;
import factory.BLFactory;
import businessLogic.BLFacade;

public class ApplicationLauncher {

	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();

		System.out.println(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		System.out.println("Locale: " + Locale.getDefault());

		MainGUI a = new MainGUI();
		a.setVisible(true);
		
		BLFactory blf = new BLFactory();

		try {
			BLFacade appFacadeInterface;
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			appFacadeInterface = blf.getBusinessLogic(c);
			/*
			 * if (c.getDataBaseOpenMode().equals("initialize"))
			 * appFacadeInterface.initializeBD();
			 */
			MainGUI.setBussinessLogic(appFacadeInterface);

		} catch (Exception e) {
			a.jLabelSelectOption.setText("Error: " + e.toString());
			a.jLabelSelectOption.setForeground(Color.RED);

			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
		// a.pack();
	}

}
