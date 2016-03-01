package jp.ojt.sst.file;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.ojt.sst.util.ASTException;

import java.io.BufferedReader;
import java.io.IOException;

public class StackTraceLog {

	/** regex of date(log4j %d) */
	private static final String LEGEX_DATE = "(\\d{4}-\\d{2}-\\d{2})";
	
	/** regex of Exception(contain package) */
	private static final String LEGEX_EXCEPTION="(\\s[\\w\\.]*Exception)";
	
	/** target StackTraceLog absolute path */
	private String filePath;
	/** search word into stackTraceLog */
	private String searchWord;
	
	private HashMap<String, StackTraceData> map = new HashMap<>();
	
	public StackTraceLog(String path, String word) {
		filePath = path;
		searchWord = word;
	}
	
	public void read() {
		
		Pattern datePtn = Pattern.compile(LEGEX_DATE);
		Pattern exceptionPtn = Pattern.compile(LEGEX_EXCEPTION);
		
		String matchDateStr = "";
		String exceptionStr = "";
		boolean dateMatch = false;
		boolean exceptionMatch = false;
		
		boolean findWord = false;
		StackTraceData stData = null;
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
			for(String line; (line = br.readLine()) != null; ) {
				
				Matcher dateMacher = datePtn.matcher(line);
				if(dateMacher.matches()) {
					matchDateStr = dateMacher.group();
					dateMatch = true;
					findWord = false;
					stData = null;
				}
				
				Matcher exceptionMacher = exceptionPtn.matcher(line);
				if(exceptionMacher.matches()) {
					exceptionStr = exceptionMacher.group();
					exceptionMatch = true;
				}
				
				if(dateMatch && exceptionMatch) {
					int idx = line.indexOf(exceptionStr) + exceptionStr.length();
					String messages = "";
					if (line.length() > idx + 1) {
						messages = line.substring(idx + 1);
					}
					stData = new StackTraceData(matchDateStr, exceptionStr, messages);
					dateMatch = false;
					exceptionMatch = false;
				}
				
				if(!findWord) {
					if(line.contains(searchWord)) {
						stData.setFindLine(line);
						// TODO create hash key matchDateStr+exceptionStr+messages+line?
						map.put(matchDateStr, stData);
						findWord = true;
					}
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
