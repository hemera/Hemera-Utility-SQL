package hemera.utility.sql.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;

import hemera.core.utility.logging.FileLogger;
import hemera.utility.sql.SQLSource;
import hemera.utility.sql.SQLSourceManager;
import hemera.utility.sql.enumn.ESQLConfig;
import hemera.utility.sql.interfaces.IModifyQuery;
import hemera.utility.sql.interfaces.IQuery;
import hemera.utility.sql.interfaces.IResultsQuery;

/**
 * <code>QueryExecutor</code> defines implementation
 * of an utility class that provides commonly shared
 * query execution procedures.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum QueryExecutor {
	/**
	 * The singleton instance.
	 */
	instance;
	
	/**
	 * The <code>FileLogger</code> instance.
	 */
	private final FileLogger logger;
	
	/**
	 * Constructor of <code>QueryExecutor</code>.
	 */
	private QueryExecutor() {
		this.logger = FileLogger.getLogger(this.getClass());
	}
	
	/**
	 * Execute the given modify query.
	 * @param <M> The query class that implements the
	 * <code>IModifyQuery</code> interface and extend
	 * the <code>AbstractQuery</code> class.
	 * @param query The <code>M</code> query to execute.
	 * @return The <code>Integer</code> result.
	 * @throws SQLException If execution failed.
	 */
	public <M extends IModifyQuery> Integer execute(final M query) throws SQLException {
		try {
			// Prepare statement.
			final PreparedStatement statement = query.prepareStatement();
			// Execute statement.
			return statement.executeUpdate();
		} catch (final SQLRecoverableException e) {
			return (Integer)this.retryQuery(e, query);
		} catch (final NullPointerException e) {
			return (Integer)this.retryQuery(e, query);
		}
	}
	
	/**
	 * Execute the given result query.
	 * @param <M> The query class that implements the
	 * <code>IResultsQuery</code> interface and extend
	 * the <code>AbstractQuery</code> class.
	 * @param query The <code>M</code> query to execute.
	 * @return The <code>ResultSet</code> results.
	 * @throws SQLException If execution failed.
	 */
	public <M extends IResultsQuery> ResultSet execute(final M query) throws SQLException {
		try {
			// Prepare statement.
			final PreparedStatement statement = query.prepareStatement();
			// Execute statement.
			final ResultSet results = statement.executeQuery();
			// No results.
			if (!results.next()) return null;
			// Return results.
			return results;
		} catch (final SQLRecoverableException e) {
			return (ResultSet)this.retryQuery(e, query);
		} catch (final NullPointerException e) {
			return (ResultSet)this.retryQuery(e, query);
		}
	}
	
	/**
	 * Retry the given query interrupted by the given
	 * exception if the retry limit is not reached.
	 * <p>
	 * This method checks to see if the query can be
	 * retried based on its retry count and causing
	 * exception.
	 * @param <M> The <code>AbstractQuery</code> to
	 * be executed.
	 * @param e The <code>SQLException</code> caused
	 * the execution failure.
	 * @param query The <code>M</code> failed query.
	 * @return The <code>Object</code> query return
	 * value.
	 * @throws SQLException If query retry failed.
	 */
	private <M extends IQuery> Object retryQuery(final Exception e, final M query) throws SQLException {
		// Check if retry is allowed.
		if (this.allowRetry(e, query)) {
			// Logging.
			final StringBuilder builder = new StringBuilder();
			builder.append(query.getClass().getName());
			builder.append(" execution failed due to:\n");
			builder.append(e.toString()).append("\n");
			builder.append(e.getMessage()).append("\n");
			builder.append("Attempt to retry.");
			this.logger.warning(builder.toString());
			// Reconnect the data source.
			final SQLSource source = SQLSourceManager.instance.getSource(query.getKey());
			source.reconnect();
			// Retry.
			return query.execute();
		} else {
			throw new SQLException(e);
		}
	}
	
	/**
	 * Check if the given failed query due to the given
	 * exception should be retried for execution.
	 * @param <M> The <code>AbstractQuery</code> to
	 * be check.
	 * @param e The <code>Exception</code> caused the
	 * execution failure.
	 * @param query The <code>M</code> failed query.
	 * @return <code>true</code> if the query should be
	 * retried for execution. <code>false</code> if
	 * either the exception is not recoverable or the
	 * query's retry limit has been reached.
	 */
	private <M extends IQuery> boolean allowRetry(final Exception e, final M query) {
		// First check limit.
		final int count = query.getAndIncrementRetryCount();
		final int limit = (Integer)ESQLConfig.Query_RetryLimit.value();
		if (count >= limit) return false;
		// Allow SQL recoverable.
		if (e instanceof SQLRecoverableException) {
			return true;
		}
		// Allow JDBC NPE.
		else if (e.getClass().equals(NullPointerException.class)) {
			for (final StackTraceElement s : e.getStackTrace()) {
				final String classname = s.getClassName();
				final String methodname = s.getMethodName();
				if (classname.equals("com.mysql.jdbc.ConnectionImpl") && methodname.equals("getServerCharacterEncoding")) {
					return true;
				}
			}
		}
		return false;
	}
}
