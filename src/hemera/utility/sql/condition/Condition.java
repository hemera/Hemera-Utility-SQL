package hemera.utility.sql.condition;

import java.util.ArrayList;
import java.util.List;

import hemera.utility.sql.enumn.ERelation;
import hemera.utility.sql.enumn.ESign;

/**
 * <code>Condition</code> defines a wrapper of a single
 * condition that can be added to a query.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class Condition {
	/**
	 * The <code>AbstractCondition</code> instance.
	 */
	AbstractCondition value;
	/**
	 * The <code>ConditionGroup</code> this condition
	 * belongs to.
	 */
	public final ConditionGroup group;
	/**
	 * The <code>List</code> of <code>String</code> of
	 * tables this condition is for.
	 * <p>
	 * Must use a list to guarantee ordering so the
	 * query template is the same for different values
	 * allowing the statement to be reused.
	 */
	private final List<String> tables;
	
	/**
	 * Constructor of <code>Condition</code>.
	 */
	public Condition() {
		this.group = new ConditionGroup();
		this.group.conditions.add(this);
		this.tables = new ArrayList<String>();
	}
	
	/**
	 * Constructor of <code>Condition</code>.
	 * @param group The <code>ConditionGroup</code>
	 * to belong to.
	 */
	private Condition(final ConditionGroup group) {
		this.group = group;
		this.tables = new ArrayList<String>();
	}
	
	/**
	 * Creates a new condition that is associated with
	 * this condition using <code>OR</code> relation.
	 * @return The new <code>Condition</code>.
	 */
	public Condition or() {
		final Condition condition = new Condition(this.group);
		this.group.conditions.add(condition);
		this.group.relations.add(ERelation.Or);
		return condition;
	}

	/**
	 * Creates a new condition that is associated with
	 * this condition using <code>AND</code> relation.
	 * @return The new <code>Condition</code>.
	 */
	public Condition and() {
		final Condition condition = new Condition(this.group);
		this.group.conditions.add(condition);
		this.group.relations.add(ERelation.And);
		return condition;
	}

	/**
	 * Set this condition to compare entries with the
	 * given integer range.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> column to
	 * check.
	 * @param lower The <code>int</code> range lower
	 * value.
	 * @param higher The <code>int</code> range higher
	 * value.
	 * @return This <code>Condition</code> instance.
	 */
	public Condition set(final String table, final String column, final int lower, final int higher) {
		this.value = new IntRangeCondition(table, column, lower, higher);
		if (!this.tables.contains(table)) this.tables.add(table);
		return this;
	}

	/**
	 * Set this condition to compare entries with the
	 * given integer value.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>int</code> value for
	 * the column to test with.
	 * @return This <code>Condition</code> instance.
	 */
	public Condition set(final String table, final String column, final ESign sign, final int value) {
		this.value = new IntCondition(table, column, sign, value);
		if (!this.tables.contains(table)) this.tables.add(table);
		return this;
	}
	
	/**
	 * Set this condition to compare entries with the
	 * given long value.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>long</code> value for
	 * the column to test with.
	 * @return This <code>Condition</code> instance.
	 */
	public Condition set(final String table, final String column, final ESign sign, final long value) {
		this.value = new LongCondition(table, column, sign, value);
		if (!this.tables.contains(table)) this.tables.add(table);
		return this;
	}

	/**
	 * Set this condition to compare entries with the
	 * given string value.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>String</code> value for
	 * the column to test with.
	 * @return This <code>Condition</code> instance.
	 */
	public Condition set(final String table, final String column, final ESign sign, final String value) {
		this.value = new StringCondition(table, column, sign, value);
		if (!this.tables.contains(table)) this.tables.add(table);
		return this;
	}
	
	/**
	 * Set this condition to compare entries with the
	 * given double value.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>double</code> value for
	 * the column to test with.
	 * @return This <code>Condition</code> instance.
	 */
	public Condition set(final String table, final String column, final ESign sign, final double value) {
		this.value = new DoubleCondition(table, column, sign, value);
		if (!this.tables.contains(table)) this.tables.add(table);
		return this;
	}

	/**
	 * Set this condition to compare entries with the
	 * given boolean value.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>boolean</code> value for
	 * the column to test with.
	 * @return This <code>Condition</code> instance.
	 */
	public Condition set(final String table, final String column, final ESign sign, final boolean value) {
		this.value = new BooleanCondition(table, column, sign, value);
		if (!this.tables.contains(table)) this.tables.add(table);
		return this;
	}
	
	/**
	 * Set this condition to compare entries of the
	 * first table column with the second table column.
	 * @param table1 The <code>String</code> name of
	 * the first table.
	 * @param column1 The <code>String</code> name of
	 * the first column.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param table2 The <code>String</code> name of
	 * the second table.
	 * @param column2 The <code>String</code> name of
	 * the second column.
	 * @return This <code>Condition</code> instance.
	 */
	public Condition set(final String table1, final String column1, final ESign sign, final String table2, final String column2) {
		this.value = new JointCondition(table1, column1, sign, table2, column2);
		if (!this.tables.contains(table1)) this.tables.add(table1);
		if (!this.tables.contains(table2)) this.tables.add(table2);
		return this;
	}

	/**
	 * Set this condition to compare entries with the
	 * given string value encrypted using given key.
	 * @param key The <code>String</code> encryption
	 * key.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> name of
	 * the column to test on.
	 * @param sign The <code>ESign</code> of this
	 * condition.
	 * @param value The <code>String</code> value for
	 * the column to test with.
	 * @return This <code>Condition</code> instance.
	 */
	public Condition set(final String key, final String table, final String column, final ESign sign, final String value) {
		this.value = new EncryptCondition(key, table, column, sign, value);
		if (!this.tables.contains(table)) this.tables.add(table);
		return this;
	}
	
	/**
	 * Set this condition to compare entries latitude
	 * and longitude distance to the given coordinates
	 * with given distance.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param latitudeCol The <code>String</code>
	 * table's latitude column in degrees.
	 * @param longitudeCol The <code>String</code>
	 * table's longitude column in degrees.
	 * @param sign The <code>ESign</code> value.
	 * @param latitude The <code>double</code> given
	 * latitude value in degrees.
	 * @param longitude The <code>double</code> given
	 * longitude value in degrees.
	 * @param distance The <code>double</code> given
	 * distance to check in meters.
	 * @return This <code>Condition</code> instance.
	 */
	public Condition set(final String table, final String latitudeCol, final String longitudeCol,
			final ESign sign, final double latitude, final double longitude, final double distance) {
		this.value = new DistanceCondition(table, latitudeCol, longitudeCol, sign, latitude, longitude, distance);
		if (!this.tables.contains(table)) this.tables.add(table);
		return this;
	}
	
	/**
	 * Retrieve the tables this condition is for.
	 * @return The <code>List</code> of all the tables
	 * <code>String</code> this condition is for.
	 */
	public List<String> getTables() {
		return this.tables;
	}
}
