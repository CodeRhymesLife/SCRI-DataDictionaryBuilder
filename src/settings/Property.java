package Settings;

public abstract class Property<T> implements IProperty {
	
	/**
	 * Get the property value
	 * @return Property value
	 */
	public abstract T Get();
	
	/**
	 * Sets the property value
	 * @param propertyValue Property value
	 */
	public abstract boolean Set(T propertyValue);
}
