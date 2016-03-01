package jp.ojt.sst.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import jp.ojt.sst.file.StackTraceLog;

public class StackTraceLogTest {
	
	public static void main(String[] args) {
		
		String path = createTestLog();
		String searchWord = "test";
		
		StackTraceLog stl = new StackTraceLog(path, searchWord);
		stl.read();
		stl.outPutCSV();
		
		// TODO delete TestLog
	}
	
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
