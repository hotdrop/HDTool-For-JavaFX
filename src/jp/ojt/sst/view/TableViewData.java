package jp.ojt.sst.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableViewData {

	private StringProperty date;
	private StringProperty exception;
	private StringProperty message;
	private StringProperty location;
	private IntegerProperty number;
	
	public TableViewData(String argDate, String argExceptionStr, String argMessages, String argLine, int argNum) {
		date = new SimpleStringProperty(argDate);
		exception = new SimpleStringProperty(argExceptionStr);
		message = new SimpleStringProperty(argMessages);
		location = new SimpleStringProperty(argLine);
		number = new SimpleIntegerProperty(argNum);
	}
	
	public StringProperty dateProperty() {
		return date;
	}
	
	public StringProperty exceptionProperty() {
		return exception;
	}
	
	public StringProperty messageProperty() {
		return message;
	}
	
	public StringProperty locationProperty() {
		return location;
	}
	
	public IntegerProperty numberProperty() {
		return number;
	}
}
