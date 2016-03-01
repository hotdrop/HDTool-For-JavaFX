package jp.ojt.sst.file;

public class StackTraceData {

	private String dateStr;
	private String exceptionStr;
	private String messages;
	private String findLine;
	/** same exception data count */
	private int count = 1;
	
	public StackTraceData(String argDateStr, String argExceptionStr, String argMessages, String argLine) {
		dateStr =  argDateStr;
		exceptionStr = argExceptionStr;
		messages = argMessages;
		findLine = argLine;
	}
	
	public void addCount() {
		count++;
	}

	public String toCSVString() {
		String[] arr = {dateStr, exceptionStr, messages, findLine, String.valueOf(count)};
		return String.join(",", arr); 
	}
}
