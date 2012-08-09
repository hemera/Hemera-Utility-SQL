package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>IntCondition</code> defines the condition that
 * compares the database entry against the given integer
 * value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
final class IntCondition extends AbstractSingleCondition {
	/**
	 * The <code>int</code> value to check against
	 * the column.
	 */
	private final int value;
	
	/**
	 * Constructor of <code>IntSingleCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>int</code> value for
	 * the column to test with.
	 */
	IntCondition(final String table, final String column, final ESign sign, final int value) {
		super(table, column, sign);
		this.value = value;
	}
	
	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setInt(start, this.value);
		return 1;
	}
}
