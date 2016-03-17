package jp.ojt.sst.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * StackTraceProperty
 * This class store a one-stackTrace information read from the StackTraceFile.
 *
 */
public class StackTraceProperty {

	/** A StringProperty representation of this exception */
	private StringProperty exceptionStr;

	/** A StringProperty representation of this message */
	private StringProperty message;

	/** The first line was found in the search word */
	private StringProperty location;

	/**  */
	private ObjectProperty<Map<String, MonthCountProperty>> monthCountMap = new SimpleObjectProperty<>();

	/**
	 * Constructor
	 *
	 * @param argDateStr
	 * @param argExceptionStr
	 * @param argMessage
	 * @param argLine
	 */
	public StackTraceProperty(String argDateStr, String argExceptionStr, String argMessage, String argLine) {
		exceptionStr = new SimpleStringProperty(argExceptionStr);
		message = new SimpleStringProperty(argMessage);
		location = new SimpleStringProperty(argLine);
		monthCountMap.set(new HashMap<String, MonthCountProperty>());
		monthCountMap.get().put(argDateStr, new MonthCountProperty(1));
	}

	/**
	 * Add data. If targetMonth is already put on map, count up. but not, add new month.
	 * @param argMonth YYYY-MM format
	 */
	public void addMonth(String argMonth) {
		if(monthCountMap.get().containsKey(argMonth)) {
			MonthCountProperty monthObj = monthCountMap.get().get(argMonth);
			monthObj.addCount();
			monthCountMap.get().replace(argMonth, monthObj);
		} else {
			monthCountMap.get().put(argMonth, new MonthCountProperty(1));
		}
	}

	/**
	 * Get exceptionProperty
	 * @return
	 */
	public StringProperty exceptionProperty() {
		return exceptionStr;
	}

	/**
	 * Get messageProperty
	 * @return
	 */
	public StringProperty messageProperty() {
		return message;
	}

	/**
	 * Get locationProperty
	 * @return
	 */
	public StringProperty locationProperty() {
		return location;
	}

	/**
	 * 指定された年月キー（YYYY-MM）に対応した例外発生数をプロパティ形式で取得する。
	 * もし年月キーが存在しない（指定月に例外が発生していない）場合は0を取得する。
	 * @param monthKey
	 * @return 年月の例外発生数プロパティ
	 */
	public MonthCountProperty monthCountProperty(String monthKey) {
		if(monthCountMap.get().containsKey(monthKey)) {
			return monthCountMap.get().get(monthKey);
		} else {
			return new MonthCountProperty(0);
		}
	}
}
