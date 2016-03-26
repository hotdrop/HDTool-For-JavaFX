package jp.ojt.sst.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javafx.collections.ObservableList;
import jp.ojt.sst.exception.ASTException;
import jp.ojt.sst.model.MonthCountProperty;
import jp.ojt.sst.model.StackTraceProperty;

public class ResultCSVFile {

	/**
	 * Save csv file
	 * @param filePath save csv file absolute path
	 * @param stFile read StackTrace file
	 */
	public void save(String filePath, StackTraceFile stFile) {
		
		ObservableList<StackTraceProperty> dataList = stFile.read();
		List<String> monthList = stFile.getMonthList();
		
		try(BufferedWriter bw = Files.newBufferedWriter(Paths.get(filePath))) {
			bw.write(createHeader(monthList));
			bw.newLine();
			for(StackTraceProperty std : dataList) {
				bw.write(createCSVLine(std, monthList));
				bw.newLine();
			}
		} catch (IOException ioe) {
			throw new ASTException(ioe);
		}
	}
	
	/**
	 * Create header line string to be output to the first line of the CSV file. 
	 * @param monthList
	 * @return header string
	 */
	private String createHeader(List<String> monthList) {
		String header = "ExceptionType,Description,Location";
		for(String month : monthList) {
			header += "," + month;
		}
		header += ",Total";
		return header;
	}
	
	/**
	 * Create data line string to be output of the CSV file.
	 * @param stp
	 * @param monthList
	 * @return
	 */
	private String createCSVLine(StackTraceProperty stp, List<String> monthList) {
		// lost in which new StringBuilder or setLength(0) 
		StringBuilder sb = new StringBuilder();
		sb.append(stp.exceptionProperty().get().toString());
		sb.append(",");
		sb.append(stp.messageProperty().get().toString());
		sb.append(",");
		sb.append(stp.locationProperty().get().toString());
		sb.append(",");
		for(String key : monthList) {
			MonthCountProperty mcp = stp.monthCountProperty(key);
			sb.append(mcp.countProperty().get());
			sb.append(",");
		}
		sb.append(String.valueOf(stp.totalProperty().get()));
		return sb.toString();
	}
}
