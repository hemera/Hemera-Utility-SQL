package hemera.utility.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import hemera.utility.sql.enumn.ESQLConfig;

/**
 * <code>SQLDataSource</code> defines the singleton
 * implementation of a MySQL database data source
 * instance that is responsible as an interface to
 * the physical data source. It utilizes connection
 * pooling for obtaining data connections.
 * <p>
 * <code>SQLDataSource</code> is thread-safe with
 * high concurrency capabilities to allow concurrent
 * use of its pool of SQL database connections.
 * <p>
 * <code>SQLDataSource</code> depends on configuration
 * values set in <code>ESQLConfig</code> enumeration
 * to connect to the database server.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum SQLDataSource {
	/**
	 * The <code>SQLDataSource</code> singleton.
	 */
	instance;

	/**
	 * The <code>DataSource</code> instance.
	 */
	private DataSource datasource;

	/**
	 * Constructor of <code>SQLDataSource</code>.
	 */
	private SQLDataSource() {
		this.initialize();
	}

	/**
	 * Initialize the data source with the values in
	 * the <code>ESQLConfig</code> enumerations, and
	 * return the previously initialized data source.
	 * <p>
	 * This method is automatically invoked during the
	 * first access of the instance. Subsequent calls
	 * will re-create a new data source with values
	 * in the configuration.
	 * @return The previously initialized instance of
	 * <code>DataSource</code>.
	 */
	public DataSource initialize() {
		final DataSource previous = this.datasource;
		// Create database URL string.
		final String ip = (String)ESQLConfig.Database_ServerIP.value();
		final String dbname = (String)ESQLConfig.Database_DBName.value();
		final StringBuilder urlbuilder = new StringBuilder();
		urlbuilder.append("jdbc:mysql://").append(ip).append("/").append(dbname);
		final String url = urlbuilder.toString();
		// Connect using login credentials.
		final String username = (String)ESQLConfig.Login_Username.value();
		final String password = (String)ESQLConfig.Login_Password.value();
		final BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setTestOnBorrow(true);
		datasource.setValidationQuery("select idvenue from venue where idvenue=1;");
		this.datasource = datasource;
		return previous;
	}

	/**
	 * Retrieve a pooled database connection from the
	 * maintained connection pool.
	 * @return The <code>Connection</code> instance.
	 * @throws SQLException If database access error
	 * occurs.
	 */
	public Connection getConnection() throws SQLException {
		return this.datasource.getConnection();
	}
}
