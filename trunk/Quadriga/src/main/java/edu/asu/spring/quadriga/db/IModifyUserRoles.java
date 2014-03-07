package edu.asu.spring.quadriga.db;


import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyUserRoles 
{

	/**
	 * This method updates the quadriga roles associated with the user
	 * @param userName - user for which the roles are updated
	 * @param quadrigaRoles - quadriga roles associated to the user.
	 * @param loggedInUser - user logged into system
	 * @throws QuadrigaStorageException
	 */
	public abstract void updateUserQuadrigaRoles(String userName,String quadrigaRoles, String loggedInUser) throws QuadrigaStorageException;

}
