import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This class represents a dictionary containing important information about FCS files
 * that can be exported to an Excel file
 * @author Ryan
 *
 */
public class ExcelDataDictionary {
	// Excel work book
	private HSSFWorkbook _workBook;
	
	// Excel sheet containing FCS file info
	private HSSFSheet _fcsFileInfoSheet;
	
	// Header row in fcs file info sheet
	private HSSFRow _headRow;
	
	// List of column data fetchers that describe how data is fetched
	// from an FCS file for each column
	private List<ColumnFCSFileDataFetcher> _columnDataFetchers;
	
	// True if this dictionary contains FCS file data. False otherwise.
	private boolean _hasFCSFileData;
	
	public ExcelDataDictionary()
	{
		// Setup the excel file
		_workBook = new HSSFWorkbook();
		_fcsFileInfoSheet = _workBook.createSheet("FirstSheet"); 
        _headRow = _fcsFileInfoSheet.createRow(0);
        
        // Create a list of data fetchers for each column
        _columnDataFetchers = new ArrayList<ColumnFCSFileDataFetcher>();
        
        _hasFCSFileData = false;
	}
	
	/**
	 * Tells whether this data dictionary has FCS file data
	 * @return True if this dictionary contains FCS file data. False otherwise.
	 */
	public boolean getHasFCSFileData()
	{
		return _hasFCSFileData;
	}
	
	/**
	 * Adds a new column to the data dictionary and supplies a data fetcher
	 * which retrieves information from FCS files for the column
	 * @param name Name of column
	 * @param dataFetcher Retrieves information from FCS files for the column
	 */
	public void AddColumn(String name, FCSFileDataFetcher dataFetcher)
	{
		// Create a cell in the header row for the column
		int newColumnIndex = _columnDataFetchers.size();
		HSSFCell cell = _headRow.createCell(newColumnIndex);
		
		// Add the column's header
		cell.setCellValue(name);
		
		// Save the data fetcher
		_columnDataFetchers.add(new ColumnFCSFileDataFetcher(newColumnIndex, dataFetcher));
	}
	
	/**
	 * Adds a row to the data dictionary from the FCS file.
	 * The data fetchers supplied in AddColumn are used to fetch
	 * information from the FCS file for each column
	 * @param file FCS file to create row from
	 */
	public void AddRowFromFCSFile(FCSFile file)
	{
		// Append a new row
		int newRowNum = _fcsFileInfoSheet.getLastRowNum() + 1;
		HSSFRow row = _fcsFileInfoSheet.createRow(newRowNum);
		
		// Create a new cell in each column and store
		// data from the FCS file in each cell
		for(ColumnFCSFileDataFetcher columnDataFetcher : _columnDataFetchers)
		{
			// Get data from the file
			String cellValue = columnDataFetcher.DataFetcher.getData(file);
			
			// Create a new cell for the data from the FCS file
			HSSFCell cell = row.createCell(columnDataFetcher.ColumnIndex);
			
			// Store the FCS file data in the new cell
			cell.setCellValue(cellValue);
		}
		
		_hasFCSFileData = true;
	}
	
	/**
	 * Writes the excel file in the specified directory with the specified name
	 * @param dir Directory to write excel file in
	 * @param fileName Name of excel file
	 */
	public Path Write(File dir, String fileName)
	{
		// Auto size each column
		for(int columnIndex = 0; columnIndex < _headRow.getLastCellNum(); columnIndex++)   
		    _fcsFileInfoSheet.autoSizeColumn(columnIndex);
		
		// File path
		Path filePath = null;
		
		// Create the Excel file
		try {
			// Create a full path for the new file
			filePath = Paths.get(dir.getAbsolutePath(), fileName + ".xls");
			
			// Write the excel file
			FileOutputStream fileOut =  new FileOutputStream(filePath.toString());
			_workBook.write(fileOut);
			fileOut.close();
		} catch ( Exception ex ) {
	        System.out.println(ex);
	        filePath = null;
		}
		
		return filePath;
	}
	
	/**
	 * Contains a data fetcher for a specified column
	 * @author Ryan
	 *
	 */
	private class ColumnFCSFileDataFetcher {
		// Column to fetch data for
		private int ColumnIndex;
		
		// Fetches data from an FCS file for the specified column
		private FCSFileDataFetcher DataFetcher;
		
		public ColumnFCSFileDataFetcher(int columnIndex, FCSFileDataFetcher dataFetcher)
		{
			ColumnIndex = columnIndex;
			DataFetcher = dataFetcher;
		}
	}
}
