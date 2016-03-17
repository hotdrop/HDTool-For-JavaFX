package jp.ojt.sst.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * MonthCountProperty 
 * This class is a property class that holds a number of exceptions for each month.
 */
public class MonthCountProperty {

	/** internal count to be used only in this class */
	private int innercnt = 0;

	/**
	 * Constructor
	 * @param cnt initial value of exception count
	 */
	public MonthCountProperty(int cnt) {
		innercnt = cnt;
	}

	/**
	 * 1 added
	 */
	public void addCount() {
		innercnt++;
	}

	/**
	 * Get IntegerProperty of a number of exceptions
	 * @return IntegerProperty of a number of exceptions 
	 */
	public IntegerProperty countProperty() {
		return new SimpleIntegerProperty(innercnt);
	}

}
