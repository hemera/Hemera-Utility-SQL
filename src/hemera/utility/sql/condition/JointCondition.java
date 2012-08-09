package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>JointCondition</code> defines the condition
 * that compares the columns of two separate tables
 * against each other.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
class JointCondition extends AbstractCondition {
	/**
	 * The <code>String</code> name of the first table.
	 */
	private final String table1;
	/**
	 * The <code>String</code> name of the first column.
	 */
	private final String column1;
	/**
	 * The <code>ESign</code> of the condition.
	 */
	private final ESign sign;
	/**
	 * The <code>String</code> name of the second table.
	 */
	private final String table2;
	/**
	 * The <code>String</code> name of the second column.
	 */
	private final String column2;

	/**
	 * Constructor of <code>JointCondition</code>.
	 * @param table1 The <code>String</code> name of
	 * the first table.
	 * @param column1 The <code>String</code> name of
	 * the first column.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param table2 The <code>String</code> name of
	 * the second table.
	 * @param column2 The <code>String</code> name of
	 * the second column.
	 */
	JointCondition(final String table1, final String column1, final ESign sign, final String table2, final String column2) {
		this.table1 = table1;
		this.column1 = column1;
		this.sign = sign;
		this.table2 = table2;
		this.column2 = column2;
	}

	@Override
	protected String buildTemplate() {
		final StringBuilder builder = new StringBuilder();
		builder.append("`").append(this.table1).append("`.").append("`").append(this.column1).append("`");
		builder.append(" ").append(this.sign.value).append(" ");
		builder.append("`").append(this.table2).append("`.").append("`").append(this.column2).append("`");
		return builder.toString();
	}

	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		return 0;
	}
}
