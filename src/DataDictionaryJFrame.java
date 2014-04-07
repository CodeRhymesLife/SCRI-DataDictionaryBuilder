import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Settings.UI.PropertiesCollectionJPanel;
import Settings.PropertiesCollection;

import java.awt.Component;


public class DataDictionaryJFrame extends JFrame {
	// Generate button
	private JButton _btnGenerate;
	
	public DataDictionaryJFrame(PropertiesCollection propertiesCollection) {
		super("Data Dictionary Builder");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		// Add space
		getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
		
		_btnGenerate = new JButton("Generate");
		_btnGenerate.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(_btnGenerate);
		
		// Add space
		getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
		
		// If we have properties then show them in a JFrame if the user wants to edit them
		if(propertiesCollection != null)
		{
			// Allow the user to see and edit settings
			JButton btnSettings = new JButton("Settings");
			btnSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
			final PropertiesCollection finalPropertiesCollection = propertiesCollection;
			btnSettings.addActionListener(new ActionListener(){

				/**
				 * Show a settings window
				 */
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JFrame settingsFrame = new JFrame("Data Dictionary Builder - Settings");
					
					// Create the settings panel
					PropertiesCollectionJPanel propertiesCollectionJPanel = new PropertiesCollectionJPanel(finalPropertiesCollection);
					propertiesCollectionJPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
					getContentPane().add(propertiesCollectionJPanel);
					
					settingsFrame.getContentPane().add(propertiesCollectionJPanel);
					settingsFrame.setSize(new Dimension(1000, 500));
					//settingsFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
					
					// Show settings frame
					settingsFrame.setVisible(true);
				}
			
			});
			getContentPane().add(btnSettings);
		}
		
	}

	/**
	 * Add action listener to generate button
	 * @param actionListener action listener
	 */
	public void addGenerateActionListener(ActionListener actionListener)
	{
		_btnGenerate.addActionListener(actionListener);
	}
}
