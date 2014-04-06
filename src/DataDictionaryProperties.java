import java.util.Dictionary;
import java.util.HashMap;
import java.util.Properties;









import Settings.BooleanProperty;
import Settings.IProperty;
import Settings.PropertiesCollection;
import Settings.Property;
import Settings.StringDictionaryProperty;
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
		{
			setProperty(properties, SaveToMultipleFilesPropertyName, new BooleanProperty(), Boolean.FALSE);
			
			HashMap<String, String> visitDescriptionToVisitIdMap = new HashMap<String, String>();
			visitDescriptionToVisitIdMap.put("Screening", "100");
			visitDescriptionToVisitIdMap.put("PreP", "200");
			visitDescriptionToVisitIdMap.put("PreC", "300");
			visitDescriptionToVisitIdMap.put("D-1", "400");
			visitDescriptionToVisitIdMap.put("D1", "500");
			visitDescriptionToVisitIdMap.put("D3", "700");
			visitDescriptionToVisitIdMap.put("D7", "800");
			visitDescriptionToVisitIdMap.put("D10", "900");
			visitDescriptionToVisitIdMap.put("D14", "1000");
			visitDescriptionToVisitIdMap.put("D21", "1100");
			visitDescriptionToVisitIdMap.put("D28", "1200");
			visitDescriptionToVisitIdMap.put("D29", "1200");
			visitDescriptionToVisitIdMap.put("D36", "1300");
			visitDescriptionToVisitIdMap.put("D42", "1400");
			setProperty(properties, VisitDescriptionToVisitIdMapPropertyName, new StringDictionaryProperty(), visitDescriptionToVisitIdMap);
		}
		
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
