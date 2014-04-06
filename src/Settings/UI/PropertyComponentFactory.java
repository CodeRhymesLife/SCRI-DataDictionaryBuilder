package Settings.UI;

import javax.swing.JComponent;

import Settings.BooleanProperty;
import Settings.IProperty;
import Settings.StringProperty;

public class PropertyComponentFactory {

	public static JComponent GetComponentForProperty(IProperty property)
	{
		if(property instanceof BooleanProperty)
			return new BooleanPropertyComponent((BooleanProperty)property);
		if(property instanceof StringProperty)
			return new StringPropertyComponent((StringProperty)property);
		
		return null;
	}
}
