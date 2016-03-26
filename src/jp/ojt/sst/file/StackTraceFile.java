package jp.ojt.sst.file;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jp.ojt.sst.exception.ASTException;
import jp.ojt.sst.model.StackTraceProperty;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * StackTraceFile
 *
 */
public class StackTraceFile {

	/** This Legex is to get a date from the line in the StackTraceFile */
	private static final String LEGEX_MONTH = "(\\d{4}-\\d{2})";

	/** This Legex is to get a Exception class name the line in the StackTraceFile */
	private static final String LEGEX_EXCEPTION="([\\w\\.]*Exception)";

	/** Absolute path of this file  */
	private String filePath;

	/** Word to search for the line in the StackTraceFile */
	private String searchWord;

	/** This list holds the month which the exception has occurred */
	private List<String> monthList;

	/**
	 * Constructor
	 *
	 * @param path stackTraceLogFile absolute path
	 * @param word search word
	 */
	public StackTraceFile(String path, String word) {
		filePath = path;
		searchWord = word;
	}

	/**
	 * Read from Stack Trace File.
	 * @return List of stackTraceProperty 
	 */
	public ObservableList<StackTraceProperty> read() {

		Map<String, StackTraceProperty> stackTracePropMap = new HashMap<>();
		List<String> tmpMonthList = new ArrayList<>();

		Pattern datePtn = Pattern.compile(LEGEX_MONTH);
		Pattern exceptionPtn = Pattern.compile(LEGEX_EXCEPTION);

		String matchDateStr = "";
		String exceptionStr = "";
		String messages = "";

		boolean foundDate = false;
		boolean foundException = false;
		boolean foundWord = false;

		try(BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
			for(String line; (line = br.readLine()) != null; ) {

				Matcher dateMacher = datePtn.matcher(line);
				if(dateMacher.find()) {
					matchDateStr = dateMacher.group();
					foundDate = true;
					foundException = false;
					foundWord = false;
				}

				if(foundDate && !foundException) {
					Matcher exceptionMacher = exceptionPtn.matcher(line);
					if(exceptionMacher.find()) {
						exceptionStr = exceptionMacher.group();
						int idx = line.indexOf(exceptionStr) + exceptionStr.length();
						if (line.length() > idx + 1) {
							messages = line.substring(idx + 1);
						}
						foundException = true;
					}
				}

				if(foundException && !foundWord) {
					if(line.contains(searchWord)) {
						String key = exceptionStr + messages + line;
						if(stackTracePropMap.containsKey(key)) { 
							StackTraceProperty stData = stackTracePropMap.get(key);
							stData.addMonth(matchDateStr);
							stackTracePropMap.replace(key, stData);
						} else {
							StackTraceProperty stData = new StackTraceProperty(matchDateStr, exceptionStr, messages, line);
							stackTracePropMap.put(key, stData);
						}
						tmpMonthList.add(matchDateStr);
						foundWord = true;
					}
				}
			}

			monthList = tmpMonthList.stream()
									.distinct()
									.sorted(Comparator.naturalOrder())
									.collect(Collectors.toList());

			return FXCollections.observableArrayList(stackTracePropMap.values());

		} catch (IOException ioe) {
			throw new ASTException(ioe);
		}
	}

	/**
	 * Get the month list an exceoption occurs.
	 * Coution! this method must be used after read in this class.
	 * @return month list (ascending order of the month) 
	 */
	public List<String> getMonthList() {
		return monthList;
	}
}
