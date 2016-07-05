package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaNotificationException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.UserOwnsOrCollaboratesDeletionException;
import edu.asu.spring.quadriga.exceptions.UsernameExistsException;
import edu.asu.spring.quadriga.web.manageusers.beans.AccountRequest;

/**
 * Interface class that places restraints on the UserManager class to implement
 * the required behaviors.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
public interface IUserManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;
	
	/**
	 * Method which creates a user object for the given userId.
	 * 
	 * @param sUserId			The unique userid of the user
	 * @return					User object created based on the userId
	 */
	public abstract IUser getUser(String sUserId) throws QuadrigaStorageException;

	
	/**
	 *  Deactivate a user account so that the particular user cannot access quadriga anymore.
	 *  
	 *  @param sUserId	The userid of the user whose account is to be deactivated.
	 *  @param sAdminId  The userid of the admin who is changing the user setting.
	 *  @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	public abstract int deactivateUser(String sUserId,String sAdminId) throws QuadrigaStorageException;
	

	/**
	 * List all active users in the Quadriga database
	 * 
	 * @return List of all active user objects
	 * 
	 */
	public abstract List<IUser> getAllActiveUsers() throws QuadrigaStorageException;

	/**
	 * List all inactive users in the quadriga database
	 * 
	 * @return List of all inactive user objects
	 */
	public abstract List<IUser> getAllInActiveUsers() throws QuadrigaStorageException;

	/**
	 * Activate an already existing user so that the user can access quadriga.
	 * 	 
	 * @param sUserId	The userid of the user whose account has been activated. 
	 * @param sAdminId  The userid of the admin who is changing the user setting.
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	public abstract int activateUser(String sUserId,String sAdminId) throws QuadrigaStorageException;

	/**
	 * List all the open user requests available in the quadriga database
	 * 
	 * @return List all open user requests
	 */
	public List<IUser> getUserRequests() throws QuadrigaStorageException;

	/**
	 * Approve a user request to access quadriga.
	 * 
	 * @param sUserId	The userid of the user whose account has been approved.
	 * @param sRoles 	The set of roles that are assigned to the user.
	 * @param sAdminId  The userid of the admin who is changing the user setting
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	public abstract int approveUserRequest(String sUserId, String sRoles, String sAdminId) throws QuadrigaStorageException;

	/**
	 * Deny a user request to access the quadriga.
	 * 
	 * @param sUserId	The userid of the user whose account has been denied.
	 * @param sAdminId  The userid of the admin who is changing the user setting.
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	public abstract int denyUserRequest(String sUserId, String sAdminId) throws QuadrigaStorageException;
	
	/**
	 * Add a new user request to access quadriga.
	 * 
	 * @param userId The user id of the user who needs access to quadriga 
	 * @return Integer value that specifies the status of the operation. 1 - Successfully place the request. 0 - An open request is already placed for the userid.
	 */
	public abstract int addAccountRequest(String userId) throws QuadrigaStorageException;


	public abstract void deleteUser(String sUserId, String sAdminId) throws QuadrigaStorageException, UserOwnsOrCollaboratesDeletionException ;


	/**
	 * Updates the quadriga roles associated with the user
	 * @param userName
	 * @param quadrigaRoles
	 * @param loggedInUser
	 * @throws QuadrigaStorageException
	 */
	public abstract void updateUserQuadrigaRoles(String userName, String quadrigaRoles,
			String loggedInUser) throws QuadrigaStorageException;

	/**
	 * This method calls the DAO layer method to insert 
	 * Quadriga Admin user record into the daabase.
	 * @param userName - Quadriga admin user name.
	 * @param sRoles - quadriga Roles possed by the admin.
	 * @throws QuadrigaStorageException - represents any database exception.
	 * @author kiran batna
	 */
	public abstract void insertQuadrigaAdminUser(String userName)
			throws QuadrigaStorageException;


    public abstract boolean addNewUser(AccountRequest request)
            throws QuadrigaStorageException, UsernameExistsException, QuadrigaNotificationException;

}
