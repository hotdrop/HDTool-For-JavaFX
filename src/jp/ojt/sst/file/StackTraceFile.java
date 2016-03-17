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
 * StackTraceFile Class
 *
 */
public class StackTraceFile {

	/** スタックトレースファイルの行情報から年月を取得するための正規表現 */
	private static final String LEGEX_MONTH = "(\\d{4}-\\d{2})";

	/** スタックトレースファイルの行情報から例外クラス文字列を取得するための正規表現 */
	private static final String LEGEX_EXCEPTION="([\\w\\.]*Exception)";

	/** スタックトレースファイルのフルパス */
	private String filePath;

	/** 検索文字列 */
	private String searchWord;

	/** スタックトレースファイルから読み込んだ例外情報が発生した年月を保持するリスト */
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
	 * @return
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
							// 既にキーが存在する場合は月毎の発生件数を加算する
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
	 * スタックトレースファイルから読み込んだ、例外が発生した年月リストを取得する
	 * @return 例外が発生した年月のリスト（年月の昇順）
	 */
	public List<String> getMonthKeys() {
		return monthList;
	}
}
