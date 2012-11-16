package hemera.utility.sql.util.cache;

import hemera.utility.sql.util.cache.CachedEntry;

/**
 * <code>ICachedAdministration</code> defines the
 * interface for database administrations that support
 * caching of its entries.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public interface ICachedAdministration<E extends CachedEntry> {

	/**
	 * Put the given entry into cache. The entry itself
	 * should contain the proper key needed.
	 * @param entry The <code>E</code> entry.
	 */
	public void putIntoCache(final E entry);
	
	/**
	 * Remove the given entry from cache. The entry
	 * itself should contain the proper key needed.
	 * @param entry The <code>E</code> entry.
	 */
	public void removeFromCache(final E entry);
}
