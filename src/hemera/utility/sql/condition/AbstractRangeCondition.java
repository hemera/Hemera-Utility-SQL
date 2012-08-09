package hemera.utility.sql.condition;

/**
 * <code>AbstractRangeCondition</code> defines the
 * abstraction of a condition that compares a database
 * value against a specified range.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
abstract class AbstractRangeCondition extends AbstractCondition {
	/**
	 * The <code>String</code> table to check.
	 */
	private final String table;
	/**
	 * The <code>String</code> column to check.
	 */
	private final String column;
	
	/**
	 * Constructor of <code>AbstractRangeCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> column to
	 * check.
	 */
	AbstractRangeCondition(final String table, final String column) {
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
