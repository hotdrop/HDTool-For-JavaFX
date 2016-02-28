package jp.ojt.sst.file;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ojt.sst.util.ASTException;

import java.io.BufferedReader;
import java.io.IOException;

public class StackTraceLog {

	/** regex of date(log4j %d) */
	private static final String LEGEX_DATE = "(\\d{4}-\\d{2}-\\d{2})";
	/** target StackTraceLog absolute path */
	private String filePath;
	/** search word into stackTraceLog */
	private String searchWord;
	
	public StackTraceLog(String path, String word) {
		filePath = path;
		searchWord = word;
	}
	
	public void read() {
		
		Pattern ptn = Pattern.compile(LEGEX_DATE);
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
			for(String line; (line = br.readLine()) != null; ) {
				Matcher m = ptn.matcher(line);
				if(m.matches()) {
					// store "date", "Exception type" and "the first line of the searchWord is found"
					// if searchWord is not found, this Exception is through. 
					// I'm thinking. create ArrayList and  
				}
			}
		} catch (IOException ioe) {
			throw new ASTException(ioe);
		}
	}
	
	public void outPutCSV() {
		// output CSV
	}
}
