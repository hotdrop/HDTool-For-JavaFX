package jp.ojt.sst.file;

import java.util.Date;

public class StackTraceData {

	private String dateStr;
	private String exceptionStr;
	private String messages;
	private String findLine;
	/** same exception data count */
	private int count = 1;
	
	public StackTraceData(String argDateStr, String argExceptionStr, String argMessages) {
		dateStr =  argDateStr;
		exceptionStr = argExceptionStr;
		messages = argMessages;
	}
	
	public void setFindLine(String line) {
		findLine = line;
	}
	
	public void addCount() {
		count++;
	}

	public String toCSVString() {
		String[] arr = {dateStr, exceptionStr, messages, findLine, String.valueOf(count)};
		return String.join(",", arr); 
	}
}
