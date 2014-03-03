import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;



public class Program {

	public static void main(String[] args) throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int returnVal = fileChooser.showDialog(null, "Select an FCS File");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			FCSFile file = new FCSFile(fileChooser.getSelectedFile().getAbsolutePath());
			String panel = file.getProperty("$SRC");
			System.out.println("panel: " + panel);
		}
	}

}
