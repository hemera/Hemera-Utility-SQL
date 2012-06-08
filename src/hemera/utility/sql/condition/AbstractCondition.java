package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>AbstractCondition</code> defines the basic
 * abstraction of a query conditional check. It has
 * its own template that can be used as part of the
 * overall query template. It provides the method to
 * insert values into the prepared statement based
 * on its template.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public abstract class AbstractCondition {
	/**
	 * The <code>String</code> condition template.
	 */
	private String template;

	/**
	 * Insert the conditional check values into the
	 * given statement place-holder positions using
	 * the given start index.
	 * @param statement The <code>PreparedStatement</code>
	 * to insert the values in.
	 * @param start The <code>int</code> starting
	 * index to insert the values at. The index
	 * starts at <code>1</code>.
	 * @return The <code>int</code> number of values
	 * inserted.
	 * @throws SQLException If insertion failed.
	 */
	public abstract int insertValues(final PreparedStatement statement, final int start) throws SQLException;
	
	/**
	 * Build the template of this condition.
	 * @return The <code>String</code> template.
	 */
	protected abstract String buildTemplate();
	
	/**
	 * Retrieve the template of the condition.
	 * @return The <code>String</code> template.
	 */
	public final String getTemplate() {
		if (this.template == null) {
			this.template = this.buildTemplate();
		}
		return this.template;
	}
}
