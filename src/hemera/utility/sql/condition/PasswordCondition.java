package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>PasswordCondition</code> defines the query
 * single condition that holds a string value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class PasswordCondition extends SingleCondition {
	/**
	 * The <code>String</code> value to check against
	 * the column.
	 */
	private final String value;
	
	/**
	 * Constructor of <code>PasswordCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>String</code> value for
	 * the column to test with.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 */
	public PasswordCondition(final String table, final String column, final String value, final ESign sign) {
		super(table, column, sign);
		this.value = value;
	}
	
	@Override
	protected String buildTemplate() {
		final StringBuilder builder = new StringBuilder();
		builder.append("`").append(this.table).append("`.`").append(this.column).append("`");
		builder.append(" ").append(this.sign.value).append(" ");
		builder.append("password(?)");
		return builder.toString();
	}
	
	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setString(start, this.value);
		return 1;
	}
}
