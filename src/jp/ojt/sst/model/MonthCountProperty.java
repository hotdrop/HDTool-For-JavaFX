package jp.ojt.sst.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * 年月の例外発生数を保持するプロパティ
 *
 */
public class MonthCountProperty {

	/** 例外発生数の内部カウント値 */
	private int innercnt = 0;

	/**
	 * コンストラクタ
	 * @param cnt 例外発生数の初期値
	 */
	public MonthCountProperty(int cnt) {
		innercnt = cnt;
	}

	/**
	 * 発生数を1加算する
	 */
	public void addCount() {
		innercnt++;
	}

	/**
	 * 例外発生数をIntegerProperty表現で取得する
	 * @return 例外発生数のIntegerProperty表現
	 */
	public IntegerProperty countProperty() {
		return new SimpleIntegerProperty(innercnt);
	}

}
