package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface provides the methods available to perform User realted 
 * functionalities in the database. 
 * 
 * @author Kiran Kumar Batna
 * @author Ram Kumar Kumaresan
 *
 */
public interface IDBConnectionManager 
{
	
	/**
	 * Add a new account request to the quadriga.
	 * 
	 * @param sUserId The user id of the user who needs access to quadriga 
	 * @return Integer value that specifies the status of the operation. 1 - Successfully place the request. 0 - Otherwise.
	 * 
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	public abstract int addAccountRequest(String sUserId) throws QuadrigaStorageException;

	/**
	 * Returns all open user requests in quadriga.
	 * 
	 * @return Returns the list of user objects whose request are to be approved/denied.
	 * 
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles. 
	 */	
	public abstract List<IUser> getUserRequests() throws QuadrigaStorageException;

	/**
	 *  Retrieves the user details for the given userid
	 *  
	 *  @param       : userid (the userid for which details are to be retrieved).
	 *  
	 *  @return      : null - if the user is not present in the quadriga DB
	 *                 IUser - User object containing the user details.
	 * @throws QuadrigaStorageException 
	 *                 
	 *  @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	public abstract IUser getUserDetails(String userid) throws QuadrigaStorageException;

	/**
	 * Retrieve a list of user objects who have a certain role
	 * 
	 * @param userRoleId	The roleid of the Quadriga Role for which the list of users are to be fetched.
	 * @return				The list of users who are assigned the roleid.
	 * @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 * @author Ram Kumar Kumaresan
	 */
	public abstract List<IUser> getUsers(String userRoleId) throws QuadrigaStorageException;

	/**
	 * Retrieve a list of user objects who don't have a certain role
	 * 
	 * @param userRoleId					The roleid of the Quadriga Role based on which the user list is to be filtered.
	 * @return								The list of users who don't have the given role
	 * @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 * @author Ram Kumar Kumaresan
	 */
	public abstract List<IUser> getUsersNotInRole(String userRoleId) throws QuadrigaStorageException;

	/**
	 * Delete a user from the quadriga database. The user must not own a project/workspace and must not be a collaborator on a project/workspace.
	 * Also the user account in Quadriga must already be deactivated
	 * 
	 * @param deleteUser					The username of the user to be deleted 
	 * @return								1 if the user was deleted successfully
	 * @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 * @author Ram Kumar Kumaresan
	 */
	public abstract int deleteUser(String deleteUser, String deactivatedRole) throws QuadrigaStorageException;


	/**
	 * Deactivate a user in Quadriga.
	 * 
	 * @param 	sUserId					The userid of the user whose account has to be deactivated
	 * @param 	sDeactiveRoleDBId		The roleid corresponding to the inactive role fetched from the application context file
	 * @param 	sAdminId 				The userid of the admin who is changing the user setting
	 * 
	 * @return	Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 * @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 * @author Ram Kumar Kumaresan
	 * 
	 */
	public abstract int deactivateUser(String sUserId, String sDeactiveRoleDBId, String sAdminId) throws QuadrigaStorageException;

	/**
	 * Overwrite the existing userroles with the new user roles.
	 * 
	 * @param sUserId The userid of the user whose roles are to be changed.
	 * @param sRoles The new roles of the user. Must be fetched from the applicaton context file.
	 * @param sAdminId The userid of the admin who is changing the user setting
	 * 
	 * @return Returns the status of the operation. 1 - Operation Success. 0 - Error occurred.
	 * @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 * @author Ram Kumar Kumaresan
	 */
	public abstract int updateUserRoles(String sUserId, String sRoles, String sAdminId)	throws QuadrigaStorageException;

	/**
	 * Approve the user request to access Quadriga and also assign new roles set by the admin.
	 * 
	 * @param sUserId The userid of the user whose access has been approved.
	 * @param sRoles The roles set by the admin. Must correspond to the roles found in the application context file
	 * @param sAdminId The userid of the admin who is changing the user setting
	 * 
	 * @return Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 * @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 * @author Ram Kumar Kumaresan
	 */
	public abstract int approveUserRequest(String sUserId, String sRoles, String sAdminId) throws QuadrigaStorageException;

	/**
	 * A user has been denied the access to Quadriga.
	 * 
	 * @param sUserId		The userid of the user whose request is rejected
	 * @param sAdminId 		The admin-userid who rejected the request
	 * 
	 * @return Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 * @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 * @author Ram Kumar Kumaresan
	 */
	public abstract int denyUserRequest(String sUserId, String sAdminId) throws QuadrigaStorageException;

	/**
	 * Method used to execute a given INSERT, UPDATE and DELETE statement in the database.
	 * 
	 * @return Success - 1
	 * @throws QuadrigaStorageException Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 * @author Ram Kumar Kumaresan
	 */
	public abstract int setupTestEnvironment(String sQuery) throws QuadrigaStorageException;

	public abstract QuadrigaUserDTO getUserDTO(String userName)
			throws QuadrigaStorageException;


}
