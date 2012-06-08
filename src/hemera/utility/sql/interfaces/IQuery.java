package hemera.utility.sql.interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>IQuery</code> defines the interface of a data
 * structure encapsulates all the necessary information
 * to make a specific database query.
 * <p>
 * <code>IQuery</code> internally utilizes a database
 * connection instance to execute the query statement
 * that corresponds to the query type. It may return
 * an <code>Object</code> result whose type depends on
 * the type of the query itself.
 * <p>
 * <code>IQuery</code> guarantees that each instance
 * utilizes database resources in a thread-safe manner
 * with high concurrency capabilities. However, there
 * is no guarantee that the same instance of query can
 * be used concurrently. Since query instance itself
 * is a light-weight object and the database resources
 * it utilizes are already pooled by the back-end,
 * query instances should be utilized as invocation-
 * local variables.
 * <p>
 * <code>IQuery</code> internally handles failures that
 * are caused by <code>SQLRecoverableException</code>,
 * and retries the execution until the retry count has
 * reached the maximum allowed amount specified by the
 * <code>SQLConfig</code> configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public interface IQuery {
	
	/**
	 * Prepare a statement for the current query for
	 * execution utilizing the stages defined.
	 * @return The <code>PreparedStatement</code>
	 * ready to be executed.
	 * @throws SQLException If value insertion failed.
	 */
	public PreparedStatement prepareStatement() throws SQLException;
	
	/**
	 * Execute the database query and return an object
	 * result whose type depends on the query's type.
	 * @return The result <code>Object</code>.
	 * @throws SQLException If query execution failed.
	 */
	public Object execute() throws SQLException;
	
	/**
	 * Closes the query to release all the resources
	 * pooled during the entire life time of this
	 * query.
	 * @throws SQLException If query closing failed.
	 */
	public void close() throws SQLException;
	
	/**
	 * Retrieve and increment the number of times this
	 * query has been retried for execution atomically
	 * due to a previous failure caused by a
	 * <code>SQLRecoverableException</code>
	 * @return The <code>int</code> number of times
	 * the query has been retried before increment.
	 */
	public int getAndIncrementRetryCount();
}
