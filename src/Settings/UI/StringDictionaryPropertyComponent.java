package Settings.UI;

import javax.swing.JPanel;

import Settings.StringDictionaryProperty;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class StringDictionaryPropertyComponent extends JPanel {

	public StringDictionaryPropertyComponent(StringDictionaryProperty property)
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel lblPropertyName = new JLabel("Property Name");
		add(lblPropertyName);
		
		final JPanel panelEntryContainer = new JPanel();
		add(panelEntryContainer);
		panelEntryContainer.setLayout(new BoxLayout(panelEntryContainer, BoxLayout.Y_AXIS));
		
		JButton btnAddEntry = new JButton("Add Entry");
		final StringDictionaryProperty finalProperty = property;
		btnAddEntry.addActionListener(new ActionListener() {
			/**
			 * Add a new map entry component
			 */
			public void actionPerformed(ActionEvent arg0) {
				panelEntryContainer.add(new MapEntryComponent("???", "???", finalProperty));
				
				// Refresh root frame so the entry shows up
				JFrame frame = (JFrame) SwingUtilities.getRoot(panelEntryContainer);
				frame.validate();
				frame.repaint();
			}
		});
		add(btnAddEntry);
		
		if(property != null)
		{
			// Set the property name
			lblPropertyName.setText(property.getName() + " : ");
			
			// Add a component for each entry in the map
			Iterator<Entry<String, String>> iterator = property.getValue().entrySet().iterator();
		    while (iterator.hasNext()) {
		    	// Get the next entry
		    	Map.Entry<String, String> keyValuePair = iterator.next();
		    	
		    	// Create and add a new component for the entry
		    	panelEntryContainer.add(new MapEntryComponent(keyValuePair.getKey(), keyValuePair.getValue(), property));
			}
		}
	}
}
