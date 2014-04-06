package Settings;

/**
 * Represents a property
 * @author Ryan
 *
 */
public interface IProperty {
	/**
	 * Loads the property value into this property
	 * @param propertyValue Property value to load
	 * @return True if load successful. False otherwise.
	 */
	boolean load(String propertyValue);
	
	/**
	 * Serializes the given property to a string
	 * @return Property as a string
	 */
	String Serialize();
}
