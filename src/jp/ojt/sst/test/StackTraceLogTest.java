package jp.ojt.sst.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import jp.ojt.sst.file.StackTraceFile;

/**
 * Class to test the StackTraceFileClass
 *
 */
public class StackTraceLogTest {
	
	/**
	 * Test main
	 * @param args
	 */
	public static void main(String[] args) {
		
		String path = createTestLog();
		String searchWord = "test";
		
		StackTraceFile stl = new StackTraceFile(path, searchWord);
		stl.read();
		stl.outputCSV();

		try {
			Files.delete(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a test log file for the test and return its path. 
	 * @return absolute path of the create file.
	 */
	private static String createTestLog() {
		
		String path = System.getProperty("java.class.path") + "/test.log";
		Stream<String> stream = Stream.of(
				"2016-02-17 19:05:34.878,[WebContainer : 0  ],java.lang.NumberFormatException: null",
				"	at java.lang.Integer.parseInt(Integer.java:465)",
				"	at java.lang.Integer.valueOf(Integer.java:593)",
				"   at jp.ojt.sst.test.success(test.java:234)",
				"	at javax.servlet.http.HttpServlet.service(HttpServlet.java:575)",
				"2016-02-17 19:05:40.261,[WebContainer : 0  ],null",
				"java.lang.NumberFormatException: null",
				"	at java.lang.Integer.parseInt(Integer.java:465)",
				"	at jp.co.ojt.sst.test.ConfigFile.<init>(ConfigFile.java:30)",
				"2016-02-17 19:05:34.878,[WebContainer : 0  ],java.lang.NumberFormatException: null",
				"	at java.lang.Integer.parseInt(Integer.java:465)",
				"	at java.lang.Integer.valueOf(Integer.java:593)",
				"   at jp.ojt.sst.test.success(test.java:234)",
				"	at javax.servlet.http.HttpServlet.service(HttpServlet.java:575)"
				);
		
		try {
			Files.write(Paths.get(path), (Iterable<String>)stream::iterator, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}
}
