package hemera.utility.sql.data.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>LongColumnValue</code> defines the immutable
 * data structure that holds a long value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class LongColumnValue extends ColumnValue {
	/**
	 * The <code>long</code> value.
	 */
	private final long value;
	
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
		this.value = value;
	}

	@Override
	public int insertValue(final int index, final PreparedStatement statement) throws SQLException {
		statement.setLong(index, this.value);
		return 1;
	}
}
