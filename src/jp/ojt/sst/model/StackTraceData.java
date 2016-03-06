package jp.ojt.sst.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * StackTraceData
 * This class store a one-stackTrace information read from the Stack Trace File.
 *
 */
public class StackTraceData {

	/** A StringProperty representation of read stackTrace date */
	private StringProperty dateStr;
	
	/** A StringProperty representation of this exception */
	private StringProperty exceptionStr;
	
	/** A StringProperty representation of this message */
	private StringProperty message;
	
	/** The first line was found in the search word */
	private StringProperty location;
	
	/** Count of the same kinds of exceptions */
	private int count = 1;
	
	/**
	 * Constructor 
	 * 
	 * @param argDateStr
	 * @param argExceptionStr
	 * @param argMessage
	 * @param argLine
	 */
	public StackTraceData(String argDateStr, String argExceptionStr, String argMessage, String argLine) {
		dateStr = new SimpleStringProperty(argDateStr);
		exceptionStr = new SimpleStringProperty(argExceptionStr);
		message = new SimpleStringProperty(argMessage);
		location = new SimpleStringProperty(argLine);
	}
	
	/**
	 * Add one to the count field of this class. 
	 */
	public void addCount() {
		count++;
	}
	
	public StringProperty dateProperty() {
		return dateStr;
	}
	
	public StringProperty exceptionProperty() {
		return exceptionStr;
	}
	
	public StringProperty messageProperty() {
		return message;
	}
	
	public StringProperty locationProperty() {
		return location;
	}
	
	public IntegerProperty numberProperty() {
		return new SimpleIntegerProperty(count);
	}

	/**
	 * Returns a csv string representation of this stackTraceData.
	 * Fields of this class are separated by the characters comma(,).
	 * And each item doesn't put double quotation marks.   
	 * @return a csv string representation of this stackTraceData
	 */
	public String toCSVString() {
		String[] arr = {dateStr.toString(), exceptionStr.toString(), message.toString(), location.toString(), String.valueOf(count)};
		return String.join(",", arr); 
	}
}
