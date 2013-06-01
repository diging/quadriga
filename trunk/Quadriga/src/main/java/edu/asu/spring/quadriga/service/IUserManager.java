package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;

/**
 * Interface class that places restraints on the UserManager class to implement
 * the required behaviors.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
public interface IUserManager {

	/**
	 * Method which creates a user object for the given userId.
	 * 
	 * @param sUserId			The unique userid of the user
	 * @return					User object created based on the userId
	 */
	public abstract IUser getUserDetails(String sUserId);

	
	/**
	 *  Deactivate a user account so that the particular user cannot access quadriga anymore.
	 *  
	 *  @param sUserId	The userid of the user whose account is to be deactivated.
	 *  @param sAdminId  The userid of the admin who is changing the user setting.
	 *  @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	public abstract int deactivateUser(String sUserId,String sAdminId);
	

	/**
	 * List all active users in the Quadriga database
	 * 
	 * @return List of all active user objects
	 * 
	 */
	public abstract List<IUser> getAllActiveUsers();

	/**
	 * List all inactive users in the quadriga database
	 * 
	 * @return List of all inactive user objects
	 */
	public abstract List<IUser> getAllInActiveUsers();

	/**
	 * Activate an already existing user so that the user can access quadriga.
	 * 	 
	 * @param sUserId	The userid of the user whose account has been activated. 
	 * @param sAdminId  The userid of the admin who is changing the user setting.
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	public abstract int activateUser(String sUserId,String sAdminId);

	/**
	 * List all the open user requests available in the quadriga database
	 * 
	 * @return List all open user requests
	 */
	public List<IUser> getUserRequests();

	/**
	 * Approve a user request to access quadriga.
	 * 
	 * @param sUserId	The userid of the user whose account has been approved.
	 * @param sRoles 	The set of roles that are assigned to the user.
	 * @param sAdminId  The userid of the admin who is changing the user setting
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	public abstract int approveUserRequest(String sUserId, String sRoles, String sAdminId);

	/**
	 * Deny a user request to access the quadriga.
	 * 
	 * @param sUserId	The userid of the user whose account has been denied.
	 * @param sAdminId  The userid of the admin who is changing the user setting.
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	public abstract int denyUserRequest(String sUserId, String sAdminId);
	
	/**
	 * Add a new user request to access quadriga.
	 * 
	 * @param userId The user id of the user who needs access to quadriga 
	 * @return Integer value that specifies the status of the operation. 1 - Successfully place the request. 0 - An open request is already placed for the userid.
	 */
	public abstract int addAccountRequest(String userId);
}
