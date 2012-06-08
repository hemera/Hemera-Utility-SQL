package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>LongSingleCondition</code> defines the query
 * single condition that holds an integer value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class LongSingleCondition extends SingleCondition {
	/**
	 * The <code>long</code> value to check against
	 * the column.
	 */
	private final long value;
	
	/**
	 * Constructor of <code>LongSingleCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>long</code> value for
	 * the column to test with.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 */
	public LongSingleCondition(final String table, final String column, final long value, final ESign sign) {
		super(table, column, sign);
		this.value = value;
	}
	
	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setLong(start, this.value);
		return 1;
	}
}
