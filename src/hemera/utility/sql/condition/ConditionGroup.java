package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hemera.utility.sql.enumn.ERelation;

/**
 * <code>ConditionGroup</code> defines a group of
 * conditions that are treated as a single condition
 * during evaluation. The conditions within the same
 * group are linked together via set relationships.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConditionGroup {
	/**
	 * The <code>List</code> of <code>Condition</code>
	 * this group includes.
	 */
	final List<Condition> conditions;
	/**
	 * The <code>List</code> of <code>ERelation</code>
	 * corresponding to the conditions.
	 */
	final List<ERelation> relations;
	/**
	 * The <code>String</code> template.
	 */
	private String template;

	/**
	 * Constructor of <code>ConditionGroup</code>.
	 */
	ConditionGroup() {
		this.conditions = new ArrayList<Condition>();
		this.relations = new ArrayList<ERelation>();
	}
	
	/**
	 * Insert the conditional check values of all the
	 * conditions in the group into the given statement
	 * with place-holder positions using the given
	 * starting index.
	 * @param statement The <code>PreparedStatement</code>
	 * to insert the values in.
	 * @param start The <code>int</code> starting
	 * index to insert the values at. The index
	 * starts at <code>1</code>.
	 * @return The <code>int</code> number of values
	 * inserted.
	 * @throws SQLException If insertion failed.
	 */
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		final int size = this.conditions.size();
		int count = 0;
		for (int i = 0; i < size; i++) {
			final Condition condition = this.conditions.get(i);
			final int inserted = condition.value.insertValues(statement, start+count);
			count += inserted;
		}
		return count;
	}
	
	/**
	 * Retrieve the template of all the conditions in
	 * this group.
	 * @return The <code>String</code> template.
	 */
	public String getTemplate() {
		if (this.template == null) {
			if (this.conditions.isEmpty()) {
				this.template = "true";
			} else {
				final StringBuilder builder = new StringBuilder();
				final int size = this.conditions.size();
				final int last = size - 1;
				for (int i = 0; i < size; i++) {
					final Condition condition = this.conditions.get(i);
					final ERelation relation = (i==last) ? null : this.relations.get(i);
					builder.append(condition.value.getTemplate());
					if (relation != null) {
						builder.append(" ").append(relation.value).append(" ");
					}
				}
				this.template = builder.toString();
			}
		}
		return this.template;
	}
}
