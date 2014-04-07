import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;


public class DataDictionaryGenerator {

	// Data dictionary settings
	private DataDictionaryProperties _settings;
	
	public DataDictionaryGenerator(DataDictionaryProperties settings)
	{
		if(settings == null)
			throw new NullPointerException("settings");
		
		_settings = settings;
	}
	
	/**
	 * Generate data dictionaries
	 */
	public void GenerateDataDictionaries()
	{
		// Ask the user to select a directory containing FCS files
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showDialog(null, "Select the directory containing FCS files");
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// Get the selected directory
			File selectedDirectory = fileChooser.getSelectedFile();
			
			// Get all sub directories in the selected directory
			List<File> dirsToSearch = getSubDirs(selectedDirectory);
			
			// Add the selected directory to the list of directories to search
			dirsToSearch.add(fileChooser.getSelectedFile());
			
			// Used to keep track of the FCS files that fail to load
			List<File> failedFiles = new ArrayList<File>();
			
			// Keep track of the successfully created data dictionaries
			String successFullyCreatedDataDictionaries = "";
			
			ExcelDataDictionary dataDictionary = null;
			
			// Are we saving to multiple files, or just one?
			boolean saveToMultipleFiles =_settings.getSaveToMultipleFilesProperty().getValue();
			
			// Get the name of the data dictionaries we're creating
			String dataDictionaryName = _settings.getDataDictionaryNameProperty().getValue();
			
			// If we're only saving to one data dictionary create it so we can add
			// info from each FCS file to it.
			if(!saveToMultipleFiles)
				dataDictionary = CreateExcelDataDictionary();
			
			// Create data dictionaries for all FCS files in each directory
			for(File dirToSearch : dirsToSearch) {
				// Attempt to load all FCS files in directory
				List<FCSFile> successfullyLoadedFCSFiles = getFCSFilesInDir(dirToSearch, failedFiles);
				
				// If we're saving to multiple files create a data dictionary for this folder
				if(saveToMultipleFiles)
					dataDictionary = CreateExcelDataDictionary();
				
				// Update the data dictionary with info from the successfully loaded FCS files
				updateDataDictionary(dataDictionary, successfullyLoadedFCSFiles);
				
				// If we're saving to multiple files then we're done with this data dictionary
				// Lets write it out
				if(saveToMultipleFiles && dataDictionary.getHasFCSFileData())
				{
					Path dataDictionaryPath = saveDataDictionary(dataDictionary, dirToSearch, dataDictionaryName);
					successFullyCreatedDataDictionaries += dataDictionaryPath + "\n";
				}	
			}
			
			// If we're only saving to one data dictionary save it now since we're done
			// adding FCS file data
			if(!saveToMultipleFiles && dataDictionary.getHasFCSFileData())
			{
				Path dataDictionaryPath = saveDataDictionary(dataDictionary, selectedDirectory, dataDictionaryName);
				successFullyCreatedDataDictionaries += dataDictionaryPath + "\n";
			}
			
			JOptionPane.showMessageDialog(null, "The following FCS files were created:\n" + successFullyCreatedDataDictionaries, "Success", JOptionPane.INFORMATION_MESSAGE);
			
			_settings.Save();
		}
	}

	/**
	 * Gets all sub dirs in the given directory
	 * @param dir Directory to get sub dirs from
	 * @return List of sub directories
	 */
	public static List<File> getSubDirs(File dir) {
		// Get sub directories
	    List<File> subdirs = Arrays.asList(dir.listFiles(new FileFilter() {
	        public boolean accept(File f) {
	            return f.isDirectory();
	        }
	    }));
	    
	    // Store sub directories in a list
	    subdirs = new ArrayList<File>(subdirs);

	    // Recursively get all directories in each sub directory
	    List<File> deepSubdirs = new ArrayList<File>();
	    for(File subdir : subdirs) {
	        deepSubdirs.addAll(getSubDirs(subdir)); 
	    }
	    
	    // Save all the directories
	    subdirs.addAll(deepSubdirs);
	    
	    return subdirs;
	}
	
	/**
	 * Gets a list of FCS files in the given directory
	 * @param dir Directory to get FCS files from
	 * @param failedFiles List of FCS files that failed to load
	 * @return List of successfully loaded FCS files
	 */
	private static List<FCSFile> getFCSFilesInDir(File dir, List<File> failedFiles) {
		// Get all FCS files within the selected dir
		String[] extensions = new String[] {"fcs"};
		IOFileFilter filter = new SuffixFileFilter(extensions, IOCase.INSENSITIVE);
		Iterator<File> fcsFileIterator = FileUtils.iterateFiles(dir,
				filter, FalseFileFilter.FALSE);
		
		// Load each FCS file into the FCSFileLoader
		FCSFileLoader loader = new FCSFileLoader();
		while(fcsFileIterator.hasNext())
		{
			// Get the next FCS file
			File fcsFile = fcsFileIterator.next();
			
			// Attempt to load the FCS file
			// if we fail save the file in a failed file list
			if(loader.LoadFCSFile(fcsFile) == null)
				failedFiles.add(fcsFile);
		}
		
		// Get all FCS files that are under the samples panel
		return loader.GetFCSFilesByPropertyValue("$SRC", "Samples");
	}
	
	/**
	 * Updates the data dictionary from a list of FCS files.
	 * @param dir Directory to create data dictionary in
	 * @param fcsFiles FCS files to create data dictionary from
	 */
	public static void updateDataDictionary(ExcelDataDictionary dataDictionary, List<FCSFile> fcsFiles) {
		// Add a row to the data dictionary from each FCS file
		for(FCSFile file : fcsFiles)
			dataDictionary.AddRowFromFCSFile(file);
	}
	
	/**
	 * Create an excel data dictionary
	 * @return Excel data dictionary
	 */
	public ExcelDataDictionary CreateExcelDataDictionary()
	{
		// Create a data dictionary object to hold FCS file info
		ExcelDataDictionary dataDictionary = new ExcelDataDictionary();
		
		// Add columns to the data dictionary, specifying how the column fetches data from FCS files
		dataDictionary.AddColumn("Name", new FCSFileNameDataFetcher());
		dataDictionary.AddColumn("ParticipantID", new FCSFileTubeNamePropertyDataFetcher(FCSFileTubeNamePropertyDataFetcher.SubProperty.ParticitantId));
		dataDictionary.AddColumn("VisitID", new FCSFileVisitIdPropertyDataFetcher(_settings.getVisitDescriptionToVisitIdMapProperty().getValue()));
		dataDictionary.AddColumn("VisitDescription", new FCSFileTubeNamePropertyDataFetcher(FCSFileTubeNamePropertyDataFetcher.SubProperty.VisitDescription));
		dataDictionary.AddColumn("Sample Type", new FCSFileTubeNamePropertyDataFetcher(FCSFileTubeNamePropertyDataFetcher.SubProperty.SampleType));
		dataDictionary.AddColumn("Panel", new FCSFilePropertyDataFetcher("PANEL"));
		
		return dataDictionary;
	}
	
	/**
	 * Save the data dictionary to the given folder with the given name
	 * @param dataDictionary data dictionary to save
	 * @param dir directory to save to
	 * @param name name of data dictionary
	 * @return Path of new data dictionary
	 */
	public static Path saveDataDictionary(ExcelDataDictionary dataDictionary, File dir, String name)
	{
		return dataDictionary.Write(dir, name);
	}
}
