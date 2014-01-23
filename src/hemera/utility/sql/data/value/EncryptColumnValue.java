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
 * @version 1.0.3
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
	
	/**
	 * Constructor of <code>EncryptColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param values The <code>String</code> array
	 * values.
	 * @param key The <code>String</code> encryption
	 * key.
	 */
	public EncryptColumnValue(final String table, final String column, final String[] values, final String key) {
		super(table, column, values);
		this.key = key;
	}
	
	@Override
	public void insertValue(int index, final int statementColumsCount, final PreparedStatement statement) throws SQLException {
		for (int i = 0; i < this.values.length; i++) {
			final String value = this.values[i];
			if (value == null) {
				statement.setNull(index, Types.VARCHAR);
			} else {
				statement.setString(index, value);
			}
			statement.setString(index+1, this.key);
			index += statementColumsCount;
		}
	}
	
	@Override
	public int getInsertCountPerValue() {
		return 2;
	}
}
