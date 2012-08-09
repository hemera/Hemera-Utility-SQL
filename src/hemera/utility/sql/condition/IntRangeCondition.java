package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <code>IntRangeCondition</code> defines the condition
 * that compares the database entry against the given
 * integer range.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
final class IntRangeCondition extends AbstractRangeCondition {
	/**
	 * The <code>int</code> range lower value.
	 */
	private final int lower;
	/**
	 * The <code>int</code> range higher value.
	 */
	private final int higher;
	
	/**
	 * Constructor of <code>IntRangeCondition</code>.
	 * @param table The <code>String</code> table to
	 * check.
	 * @param column The <code>String</code> column to
	 * check.
	 * @param lower The <code>int</code> range lower
	 * value.
	 * @param encryptLower <code>true</code> if lower
	 * value should be encrypted. <code>false</code>
	 * otherwise.
	 * @param higher The <code>int</code> range higher
	 * value.
	 * @param encryptHigher <code>true</code> if higher
	 * value should be encrypted. <code>false</code>
	 * otherwise.
	 */
	IntRangeCondition(final String table, final String column, final int lower, final int higher) {
		super(table, column);
		this.lower = lower;
		this.higher = higher;
	}
	
	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setInt(start, this.lower);
		statement.setInt(start+1, this.higher);
		return 2;
	}
}
