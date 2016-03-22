package jp.ojt.sst.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	private StringProperty expProperty;

	/** A StringProperty representation of this message */
	private StringProperty msgProperty;

	/** The first line was found in the search word */
	private StringProperty locationProperty;

	private ObjectProperty<Map<String, MonthCountProperty>> monthCountMap = new SimpleObjectProperty<>();
	
	/** total each monthCount */
	private int totalMonthCount;

	/**
	 * Constructor
	 *
	 * @param argDateStr
	 * @param argExceptionStr
	 * @param argMessage
	 * @param argLine
	 */
	public StackTraceProperty(String argDateStr, String argExceptionStr, String argMessage, String argLine) {
		expProperty = new SimpleStringProperty(argExceptionStr);
		msgProperty = new SimpleStringProperty(argMessage);
		locationProperty = new SimpleStringProperty(argLine);
		monthCountMap.set(new HashMap<String, MonthCountProperty>());
		monthCountMap.get().put(argDateStr, new MonthCountProperty(1));
		totalMonthCount++;
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
		totalMonthCount++;
	}

	/**
	 * Get exceptionProperty
	 * @return
	 */
	public StringProperty exceptionProperty() {
		return expProperty;
	}

	/**
	 * Get messageProperty
	 * @return
	 */
	public StringProperty messageProperty() {
		return msgProperty;
	}

	/**
	 * Get locationProperty
	 * @return
	 */
	public StringProperty locationProperty() {
		return locationProperty;
	}

	/**
	 * Get MonthCountProperty of the number of exception that corresponding to the specified month key(YYYY-MM).
	 * If the specified month key does not exist, return the MonthCountProperty of 0.  
	 * @param monthKey
	 * @return MonthCountProperty of the number of exception
	 */
	public MonthCountProperty monthCountProperty(String monthKey) {
		if(monthCountMap.get().containsKey(monthKey)) {
			return monthCountMap.get().get(monthKey);
		} else {
			return new MonthCountProperty(0);
		}
	}
	
	/**
	 * Get totalMonthCountProperty
	 * @return
	 */
	public IntegerProperty totalProperty() {
		return new SimpleIntegerProperty(totalMonthCount);
	}
}
