package edu.asu.spring.quadriga.db;

import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
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
	public abstract List<IQuadrigaRole> UserRoles(String roles);

	public abstract List<IUser> getAllActiveUsers();
	public abstract List<IUser> getAllInActiveUsers();
	
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

	public abstract int deactivateUser(String sUserId, String sDeactiveRoleDBId);

	public abstract int updateUserRoles(String sUserId, String sRoles);

	public abstract List<IUser> getUserRequests();

	public abstract int approveUserRequest(String sUserId, String sRoles);

	public abstract int denyUserRequest(String sUserId, String sAdminId);
	
	public abstract List<IProject> getProjectOfUser(String sUserId);
	
	public abstract void setUserDetails(String name,String username,String email,String roles);

}
