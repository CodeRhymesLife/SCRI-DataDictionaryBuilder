import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

/**
 * This class epresents an FCS file and allows consumers to interact with FCS file properties
 * through an easy to use api.
 * 
 * @author Ryan
 *
 */
public class FCSFile extends File {
	// FCS file version
	private String _version;
	
	// FCS file content
	private String _content;
	
	// Start and end position of properties text in content string
	private int _propertiesStartIndex;
	private int _propertiesEndIndex;
	
	// Start and end position of data text in content string
	private int _dataStartIndex;
	private int _dataEndIndex;
	
	// Start and end position of analysis text in content string
	private int _analysisStartIndex;
	private int _analysisEndIndex;
	
	// Delimiter between properties in properties string
	private String _propertiesDelimiter;
	
	// Maps property keys to property values
	private Map<String, String> _properties;
	
	public FCSFile(String path) throws IOException {
		super(path);
		
		if(!this.exists())
			throw new IllegalArgumentException("File passed in to FCS file constructor does not exist");
		
		String fileExtension = FilenameUtils.getExtension(this.getAbsolutePath());
		if(!fileExtension.equalsIgnoreCase("fcs"))
			throw new IllegalArgumentException("File passed in to FCSFile constructor has invalid file extension: " + fileExtension);
		
		// Read the FCS file's contents
		_content = new String(Files.readAllBytes(this.toPath()), StandardCharsets.UTF_8);
		
		ParseHeaderArgs();
		
		BuildPropertyMap();
	}
	
	/**
	 * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key. 
	 * @param key - the key whose associated value is to be returned 
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key 
	 */
	public String getProperty(String key)
	{
		return _properties.get(key);
	}
	
	/**
	 * Parse header args from the FCS file's content
	 */
	private void ParseHeaderArgs()
	{
		// Get the header string
		String header = _content.substring(0, 58);
		
		// Parse individual args from header string
		_version = header.substring(0, 6).trim();
		_propertiesStartIndex = Integer.parseInt(header.substring(10, 18).trim());
		_propertiesEndIndex = Integer.parseInt(header.substring(18, 26).trim());
		_dataStartIndex = Integer.parseInt(header.substring(26, 34).trim());
		_dataEndIndex = Integer.parseInt(header.substring(34, 42).trim());
		_analysisStartIndex = Integer.parseInt(header.substring(42, 50).trim());
		_analysisEndIndex = Integer.parseInt(header.substring(50, 58).trim());
	}
	
	/**
	 * Builds the property map from the properties section of the FCS file
	 */
	private void BuildPropertyMap()
	{
		// The first char is the delimiter
		_propertiesDelimiter = _content.substring(_propertiesStartIndex, _propertiesStartIndex + 1);
		
		// The properties come next
		String propertiesText = _content.substring(_propertiesStartIndex + 1, _propertiesEndIndex + 1);
		
		// Each pair key value pair is separated by the delimeter
		String[] kvps = propertiesText.split(_propertiesDelimiter);
		
		// Store key value pairs in in the properties map
		_properties = new HashMap<String, String>();
		for(int i = 0; i < kvps.length; i = i + 2)
		{
			_properties.put(kvps[i], kvps[i+1]);
		}
	}
}
