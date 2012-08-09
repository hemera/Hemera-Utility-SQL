package hemera.utility.sql.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import hemera.utility.sql.enumn.ESign;

/**
 * <code>DistanceCondition</code> defines a special
 * condition that checks the distance between the
 * database entry's latitude and longitude to the
 * specified latitude and longitude coordinates with
 * a specified value.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
final class DistanceCondition extends AbstractCondition {
	/**
	 * The <code>String</code> table to check.
	 */
	private final String table;
	/**
	 * The <code>String</code> table's latitude column
	 * in degrees.
	 */
	private final String latitudeCol;
	/**
	 * The <code>String</code> table's longitude column
	 * in degrees.
	 */
	private final String longitudeCol;
	/**
	 * The <code>double</code> given latitude value
	 * in degrees.
	 */
	private final double latitude;
	/**
	 * The <code>double</code> given longitude value
	 * in degrees.
	 */
	private final double longitude;
	/**
	 * The <code>double</code> given distance to check
	 * in meters.
	 */
	private final double distance;
	/**
	 * The <code>ESign</code> of the condition.
	 */
	private final ESign sign;
	
	/**
	 * Constructor of <code>DistanceCondition</code>.
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
	 * distance to check.
	 */
	DistanceCondition(final String table, final String latitudeCol, final String longitudeCol,
			final ESign sign, final double latitude, final double longitude, final double distance) {
		this.table = table;
		this.latitudeCol = latitudeCol;
		this.longitudeCol = longitudeCol;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
		this.sign = sign;
	}

	@Override
	protected String buildTemplate() {
		final StringBuilder builder = new StringBuilder();
		builder.append("acos(sin(radians(`").append(this.table).append("`.`").append(this.latitudeCol).append("`))");
		builder.append("*sin(radians(?))");
		builder.append("+cos(radians(`").append(this.table).append("`.`").append(this.latitudeCol).append("`))");
		builder.append("*cos(radians(?))");
		builder.append("*cos(radians(?)-radians(`").append(this.table).append("`.`").append(this.longitudeCol).append("`").append(")))");
		builder.append("*6371000");
		builder.append(" ").append(this.sign.value).append(" ?");
		return builder.toString();
	}

	@Override
	public int insertValues(final PreparedStatement statement, final int start) throws SQLException {
		statement.setDouble(start, this.latitude);
		statement.setDouble(start+1, this.latitude);
		statement.setDouble(start+2, this.longitude);
		statement.setDouble(start+3, this.distance);
		return 4;
	}
}
