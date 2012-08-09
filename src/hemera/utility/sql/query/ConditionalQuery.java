package hemera.utility.sql.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hemera.utility.sql.condition.Condition;
import hemera.utility.sql.condition.ConditionGroup;
import hemera.utility.sql.enumn.ERelation;

/**
 * <code>ConditionalQuery</code> defines abstraction
 * of a database query that has one or more conditions
 * to be checked.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public abstract class ConditionalQuery extends AbstractQuery {
	/**
	 * The <code>List</code> of <code>ConditionGroup</code>.
	 */
	protected final List<ConditionGroup> conditionGroups;
	/**
	 * The <code>List</code> of <code>ERelation</code>
	 * that associates the corresponding condition groups.
	 */
	private final List<ERelation> relations;
	
	/**
	 * Constructor of <code>ConditionalQuery</code>.
	 * @param key The <code>String</code> key used to
	 * identify the data source.
	 */
	protected ConditionalQuery(final String key) {
		super(key);
		this.conditionGroups = new ArrayList<ConditionGroup>();
		this.relations = new ArrayList<ERelation>();
	}
	
	/**
	 * Add a single condition to this query.
	 * @param condition The <code>Condition</code>.
	 */
	public void addCondition(final Condition condition) {
		if (this.conditionGroups.contains(condition.group)) return;
		this.conditionGroups.add(condition.group);
	}
	
	/**
	 * Add an array of conditions associated with the
	 * given array of relations to this query.
	 * <p>
	 * If any of the conditions is a nested condition,
	 * the nested children conditions are grouped together
	 * as a single condition during evaluation.
	 * @param conditions The array of <code>Condition</code>.
	 * @param relations The array of <code>ERelation</code>.
	 */
	public void addConditions(final Condition[] conditions, final ERelation[] relations) {
		if (conditions.length != relations.length+1) {
			throw new IllegalArgumentException("There must be n-1 relations with n conditions.");
		}
		for (int i = 0; i < conditions.length; i++) {
			if (!this.conditionGroups.contains(conditions[i].group)) {
				this.conditionGroups.add(conditions[i].group);
			}
		}
		for (int i = 0; i < relations.length; i++) {
			this.relations.add(relations[i]);
		}
	}
	
	/**
	 * Build the conditional check part of a query
	 * template.
	 * @return The <code>String</code> template. Or
	 * <code>where true</code> if there are no conditions.
	 */
	protected final String buildConditionsTemplate() {
		final StringBuilder builder = new StringBuilder();
		builder.append("where ");
		final int size = this.conditionGroups.size();
		if (size <= 0) {
			builder.append("true");
		} else {
			final int last = size - 1;
			for (int i = 0; i < size; i++) {
				final ConditionGroup conditionGroup = this.conditionGroups.get(i);
				final ERelation relation = (i==last) ? null : this.relations.get(i);
				builder.append("(").append(conditionGroup.getTemplate()).append(")");
				if (relation != null) {
					builder.append(" ").append(relation.value).append(" ");
				}
			}
		}
		return builder.toString();
	}
	
	/**
	 * Insert the conditional values into the given
	 * statement starting at given start index.
	 * @param statement The <code>PreparedStatement</code>
	 * to insert the values in.
	 * @param start The <code>int</code> starting
	 * index to insert values at. The index starts at
	 * <code>1</code>.
	 * @return The <code>int</code> number of values
	 * inserted.
	 * @throws SQLException If insertion failed.
	 */
	protected final int insertConditionValues(final PreparedStatement statement, final int start) throws SQLException {
		final int size = this.conditionGroups.size();
		int count = 0;
		for (int i = 0; i < size; i++) {
			final ConditionGroup conditionGroup = this.conditionGroups.get(i);
			final int inserted = conditionGroup.insertValues(statement, start+count);
			count += inserted;
		}
		return count;
	}
	
	/**
	 * Clear all previously added conditions.
	 */
	public final void clearConditions() {
		this.conditionGroups.clear();
		this.relations.clear();
	}
}
