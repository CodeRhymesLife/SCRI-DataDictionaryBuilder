package Settings.UI;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Settings.BooleanProperty;

public class BooleanPropertyComponent extends JPanel {
	public BooleanPropertyComponent(BooleanProperty property) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel lblPropertyName = new JLabel("Property Name");
		add(lblPropertyName);
		
		final JCheckBox chckbxPropertyValue = new JCheckBox("");
		add(chckbxPropertyValue);
		
		if(property != null)
		{
			// Set the property name
			lblPropertyName.setText(property.getName() + "? ");
			
			// Set the checkbox's value
			chckbxPropertyValue.setSelected(property.getValue());
			
			// Add change listener
			final BooleanProperty finalProperty = property;
			chckbxPropertyValue.addChangeListener(new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent arg0) {
					finalProperty.setValue(chckbxPropertyValue.isSelected());
				}
			});
		}
	}

}
