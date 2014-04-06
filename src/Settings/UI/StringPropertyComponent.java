package Settings.UI;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

import Settings.BooleanProperty;
import Settings.StringProperty;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Component;
import java.awt.Dimension;

public class StringPropertyComponent extends JPanel {
	public StringPropertyComponent(StringProperty property) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel lblPropertyName = new JLabel("Property Name");
		add(lblPropertyName);
		
		final JTextField textPropertyValue = new JTextField();
		textPropertyValue.setMinimumSize(new Dimension(300, 30));
		textPropertyValue.setMaximumSize(new Dimension(300, 30));
		textPropertyValue.setPreferredSize(new Dimension(300, 30));
		add(textPropertyValue);
		textPropertyValue.setColumns(10);
		
		if(property != null)
		{
			// Set the property name
			lblPropertyName.setText(property.getName() + " : ");
			
			// Set the property value
			textPropertyValue.setText(property.getValue());
			
			// Add change listener
			final StringProperty finalProperty = property;
			textPropertyValue.getDocument().addDocumentListener(new DocumentListener() {
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
				 * Update the underlying string property with the new value
				 */
				private void updateProperty()
				{
					finalProperty.setValue(textPropertyValue.getText());
				}
			});
		}
	}

}
