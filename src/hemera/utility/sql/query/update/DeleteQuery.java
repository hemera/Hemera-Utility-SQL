package hemera.utility.sql.query.update;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.interfaces.IModifyQuery;
import hemera.utility.sql.query.ConditionalQuery;
import hemera.utility.sql.util.QueryExecutor;

/**
 * <code>DeleteQuery</code> defines the implementation
 * of a SQL query that deletes an existing row from a
 * specified table. This implementation depends on the
 * values in <code>ESQLConfig</code> for database name.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class DeleteQuery extends ConditionalQuery implements IModifyQuery {
	/**
	 * The <code>String</code> name of the table to
	 * operate the query on.
	 */
	private final String tablename;

	/**
	 * Constructor of <code>DeleteQuery</code>.
	 * @param key The <code>String</code> key used to
	 * identify the data source.
	 * @param tablename The <code>String</code> name of
	 * the table.
	 */
	public DeleteQuery(final String key, final String tablename) {
		super(key);
		this.tablename = tablename;
	}
	
	@Override
	public Integer execute() throws SQLException {
		return QueryExecutor.instance.execute(this);
	}

	@Override
	protected String buildTemplate() {
		final StringBuilder builder = new StringBuilder();
		// Header.
		builder.append("delete from `").append(this.source.dbName).append("`");
		builder.append(".`").append(this.tablename).append("` ");
		// Conditions.
		final String conditions = this.buildConditionsTemplate();
		builder.append(conditions);
		builder.append(";");
		return builder.toString();
	}

	@Override
	protected void insertValues(final PreparedStatement statement) throws SQLException {
		this.insertConditionValues(statement, 1);
	}
}
