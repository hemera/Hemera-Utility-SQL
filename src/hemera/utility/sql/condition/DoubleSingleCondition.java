package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>DoubleSingleCondition</code> defines the query
 * single condition that holds a double value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class DoubleSingleCondition extends SingleCondition {
	/**
	 * The <code>double</code> value to check against
	 * the column.
	 */
	private final double value;
	
	/**
	 * Constructor of <code>IntSingleCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>double</code> value for
	 * the column to test with.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 */
	public DoubleSingleCondition(final String table, final String column, final double value, final ESign sign) {
		super(table, column, sign);
		this.value = value;
	}
	
	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setDouble(start, this.value);
		return 1;
	}
}
