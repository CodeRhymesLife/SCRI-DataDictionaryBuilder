
/**
 * Fetches file name data from FCS files
 * @author Ryan
 *
 */
public class FCSFileNameDataFetcher implements FCSFileDataFetcher {

	/**
	 * Fetches the file name from a given FCS file
	 */
	@Override
	public String getData(FCSFile file) {
		return file.getName();
	}

}
