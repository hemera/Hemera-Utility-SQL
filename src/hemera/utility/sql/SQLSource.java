package hemera.utility.sql;

import hemera.utility.sql.config.SQLConfig;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * <code>SQLSource</code> defines the immutable structure
 * representing a SQL data source.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class SQLSource {
	/**
	 * The <code>String</code> key.
	 */
	public final String key;
	/**
	 * The <code>String</code> address of the host.
	 */
	private final String host;
	/**
	 * The <code>int</code> port of the host.
	 */
	private final int port;
	/**
	 * The <code>String</code> name of the database.
	 */
	public final String dbName;
	/**
	 * The <code>String</code> login user name.
	 */
	private final String dbUsername;
	/**
	 * The <code>String</code> login password.
	 */
	private final String dbPassword;
	/**
	 * The <code>BasicDataSource</code> instance.
	 */
	public volatile BasicDataSource datasource;

	/**
	 * Constructor of <code>SQLSource</code>.
	 * @param key The <code>String</code> key used to
	 * identify the attached data source.
	 * @param host The <code>String</code> address of
	 * the host.
	 * @param port The <code>int</code> port of the
	 * host to connect to.
	 * @param dbName The <code>String</code> name of
	 * the database.
	 * @param dbUsername The <code>String</code> user
	 * name used to login to the database.
	 * @param dbPassword The <code>String</code> password
	 * used to login to the database.
	 */
	SQLSource(final String key, final String host, final int port, final String dbName,
			final String dbUsername, final String dbPassword) {
		this.key = key;
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		this.reconnect();
	}

	/**
	 * Re-create the data source and connect to the
	 * remote host.
	 */
	public synchronized void reconnect() {
		// Create database URL string.
		final StringBuilder urlbuilder = new StringBuilder();
		urlbuilder.append("jdbc:mysql://").append(this.host).append(":").append(this.port).append("/").append(this.dbName);
		final String url = urlbuilder.toString();
		// Create data source.
		final BasicDataSource datasource = new BasicDataSource();
		datasource.setDefaultAutoCommit((Boolean)SQLConfig.AutoCommit.value());
		datasource.setInitialSize((Integer)SQLConfig.InitialPoolSize.value());
		datasource.setMaxActive((Integer)SQLConfig.MaxPoolSize.value());
		datasource.setMaxIdle((Integer)SQLConfig.MaxIdleSize.value());
		datasource.setMinIdle((Integer)SQLConfig.MinIdleSize.value());
		datasource.setMaxWait((Long)SQLConfig.MaxWaitTime.value());
		datasource.setValidationQuery((String)SQLConfig.ValidationQuery.value());
		datasource.setTestOnBorrow((Boolean)SQLConfig.TestOnBorrow.value());
		datasource.setTestOnReturn((Boolean)SQLConfig.TestOnReturn.value());
		datasource.setTestWhileIdle((Boolean)SQLConfig.TestWhileIdle.value());
		datasource.setPoolPreparedStatements((Boolean)SQLConfig.PoolQueryStatements.value());
		datasource.setRemoveAbandoned((Boolean)SQLConfig.RemoveAbandoned.value());
		datasource.setRemoveAbandonedTimeout((Integer)SQLConfig.RemoveAbandonedTimeout.value());
		datasource.setLogAbandoned((Boolean)SQLConfig.LogAbandonedCode.value());
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl(url);
		datasource.setTestOnBorrow(true);
		datasource.setUsername(this.dbUsername);
		datasource.setPassword(this.dbPassword);
		this.datasource = datasource;
	}
	
	/**
	 * Close the data source connection.
	 * @throws SQLException If closing failed.
	 */
	public synchronized void close() throws SQLException {
		this.datasource.close();
	}
}
