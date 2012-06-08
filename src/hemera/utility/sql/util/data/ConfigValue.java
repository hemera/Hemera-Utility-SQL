package hemera.utility.sql.util.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import hemera.core.utility.config.TimeData;
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
	 * Constructor of <code>AbstractConfig</code>.
	 * @param table The <code>String</code> table name.
	 * @param keycol The <code>String</code> key column
	 * name.
	 * @param valuecol The <code>String</code> value
	 * column name.
	 * @param key The <code>String</code> key column
	 * for the configuration value.
	 */
	public ConfigValue(final String table, final String keycol, final String valuecol, final String key) {
		this.table = table;
		this.keycol = keycol;
		this.valuecol = valuecol;
		this.key = key;
		this.dataref = new AtomicReference<Object>(null);
	}
	
	/**
	 * Retrieve the value cache.
	 * @return The last cached <code>Object</code> value.
	 */
	public Object clearCache() {
		final Object value = this.dataref.get();
		this.dataref.set(null);
		return value;
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
		return this.dataref.get();
	}
	
	/**
	 * Retrieve the configuration as an integer value.
	 * @return The <code>int</code> value. If no such
	 * data is found, <code>-1</code> is returned.
	 */
	public int getIntValue() {
		final Integer value = (Integer)this.dataref.get();
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
		final Long value = (Long)this.dataref.get();
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
		final String[] array = value.split(" ");
		final Long time = Long.valueOf(array[0]);
		final TimeUnit unit = TimeUnit.valueOf(array[1]);
		return new TimeData(time, unit);
	}

	/**
	 * Retrieve the configuration as a float value.
	 * @return The <code>float</code> value. If no such
	 * data is found, <code>-1</code> is returned.
	 */
	public float getFloatValue() {
		final Float value = (Float)this.dataref.get();
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
		final String value = (String)this.dataref.get();
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
		final Double value = (Double)this.dataref.get();
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
		final Boolean value = (Boolean)this.dataref.get();
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
		final SelectQuery query = new SelectQuery();
		query.addResultColumn(this.table, this.valuecol);
		query.addCondition(this.table, this.keycol, this.key, ESign.Equal, null);
		return query;
	}
}
