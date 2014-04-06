import java.util.Properties;





import Settings.BooleanProperty;
import Settings.IProperty;
import Settings.PropertiesCollection;
import Settings.Property;
import Settings.StringProperty;


public class DataDictionaryProperties extends PropertiesCollection {

	// Property names
	public static final String SaveToMultipleFilesPropertyName = "SaveToMultipleFiles";
	public static final String VisitDescriptionToVisitIdMapPropertyName = "VisitDescriptionToVisitIdMap";
	
	public DataDictionaryProperties(String propertiesFilename) {
		super(propertiesFilename, getDefaultProperties());
		// TODO Auto-generated constructor stub
	}

	private static Properties getDefaultProperties()
	{
		Properties properties = new Properties();
		
		// Set defaults
		setProperty(properties, SaveToMultipleFilesPropertyName, new BooleanProperty(), Boolean.FALSE);
		
		return properties;
	}
	
	/**
	 * Set the property on the properties collection
	 * @param properties Properties instance to set property in
	 * @param key Property key
	 * @param property Property to set
	 */
	private static <T> void setProperty(Properties properties, String key, Property<T> property, T value)
	{
		property.set(value);
		properties.setProperty(key, property.Serialize());
	}
}
