package hemera.utility.sql.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hemera.utility.sql.condition.AbstractCondition;
import hemera.utility.sql.condition.BooleanSingleCondition;
import hemera.utility.sql.condition.DistanceCondition;
import hemera.utility.sql.condition.IntRangeCondition;
import hemera.utility.sql.condition.IntSingleCondition;
import hemera.utility.sql.condition.JointCondition;
import hemera.utility.sql.condition.LongSingleCondition;
import hemera.utility.sql.condition.PasswordCondition;
import hemera.utility.sql.condition.StringSingleCondition;
import hemera.utility.sql.enumn.ERelation;
import hemera.utility.sql.enumn.ESign;

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
	 * The <code>List</code> of <code>AbstractCondition</code>.
	 */
	private final List<AbstractCondition> conditions;
	/**
	 * The <code>List</code> of <code>ERelation</code>
	 * in correspondence to the list of conditions.
	 */
	private final List<ERelation> relations;
	
	/**
	 * Constructor of <code>ConditionalQuery</code>.
	 */
	protected ConditionalQuery() {
		this.conditions = new ArrayList<AbstractCondition>();
		this.relations = new ArrayList<ERelation>();
	}
	
	/**
	 * Add a single test condition to test.
	 * <p>
	 * Given condition only affects the relation
	 * between this condition and the next added one.
	 * The last condition's relation is ignored.
	 * <p>
	 * This method does not provide any duplication
	 * check. It is the caller's responsibility to
	 * ensure no duplicate conditions are added.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>int</code> value for
	 * the column to test with.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param relation The <code>ERelation</code> of
	 * this condition to other conditions in the same
	 * query.
	 */
	public void addCondition(final String table, final String column, final int value, final ESign sign, final ERelation relation) {
		this.conditions.add(new IntSingleCondition(table, column, value, sign));
		this.relations.add(relation);
	}
	
	/**
	 * Add a single test condition to test.
	 * <p>
	 * Given condition only affects the relation
	 * between this condition and the next added one.
	 * The last condition's relation is ignored.
	 * <p>
	 * This method does not provide any duplication
	 * check. It is the caller's responsibility to
	 * ensure no duplicate conditions are added.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>long</code> value for
	 * the column to test with.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param relation The <code>ERelation</code> of
	 * this condition to other conditions in the same
	 * query.
	 */
	public void addCondition(final String table, final String column, final long value, final ESign sign, final ERelation relation) {
		this.conditions.add(new LongSingleCondition(table, column, value, sign));
		this.relations.add(relation);
	}

	/**
	 * Add a single test condition to test.
	 * <p>
	 * Given condition only affects the relation
	 * between this condition and the next added one.
	 * The last condition's relation is ignored.
	 * <p>
	 * This method does not provide any duplication
	 * check. It is the caller's responsibility to
	 * ensure no duplicate conditions are added.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>boolean</code> value for
	 * the column to test with.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param relation The <code>ERelation</code> of
	 * this condition to other conditions in the same
	 * query.
	 */
	public void addCondition(final String table, final String column, final boolean value, final ESign sign, final ERelation relation) {
		this.conditions.add(new BooleanSingleCondition(table, column, value, sign));
		this.relations.add(relation);
	}
	
	/**
	 * Add a single test condition to test.
	 * <p>
	 * Given condition only affects the relation
	 * between this condition and the next added one.
	 * The last condition's relation is ignored.
	 * <p>
	 * This method does not provide any duplication
	 * check. It is the caller's responsibility to
	 * ensure no duplicate conditions are added.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>String</code> value for
	 * the column to test with.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param relation The <code>ERelation</code> of
	 * this condition to other conditions in the same
	 * query.
	 */
	public void addCondition(final String table, final String column, final String value, final ESign sign, final ERelation relation) {
		this.conditions.add(new StringSingleCondition(table, column, value, sign));
		this.relations.add(relation);
	}
	
	/**
	 * Add a single password condition to test.
	 * <p>
	 * Given condition only affects the relation
	 * between this condition and the next added one.
	 * The last condition's relation is ignored.
	 * <p>
	 * This method does not provide any duplication
	 * check. It is the caller's responsibility to
	 * ensure no duplicate conditions are added.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param value The <code>String</code> value for
	 * the column to test with.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param relation The <code>ERelation</code> of
	 * this condition to other conditions in the same
	 * query.
	 */
	public void addPasswordCondition(final String table, final String column, final String value, final ESign sign, final ERelation relation) {
		this.conditions.add(new PasswordCondition(table, column, value, sign));
		this.relations.add(relation);
	}
	
	/**
	 * Add a range condition to test a value range.
	 * <p>
	 * Given condition only affects the relation
	 * between this condition and the next added one.
	 * The last condition's relation is ignored.
	 * <p>
	 * This method does not provide any duplication
	 * check. It is the caller's responsibility to
	 * ensure no duplicate conditions are added.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> column to
	 * check.
	 * @param lower The <code>int</code> range lower
	 * value.
	 * @param higher The <code>int</code> range higher
	 * value.
	 * @param relation The <code>ERelation</code> of
	 * this condition to other conditions in the same
	 * query.
	 */
	public void addCondition(final String table, final String column, final int lower, final int higher, final ERelation relation) {
		this.conditions.add(new IntRangeCondition(table, column, lower, higher));
		this.relations.add(relation);
	}
	
	/**
	 * Add a joint condition that tests against a pair
	 * of columns of two tables. This condition is
	 * only valid if both tables are added to the query
	 * as a joint operation.
	 * <p>
	 * Given condition only affects the relation
	 * between this condition and the next added one.
	 * The last condition's relation is ignored.
	 * <p>
	 * This method does not provide any duplication
	 * check. It is the caller's responsibility to
	 * ensure no duplicate conditions are added.
	 * @param table1 The <code>String</code> name of
	 * the first table.
	 * @param column1 The <code>String</code> name of
	 * the first column.
	 * @param table2 The <code>String</code> name of
	 * the second table.
	 * @param column2 The <code>String</code> name of
	 * the second column.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param relation The <code>ERelation</code> of
	 * this condition to other conditions in the same
	 * query.
	 */
	public void addCondition(final String table1, final String column1, final String table2, final String column2,
			final ESign sign, final ERelation relation) {
		this.conditions.add(new JointCondition(table1, column1, table2, column2, sign));
		this.relations.add(relation);
	}
	
	/**
	 * Add a distance condition that tests the table
	 * entries coordinates distance to the specified
	 * coordinates against the given distance value.
	 * <p>
	 * Given condition only affects the relation
	 * between this condition and the next added one.
	 * The last condition's relation is ignored.
	 * <p>
	 * This method does not provide any duplication
	 * check. It is the caller's responsibility to
	 * ensure no duplicate conditions are added.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param latitudeCol The <code>String</code>
	 * table's latitude column in degrees.
	 * @param longitudeCol The <code>String</code>
	 * table's longitude column in degrees.
	 * @param latitude The <code>double</code> given
	 * latitude value in degrees.
	 * @param longitude The <code>double</code> given
	 * longitude value in degrees.
	 * @param distance The <code>double</code> given
	 * distance to check in meters.
	 * @param sign The <code>ESign</code> value.
	 * @param relation The <code>ERelation</code> of
	 * this condition to other conditions in the same
	 * query.
	 */
	public void addCondition(final String table, final String latitudeCol, final String longitudeCol,
			final double latitude, final double longitude, final double distance, final ESign sign, final ERelation relation) {
		this.conditions.add(new DistanceCondition(table, latitudeCol, longitudeCol, latitude, longitude, distance, sign));
		this.relations.add(relation);
	}
	
	/**
	 * Build the conditional check part of a query
	 * template.
	 * @return The <code>String</code> template. Or
	 * <code>where true</code> if there are no conditions.
	 */
	protected String buildConditionsTemplate() {
		final StringBuilder builder = new StringBuilder();
		builder.append("where ");
		final int size = this.conditions.size();
		if (size <= 0) {
			builder.append("true");
		} else {
			final int last = size - 1;
			for (int i = 0; i < size; i++) {
				final AbstractCondition condition = this.conditions.get(i);
				final ERelation relation = this.relations.get(i);
				builder.append(condition.getTemplate());
				if (i != last) {
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
	protected int insertConditionValues(final PreparedStatement statement, final int start) throws SQLException {
		final int size = this.conditions.size();
		int count = 0;
		for (int i = 0; i < size; i++) {
			final AbstractCondition condition = this.conditions.get(i);
			final int inserted = condition.insertValues(statement, start+count);
			count += inserted;
		}
		return count;
	}
	
	/**
	 * Clear all previously added conditions.
	 */
	public void clearConditions() {
		this.conditions.clear();
		this.relations.clear();
	}
}
