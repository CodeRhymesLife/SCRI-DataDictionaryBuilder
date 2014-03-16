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
	private static Map<String, String> _visitDescriptionToVisitIdMap;
	
	static
	{
		_visitDescriptionToVisitIdMap = new HashMap<String, String>();
		_visitDescriptionToVisitIdMap.put("Screening", "100");
		_visitDescriptionToVisitIdMap.put("PreP", "200");
		_visitDescriptionToVisitIdMap.put("PreC", "300");
		_visitDescriptionToVisitIdMap.put("D-1", "400");
		_visitDescriptionToVisitIdMap.put("D1", "500");
		_visitDescriptionToVisitIdMap.put("D3", "700");
		_visitDescriptionToVisitIdMap.put("D7", "800");
		_visitDescriptionToVisitIdMap.put("D10", "900");
		_visitDescriptionToVisitIdMap.put("D14", "1000");
		_visitDescriptionToVisitIdMap.put("D21", "1100");
		_visitDescriptionToVisitIdMap.put("D28", "1200");
		_visitDescriptionToVisitIdMap.put("D29", "1200");
		_visitDescriptionToVisitIdMap.put("D36", "1300");
		_visitDescriptionToVisitIdMap.put("D42", "1400");
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
