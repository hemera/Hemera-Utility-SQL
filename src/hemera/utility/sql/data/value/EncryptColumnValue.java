package hemera.utility.sql.data.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * <code>EncryptColumnValue</code> defines the immutable
 * data structure that holds an string value that will
 * be encrypted in the database.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class EncryptColumnValue extends StringColumnValue {
	/**
	 * The <code>String</code> key.
	 */
	private final String key;
	
	/**
	 * Constructor of <code>EncryptColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param value The <code>String</code> value.
	 * @param key The <code>String</code> encryption
	 * key.
	 */
	public EncryptColumnValue(final String table, final String column, final String value, final String key) {
		super(table, column, value);
		this.key = key;
	}
	
	@Override
	public int insertValue(final int index, final PreparedStatement statement) throws SQLException {
		if (this.value == null) statement.setNull(index, Types.VARBINARY);
		else statement.setString(index, this.value);
		statement.setString(index+1, this.key);
		return 2;
	}
}
