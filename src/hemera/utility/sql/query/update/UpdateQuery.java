package hemera.utility.sql.query.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hemera.utility.sql.interfaces.IModifyQuery;
import hemera.utility.sql.query.ConditionalQuery;
import hemera.utility.sql.util.QueryExecutor;
import hemera.utility.sql.util.data.BooleanColumnValue;
import hemera.utility.sql.util.data.ColumnValue;
import hemera.utility.sql.util.data.DeltaValue;
import hemera.utility.sql.util.data.DoubleColumnValue;
import hemera.utility.sql.util.data.IntColumnValue;
import hemera.utility.sql.util.data.LongColumnValue;
import hemera.utility.sql.util.data.StringColumnValue;

/**
 * <code>UpdateQuery</code> defines the implementation
 * of a SQL query that updates the values of an existing
 * row in a specified table. This implementation depends
 * on the values in <code>ESQLConfig</code> for database
 * name.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class UpdateQuery extends ConditionalQuery implements IModifyQuery {
	/**
	 * The <code>String</code> name of the table to
	 * operate the query on.
	 */
	private final String tablename;
	/**
	 * The <code>List</code> of <code>DataPair</code>.
	 */
	private final List<ColumnValue> data;
	
	/**
	 * Constructor of <code>UpdateQuery</code>.
	 * @param key The <code>String</code> key used to
	 * identify the data source.
	 * @param tablename The <code>String</code> name of
	 * the table.
	 */
	public UpdateQuery(final String key, final String tablename) {
		super(key);
		this.tablename = tablename;
		this.data = new ArrayList<ColumnValue>();
	}
	
	/**
	 * Add the column-name value pair for to be set.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>int</code> value for
	 * the column.
	 */
	public void addData(final String column, final int value) {
		this.data.add(new IntColumnValue(this.tablename, column, value));
	}
	
	/**
	 * Add the column-name value pair for to be set.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>long</code> value for
	 * the column.
	 */
	public void addData(final String column, final long value) {
		this.data.add(new LongColumnValue(this.tablename, column, value));
	}
	
	/**
	 * Add the column-name value pair for to be set.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>double</code> value for
	 * the column.
	 */
	public void addData(final String column, final double value) {
		this.data.add(new DoubleColumnValue(this.tablename, column, value));
	}

	/**
	 * Add the column-name value pair for to be set.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>boolean</code> value for
	 * the column.
	 */
	public void addData(final String column, final boolean value) {
		this.data.add(new BooleanColumnValue(this.tablename, column, value));
	}
	
	/**
	 * Add the column-name value pair for to be set.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>String</code> value for
	 * the column.
	 */
	public void addData(final String column, final String value) {
		this.data.add(new StringColumnValue(this.tablename, column, value));
	}
	
	/**
	 * Update the specified column by the delta integer
	 * amount.
	 * @param column The <code>String</code> column
	 * name.
	 * @param delta The <code>int</code> delta amount.
	 */
	public void updateDelta(final String column, final int delta) {
		this.data.add(new DeltaValue(this.tablename, column, delta));
	}
	
	@Override
	public Integer execute() throws SQLException {
		return QueryExecutor.instance.execute(this);
	}

	@Override
	protected String buildTemplate() {
		final StringBuilder builder = new StringBuilder();
		// Header.
		builder.append("update `").append(this.source.dbName).append("`");
		builder.append(".`").append(this.tablename).append("` ");
		// Set values.
		builder.append("set ");
		final int size = this.data.size();
		final int last = size - 1;
		for (int i = 0; i < size; i++) {
			final ColumnValue data = this.data.get(i);
			builder.append("`").append(data.table).append("`.");
			builder.append("`").append(data.column).append("`");
			builder.append("=");
			// Delta change is a special case.
			if (data instanceof DeltaValue) {
				builder.append("`").append(data.table).append("`.");
				builder.append("`").append(data.column).append("`");
				builder.append("+").append(((DeltaValue)data).delta);
			} else {
				builder.append("?");
			}
			if (i != last) builder.append(",");
		}
		builder.append(" ");
		// Conditions.
		final String conditions = this.buildConditionsTemplate();
		builder.append(conditions);
		builder.append(";");
		return builder.toString();
	}

	@Override
	protected void insertValues(final PreparedStatement statement) throws SQLException {
		// This invocation order correlates to the template order.
		final int count = this.insertSetValues(statement);
		this.insertConditionValues(statement, count+1);
	}
	
	/**
	 * Insert the values to be set.
	 * @param statement The <code>PreparedStatement</code>
	 * based on the first stage template.
	 * @return The <code>int</code> number of values
	 * inserted.
	 * @throws SQLException If value insertion failed.
	 */
	private int insertSetValues(final PreparedStatement statement) throws SQLException {
		final int size = this.data.size();
		int index = 0;
		for (int i = 0; i < size; i++) {
			final ColumnValue data = this.data.get(i);
			if (data instanceof DeltaValue) continue;
			data.insertValue(index+1, statement);
			index++;
		}
		return index;
	}
}
