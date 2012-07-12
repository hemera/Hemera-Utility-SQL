package hemera.utility.sql.data.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>BooleanColumnValue</code> defines the immutable
 * data structure that holds a boolean value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class BooleanColumnValue extends ColumnValue {
	/**
	 * The <code>boolean</code> value.
	 */
	private final boolean value;
	
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
		this.value = value;
	}

	@Override
	public int insertValue(final int index, final PreparedStatement statement) throws SQLException {
		statement.setBoolean(index, this.value);
		return 1;
	}
}
