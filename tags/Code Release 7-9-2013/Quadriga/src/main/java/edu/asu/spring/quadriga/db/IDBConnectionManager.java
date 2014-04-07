package edu.asu.spring.quadriga.db;

import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface for the DBConnectionManager Class.
 * 
 * @author Kiran Kumar Batna
 * @author Ram Kumar Kumaresan
 *
 */
public interface IDBConnectionManager 
{
	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;
	
	public abstract List<IQuadrigaRole> listQuadrigaUserRoles(String roles) throws QuadrigaStorageException;

	/**
	 * Queries the database and builds a list of active user objects
	 * 
	 * @return List containing user objects of all active users
	 */
	public abstract List<IUser> getAllActiveUsers(String sInactiveRoleId) throws QuadrigaStorageException;

	/**
	 * Queries the database and builds a list of inactive user objects
	 * 
	 * @return List containing user objects of all inactive users
	 */
	public abstract List<IUser> getAllInActiveUsers(String sInactiveRoleId) throws QuadrigaStorageException;

	/**
	 * Creates a user object for the given userid.
	 * @param userid	The unique userid of the user based on which a user object will be created
	 * @return			User object for the corresponding userid
	 */
	public abstract IUser getUserDetails(String userid) throws QuadrigaStorageException;

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
	public abstract int deactivateUser(String sUserId, String sDeactiveRoleDBId,String sAdminId) throws QuadrigaStorageException;

	/**
	 * Overwrite the existing userroles with the new user roles.
	 * 
	 * @param sUserId The userid of the user whose roles are to be changed.
	 * @param sRoles The new roles of the user. Must be fetched from the applicaton context file.
	 * @param sAdminId The userid of the admin who is changing the user setting
	 * 
	 * @return Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 */
	public abstract int updateUserRoles(String sUserId, String sRoles,String sAdminId) throws QuadrigaStorageException;

	/**
	 * Returns all open user requests to quadriga.
	 * 
	 * @return Returns the list of user objects whose request are to be approved/denied.
	 * 
	 */	
	public abstract List<IUser> getUserRequests() throws QuadrigaStorageException;

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
	public abstract int approveUserRequest(String sUserId, String sRoles, String sAdminId) throws QuadrigaStorageException;

	/**
	 * A user has been denied the access to Quadriga.
	 * 
	 * @param sUserId		The userid of the user whose request is rejected
	 * @param sAdminId 		The admin-userid who rejected the request
	 * 
	 * Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 */
	public abstract int denyUserRequest(String sUserId, String sAdminId) throws QuadrigaStorageException;

	/**
	 * Add a new account request to the quadriga.
	 * 
	 * @param sUserId The user id of the user who needs access to quadriga 
	 * @return Integer value that specifies the status of the operation. 1 - Successfully place the request.
	 * 
	 * @author Ram Kumar Kumaresan 
	 */
	public abstract int addAccountRequest(String sUserId) throws QuadrigaStorageException;

	/**
	 * Method used to execute a given INSERT, UPDATE and DELETE statement in the database.
	 * 
	 * @return Success - 1
	 * @author Ram Kumar Kumaresan
	 */
	public abstract int setupTestEnvironment(String sQuery) throws QuadrigaStorageException;

}