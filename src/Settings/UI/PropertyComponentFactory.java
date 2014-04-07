package Settings.UI;

import javax.swing.JComponent;

import Settings.BooleanProperty;
import Settings.IProperty;
import Settings.StringDictionaryProperty;
import Settings.StringProperty;

public class PropertyComponentFactory {

	public static JComponent GetComponentForProperty(IProperty property)
	{
		// Create a property component for the property
		if(property instanceof BooleanProperty)
			return new BooleanPropertyComponent((BooleanProperty)property);
		if(property instanceof StringProperty)
			return new StringPropertyComponent((StringProperty)property);
		if(property instanceof StringDictionaryProperty)
			return new StringDictionaryPropertyComponent((StringDictionaryProperty)property);
		
		return null;
	}
}
