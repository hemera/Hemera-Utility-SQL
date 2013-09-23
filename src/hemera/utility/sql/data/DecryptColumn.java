package hemera.utility.sql.data;

/**
 * <code>DecryptColumn</code> defines the immutable
 * data structure that represents a table column to
 * be decrypted.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.2
 */
public class DecryptColumn extends TableColumn {
	/**
	 * The <code>String</code> decryption key.
	 */
	public final String key;

	/**
	 * Constructor of <code>DecryptColumn</code>.
	 * @param table The <code>String</code> name of
	 * the table.
	 * @param column The <code>String</code> name of
	 * the column.
	 * @param key The <code>String</code> decryption
	 * key.
	 */
	public DecryptColumn(final String table, final String column, final String key) {
		super(table, column);
		this.key = key;
	}
}
