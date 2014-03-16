
/**
 * Fetches data from the tube name property in FCS files
 * 
 * The tube name property is a string containing several sub properties
 * separated by spaces. This class fetches those sub property by index.
 * 
 * Here's an example of the tube name property:
 * 	"S01 D1 PB"
 * 	S01 - N/A
 * 	D1 - Visit description
 * 	PB - Sample Type
 * 	
 * @author Ryan
 *
 */
public class FCSFileTubeNamePropertyDataFetcher implements FCSFileDataFetcher {
	// Sub property of tube name property to fetch
	private SubProperty _subProperty;
	
	public FCSFileTubeNamePropertyDataFetcher(SubProperty subProperty)
	{
		_subProperty = subProperty;
	}
	
	/**
	 * Gets a sub property from the tube name property from the given FCS file
	 */
	@Override
	public String getData(FCSFile file) {
		return getSubProperty(file, _subProperty);
	}
	
	/**
	 * Gets the given sub property from the tube name property from the given FCS file
	 * @param file FCS file to get data from
	 * @param subProperty Sub property to get from tube name property
	 * @return Sub property value
	 */
	public static String getSubProperty(FCSFile file, SubProperty subProperty)
	{
		String tubeNameValue = file.getProperty("TUBE NAME");
		
		if(tubeNameValue == null)
			return null;
		
		// Get each sub property
		String[] subProperties = tubeNameValue.split(" ");
		
		// Get the sub property from the tube name property
		int propertyIndex = subProperty.getIndex();
		if(subProperties.length > propertyIndex)
			return subProperties[propertyIndex];
		
		// If the tube name property doesn't have the sub property return null
		return null;
	}

	public enum SubProperty
	{
		VisitDescription(1),
		SampleType(2);
		
		// Index of sub property in tube name property
		private final int _subPropertyIndex;
		
	    private SubProperty(int subPropertyIndex) {
	        _subPropertyIndex = subPropertyIndex;
	    }

	    /**
	     * Gets the sub property index in the tube name property
	     * @return sub property index
	     */
	    public int getIndex() {
	        return _subPropertyIndex;
	    }
	}
}
