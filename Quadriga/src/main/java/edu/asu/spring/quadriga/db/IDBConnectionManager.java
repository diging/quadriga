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

	/**
	 * Queries the database and builds a list of active user objects
	 * 
	 * @return List containing user objects of all active users
	 */
	public abstract List<IUser> getAllActiveUsers(String sInactiveRoleId);
	
	/**
	 * Queries the database and builds a list of inactive user objects
	 * 
	 * @return List containing user objects of all inactive users
	 */
	public abstract List<IUser> getAllInActiveUsers(String sInactiveRoleId);
	
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

	/**
	 * Deactivate a user in Quadriga.
	 * 
	 * @param 	sUserId					The userid of the user whose account has to be deactivated
	 * @param 	sDeactiveRoleDBId		The roleid corresponding to the inactive role fetched from the application context file
	 * @param 	sAdminId 				The userid of the admin who is changing the user setting
	 * 
	 * @return	Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 * 
	 */
	public abstract int deactivateUser(String sUserId, String sDeactiveRoleDBId,String sAdminId);

	/**
	 * Overwrite the existing userroles with the new user roles.
	 * 
	 * @param sUserId The userid of the user whose roles are to be changed.
	 * @param sRoles The new roles of the user. Must be fetched from the applicaton context file.
	 * @param sAdminId The userid of the admin who is changing the user setting
	 * 
	 * @return Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 */
	public abstract int updateUserRoles(String sUserId, String sRoles,String sAdminId);

	/**
	 * Returns all open user requests to quadriga.
	 * 
	 * @return Returns the list of user objects whose request are to be approved/denied.
	 * 
	 */	
	public abstract List<IUser> getUserRequests();

	/**
	 * Approve the user request to access Quadriga and also assign new roles set by the admin.
	 * 
	 * @param sUserId The userid of the user whose access has been approved.
	 * @param sRoles The roles set by the admin. Must correspond to the roles found in the application context file
	 * @param sAdminId The userid of the admin who is changing the user setting
	 * 
	 * @return Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 * 
	 */
	public abstract int approveUserRequest(String sUserId, String sRoles, String sAdminId);

	/**
	 * A user has been denied the access to Quadriga.
	 * 
	 * @param sUserId		The userid of the user whose request is rejected
	 * @param sAdminId 		The admin-userid who rejected the request
	 * 
	 * Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 */
	public abstract int denyUserRequest(String sUserId, String sAdminId);
	
	public abstract List<IProject> getProjectOfUser(String sUserId);
	
	public abstract void setUserDetails(String name,String username,String email,String roles);

}
