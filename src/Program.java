import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class Program {

	public static void main(String[] args) throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int returnVal = fileChooser.showDialog(null, "Select the directory containing FCS files");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// Get all FCS files within the selected dir
			String[] extensions = new String[] {"fcs"};
			IOFileFilter filter = new SuffixFileFilter(extensions, IOCase.INSENSITIVE);
			Iterator<File> fcsFileIterator = FileUtils.iterateFiles(fileChooser.getSelectedFile(),
					filter, DirectoryFileFilter.DIRECTORY);
			
			// Load each FCS file into the FCSFileLoader
			FCSFileLoader loader = new FCSFileLoader();
			while(fcsFileIterator.hasNext())
			{
				loader.LoadFCSFile(fcsFileIterator.next());
			}
			
			// Get all FCS files that are under the samples panel
			List<FCSFile> samplePanelFiles = loader.GetFCSFilesByPropertyValue("$SRC", "Samples");
		}
	}

}
