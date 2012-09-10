package hemera.utility.sql.data.value;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import hemera.core.utility.data.TimeData;
import hemera.utility.sql.condition.Condition;
import hemera.utility.sql.enumn.ESign;
import hemera.utility.sql.query.result.SelectQuery;

/**
 * <code>ConfigValue</code> defines the implementation
 * of an utility unit that provides read-access for a
 * configuration value in the database. The value data
 * is cached in memory to avoid performance overhead
 * in case of multiple reads are necessary.
 * <p>
 * The data is retrieved and cached atomically in order
 * to provide memory transparent access. Once a value
 * is retrieved and cached, it will not change for the
 * lifetime of the JVM, unless it is reset. Retrievals
 * do not provide locking thread-safety, only memory
 * transparency is guaranteed.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConfigValue {
	/**
	 * The <code>String</code> source key.
	 */
	private final String sourceKey;
	/**
	 * The <code>String</code> configuration table
	 * name.
	 */
	private final String table;
	/**
	 * The <code>String</code> configuration table
	 * key column name.
	 */
	private final String keycol;
	/**
	 * The <code>String</code> configuration table
	 * value column name.
	 */
	private final String valuecol;
	/**
	 * The <code>String</code> key column for the
	 * configuration value.
	 */
	private final String key;
	/**
	 * The <code>AtomicReference</code> refrence of
	 * the cached data value.
	 */
	private final AtomicReference<Object> dataref;
	/**
	 * The <code>long</code> cache lasting duration
	 * in milliseconds.
	 */
	private final long period;
	/**
	 * The <code>long</code> last update time in
	 * milliseconds.
	 */
	private volatile long lastUpdateTime;

	/**
	 * Constructor of <code>AbstractConfig</code>.
	 * @param sourceKey The <code>String</code> key used
	 * to identify the data source.
	 * @param table The <code>String</code> table name.
	 * @param keycol The <code>String</code> key column
	 * name.
	 * @param valuecol The <code>String</code> value
	 * column name.
	 * @param key The <code>String</code> key column
	 * for the configuration value.
	 * @param period The <code>long</code> cache valied
	 * duration in milliseconds.
	 */
	public ConfigValue(final String sourceKey, final String table, final String keycol, final String valuecol, final String key, final long period) {
		this.sourceKey = sourceKey;
		this.table = table;
		this.keycol = keycol;
		this.valuecol = valuecol;
		this.key = key;
		this.dataref = new AtomicReference<Object>(null);
		this.period = period;
		this.lastUpdateTime = Long.MIN_VALUE;
	}
	
	/**
	 * Clear out cache value.
	 */
	public void clearCache() {
		this.dataref.set(null);
		this.lastUpdateTime = Long.MIN_VALUE;
	}
	
	/**
	 * Retrieve the data from cache if the cache is
	 * still valid.
	 * @return The <code>Object</code> data if the
	 * cache is still valid. <code>null</code> if
	 * there is no data cached or cache has expired.
	 */
	private Object getFromCache() {
		// Make sure cache is still valid.
		if (this.lastUpdateTime == Long.MIN_VALUE) {
			this.clearCache();
			return null;
		}
		final long current = System.currentTimeMillis();
		final long end = this.lastUpdateTime + this.period;
		if (current >= end) {
			this.clearCache();
			return null;
		}
		else return this.dataref.get();
	}
	
	/**
	 * Try to cache the given value as the data value.
	 * @param value The <code>Object</code> value to be
	 * cached.
	 * @return The <code>Object</code> data value. This
	 * is either the given value if there has not been
	 * a retrieval yet. Otherwise the original cached
	 * retrieval value is returned.
	 */
	private Object tryCache(final Object value) {
		this.dataref.compareAndSet(null, value);
		this.lastUpdateTime = System.currentTimeMillis();
		return this.dataref.get();
	}
	
	/**
	 * Retrieve the configuration as an integer value.
	 * @return The <code>int</code> value. If no such
	 * data is found, <code>-1</code> is returned.
	 */
	public int getIntValue() {
		final Integer value = (Integer)this.getFromCache();
		if (value != null) return value;
		try {
			final SelectQuery query = this.newSelectQuery();
			try {
				final ResultSet result = query.execute();
				if (result == null) return -1;
				final int retrieved = result.getInt(this.valuecol);
				return (Integer)this.tryCache(retrieved);
			} finally {
				query.close();
			}
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieve the configuration as a long value.
	 * @return The <code>long</code> value. If no such
	 * data is found, <code>-1</code> is returned.
	 */
	public long getLongValue() {
		final Long value = (Long)this.getFromCache();
		if (value != null) return value;
		try {
			final SelectQuery query = this.newSelectQuery();
			try {
				final ResultSet result = query.execute();
				if (result == null) return -1;
				final Long retrieved = result.getLong(this.valuecol);
				return (Long)this.tryCache(retrieved);
			} finally {
				query.close();
			}
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieve the configuration as a time value.
	 * @return The <code>TimeData</code> value. If no
	 * data is found, <code>null</code> is returned.
	 */
	public TimeData getTimeValue() {
		final String value = this.getStringValue();
		return new TimeData(value);
	}

	/**
	 * Retrieve the configuration as a float value.
	 * @return The <code>float</code> value. If no such
	 * data is found, <code>-1</code> is returned.
	 */
	public float getFloatValue() {
		final Float value = (Float)this.getFromCache();
		if (value != null) return value;
		try {
			final SelectQuery query = this.newSelectQuery();
			try {
				final ResultSet result = query.execute();
				if (result == null) return -1;
				final Float retrieved = result.getFloat(this.valuecol);
				return (Float)this.tryCache(retrieved);
			} finally {
				query.close();
			}
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Retrieve the configuration as a string value.
	 * @return The <code>String</code> value. If no
	 * data is found, <code>null</code> is returned.
	 */
	public String getStringValue() {
		final String value = (String)this.getFromCache();
		if (value != null) return value;
		try {
			final SelectQuery query = this.newSelectQuery();
			try {
				final ResultSet result = query.execute();
				if (result == null) return null;
				final String retrieved = result.getString(this.valuecol);
				return (String)this.tryCache(retrieved);
			} finally {
				query.close();
			}
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Retrieve the configuration as a double value.
	 * @return The <code>double</code> value. If no such
	 * data is found, <code>-1</code> is returned.
	 */
	public double getDoubleValue() {
		final Double value = (Double)this.getFromCache();
		if (value != null) return value;
		try {
			final SelectQuery query = this.newSelectQuery();
			try {
				final ResultSet result = query.execute();
				if (result == null) return -1;
				final Double retrieved = result.getDouble(this.valuecol);
				return (Double)this.tryCache(retrieved);
			} finally {
				query.close();
			}
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieve the configuration as a boolean value.
	 * @return The <code>boolean</code> value. If no
	 * data is found, <code>false</code> is returned.
	 */
	public boolean getBooleanValue() {
		final Boolean value = (Boolean)this.getFromCache();
		if (value != null) return value;
		try {
			final SelectQuery query = this.newSelectQuery();
			try {
				final ResultSet result = query.execute();
				if (result == null) return false;
				final Boolean retrieved = result.getBoolean(this.valuecol);
				return (Boolean)this.tryCache(retrieved);
			} finally {
				query.close();
			}
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create a new select query with all the proper
	 * condition and result columns set.
	 * @return The <code>SelectQuery</code> instance.
	 */
	private SelectQuery newSelectQuery() {
		final SelectQuery query = new SelectQuery(this.sourceKey);
		query.addResultColumn(this.table, this.valuecol);
		query.addCondition(new Condition().set(this.table, this.keycol, ESign.Equal, this.key));
		return query;
	}
}
