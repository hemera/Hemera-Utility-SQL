package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>BooleanSingleCondition</code> defines the query
 * single condition that holds an integer value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class BooleanSingleCondition extends SingleCondition {
	/**
	 * The <code>boolean</code> value to check against
	 * the column.
	 */
	private final boolean value;
	
	/**
	 * Constructor of <code>BooleanSingleCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>boolean</code> value for
	 * the column to test with.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 */
	public BooleanSingleCondition(final String table, final String column, final boolean value, final ESign sign) {
		super(table, column, sign);
		this.value = value;
	}
	
	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setBoolean(start, this.value);
		return 1;
	}
}
