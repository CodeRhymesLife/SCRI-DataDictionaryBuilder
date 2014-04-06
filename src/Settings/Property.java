package Settings;

public abstract class Property<T> implements IProperty {
	
	// Separates values in the property string
	protected static final String Separator = ";";
	
	// Property name
	private String _name;
	
	// Property value
	private T _propertyValue;
	
	public Property(String name)
	{
		if(name == null)
			throw new NullPointerException("name");
		
		_name = name;
	}
	
	@Override
	public String getName()
	{
		return _name;
	}
	
	@Override
	public boolean load(String property)
	{
		// Null property values are invalid
		if(property == null)
			return false;
		
		String propertyTypePrefix = getPropertyTypePrefix();
		
		// Is the property type correct?
		if(!property.startsWith(propertyTypePrefix))
			return false;
		
		// Get the value portion of the property string
		String propertyValue = property.substring(propertyTypePrefix.length());
		
		// Load the property value
		T value = loadPropertyValue(propertyValue);
		
		// Is the value valid?
		if(value == null)
			return false;
		
		// Save the value
		_propertyValue = value;
		
		return true;
	}
	
	/**
	 * Serializes the given property to a string
	 * @return Property as a string
	 */
	public String Serialize()
	{
		return getPropertyTypePrefix() + getSerializedPropertyValue(getValue());
	}
	
	/**
	 * Get the property value
	 * @return Property value
	 */
	public T getValue()
	{
		return _propertyValue;
	}
	
	/**
	 * Sets the property value
	 * @param propertyValue Property value
	 * @return property value
	 */
	public boolean setValue(T propertyValue)
	{
		// If this value is invalid return false
		if(propertyValue == null)
			return false;
		
		_propertyValue = propertyValue;
		return true;
	}

	/**
	 * Get the property type
	 * @return Property type
	 */
	protected abstract String getPropertyType();
	
	/**
	 * Loads property value from property string
	 * @param propertyValue Property value
	 * @return True if load successful. False otherwise.
	 */
	protected abstract T loadPropertyValue(String propertyValue);
	
	/**
	 * Get the serialized property value
	 * @param value Property value
	 * @return Serialize property value
	 */
	protected abstract String getSerializedPropertyValue(T value);
	
	/**
	 * Get the property type prefix
	 * @return Property type prefix
	 */
	private String getPropertyTypePrefix()
	{
		return getPropertyType() + Separator;
	}
}
