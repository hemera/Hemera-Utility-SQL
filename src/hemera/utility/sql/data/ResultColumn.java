package hemera.utility.sql.data;

/**
 * <code>ResultColumn</code> defines the immutable
 * data structure that represents a table column used
 * for a select query.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.2
 */
public class ResultColumn extends TableColumn {
	/**
	 * The <code>boolean</code> indicating if the column
	 * selection should be distinct.
	 */
	public final boolean distinct;

	/**
	 * Constructor of <code>DecryptColumn</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param distinct The <code>boolean</code>
	 * indicating if the column selection should be
	 * distinct.
	 */
	public ResultColumn(final String table, final String column, final boolean distinct) {
		super(table, column);
		this.distinct = distinct;
	}
}
