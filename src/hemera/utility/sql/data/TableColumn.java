package hemera.utility.sql.data;

/**
 * <code>TableColumn</code> defines implementation of
 * an immutable data structure that represents a column
 * of a specific table.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class TableColumn {
	/**
	 * The <code>String</code> name of the table.
	 */
	public final String table;
	/**
	 * The <code>String</code> name of the column.
	 */
	public final String column;
	
	/**
	 * Constructor of <code>TableColumn</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 */
	public TableColumn(final String table, final String column) {
		this.table = table;
		this.column = column;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (o == null) return false;
		else if (o instanceof TableColumn) {
			final TableColumn given = (TableColumn)o;
			return (given.table.equals(this.table) && given.column.equals(this.column));
		} else {
			return false;
		}
	}
}
