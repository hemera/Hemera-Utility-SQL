package hemera.utility.sql.data.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>LongColumnValue</code> defines the immutable
 * data structure that holds a long value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.3
 */
public final class LongColumnValue extends ColumnValue {
	/**
	 * The <code>long</code> array values.
	 */
	private final long[] values;
	
	/**
	 * Constructor of <code>LongColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param value The <code>long</code> value.
	 */
	public LongColumnValue(final String table, final String column, final long value) {
		super(table, column);
		this.values = new long[] {value};
	}
	
	/**
	 * Constructor of <code>LongColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param values The <code>long</code> array
	 * values.
	 */
	public LongColumnValue(final String table, final String column, final long[] values) {
		super(table, column);
		this.values = values;
	}

	@Override
	public void insertValue(int index, final int statementColumsCount, final PreparedStatement statement) throws SQLException {
		for (int i = 0; i < this.values.length; i++) {
			statement.setLong(index, this.values[i]);
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
