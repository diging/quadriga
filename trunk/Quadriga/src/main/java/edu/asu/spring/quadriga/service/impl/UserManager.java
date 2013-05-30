package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * @description  UserManager class implementing the User
 *               functionality
 * 
 * @author       Kiran
 * @author 		 Ram Kumar Kumaresan
 *
 */
@Service("userManager")
public class UserManager implements IUserManager {

	@Autowired
	@Qualifier("DBConnectionManagerBean")
	private IDBConnectionManager dbConnect;

	@Autowired
	private QuadrigaRoleManager rolemanager;

	@Autowired
	private IUserFactory userFactory;

	/**
	 * @description : retrieve the user details from DBConectionManager and
	 *                retrieve the associate roles from the quadriga-roles.xml
	 * 
	 * @param       : userId - the userid for which the details are obtained.
	 * 
	 * @return      : IUser - User object with full details of user if he is 
	 *                        present in the Quadriga DB.
	 *                      - User object assigned to 'No Account' role' if he 
	 *                        is not present in Quadriga DB. 
	 * @author      : Kiran
	 *
	 */
	@Override
	public IUser getUserDetails(String sUserId)
	{
		int i = 0;
		IUser user = null;
		List<IQuadrigaRole> userRole = null;
		IQuadrigaRole quadrigaRole = null;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();

		user = dbConnect.getUserDetails(sUserId);

		try
		{
			if(user!=null)
			{
				userRole = user.getQuadrigaRoles();

				for(i=0;i<userRole.size();i++)
				{
					quadrigaRole = rolemanager.getQuadrigaRole(userRole.get(i).getDBid());

					//If user account is deactivated remove other roles 
					if(quadrigaRole.getId().equals(RoleNames.ROLE_QUADRIGA_DEACTIVATED))
					{
						rolesList.clear();
					}
					rolesList.add(quadrigaRole);
				}
				user.setQuadrigaRoles(rolesList);
			}
			else
			{
				user = userFactory.createUserObject();
				quadrigaRole = rolemanager.getQuadrigaRole(RoleNames.DB_ROLE_QUADRIGA_NOACCOUNT);
				rolesList.add(quadrigaRole);
				user.setQuadrigaRoles(rolesList);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error occurred in associating roles to User");
		}		

		return user;	
	}

	/**
	 * List all active users in the Quadriga database
	 * 
	 * @return List of all active user objects
	 * 
	 */
	@Override
	public List<IUser> getAllActiveUsers()
	{
		List<IUser> listUsers = null;
		
		//Find the ROLEDBID for Deactivated account
		String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		try
		{
			listUsers = dbConnect.getAllActiveUsers(sDeactiveRoleDBId);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
		return listUsers;		
	}

	/**
	 * List all inactive users in the quadriga database
	 * 
	 * @return List of all inactive user objects
	 */
	@Override
	public List<IUser> getAllInActiveUsers()
	{
		List<IUser> listUsers = null;
		
		//Find the ROLEDBID for Deactivated account
		String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		try
		{
			listUsers = dbConnect.getAllInActiveUsers(sDeactiveRoleDBId);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
		return listUsers;		
	}

	/**
	 * List all the open user requests available in the quadriga database
	 * 
	 * @return List all open user requests
	 */
	@Override
	public List<IUser> getUserRequests()
	{
		List<IUser> listUsers = null;

		try
		{
			listUsers = dbConnect.getUserRequests();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
		return listUsers;		
	}

	/**
	 *  @description   : update the user details
	 *                   (Not yet implemented)
	 */
	@Override
	public String updateUserDetails(IUser existingUser) {
		return null;
	}

	/**
	 *  Deactivate a user account so that the particular user cannot access quadriga anymore.
	 *  
	 *  @param sUserId	The userid of the user whose account is to be deactivated.
	 *  @param sAdminId  The userid of the admin who is changing the user setting.
	 *  @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	@Override
	public int deactivateUser(String sUserId,String sAdminId) {
		//Find the ROLEDBID for Deactivated account
		String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		//Add the new role to the user.
		int iResult = dbConnect.deactivateUser(sUserId, sDeactiveRoleDBId, sAdminId);
		return iResult;
	}

	/**
	 * Approve a user request to access quadriga.
	 * 
	 * @param sUserId	The userid of the user whose account has been approved.
	 * @param sRoles 	The set of roles that are assigned to the user.
	 * @param sAdminId  The userid of the admin who is changing the user setting
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	@Override
	public int approveUserRequest(String sUserId,String sRoles,String sAdminId) {

		int iResult = dbConnect.approveUserRequest(sUserId, sRoles, sAdminId);
		return iResult;
	}

	/**
	 * Deny a user request to access the quadriga.
	 * 
	 * @param sUserId	The userid of the user whose account has been denied.
	 * @param sAdminId  The userid of the admin who is changing the user setting.
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	@Override
	public int denyUserRequest(String sUserId,String sAdminId) {

		int iResult = dbConnect.denyUserRequest(sUserId, sAdminId);
		return iResult;
	}

	/**
	 * Activate an already existing user so that the user can access quadriga.
	 * 	 
	 * @param sUserId	The userid of the user whose account has been activated. 
	 * @param sAdminId  The userid of the admin who is changing the user setting.
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 */
	@Override
	public int activateUser(String sUserId,String sAdminId) {

		//Find the deactivated role id and create a QuadrigaRole Object
		String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		//Find all the roles of the user
		IUser user = null;
		List<IQuadrigaRole> userRole = null;
		IQuadrigaRole quadrigaRole = null;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();

		user = dbConnect.getUserDetails(sUserId);

		//Remove the deactivated role from user roles
		if(user!=null)
		{
			userRole = user.getQuadrigaRoles();
			for(int i=0;i<userRole.size();i++)
			{				
				if(!userRole.get(i).getDBid().equals(sDeactiveRoleDBId))
				{
					quadrigaRole = rolemanager.getQuadrigaRole(userRole.get(i).getDBid());
					rolesList.add(quadrigaRole);
				}
			}
			user.setQuadrigaRoles(rolesList);
		}

		//Convert the user roles to one string with DBROLEIDs
		//Update the role in the Quadriga Database.
		int iResult = dbConnect.updateUserRoles(sUserId, user.getQuadrigaRolesDBId(),sAdminId);

		return iResult;
	}

	/**
	 *  @description   : adding new user
	 *                   (Not yet implemented)
	 */
	@Override
	public int addNewUser(IUser newUser) {
		throw new NotImplementedException("addNewUser() is not yet implemented");
	}

	@Override
	public void setUserDetails(String name, String username, String email,
			String roles) {
		throw new NotImplementedException("setUserDetails() is not yet implemented");

	}

	@Override
	public void addAccountRequest(String userId) {
		throw new NotImplementedException("addAccountRequest is not yet implemented");
	}

}