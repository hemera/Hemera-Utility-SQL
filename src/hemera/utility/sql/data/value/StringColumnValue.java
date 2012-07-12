package hemera.utility.sql.data.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>StringColumnValue</code> defines the immutable
 * data structure that holds an string value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class StringColumnValue extends ColumnValue {
	/**
	 * The <code>String</code> value.
	 */
	protected final String value;
	
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
		this.value = value;
	}

	@Override
	public int insertValue(final int index, final PreparedStatement statement) throws SQLException {
		statement.setString(index, this.value);
		return 1;
	}
}
