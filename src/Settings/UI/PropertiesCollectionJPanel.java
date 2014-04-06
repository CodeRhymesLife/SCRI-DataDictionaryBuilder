package Settings.UI;

import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JFrame;

import Settings.IProperty;
import Settings.PropertiesCollection;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PropertiesCollectionJPanel extends JPanel {

	public PropertiesCollectionJPanel(PropertiesCollection propertiesCollection)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Create the settings title
		JLabel lblTitle = new JLabel("Settings");
		add(lblTitle);
		
		// Create property component container
		JPanel panelPropertyComponentContainer = new JPanel();
		panelPropertyComponentContainer.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		add(panelPropertyComponentContainer);
		panelPropertyComponentContainer.setLayout(new BoxLayout(panelPropertyComponentContainer, BoxLayout.Y_AXIS));
		
		// Create a component for each property and add it to the container
		addPropertyComponentsFromPropertiesCollection(panelPropertyComponentContainer, propertiesCollection);
		
		// Create the save button
		JButton btnSave = new JButton("Save Settings");
		final PropertiesCollection finalPropertiesCollection = propertiesCollection;
		btnSave.addActionListener(new ActionListener() {
			/**
			 * Save the properties collection when the property is clicked
			 */
			public void actionPerformed(ActionEvent arg0) {
				if(finalPropertiesCollection != null)
					finalPropertiesCollection.Save();
			}
		});
		add(btnSave);
	}
	
	/**
	 * Create a property component for each property in the collection and add it to the content pane
	 * @param propertiesCollection
	 */
	private void addPropertyComponentsFromPropertiesCollection(JPanel panelPropertyComponentContainer, PropertiesCollection propertiesCollection)
	{
		if(propertiesCollection == null)
			return;
		
		// Get a UI component for each property and add it to the content pane
		for(IProperty property : propertiesCollection.getProperties())
		{
			// Get the property component
			JComponent propertyComponenet = PropertyComponentFactory.GetComponentForProperty(property);
			
			// If the property component is valid add it to the content pane
			if(propertyComponenet != null)
				panelPropertyComponentContainer.add(propertyComponenet);
		}
	}
}
