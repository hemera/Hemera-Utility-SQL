package hemera.utility.sql;

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
	 * The <code>String</code> IP address of the host.
	 */
	public final String ip;
	/**
	 * The <code>String</code> name of the database.
	 */
	public final String dbName;
	/**
	 * The <code>BasicDataSource</code> instance.
	 */
	public final BasicDataSource datasource;
	
	/**
	 * Constructor of <code>SQLSource</code>.
	 * @param key The <code>String</code> key used to
	 * identify the attached data source.
	 * @param ip The <code>String</code> IP of the
	 * host data source node.
	 * @param dbName The <code>String</code> name of
	 * the database.
	 */
	SQLSource(final String key, final String ip, final String dbName) {
		this.key = key;
		this.ip = ip;
		this.dbName = dbName;
		// Create database URL string.
		final StringBuilder urlbuilder = new StringBuilder();
		urlbuilder.append("jdbc:mysql://").append(ip).append("/").append(this.dbName);
		final String url = urlbuilder.toString();
		// Create data source.
		final BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl(url);
		datasource.setTestOnBorrow(true);
		this.datasource = datasource;
	}
}
