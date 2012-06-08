package hemera.utility.sql.condition;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>SingleCondition</code> defines implementation
 * a conditional query check that only checks a single
 * column against a single value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public abstract class SingleCondition extends AbstractCondition {
	/**
	 * The <code>String</code> table to check.
	 */
	protected final String table;
	/**
	 * The <code>String</code> column to check.
	 */
	protected final String column;
	/**
	 * The <code>ESign</code> of the condition.
	 */
	protected final ESign sign;
	
	/**
	 * Constructor of <code>SingleCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>String</code> value for
	 * the column to test with.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 */
	public SingleCondition(final String table, final String column, final ESign sign) {
		this.table = table;
		this.column = column;
		this.sign = sign;
	}
	
	@Override
	protected String buildTemplate() {
		final StringBuilder builder = new StringBuilder();
		builder.append("`").append(this.table).append("`.`").append(this.column).append("`");
		builder.append(" ").append(this.sign.value).append(" ");
		builder.append("?");
		return builder.toString();
	}
}
