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
		String matchDateStr = "";
		String exceptionStr = "";
		boolean findWord = false;
		StackTraceData stData = null;
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
			for(String line; (line = br.readLine()) != null; ) {
				Matcher m = datePtn.matcher(line);
				if(m.matches()) {
					matchDateStr = m.group();
					findWord = false;
					stData = null;
				}
				if(!findWord) {
					if(stData == null) {
						// TODO extract exceptionStr from line
						if(!exceptionStr.equals("")) {
							// TODO extract error messages from line
							String messages = "";
							// TODO same stackTraceData
							if(map.containsKey(matchDateStr + exceptionStr)) {
								stData = map.get(matchDateStr + exceptionStr);
								stData.addCount();
								map.replace(matchDateStr + exceptionStr, stData);
							} else {
								stData = new StackTraceData(matchDateStr, exceptionStr, messages);
							}
						}
					} else if(line.contains(searchWord)) {
						stData.setFindLine(line);
						// TODO Insufficient that it only this key..
						map.put(matchDateStr + exceptionStr, stData);
						findWord = true;
						stData = null;
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
