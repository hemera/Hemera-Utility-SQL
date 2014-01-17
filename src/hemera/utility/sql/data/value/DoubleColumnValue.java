package hemera.utility.sql.data.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>DoubleColumnValue</code> defines the immutable
 * data structure that holds a double value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.3
 */
public final class DoubleColumnValue extends ColumnValue {
	/**
	 * The <code>double</code> array values.
	 */
	private final double[] values;
	
	/**
	 * Constructor of <code>DoubleColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param value The <code>double</code> value.
	 */
	public DoubleColumnValue(final String table, final String column, final double value) {
		super(table, column);
		this.values = new double[] {value};
	}
	
	/**
	 * Constructor of <code>DoubleColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param values The <code>double</code> array
	 * values.
	 */
	public DoubleColumnValue(final String table, final String column, final double[] values) {
		super(table, column);
		this.values = values;
	}

	@Override
	public void insertValue(int index, final int statementColumsCount, final PreparedStatement statement) throws SQLException {
		for (int i = 0; i < this.values.length; i++) {
			statement.setDouble(index, this.values[i]);
			index += statementColumsCount;
		}
	}
	
	@Override
	public int getValuesCount() {
		return this.values.length;
	}
}
