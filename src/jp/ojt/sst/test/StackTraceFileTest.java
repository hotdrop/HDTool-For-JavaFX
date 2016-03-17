package jp.ojt.sst.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import jp.ojt.sst.file.StackTraceFile;
import jp.ojt.sst.model.MonthCountProperty;
import jp.ojt.sst.model.StackTraceProperty;

/**
 * Class to test the StackTraceFileClass
 *
 */
public class StackTraceFileTest {

	/**
	 * Test main
	 * @param args
	 */
	public static void main(String[] args) {

		String path = createTestLogFile();
		String searchWord = "test";

		StackTraceFile stl = new StackTraceFile(path, searchWord);
		ObservableList<StackTraceProperty> stdList = stl.read();

		List<String> monthKeyList = stl.getMonthKeys();

		for(StackTraceProperty std : stdList) {
			printCSV(std, monthKeyList);
		}

		try {
			Files.delete(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print a csv string representation of this stackTraceProperty.
	 * Fields of this class are separated by the characters comma(,).
	 * And each item doesn't put double quotation marks.
	 */
	public static void printCSV(StackTraceProperty std, List<String> monthKeyList) {

		StringJoiner sj = new StringJoiner(",");
		sj.add(std.exceptionProperty().get().toString());
		sj.add(std.messageProperty().get().toString());
		sj.add(std.locationProperty().get().toString());

		for(String key : monthKeyList) {
			MonthCountProperty mcp = std.monthCountProperty(key);
			sj.add(String.valueOf(mcp.countProperty().get()));
		}

		System.out.println(sj.toString());
	}

	/**
	 * Create a test log file for the test and return its path.
	 * @return absolute path of the create file.
	 */
	private static String createTestLogFile() {

		String path = System.getProperty("user.dir") + "/test.log";
		Stream<String> stream = Stream.of(
				"2016-02-17 19:05:34.878,[WebContainer : 0  ],java.lang.NumberFormatException: null",
				"	at java.lang.Integer.parseInt(Integer.java:465)",
				"	at java.lang.Integer.valueOf(Integer.java:593)",
				"   at jp.ojt.sst.test.success(test.java:234)",
				"	at javax.servlet.http.HttpServlet.service(HttpServlet.java:575)",
				"2016-04-11 19:05:40.261,[WebContainer : 0  ],null",
				"java.lang.NumberFormatException: null",
				"	at java.lang.Integer.parseInt(Integer.java:465)",
				"	at jp.co.ojt.sst.test.ConfigFile.<init>(ConfigFile.java:30)",
				"2016-02-17 19:05:40.261,[WebContainer : 0  ],null",
				"java.lang.NumberFormatException: null",
				"	at java.lang.Integer.parseInt(Integer.java:465)",
				"	at jp.co.ojt.sst.test.ConfigFile.<init>(ConfigFile.java:30)",
				"2016-02-17 19:05:34.878,[WebContainer : 0  ],java.lang.NumberFormatException: null",
				"	at java.lang.Integer.parseInt(Integer.java:465)",
				"	at java.lang.Integer.valueOf(Integer.java:593)",
				"   at jp.ojt.sst.test.success(test.java:234)",
				"	at javax.servlet.http.HttpServlet.service(HttpServlet.java:575)",
				"2016-03-17 19:05:34.878,[WebContainer : 0  ],java.lang.NumberFormatException: null",
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
