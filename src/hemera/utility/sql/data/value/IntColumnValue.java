package hemera.utility.sql.data.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>IntColumnValue</code> defines the immutable
 * data structure that holds an integer value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.3
 */
public final class IntColumnValue extends ColumnValue {
	/**
	 * The <code>int</code> array values.
	 */
	private final int[] values;
	
	/**
	 * Constructor of <code>IntColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param value The <code>int</code> value.
	 */
	public IntColumnValue(final String table, final String column, final int value) {
		super(table, column);
		this.values = new int[] {value};
	}
	
	/**
	 * Constructor of <code>IntColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param values The <code>int</code> array
	 * values.
	 */
	public IntColumnValue(final String table, final String column, final int[] values) {
		super(table, column);
		this.values = values;
	}

	@Override
	public void insertValue(int index, final int statementColumsCount, final PreparedStatement statement) throws SQLException {
		for (int i = 0; i < this.values.length; i++) {
			statement.setInt(index, this.values[i]);
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
