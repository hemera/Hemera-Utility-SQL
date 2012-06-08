package hemera.utility.sql.util.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>DoubleColumnValue</code> defines the immutable
 * data structure that holds a double value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class DoubleColumnValue extends ColumnValue {
	/**
	 * The <code>double</code> value.
	 */
	private final double value;
	
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
		this.value = value;
	}

	@Override
	public void insertValue(final int index, final PreparedStatement statement) throws SQLException {
		statement.setDouble(index, this.value);
	}
}
