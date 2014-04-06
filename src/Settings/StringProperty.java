package Settings;

public class StringProperty extends Property<String> {

	public StringProperty(String name) {
		super(name);
	}

	// Property type
	public static final String PropertyType = "String";
	
	@Override
	protected String getPropertyType() {
		return PropertyType;
	}

	@Override
	protected String loadPropertyValue(String propertyValue) {
		return propertyValue;
	}

	@Override
	protected String getSerializedPropertyValue(String value) {
		return value;
	}
}
