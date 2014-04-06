import java.util.HashMap;
import java.util.Map;

/**
 * Fetches visit id data from specified FCS files.
 * The visit id is determined from the visit description.
 * @author Ryan
 *
 */
public class FCSFileVisitIdPropertyDataFetcher implements FCSFileDataFetcher {
	// Maps visit description to visit id
	private Map<String, String> _visitDescriptionToVisitIdMap;
	
	public FCSFileVisitIdPropertyDataFetcher(Map<String, String> visitDescriptionToVisitIdMap)
	{
		if(visitDescriptionToVisitIdMap == null)
			throw new NullPointerException("visitDescriptionToVisitIdMap");
		
		_visitDescriptionToVisitIdMap = visitDescriptionToVisitIdMap;
	}
	
	/**
	 * Gets visit id data from the the given FCS file.
	 */
	@Override
	public String getData(FCSFile file) {
		// Get the visit description
		String visitDescription = FCSFileTubeNamePropertyDataFetcher.getSubProperty(file,
				FCSFileTubeNamePropertyDataFetcher.SubProperty.VisitDescription);
		
		// Gets the visit id from the visit description
		return _visitDescriptionToVisitIdMap.get(visitDescription);
	}
}
