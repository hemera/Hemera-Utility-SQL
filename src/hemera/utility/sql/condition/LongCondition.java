package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>LongCondition</code> defines the condition
 * that compares the database entry against the given
 * long value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
final class LongCondition extends AbstractSingleCondition {
	/**
	 * The <code>long</code> value to check against
	 * the column.
	 */
	private final long value;
	
	/**
	 * Constructor of <code>LongCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>long</code> value for
	 * the column to test with.
	 */
	LongCondition(final String table, final String column, final ESign sign, final long value) {
		super(table, column, sign);
		this.value = value;
	}
	
	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setLong(start, this.value);
		return 1;
	}
}
