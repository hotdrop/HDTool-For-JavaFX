package jp.ojt.sst.file;

public class StackTraceLog {

	/** target StackTraceLog absolute path */
	private String filePath;
	/** search word into stackTraceLog */
	private String searchWord;
	
	public StackTraceLog(String path, String word) {
		filePath = path;
		searchWord = word;
	}
	
	public void read() {
		// TODO
	}
	
	public void outPutCSV() {
		// CSVで出力
	}
}
