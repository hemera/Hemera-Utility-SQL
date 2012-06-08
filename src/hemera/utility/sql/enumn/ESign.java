package hemera.utility.sql.enumn;

/**
 * <code>ESign</code> defines the enumeration of all
 * the signs used in database query condition check
 * with a single value comparison.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum ESign {
	/**
	 * The <code>equal</code> condition check sign.
	 */
	Equal("="),
	/**
	 * The <code>not-equal</code> condition check sign.
	 */
	NotEqual("!="),
	/**
	 * The <code>less-than</code> condition check sign.
	 */
	LessThan("<"),
	/**
	 * The <code>less-than-or-equal-to</code> condition
	 * check sign.
	 */
	LessThanOrEqual("<="),
	/**
	 * The <code>greater-than</code> condition check
	 * sign.
	 */
	GreaterThan(">"),
	/**
	 * The <code>greater-than-or-equal-to</code>
	 * condition check sign.
	 */
	GreaterThanOrEqual(">="),
	/**
	 * The <code>like</code> string condition check
	 * sign.
	 */
	Like("like");

	/**
	 * The <code>String</code> value of the sign that
	 * can be directly used in a query statement.
	 */
	public final String value;
	
	/**
	 * Constructor of <code>ESign</code>.
	 * @param value The <code>String</code> value of
	 * the sign.
	 */
	private ESign(final String value) {
		this.value = value;
	}
}
