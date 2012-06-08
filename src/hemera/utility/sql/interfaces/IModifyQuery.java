package hemera.utility.sql.interfaces;

import java.sql.SQLException;

/**
 * <code>IModifyQuery</code> defines the interface of
 * a type of database queries that modifies data in
 * the database. Such as insert, create or drop query.
 * The returned result is the number of rows affected
 * by the update.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public interface IModifyQuery extends IQuery {

	/**
	 * Execute the update query.
	 * @return The <code>Integer</code> number of rows
	 * that are affected by the result of execution.
	 * @throws SQLException If query execution failed.
	 */
	@Override
	public Integer execute() throws SQLException;
}
