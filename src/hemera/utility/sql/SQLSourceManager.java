package hemera.utility.sql;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <code>SQLSourceManager</code> defines the singleton
 * management unit responsible for maintaining a set of
 * SQL <code>SQLSource</code> instances.
 * <p>
 * <code>SQLSourceManager</code> is thread-safe while
 * providing high concurrency capabilities to allow
 * concurrent access to the managed data sources.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum SQLSourceManager {
	/**
	 * The <code>SQLDataSource</code> singleton.
	 */
	instance;
	
	/**
	 * The <code>Concurrent</code> of <code>String</code>
	 * key to <code>SQLSource</code>.
	 */
	private final ConcurrentMap<String, SQLSource> sources;

	/**
	 * Constructor of <code>SQLSourceManager</code>.
	 */
	private SQLSourceManager() {
		this.sources = new ConcurrentHashMap<String, SQLSource>();
	}

	/**
	 * Attach the data source with specified values
	 * to the manager if the key is not associated
	 * with a source already.
	 * @param key The <code>String</code> key used to
	 * identify the attached data source.
	 * @param ip The <code>String</code> IP of the
	 * host data source node.
	 * @param dbName The <code>String</code> name of
	 * the database.
	 * @param username The <code>String</code> login
	 * user name.
	 * @param password The <code>String</code> login
	 * password.
	 * @return The <code>SQLSource</code> associated
	 * with the key.
	 */
	public SQLSource attachIfAbscent(final String key, final String ip, final String dbName, final String username, final String password) {
		final SQLSource existing = this.sources.get(key);
		if (existing != null) return existing;
		// Create data source.
		final SQLSource source = new SQLSource(key, ip, dbName);
		// Connect using login credentials.
		source.datasource.setUsername(username);
		source.datasource.setPassword(password);
		return this.sources.putIfAbsent(key, source);
	}

	/**
	 * Retrieve a SQL source identified by given key.
	 * @param key The <code>String</code> key used to
	 * identify the data source.
	 * @return The <code>SQLSource</code> instance.
	 */
	public SQLSource getSource(final String key) {
		return this.sources.get(key);
	}
}
