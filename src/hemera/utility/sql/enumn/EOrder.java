package hemera.utility.sql.enumn;

/**
 * <code>EOrder</code> defines the enumeration of the
 * select query ordering.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum EOrder {
	/**
	 * The ascending order.
	 */
	Ascending("asc"),
	/**
	 * The descending order.
	 */
	Descending("desc");
	
	/**
	 * The <code>String</code> value of the relation
	 * that can be directly used in a query statement.
	 */
	public final String value;
	
	/**
	 * Constructor of <code>EOrder</code>.
	 * @param value The <code>String</code> value of
	 * the relation.
	 */
	private EOrder(final String value) {
		this.value = value;
	}
}
