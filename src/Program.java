import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


/**
 * Creates data dictionaries from FCS files to enable SCRI to easily import files into LabKey 
 * @author Ryan
 *
 */
public class Program {

	/**
	 * Asks the user for a directory and creates data dictionaries
	 * in the selected directory, and all sub directories, if the directory contains at least one FCS file
	 * @param args Not Used
	 */
	public static void main(String[] args) {
		// Load our settings
		DataDictionaryProperties settings = new DataDictionaryProperties("settings.txt");
		
		// Create our frame and set defaults
		DataDictionaryJFrame frame = new DataDictionaryJFrame(settings);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Show frame
		frame.setVisible(true);
		
		// Generate data dictionaries when the gnerate button is clicked
		final DataDictionaryGenerator dataDictionaryGenerator = new DataDictionaryGenerator(settings);
		frame.addGenerateActionListener(new ActionListener()
		{
			/**
			 * Generate dictionaries
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dataDictionaryGenerator.GenerateDataDictionaries();
			}
		});
	}

	
	
}
