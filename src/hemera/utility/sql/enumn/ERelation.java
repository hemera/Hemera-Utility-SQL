package hemera.utility.sql.enumn;

/**
 * <code>Relation</code> defines the enumeration of
 * all database query condition's relationship with
 * other conditions in the same query.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum ERelation {
	/**
	 * The <code>and</code> condition relation.
	 */
	And("and"),
	/**
	 * The <code>or</code> condition relation.
	 */
	Or("or");
	
	/**
	 * The <code>String</code> value of the relation
	 * that can be directly used in a query statement.
	 */
	public final String value;
	
	/**
	 * Constructor of <code>ERelation</code>.
	 * @param value The <code>String</code> value of
	 * the relation.
	 */
	private ERelation(final String value) {
		this.value = value;
	}
}
