package hemera.utility.sql.data.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * <code>StringColumnValue</code> defines the immutable
 * data structure that holds an string value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.3
 */
public class StringColumnValue extends ColumnValue {
	/**
	 * The <code>String</code> array values.
	 */
	protected final String[] values;
	
	/**
	 * Constructor of <code>StringColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param value The <code>String</code> value.
	 */
	public StringColumnValue(final String table, final String column, final String value) {
		super(table, column);
		this.values = new String[] {value};
	}
	
	/**
	 * Constructor of <code>StringColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param values The <code>String</code> array
	 * values.
	 */
	public StringColumnValue(final String table, final String column, final String[] values) {
		super(table, column);
		this.values = values;
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
			index += statementColumsCount;
		}
	}
	
	@Override
	public int getValuesCount() {
		return this.values.length;
	}
}
