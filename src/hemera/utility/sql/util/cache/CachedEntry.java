package hemera.utility.sql.util.cache;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <code>CachedEntry</code> defines the data structure
 * of a database entry that is cached in memory for a
 * limited amount of time.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public abstract class CachedEntry {
	/**
	 * The <code>long</code> caching time stamp in
	 * milliseconds.
	 */
	private final long cacheTimestamp;
	/**
	 * The <code>AtomicLong</code> of cache entry last
	 * access time.
	 */
	final AtomicLong lastAccessTime;
	
	/**
	 * Constructor of <code>CachedEntry</code>.
	 */
	protected CachedEntry() {
		this.cacheTimestamp = System.currentTimeMillis();
		this.lastAccessTime = new AtomicLong(this.cacheTimestamp);
	}
	
	/**
	 * Check if this cached entry has expired.
	 * @return <code>true</code> if entry has expired
	 * <code>false</code> otherwise.
	 */
	public boolean hasExpired() {
		final long lifetime = this.getLifetime();
		if (lifetime < 0) return false;
		final long end = this.cacheTimestamp + lifetime;
		final long current = System.currentTimeMillis();
		if (current >= end) return true;
		else return false;
	}
	
	/**
	 * Check if this cached entry has exceeded the
	 * maximum idle time, thus should be purged from
	 * cache.
	 * @return <code>true</code> if the cached entry
	 * has exceeded the maximum idle time and should
	 * be purged. <code>false</code> otherwise.
	 */
	public boolean hasIdleTimeExceeded() {
		final long maxIdleTime = (Long)CacheConfig.MaxEntryIdleTime.value;
		final long current = System.currentTimeMillis();
		final long end = this.lastAccessTime.get() + maxIdleTime;
		if (current >= end) return true;
		else return false;
	}
	
	/**
	 * Retrieve the lifetime of this type of cache.
	 * @return The <code>long</code> lifetime in milli-
	 * seconds. A negative value indicates the entry
	 * can be cached indefinitely.
	 */
	public abstract long getLifetime();
}
