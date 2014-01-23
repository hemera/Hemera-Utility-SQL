package hemera.utility.sql.data.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>BooleanColumnValue</code> defines the immutable
 * data structure that holds a boolean value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.3
 */
public final class BooleanColumnValue extends ColumnValue {
	/**
	 * The <code>boolean</code> array values.
	 */
	private final boolean[] values;
	
	/**
	 * Constructor of <code>BooleanColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param value The <code>boolean</code> value.
	 */
	public BooleanColumnValue(final String table, final String column, final boolean value) {
		super(table, column);
		this.values = new boolean[] {value};
	}
	
	/**
	 * Constructor of <code>BooleanColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param values The <code>boolean</code> array
	 * values.
	 */
	public BooleanColumnValue(final String table, final String column, final boolean[] values) {
		super(table, column);
		this.values = values;
	}

	@Override
	public void insertValue(int index, final int statementColumsCount, final PreparedStatement statement) throws SQLException {
		for (int i = 0; i < this.values.length; i++) {
			statement.setBoolean(index, this.values[i]);
			index += statementColumsCount;
		}
	}

	@Override
	public int getValuesCount() {
		return this.values.length;
	}
	
	@Override
	public int getInsertCountPerValue() {
		return 1;
	}
}
