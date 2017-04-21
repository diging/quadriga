package edu.asu.spring.quadriga.service.user.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IUserDeepMapper {

	/**
	 *  Retrieves the user details for the given userid
	 *  @param userName      						{@link IUser} name of type {@link String}
	 *  @return      								null - if the user is not present in the quadriga DB else IUser - User object containing the user details.
	 *  @throws QuadrigaStorageException 			Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	public abstract IUser getUser(String userName)
			throws QuadrigaStorageException;

	/**
	 * Retrieves the {@link List} of {@link IUser} object which are active users in Quadriga.
	 * @return										{@link List} of {@link IUser} objects which are active users in quadriga.
	 * @throws QuadrigaStorageException				Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	public abstract List<IUser> getAllActiveUsers() throws QuadrigaStorageException;

	/**
	 * Retrieves the {@link List} of {@link IUser} object which are inactive users in Quadriga.
	 * @return										{@link List} of {@link IUser} objects which are inactive users in quadriga.
	 * @throws QuadrigaStorageException				Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	public abstract List<IUser> getAllInActiveUsers() throws QuadrigaStorageException;

	/**
	 * 
	 * @return
	 * @throws QuadrigaStorageException				Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	public abstract List<IUser> getUserRequests() throws QuadrigaStorageException;

	/**
	 * 
	 * @param roleId
	 * @return
	 * @throws QuadrigaStorageException				Exception will be thrown when the input parameters do not satisfy the system/database constraints or due to database connection troubles.
	 */
	public abstract List<IUser> getUsersByRoleId(String roleId) throws QuadrigaStorageException;
	
	public abstract IUser findUserByProviderUserId(String userId, String provider)
            throws QuadrigaStorageException; 

}