package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>BooleanCondition</code> defines the condition
 * that compares the database entry against the given
 * boolean value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
final class BooleanCondition extends AbstractSingleCondition {
	/**
	 * The <code>boolean</code> value to check against
	 * the column.
	 */
	private final boolean value;
	
	/**
	 * Constructor of <code>BooleanCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>boolean</code> value for
	 * the column to test with.
	 */
	BooleanCondition(final String table, final String column, final ESign sign, final boolean value) {
		super(table, column, sign);
		this.value = value;
	}
	
	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setBoolean(start, this.value);
		return 1;
	}
}
