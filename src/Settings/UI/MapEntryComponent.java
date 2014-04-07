package Settings.UI;

import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Settings.StringDictionaryProperty;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MapEntryComponent extends JPanel {
	// Entry key
	private String _key;
	
	// Entry value
	private String _value;
	
	public MapEntryComponent(String key, String value, StringDictionaryProperty property) {
		
		final JTextField textKey = new JTextField("key");
		add(textKey);
		textKey.setColumns(10);
		
		JLabel lblArrow = new JLabel("->");
		add(lblArrow);
		
		final JTextField textValue = new JTextField("value");
		add(textValue);
		textValue.setColumns(10);
		
		if(key != null && value != null)
		{
			// Set the key text
			textKey.setText(key);
			
			// Set the value text
			textValue.setText(value);
			
			// Update the property when values change
			// And add the ability to delete an entry
			if(property != null)
			{
				_key = key;
				_value = value;
				
				final StringDictionaryProperty finalProperty = property;
				DocumentListener changeListener = new DocumentListener() {
					public void changedUpdate(DocumentEvent e)
					{
						updateProperty();
					}
					
					public void removeUpdate(DocumentEvent e)
					{
						updateProperty();
					}
					
					public void insertUpdate(DocumentEvent e)
					{
						updateProperty();
					}
					
					/**
					 * Update the underlying map property with the new key value pair
					 */
					private void updateProperty()
					{
						// Get the map
						Map<String, String> map = finalProperty.getValue();
						
						// Remove the old entry
						map.remove(MapEntryComponent.this._key);
						
						// Add the new entry
						map.put(textKey.getText(), textValue.getText());
						
						// Save the values
						MapEntryComponent.this._key = textKey.getText();
						MapEntryComponent.this._value = textValue.getText();
					}
				};
				
				// Add a listener for changes in the key value pair
				textKey.getDocument().addDocumentListener(changeListener);
				textValue.getDocument().addDocumentListener(changeListener);
				
				// Delete entries
				JButton btnDelete = new JButton("Delete");
				btnDelete.addActionListener(new ActionListener() {
					/**
					 * Delete this entry
					 */
					public void actionPerformed(ActionEvent arg0) {
						// Get the map
						Map<String, String> map = finalProperty.getValue();
						
						// Delete this entry
						map.remove(MapEntryComponent.this._key);
						
						// Disable the key value boxes
						textKey.setEnabled(false);
						textValue.setEnabled(false);
					}
				});
				add(btnDelete);
			}
		}
	}

}
