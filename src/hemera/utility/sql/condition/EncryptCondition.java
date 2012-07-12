package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>EncryptCondition</code> defines the query
 * single condition that holds a string value that
 * is encrypted using the AES algorithm.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class EncryptCondition extends SingleCondition {
	/**
	 * The <code>String</code> value to check against
	 * the column.
	 */
	private final String value;
	/**
	 * The <code>String</code> key.
	 */
	private final String key;
	
	/**
	 * Constructor of <code>EncryptCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>String</code> value for
	 * the column to test with.
	 * @param key The <code>String</code> encryption
	 * key.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 */
	public EncryptCondition(final String table, final String column, final String value, final String key, final ESign sign) {
		super(table, column, sign);
		this.value = value;
		this.key = key;
	}
	
	@Override
	protected String buildTemplate() {
		final StringBuilder builder = new StringBuilder();
		builder.append("`").append(this.table).append("`.`").append(this.column).append("`");
		builder.append(" ").append(this.sign.value).append(" ");
		builder.append("AES_ENCRYPT(?, ?)");
		return builder.toString();
	}
	
	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setString(start, this.value);
		statement.setString(start+1, this.key);
		return 2;
	}
}
