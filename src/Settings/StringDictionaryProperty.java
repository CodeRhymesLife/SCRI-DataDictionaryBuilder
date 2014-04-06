package Settings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class StringDictionaryProperty extends Property<Map<String, String>> {

	public StringDictionaryProperty(String name) {
		super(name);
	}

	// Property type
	public static final String PropertyType = "StringDictionary";

	@Override
	protected String getPropertyType() {
		return PropertyType;
	}

	@Override
	protected Map<String, String> loadPropertyValue(String propertyValue) {
		String[] keyValuePairs = propertyValue.split(Separator);
		Map<String, String> map = new HashMap<String, String>();
		
		// Get the key value pairs from the 
		for(int i = 0; i < keyValuePairs.length; i = i + 2)
		{
			// Get the value for the given key if it's available
			if(i + 1 < keyValuePairs.length)
			{
				String key = keyValuePairs[i];
				String value = keyValuePairs[i + 1];
				
				// Save the key value pair
				map.put(key, value);
			}
		}

		return map;
	}

	@Override
	protected String getSerializedPropertyValue(Map<String, String> value) {
		String serializedValue = "";
		
		// Loop over key value properties and create a string
		Iterator<Entry<String, String>> iterator = value.entrySet().iterator();
	    while (iterator.hasNext()) {
	        Map.Entry<String, String> keyValuePair = iterator.next();
	        
	        // Store the key value pair
	        serializedValue += keyValuePair.getKey() + Separator + keyValuePair.getValue() + Separator;
	    }

		return serializedValue;
	}

}
