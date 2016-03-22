/**
 * 
 */
package jp.ojt.sst.exception;

/**
 * Analyze StackTrace Exception
 *
 */
public class ASTException extends RuntimeException {

	/** serial version UID */
	private static final long serialVersionUID = 3222670800024751438L;

	/**
	 * @param message
	 */
	public ASTException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ASTException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ASTException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ASTException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
