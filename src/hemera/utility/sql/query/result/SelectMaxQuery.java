package hemera.utility.sql.query.result;

import java.sql.SQLException;

import hemera.utility.sql.util.data.TableColumn;

/**
 * <code>SelectMaxQuery</code> defines the selection
 * query that returns the maximum value of a specified
 * column.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class SelectMaxQuery extends AbstractSelectQuery {
	/**
	 * The <code>TableColumn</code> to select maximum
	 * value from.
	 */
	private final TableColumn resultcolumn;
	
	/**
	 * Constructor of <code>SelectMaxQuery</code>.
	 * @param key The <code>String</code> key used to
	 * identify the data source.
	 * @param table The <code>String</code> name of
	 * the table the column belongs.
	 * @param column The <code>String</code> column
	 * to select the maximum value of.
	 */
	public SelectMaxQuery(final String key, final String table, final String column) {
		super(key);
		if (!this.tables.contains(table)) {
			this.tables.add(table);
		}
		this.resultcolumn = new TableColumn(table, column);
	}

	@Override
	protected String buildResultTemplate() {
		final StringBuilder builder = new StringBuilder();
		builder.append("max(");
		builder.append("`").append(this.resultcolumn.table).append("`.");
		builder.append("`").append(this.resultcolumn.column).append("`");
		builder.append(")");
		return builder.toString();
	}
	
	/**
	 * Retrieve the maximum integer value from of the
	 * result column specified.
	 * @return The <code>int</code> value. If there are
	 * no matching entries, <code>Integer.MIN_VALUE</code>
	 * is returned.
	 * @throws SQLException If result set access failed.
	 */
	public int getMaxValue() throws SQLException {
		if (this.resultset == null) return Integer.MIN_VALUE;
		else return this.resultset.getInt(1);
	}
}
