package hemera.utility.sql.data.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>IntColumnValue</code> defines the immutable
 * data structure that holds an integer value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class IntColumnValue extends ColumnValue {
	/**
	 * The <code>int</code> value.
	 */
	private final int value;
	
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
		this.value = value;
	}

	@Override
	public int insertValue(final int index, final PreparedStatement statement) throws SQLException {
		statement.setInt(index, this.value);
		return 1;
	}
}
