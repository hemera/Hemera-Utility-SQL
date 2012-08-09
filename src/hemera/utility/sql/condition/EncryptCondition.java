package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>EncryptCondition</code> defines the condition
 * that compares an encrypted database value against
 * the given string value encrypted using given key.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
final class EncryptCondition extends AbstractSingleCondition {
	/**
	 * The <code>String</code> key.
	 */
	private final String key;
	/**
	 * The <code>String</code> value to check against
	 * the column.
	 */
	private final String value;
	
	/**
	 * Constructor of <code>EncryptCondition</code>.
	 * @param key The <code>String</code> encryption
	 * key.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>String</code> value for
	 * the column to test with.
	 */
	EncryptCondition(final String key, final String table, final String column, final ESign sign, final String value) {
		super(table, column, sign);
		this.key = key;
		this.value = value;
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
