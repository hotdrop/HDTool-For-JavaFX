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
	 * �w�肳�ꂽ�N���L�[�iYYYY-MM�j�ɑΉ�������O���������v���p�e�B�`���Ŏ擾����B
	 * �����N���L�[�����݂��Ȃ��i�w�茎�ɗ�O���������Ă��Ȃ��j�ꍇ��0���擾����B
	 * @param monthKey
	 * @return �N���̗�O�������v���p�e�B
	 */
	public MonthCountProperty monthCountProperty(String monthKey) {
		if(monthCountMap.get().containsKey(monthKey)) {
			return monthCountMap.get().get(monthKey);
		} else {
			return new MonthCountProperty(0);
		}
	}
}
