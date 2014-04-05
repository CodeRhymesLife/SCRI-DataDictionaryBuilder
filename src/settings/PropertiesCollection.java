package Settings;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.formula.eval.NotImplementedException;


public class PropertiesCollection {
	// Properties file name
	private String _propertiesFilename;
	
	// Collection of properties
	private Properties _properties;
	
	// Maps property key to property object
	private Map<String, IProperty> _propertiesMap;
	
	public PropertiesCollection(String propertiesFilename, Properties defaults){
		_propertiesFilename = propertiesFilename;
		
		_properties = LoadPropertiesFromFile(defaults);
		
		_propertiesMap = CreatePropertiesMap(_properties);
	}
	
	/**
	 * Save properties
	 */
	public void Save()
	{
		throw new NotImplementedException("Save not implemented");
		/* http://www.mkyong.com/java/java-properties-file-examples/ */
	}
	
	/**
	 * Load properties from file
	 * @param filename Property filename
	 * @param defaults Default properties
	 */
	private Properties LoadPropertiesFromFile(Properties defaults)
	{
		Properties properties = new Properties(defaults);
		InputStream input = null;
		 
		try {
			// Load settings file from 
			input = PropertiesCollection.class.getClassLoader().getResourceAsStream(_propertiesFilename);
			
			// If file does not exist return
    		if(input==null){
	            System.out.println("Sorry, unable to find " + _propertiesFilename);
	            return properties;
    		}
	 
			// load a properties file
			properties.load(input);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return properties;
	}
	
	/**
	 * Creates a map of property keys to property instances
	 * @param properties Properties
	 * @return Map from property key to property instance
	 */
	private Map<String, IProperty> CreatePropertiesMap(Properties properties){
		HashMap<String, IProperty> propertyMap = new HashMap<String, IProperty>();
		
		// Iterate over each property and create a property instance from the string value
		for(String key : properties.stringPropertyNames()) {
			// Get a property instance from the property value
			IProperty property = PropertyFactory.GetProperty(properties.getProperty(key));
			
			// Save the property instance
			propertyMap.put(key, property);
		}
		
		return propertyMap;
	}
}
