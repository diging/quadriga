package edu.asu.spring.quadriga.db;

import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IQuadrigaRoles;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * Interface for the DBConnectionManager Class.
 * 
 * @author Kiran
 * @author Ram Kumar Kumaresan
 *
 */
public interface IDBConnectionManager 
{
	public abstract List<IQuadrigaRoles> UserRoles(String roles);

	/**
	 * Creates a user object for the given userid.
	 * @param userid	The unique userid of the user based on which a user object will be created
	 * @return			User object for the corresponding userid
	 */
	public abstract IUser getUserDetails(String userid);

	/**
	 * Assign the dataSource object to the class state.
	 * @param dataSource	The datasource object must contain the database connection details
	 */
	public abstract void setDataSource(DataSource dataSource);
}
