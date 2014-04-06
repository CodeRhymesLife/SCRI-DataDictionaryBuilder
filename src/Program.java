import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import Settings.PropertiesCollection;

/**
 * Creates data dictionaries from FCS files to enable SCRI to easily import files into LabKey 
 * @author Ryan
 *
 */
public class Program {

	/**
	 * Asks the user for a directory and creates data dictionaries
	 * in the selected directory, and all sub directories, if the directory contains at least one FCS file
	 * @param args Not Used
	 */
	public static void main(String[] args) {
		DataDictionaryProperties settings = new DataDictionaryProperties("config.properties");
		
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
			
			// Create data dictionaries for all FCS files in each directory
			for(File dirToSearch : dirsToSearch) {
				// Attempt to load all FCS files in directory
				List<FCSFile> successfullyLoadedFCSFiles = getFCSFilesInDir(dirToSearch, failedFiles);
				
				// Create a data dictionary from the successfully loaded FCS files
				Path dataDictionaryPath = createFCSFileDataDictionary(dirToSearch, successfullyLoadedFCSFiles);
				if(dataDictionaryPath != null)
					successFullyCreatedDataDictionaries += dataDictionaryPath + "\n";
			}
			
			JOptionPane.showMessageDialog(null, "The following FCS files were created:\n" + successFullyCreatedDataDictionaries, "Success", JOptionPane.INFORMATION_MESSAGE);
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
	 * 	 * Creates a data dictionary from a list of FCS files.
	 * @param dir Directory to create data dictionary in
	 * @param fcsFiles FCS files to create data dictionary from
	 * @return Path to data dictionary if it was created successfully. Null otherwise.
	 */
	public static Path createFCSFileDataDictionary(File dir, List<FCSFile> fcsFiles) {
		// Don't create a data dictionary if we don't have any FCS files
		if(fcsFiles.size() == 0)
			return null;
		
		// Create a data dictionary object to hold FCS file info
		ExcelDataDictionary dataDictionary = new ExcelDataDictionary();
		
		// Add columns to the data dictionary, specifying how the column fetches data from FCS files
		dataDictionary.AddColumn("Name", new FCSFileNameDataFetcher());
		dataDictionary.AddColumn("ParticipantID", new FCSFilePropertyDataFetcher("PATIENT ID"));
		dataDictionary.AddColumn("VisitID", new FCSFileVisitIdPropertyDataFetcher());
		dataDictionary.AddColumn("VisitDescription", new FCSFileTubeNamePropertyDataFetcher(FCSFileTubeNamePropertyDataFetcher.SubProperty.VisitDescription));
		dataDictionary.AddColumn("Sample Type", new FCSFileTubeNamePropertyDataFetcher(FCSFileTubeNamePropertyDataFetcher.SubProperty.SampleType));
		dataDictionary.AddColumn("Panel", new FCSFilePropertyDataFetcher("PANEL"));
		
		// Add a row to the data dictionary from each FCS file
		for(FCSFile file : fcsFiles)
			dataDictionary.AddRowFromFCSFile(file);
		
		// Write the excel file in the specified directory with the specified name
		return dataDictionary.Write(dir, "DataDictionary");
	}
}
