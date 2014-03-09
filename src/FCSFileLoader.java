import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class loads FCS files and provides mechanisms to filter the loaded FCS files.
 * 
 * This class is essentially a collection of FCS files that offer filters. This class does
 * not need to exist if I depend on a library that filters collections, like labmdaj, however,
 * I wanted to minimize my external dependencies for this application for SCRI.
 * @author Ryan
 *
 */
public class FCSFileLoader {
	// Collection of FCS files
	private Map<String, FCSFile>  _fcsFiles;
	
	public FCSFileLoader()
	{
		_fcsFiles = new HashMap<String, FCSFile>();
	}
	
	/**
	 * Loads an FCS file and adds it to the collection of FCS files.
	 * If the file is already in the collection it's overwritten.
	 * @param file FCS file
	 * @return FCSFile
	 * @throws IOException
	 */
	public FCSFile LoadFCSFile(File file) throws IOException
	{
		// Create the FCS file
		FCSFile fcsFile = new FCSFile(file.getAbsolutePath());
		
		// Save the FCS file
		_fcsFiles.put(file.getAbsolutePath(), fcsFile);
		
		return fcsFile;
	}
	
	/**
	 * Retrieves the list of FCS files that meet the predicate condition
	 * @param predicate
	 * @return List of FCS files that meet the predicate condition
	 */
	public List<FCSFile> GetFCSFiles(Java7Predicate<FCSFile> predicate)
	{
		if(predicate == null)
			throw new NullPointerException("predicate");
		
		// List of files that meet the predicate condition
		List<FCSFile> predicateFiles = new ArrayList<FCSFile>();
		
		// Loop through all FCS files
		Iterator<Entry<String, FCSFile>> fcsFileIterator = _fcsFiles.entrySet().iterator();
	    while (fcsFileIterator.hasNext()) {
	    	// Get the next FCS file in the collection of FCS files
			Entry<String, FCSFile> entry = (Entry<String, FCSFile>) fcsFileIterator.next();
	        FCSFile fileToCheck = entry.getValue();
	        
	        // If this file meets the predicate condition
	        // add it to the list of file's we're going to return
	        if(predicate.test(fileToCheck))
	        	predicateFiles.add(fileToCheck);
	    }
		
		return predicateFiles;
	}
	
	/**
	 * Gets a list of FCS files that contain the given property value
	 * @param propertyKey property key to check for
	 * @param propertyValue property value to check for
	 * @return List of FCS files that contain the given property value
	 */
	public List<FCSFile> GetFCSFilesByPropertyValue(final String propertyKey, final String propertyValue)
	{
		// Get all FCS files that are under the given panel
		return GetFCSFiles(new Java7Predicate<FCSFile>() {

			/**
			 * Tells whether the given FCS file has the property with the given value
			 * @param file FCS file to check
			 * @return True if FCS file has the given property key and value. False otherwise.
			 */
			@Override
			public boolean test(FCSFile file) {
				// Get the property
				String value = file.getProperty(propertyKey);
				
				// Check the property value
				return value != null &&
						value.equals(propertyValue);
			}
		});
	}
}
