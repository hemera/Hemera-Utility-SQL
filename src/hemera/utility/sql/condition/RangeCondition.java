package hemera.utility.sql.condition;

/**
 * <code>RangeCondition</code> defines abstraction
 * of a data structure representing a database query
 * checking condition that checks a column in a range
 * of a pair of values.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public abstract class RangeCondition extends AbstractCondition {
	/**
	 * The <code>String</code> table to check.
	 */
	private final String table;
	/**
	 * The <code>String</code> column to check.
	 */
	private final String column;
	
	/**
	 * Constructor of <code>RangeCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> column to
	 * check.
	 */
	protected RangeCondition(final String table, final String column) {
		this.table = table;
		this.column = column;
	}
	
	@Override
	protected String buildTemplate() {
		final StringBuilder builder = new StringBuilder();
		builder.append("`").append(this.table).append("`.`").append(this.column).append("`");
		builder.append(" between ");
		builder.append("?");
		builder.append(" and ");
		builder.append("?");
		return builder.toString();
	}
}
