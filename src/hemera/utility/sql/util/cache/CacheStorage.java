package hemera.utility.sql.util.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <code>CacheStorage</code> defines the storage unit
 * of <code>CachedEntry</code>. It allows thread-safe
 * concurrent access.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class CacheStorage<K, E extends CachedEntry> {
	/**
	 * The <code>ConcurrentMap</code> of <code>K</code>
	 * to <code>E</code>.
	 */
	final ConcurrentMap<K, E> map;

	/**
	 * Constructor of <code>CacheStorage</code>.
	 */
	public CacheStorage() {
		this.map = new ConcurrentHashMap<K, E>();
		// Register with purger.
		CachePurger.instance.register(this);
	}
	
	/**
	 * Put the given key and value cache into this
	 * storage.
	 * @param key The <code>K</code> key.
	 * @param value The <code>E</code> value.
	 */
	public void put(final K key, final E value) {
		this.map.put(key, value);
	}
	
	/**
	 * Retrieve the cache value associated with given
	 * key.
	 * @param key The <code>K</code> key.
	 * @return The <code>E</code> value associated with
	 * given key. <code>null</code> if there is no such
	 * key or the value has expired.
	 */
	public E get(final K key) {
		final E value = this.map.get(key);
		if (value == null) return null;
		// Check expiration.
		final boolean expired = value.hasExpired();
		if (expired) return null;
		else {
			// Update access time.
			final long current = System.currentTimeMillis();
			value.lastAccessTime.set(current);
			return value;
		}
	}
	
	/**
	 * Remove the cache associate with given key.
	 * @param key The <code>K</code> key.
	 * @return The <code>E</code> previously associated
	 * value. <code>null</code> if there is none.
	 */
	public E remove(final K key) {
		return this.map.remove(key);
	}
}
