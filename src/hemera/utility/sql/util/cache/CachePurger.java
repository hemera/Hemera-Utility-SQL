package hemera.utility.sql.util.cache;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import hemera.core.execution.interfaces.IExecutionService;
import hemera.core.execution.interfaces.task.ICyclicTask;
import hemera.core.execution.interfaces.task.handle.ICyclicTaskHandle;
import hemera.core.utility.logging.FileLogger;

/**
 * <code>CachePurger</code> defines the cyclic task that
 * periodically purges the expired and unused cached
 * entries from the registered cache storages.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum CachePurger implements ICyclicTask {
	/**
	 * The singleton instance.
	 */
	instance;
	
	/**
	 * The <code>FileLogger</code> instance.
	 */
	private final FileLogger logger;
	/**
	 * The thread-safe <code>Queue</code> of all the
	 * registered <code>CacheStorage</code>.
	 */
	private final Queue<CacheStorage<?, ? extends CachedEntry>> storages;
	/**
	 * The <code>AtomicBoolean</code> indicating if
	 * the purger has been submitted for execution.
	 */
	private final AtomicBoolean submitted;
	/**
	 * The <code>ICyclicTaskHandle</code> for the
	 * purger.
	 */
	private ICyclicTaskHandle handle;
	
	/**
	 * Constructor of <code>CachePurger</code>.
	 */
	private CachePurger() {
		this.logger = FileLogger.getLogger(this.getClass());
		this.storages = new ConcurrentLinkedQueue<CacheStorage<?,? extends CachedEntry>>();
		this.submitted = new AtomicBoolean(false);
	}
	
	/**
	 * Stop the cache purger if it has been started.
	 * This method guarantees thread-safety that the
	 * purger instance is only stopped once. All
	 * subsequent attempts will perform no operations.
	 */
	public void stop() {
		if (this.submitted.compareAndSet(true, false)) {
			this.handle.terminate();
			this.handle = null;
		}
	}

	/**
	 * Start the purger with given execution service.
	 * This method guarantees thread-safety that the
	 * purger instance is only submitted once. All
	 * subsequent attempts will perform no operations.
	 * @param service The <code>IExecutionService</code>
	 * to submit the purger for execution to.
	 */
	public void start(final IExecutionService service) {
		if (this.submitted.compareAndSet(false, true)) {
			this.handle = service.submit(this);
		}
	}
	
	@Override
	public boolean execute() throws Exception {
		int totalCount = 0;
		int purgedCount = 0;
		for (final CacheStorage<?, ? extends CachedEntry> storage : this.storages) {
			final ArrayList<Object> toRemove = new ArrayList<Object>();
			// Check all keys in the storage.
			final Set<?> keys = storage.map.keySet();
			totalCount += keys.size();
			for (final Object key : keys) {
				final CachedEntry value = storage.map.get(key);
				if (value == null) continue;
				// Check expiration.
				final boolean expired = value.hasExpired();
				if (expired) toRemove.add(key);
				// Check idle time.
				final boolean idleTimeExceeded = value.hasIdleTimeExceeded();
				if (idleTimeExceeded) toRemove.add(key);
			}
			// Remove all marked.
			final int size = toRemove.size();
			purgedCount += size;
			for (int i = 0; i < size; i++) {
				final Object key = toRemove.get(i);
				storage.map.remove(key);
			}
		}
		// Log.
		final StringBuilder builder = new StringBuilder();
		builder.append("Purged ").append(purgedCount).append(" cached entries out of ");
		builder.append(totalCount).append(" entries.");
		this.logger.info(builder.toString());
		return true;
	}

	@Override
	public void cleanup() throws Exception {
		this.storages.clear();
	}

	@Override
	public void signalTerminate() throws Exception {}
	
	/**
	 * Register the given storage for periodic purging.
	 * This method guarantees thread-safety as well as
	 * concurrency. However, there is no duplicate
	 * entries check performed.
	 * @param storage The <code>CacheStorage</code> to
	 * register.
	 */
	void register(final CacheStorage<?, ? extends CachedEntry> storage) {
		this.storages.add(storage);
	}

	@Override
	public int getCycleCount() {
		return -1;
	}

	@Override
	public long getCycleLimit(final TimeUnit unit) {
		return unit.convert((Long)CacheConfig.EntryPurgeCycleTime.value, TimeUnit.MILLISECONDS);
	}
}
