package hemera.utility.sql.data.value;

import hemera.utility.sql.data.TableColumn;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>ColumnValue</code> defines an abstraction of
 * an immutable data structure that represents a value
 * of a specific column in a specific table.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.3
 */
public abstract class ColumnValue extends TableColumn {
	
	/**
	 * Constructor of <code>ColumnValue</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 */
	public ColumnValue(final String table, final String column) {
		super(table, column);
	}
	
	/**
	 * Insert the value into the given statement for
	 * query execution.
	 * @param index The <code>int</code> index to insert
	 * value at.
	 * @param statementColumsCount The <code>int</code>
	 * number of columns for the entire statement.
	 * @param statement The <code>PreparedStatement</code>
	 * based on the first stage template.
	 * @throws SQLException If value insertion failed.
	 */
	public abstract void insertValue(final int index, final int statementColumsCount, final PreparedStatement statement) throws SQLException;

	/**
	 * Retrieve the number of values for the column.
	 * @return The <code>int</code> number of values.
	 */
	public abstract int getValuesCount();
}
