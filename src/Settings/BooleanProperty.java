package Settings;

public class BooleanProperty extends Property<Boolean> {

	public BooleanProperty(String name) {
		super(name);
	}

	// Property type
	public static final String PropertyType = "Boolean";
	
	@Override
	protected String getPropertyType() {
		return PropertyType;
	}

	@Override
	protected Boolean loadPropertyValue(String propertyValue) {
		return Boolean.parseBoolean(propertyValue);
	}

	@Override
	protected String getSerializedPropertyValue(Boolean value) {
		// If the value is null return false
		if(value == null)
			return Boolean.FALSE.toString();
		
		return value.toString();
	}
}
