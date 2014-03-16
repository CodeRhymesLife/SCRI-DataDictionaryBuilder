
/**
 * Fetches property data from a given FCS file
 * @author Ryan
 *
 */
public class FCSFilePropertyDataFetcher implements FCSFileDataFetcher {
	// Property key to fetch from FCS files
	private String _propertyKey;
	
	public FCSFilePropertyDataFetcher(String propertyKey)
	{
		_propertyKey = propertyKey;
	}
	
	/**
	 * Fetches property data from the given FCS file
	 */
	@Override
	public String getData(FCSFile file) {
		return file.getProperty(_propertyKey);
	}
}
