package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>DoubleCondition</code> defines the condition
 * that compares the database entry against the given
 * double value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
final class DoubleCondition extends AbstractSingleCondition {
	/**
	 * The <code>double</code> value to check against
	 * the column.
	 */
	private final double value;
	
	/**
	 * Constructor of <code>DoubleCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>double</code> value for
	 * the column to test with.
	 */
	DoubleCondition(final String table, final String column, final ESign sign, final double value) {
		super(table, column, sign);
		this.value = value;
	}
	
	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setDouble(start, this.value);
		return 1;
	}
}
