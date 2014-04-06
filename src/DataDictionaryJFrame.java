import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import Settings.UI.PropertiesCollectionJPanel;
import Settings.PropertiesCollection;


public class DataDictionaryJFrame extends JFrame {
	// Generate button
	private JButton _btnGenerate;
	
	public DataDictionaryJFrame(PropertiesCollection propertiesCollection) {
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		_btnGenerate = new JButton("Generate");
		getContentPane().add(_btnGenerate);
		
		PropertiesCollectionJPanel propertiesCollectionJPanel = new PropertiesCollectionJPanel(propertiesCollection);
		getContentPane().add(propertiesCollectionJPanel);
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
