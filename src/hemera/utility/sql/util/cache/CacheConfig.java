package hemera.utility.sql.util.cache;

import java.util.concurrent.TimeUnit;

/**
 * <code>CacheConfig</code> defines the enumerations of
 * configurations related to memory cache. The values of
 * the configurations should be modified before a cache
 * storage is created.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum CacheConfig {
	/**
	 * The <code>long</code> configuration cache lifetime
	 * in milliseconds. The default value is 1 hour.
	 */
	ConfigLifetime(TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)),
	/**
	 * The <code>long</code> maximum time a cached entry
	 * can stay idle, not accessed before it is purged in
	 * milliseconds. The default value is 1 hour.
	 */
	MaxEntryIdleTime(TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)),
	/**
	 * The <code>long</code> execution cycle time of
	 * the database entry cache purging process in
	 * milliseconds. The default value is 30 minutes.
	 */
	EntryPurgeCycleTime(TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES));
	
	/**
	 * The <code>long</code> value.
	 */
	public long value;
	
	/**
	 * Constructor of <code>CacheConfig</code>.
	 * @param value The <code>long</code> default
	 * value.
	 */
	private CacheConfig(final long value) {
		this.value = value;
	}
}
