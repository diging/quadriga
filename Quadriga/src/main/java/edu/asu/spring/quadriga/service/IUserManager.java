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
	 * Method to update the user details.
	 * 
	 * @param existingUser		The user object corresponding to the authenticated user.
	 * @return					String which indicates the operations status.
	 */
	public abstract String updateUserDetails(IUser existingUser);
	
	/**
	 * Method to deactivate the user account in Quadriga database.
	 * 
	 * @param sUserId			The unique userid of the user
	 * @return					Integer to indicate the operation status. 0 - Success. 1 - Already Inactive user. -1 - Error in performing the operation
	 */
	public abstract int deactivateUser(String sUserId);
	
	/**
	 * Method to add a new active user to the existing active user list in Quadriga database.
	 * 
	 * @param newUser			The user object which contains the details of the user to be added to Quadriga database.
	 * @return					Integer to indicate the operation status. 0 - Success. 1 - Userid is taken. -1 - Error in performing the operation
	 */
	public abstract int addNewUser(IUser newUser);

	public abstract List<IUser> getAllActiveUsers();

	public abstract List<IUser> getAllInActiveUsers();

	public abstract int activateUser(String sUserId);

	public List<IUser> getUserRequests();

	public abstract int approveUserRequest(String sUserId, String sRoles);

	public abstract int denyUserRequest(String sUserId, String sAdminId);
	
	public abstract void setUserDetails(String name,String username,String email,String roles);
}
