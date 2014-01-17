package hemera.utility.sql.query.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hemera.utility.sql.data.value.BooleanColumnValue;
import hemera.utility.sql.data.value.ColumnValue;
import hemera.utility.sql.data.value.DoubleColumnValue;
import hemera.utility.sql.data.value.EncryptColumnValue;
import hemera.utility.sql.data.value.IntColumnValue;
import hemera.utility.sql.data.value.LongColumnValue;
import hemera.utility.sql.data.value.StringColumnValue;
import hemera.utility.sql.interfaces.IModifyQuery;
import hemera.utility.sql.query.AbstractQuery;
import hemera.utility.sql.util.QueryExecutor;

/**
 * <code>InsertQuery</code> defines the implementation
 * of a SQL query that inserts a new row into specified
 * table with given values. This implementation depends
 * on the values in <code>ESQLConfig</code> for database
 * name.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.3
 */
public class InsertQuery extends AbstractQuery implements IModifyQuery {
	/**
	 * The <code>String</code> name of the table to
	 * operate the query on.
	 */
	private final String tablename;
	/**
	 * The <code>List</code> of <code>ColumnValue</code>.
	 */
	private final List<ColumnValue> data;

	/**
	 * Constructor of <code>InsertQuery</code>.
	 * @param key The <code>String</code> key used to
	 * identify the data source.
	 * @param tablename The <code>String</code> name of
	 * the table.
	 */
	public InsertQuery(final String key, final String tablename) {
		super(key);
		this.tablename = tablename;
		this.data = new ArrayList<ColumnValue>();
	}

	/**
	 * Add the column-name value pair for the new row
	 * to be inserted.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>int</code> value for
	 * the column.
	 */
	public void addData(final String column, final int value) {
		this.data.add(new IntColumnValue(this.tablename, column, value));
	}

	/**
	 * Add the column-name value pair for the new rows
	 * to be inserted.
	 * @param column The <code>String</code> column
	 * name.
	 * @param values The <code>int</code> array values
	 * for the column.
	 */
	public void addData(final String column, final int[] values) {
		this.data.add(new IntColumnValue(this.tablename, column, values));
	}

	/**
	 * Add the column-name value pair for the new row
	 * to be inserted.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>long</code> value for
	 * the column.
	 */
	public void addData(final String column, final long value) {
		this.data.add(new LongColumnValue(this.tablename, column, value));
	}

	/**
	 * Add the column-name value pair for the new rows
	 * to be inserted.
	 * @param column The <code>String</code> column
	 * name.
	 * @param values The <code>long</code> array values
	 * for the column.
	 */
	public void addData(final String column, final long[] values) {
		this.data.add(new LongColumnValue(this.tablename, column, values));
	}

	/**
	 * Add the column-name value pair for the new row
	 * to be inserted.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>double</code> value for
	 * the column.
	 */
	public void addData(final String column, final double value) {
		this.data.add(new DoubleColumnValue(this.tablename, column, value));
	}

	/**
	 * Add the column-name value pair for the new rows
	 * to be inserted.
	 * @param column The <code>String</code> column
	 * name.
	 * @param values The <code>double</code> array values
	 * for the column.
	 */
	public void addData(final String column, final double[] values) {
		this.data.add(new DoubleColumnValue(this.tablename, column, values));
	}

	/**
	 * Add the column-name value pair for the new row
	 * to be inserted.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>boolean</code> value for
	 * the column.
	 */
	public void addData(final String column, final boolean value) {
		this.data.add(new BooleanColumnValue(this.tablename, column, value));
	}

	/**
	 * Add the column-name value pair for the new rows
	 * to be inserted.
	 * @param column The <code>String</code> column
	 * name.
	 * @param values The <code>boolean</code> array
	 * values for the column.
	 */
	public void addData(final String column, final boolean[] values) {
		this.data.add(new BooleanColumnValue(this.tablename, column, values));
	}

	/**
	 * Add the column-name value pair for the new row
	 * to be inserted.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>String</code> value for
	 * the column.
	 */
	public void addData(final String column, final String value) {
		this.data.add(new StringColumnValue(this.tablename, column, value));
	}

	/**
	 * Add the column-name value pair for the new rows
	 * to be inserted.
	 * @param column The <code>String</code> column
	 * name.
	 * @param values The <code>String</code> array values
	 * for the column.
	 */
	public void addData(final String column, final String[] values) {
		this.data.add(new StringColumnValue(this.tablename, column, values));
	}

	/**
	 * Add the column-name value pair for the new row
	 * to be inserted and encrypted with given key.
	 * @param key The <code>String</code> encryption
	 * key.
	 * @param column The <code>String</code> column
	 * name.
	 * @param value The <code>String</code> value for
	 * the column.
	 */
	public void addEncryptData(final String key, final String column, final String value) {
		this.data.add(new EncryptColumnValue(this.tablename, column, value, key));
	}

	/**
	 * Add the column-name value pair for the new rows
	 * to be inserted and encrypted with given key.
	 * @param key The <code>String</code> encryption
	 * key.
	 * @param column The <code>String</code> column
	 * name.
	 * @param values The <code>String</code> array
	 * values for the column.
	 */
	public void addEncryptData(final String key, final String column, final String[] values) {
		this.data.add(new EncryptColumnValue(this.tablename, column, values, key));
	}

	@Override
	public Integer execute() throws SQLException {
		return QueryExecutor.instance.execute(this);
	}

	@Override
	protected String buildTemplate() {
		final int valuesCount = this.validateValuesCount();
		// Header.
		final StringBuilder builder = new StringBuilder();
		builder.append("insert into `").append(this.source.dbName).append("`");
		builder.append(".`").append(this.tablename).append("` ");
		// All columns.
		builder.append("(");
		final int size = this.data.size();
		final int last = size - 1;
		for (int i = 0; i < size; i++) {
			final ColumnValue data = this.data.get(i);
			builder.append("`").append(data.table).append("`.");
			builder.append("`").append(data.column).append("`");
			if (i != last) builder.append(",");
		}
		builder.append(") ");
		// Value place-holders.
		builder.append("values ");
		final int lastValueIndex = valuesCount - 1;
		for (int i = 0; i < valuesCount; i++) {
			builder.append("(");
			for (int j = 0; j < size; j++) {
				final ColumnValue data = this.data.get(j);
				if (data instanceof EncryptColumnValue) {
					builder.append("AES_ENCRYPT(?, ?)");
				} else {
					builder.append("?");
				}
				if (j != last) builder.append(",");
			}
			builder.append(")");
			if (i != lastValueIndex) {
				builder.append(", ");
			}
		}
		builder.append(";");
		return builder.toString();
	}

	/**
	 * Validate the number of values to ensure that
	 * they all match.
	 * @return The number of values.
	 */
	private int validateValuesCount() {
		final int count = this.data.get(0).getValuesCount();
		final int size = this.data.size();
		for (int i = 1; i < size; i++) {
			if (count != this.data.get(i).getValuesCount()) {
				throw new RuntimeException("Inconsistent data values count.");
			}
		}
		return count;
	}

	@Override
	protected void insertValues(final PreparedStatement statement) throws SQLException {
		final int stateColumnsCount = this.getStateColumnsCount();
		final int size = this.data.size();
		int index = 1;
		for (int i = 0; i < size; i++) {
			final ColumnValue data = this.data.get(i);
			data.insertValue(index, stateColumnsCount, statement);
			if (data instanceof EncryptColumnValue) {
				index += 2;
			} else {
				index++;
			}
		}
	}
	
	/**
	 * Retrieve the statement column count including
	 * encrypted columns.
	 * @return The <code>int</code> count value.
	 */
	private int getStateColumnsCount() {
		int count = 0;
		final int size = this.data.size();
		for (int i = 0; i < size; i++) {
			final ColumnValue data = this.data.get(i);
			if (data instanceof EncryptColumnValue) {
				count += 2;
			} else {
				count++;
			}
		}
		return count;
	}
}
