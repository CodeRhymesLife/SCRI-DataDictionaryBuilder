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
	public static final String SaveToMultipleFilesPropertyName = "Save To Multiple Files";
	public static final String DataDictionaryNamePropertyName = "Data Dictionary Name";
	public static final String VisitDescriptionToVisitIdMapPropertyName = "VisitDescription To VisitId Map";
	
	public DataDictionaryProperties(String propertiesFilename) {
		super(propertiesFilename, getDefaultProperties());
	}
	
	/**
	 * Get the save to multiple files property
	 * @return Save to multiple files property
	 */
	public BooleanProperty getSaveToMultipleFilesProperty()
	{
		return (BooleanProperty)getProperty(SaveToMultipleFilesPropertyName);
	}
	
	/**
	 * Get the data dictionary name property
	 * @return Data dictionary name property
	 */
	public StringProperty getDataDictionaryNameProperty()
	{
		return (StringProperty)getProperty(DataDictionaryNamePropertyName);
	}
	
	/**
	 * Get the visit description to visit id property
	 * @return Visit description to visit id property
	 */
	public StringDictionaryProperty getVisitDescriptionToVisitIdMapProperty()
	{
		return (StringDictionaryProperty)getProperty(VisitDescriptionToVisitIdMapPropertyName);
	}

	/**
	 * Get default properties
	 * @return Default properties
	 */
	private static Properties getDefaultProperties()
	{
		Properties properties = new Properties();
		
		// Set defaults
		{
			setProperty(properties, SaveToMultipleFilesPropertyName, new BooleanProperty(SaveToMultipleFilesPropertyName), Boolean.TRUE);
			
			setProperty(properties, DataDictionaryNamePropertyName, new StringProperty(DataDictionaryNamePropertyName), "DataDictionary");
			
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
			setProperty(properties, VisitDescriptionToVisitIdMapPropertyName, new StringDictionaryProperty(SaveToMultipleFilesPropertyName), visitDescriptionToVisitIdMap);
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
		property.setValue(value);
		properties.setProperty(key, property.Serialize());
	}
}
