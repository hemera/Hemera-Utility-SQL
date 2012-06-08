package hemera.utility.sql.enumn;

/**
 * <code>ESQLConfig</code> defines the enumeration of
 * configuration values used for MySQL database server
 * connection.
 * <p>
 * All values must be set before a connection attempt
 * is made to the database server. Each enumeration
 * value has its own <code>Object</code> value to be
 * used for database configuration. The value object
 * reference guarantees its memory consistency.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum ESQLConfig {
	/**
	 * The <code>String</code> database server IP address.
	 */
	Database_ServerIP,
	/**
	 * The <code>String</code> database name to use.
	 */
	Database_DBName,
	/**
	 * The <code>String</code> login user-name.
	 */
	Login_Username,
	/**
	 * The <code>String</code> login password.
	 */
	Login_Password,
	/**
	 * The <code>Integer</code> query retry limit.
	 */
	Query_RetryLimit;
	
	/**
	 * The <code>Object</code> value.
	 */
	private volatile Object value;

	/**
	 * Set the database configuration value to given
	 * value.
	 * <p>
	 * This method guarantees the memory consistency
	 * of the value.
	 * @param value The <code>Object</code> value to
	 * set to.
	 */
	public void set(final Object value) {
		this.value = value;
	}
	
	/**
	 * Retrieve the set configuration value.
	 * <p>
	 * This method guarantees the memory consistency
	 * of the value.
	 * @return The <code>Object</code> value.
	 */
	public Object value() {
		return this.value;
	}
}
