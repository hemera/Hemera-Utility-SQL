package hemera.utility.sql.query.result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hemera.utility.sql.enumn.EOrder;
import hemera.utility.sql.enumn.ERelation;
import hemera.utility.sql.enumn.ESign;
import hemera.utility.sql.interfaces.IResultsQuery;
import hemera.utility.sql.query.ConditionalQuery;
import hemera.utility.sql.util.QueryExecutor;

/**
 * <code>AbstractSelectQuery</code> defines a the
 * abstraction of a selection query that maintains a
 * set of conditions.
 * <p>
 * <code>AbstractSelectQuery</code> internally manages
 * its result set instance and properly releases result
 * set resources when the query is closed.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
abstract class AbstractSelectQuery extends ConditionalQuery implements IResultsQuery {
	/**
	 * The <code>List</code> of <code>String</code> of
	 * tables this query is operating on.
	 * <p>
	 * Must use a list to guarantee ordering so the
	 * query template is the same for different values
	 * allowing the statement to be reused.
	 */
	protected final List<String> tables;
	/**
	 * The <code>int</code> limit on the results.
	 */
	private int limit;
	/**
	 * The <code>EOrder</code> results ordering method.
	 */
	private EOrder ordering;
	/**
	 * The <code>String</code> table of the column to
	 * order results by.
	 */
	private String ordertable;
	/**
	 * The <code>String</code> column to order results
	 * by.
	 */
	private String ordercolumn;
	/**
	 * The <code>ResultSet</code> instance.
	 */
	protected ResultSet resultset;

	/**
	 * Constructor of <code>AbstractSelectQuery</code>.
	 * @param key The <code>String</code> key used to
	 * identify the data source.
	 */
	protected AbstractSelectQuery(final String key) {
		super(key);
		this.tables = new ArrayList<String>();
	}

	@Override
	public final void addCondition(final String table, final String column, final int value, final ESign sign, final ERelation relation) {
		super.addCondition(table, column, value, sign, relation);
		if (!this.tables.contains(table)) {
			this.tables.add(table);
		}
	}

	@Override
	public final void addCondition(final String table, final String column, final long value, final ESign sign, final ERelation relation) {
		super.addCondition(table, column, value,  sign, relation);
		if (!this.tables.contains(table)) {
			this.tables.add(table);
		}
	}

	@Override
	public final void addCondition(final String table, final String column, final String value, final ESign sign, final ERelation relation) {
		super.addCondition(table, column, value, sign, relation);
		if (!this.tables.contains(table)) {
			this.tables.add(table);
		}
	}

	@Override
	public final void addRangeCondition(final String table, final String column, final int lower, final int higher, final ERelation relation) {
		super.addRangeCondition(table, column, lower, higher, relation);
		if (!this.tables.contains(table)) {
			this.tables.add(table);
		}
	}

	@Override
	public final void addCondition(final String table, final String column, final boolean value, final ESign sign, final ERelation relation) {
		super.addCondition(table, column, value, sign, relation);
		if (!this.tables.contains(table)) {
			this.tables.add(table);
		}
	}

	@Override
	public final void addPasswordCondition(final String table, final String column, final String value, final ESign sign, final ERelation relation) {
		super.addPasswordCondition(table, column, value, sign, relation);
		if (!this.tables.contains(table)) {
			this.tables.add(table);
		}
	}
	
	@Override
	public final void addJointCondition(final String table1, final String column1, final String table2, final String column2,
			final ESign sign, final ERelation relation) {
		super.addJointCondition(table1, column1, table2, column2, sign, relation);
		if (!this.tables.contains(table1)) {
			this.tables.add(table1);
		}
		if (!this.tables.contains(table2)) {
			this.tables.add(table2);
		}
	}

	@Override
	public void addEncryptionCondition(final String table, final String column, final String value, final String key, final ESign sign, final ERelation relation) {
		super.addEncryptionCondition(table, column, value, key, sign, relation);
		if (!this.tables.contains(table)) {
			this.tables.add(table);
		}
	}

	@Override
	public final void addDistanceCondition(final String table, final String latitudeCol, final String longitudeCol,
			final double latitude, final double longitude, final double distance, final ESign sign, final ERelation relation) {
		super.addDistanceCondition(table, latitudeCol, longitudeCol, latitude, longitude, distance, sign, relation);
		if (!this.tables.contains(table)) {
			this.tables.add(table);
		}
	}

	/**
	 * Set the limit on the number of results returned.
	 * @param limit The <code>int</code> limit number.
	 */
	public final void setLimit(final int limit) {
		this.limit = limit;
	}

	/**
	 * Set the results ordering table-column and the
	 * ordering method.
	 * @param order The <code>EOrder</code> ordering
	 * method.
	 * @param table The <code>String</code> name of
	 * the table of the column to order results by.
	 * @param column The <code>String</code> name
	 * of the column to order by.
	 */
	public final void setOrdering(final EOrder order, final String table, final String column) {
		this.ordering = order;
		this.ordertable = table;
		this.ordercolumn = column;
	}
	
	@Override
	public final ResultSet execute() throws SQLException {
		this.resultset = QueryExecutor.instance.execute(this);
		return this.resultset;
	}
	
	@Override
	public final void close() throws SQLException {
		try {
			if (this.resultset != null) {
				this.resultset.close();
			}
		} finally {
			super.close();
		}
	}

	@Override
	protected final String buildTemplate() {
		final StringBuilder builder = new StringBuilder();
		final String result = this.buildResultTemplate();
		builder.append("select ").append(result).append(" ");
		// From.
		builder.append("from ");
		final int tsize = this.tables.size();
		final int tlast = tsize - 1;
		for (int i = 0; i < tsize; i++) {
			final String table = this.tables.get(i);
			builder.append("`").append(this.source.dbName).append("`.");
			builder.append("`").append(table).append("`");
			if (i != tlast) builder.append(",");
		}
		builder.append(" ");
		// Conditions.
		final String conditions = this.buildConditionsTemplate();
		builder.append(conditions);
		// Order.
		if (this.ordertable != null && this.ordercolumn != null && this.ordering != null) {
			builder.append(" order by ");
			builder.append("`").append(this.ordertable).append("`.");
			builder.append("`").append(this.ordercolumn).append("`");
			builder.append(" ").append(this.ordering.value);
		}
		// Limit.
		if (this.limit > 0) {
			builder.append(" limit ").append(this.limit);
		}
		builder.append(";");
		return builder.toString();
	}

	/**
	 * Build the query template result portion.
	 * @return The <code>String</code> template.
	 */
	protected abstract String buildResultTemplate();

	@Override
	protected final void insertValues(final PreparedStatement statement) throws SQLException {
		final int index = this.insertResultValues(statement);
		this.insertConditionValues(statement, index);
	}
	
	/**
	 * Insert the result column necessary values.
	 * @param statement The <code>PreparedStatement</code>
	 * to insert into.
	 * @return The <code>int</code> next index. If
	 * no values are inserted, return <code>1</code>.
	 * @throws SQLException If insertion failed.
	 */
	protected abstract int insertResultValues(final PreparedStatement statement) throws SQLException;
}
