import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import Settings.PropertiesCollection;

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
		DataDictionaryProperties settings = new DataDictionaryProperties("config.properties");
		
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
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dataDictionaryGenerator.GenerateDataDictionaries();
			}
		});
	}

	
	
}
