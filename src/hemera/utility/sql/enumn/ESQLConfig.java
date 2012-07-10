package hemera.utility.sql.enumn;

/**
 * <code>ESQLConfig</code> defines the enumeration of
 * configuration values used for MySQL database server
 * connection.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum ESQLConfig {
	/**
	 * The <code>Integer</code> query retry limit. The
	 * default is 3.
	 */
	Query_RetryLimit(3);
	
	/**
	 * The <code>Object</code> value.
	 */
	private volatile Object value;
	
	/**
	 * Constructor of <code>ESQLConfig</code>.
	 * @param value The default <code>Object</code>
	 * value.
	 */
	private ESQLConfig(final Object value) {
		this.value = value;
	}

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
