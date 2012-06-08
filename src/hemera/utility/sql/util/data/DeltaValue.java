package hemera.utility.sql.util.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>DeltaValue</code> defines the table column value
 * that contains an integer delta change to the value of
 * the column.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public final class DeltaValue extends ColumnValue {
	/**
	 * The <code>int</code> delta amount.
	 */
	public final int delta;

	/**
	 * Constructor of <code>DeltaValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param delta The <code>int</code> delta amount.
	 */
	public DeltaValue(String table, String column, final int delta) {
		super(table, column);
		this.delta = delta;
	}

	@Override
	public void insertValue(final int index, final PreparedStatement statement) throws SQLException {}
}
