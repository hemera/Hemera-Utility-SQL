package hemera.utility.sql.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <code>IResultsQuery</code> defines the interface of
 * a type of database queries that return a set of data
 * results after the execution. Such as select queries.
 * The returned results are contained in an instance of
 * <code>ResultSet</code> starting on the first row.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public interface IResultsQuery extends IQuery {

	/**
	 * Execute the query and return a set of results.
	 * @return The <code>ResultSet</code> containing all
	 * the result rows starting on the first row, or
	 * <code>null</code> if no results.
	 * @throws SQLException If query execution failed.
	 */
	@Override
	public ResultSet execute() throws SQLException;
}
