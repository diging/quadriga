
package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.email.IEmailNotificationManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * @description  UserManager class implementing the User
 *               functionality
 * 
 * @author       Kiran Kumar Batna
 * @author 		 Ram Kumar Kumaresan
 *
 */
@Service("userManager")
public class UserManager implements IUserManager {

	private static final Logger logger = LoggerFactory
			.getLogger(UserManager.class);

	@Autowired
	@Qualifier("DBConnectionManagerBean")
	private IDBConnectionManager dbConnect;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IEmailNotificationManager emailManager;

	public IUserFactory getUserFactory() {
		return userFactory;
	}

	public void setUserFactory(IUserFactory userFactory) {
		this.userFactory = userFactory;
	}

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
	 * @throws QuadrigaStorageException 
	 *
	 */
	@Override
	public IUser getUserDetails(String sUserId) throws QuadrigaStorageException
	{
		int i = 0;
		IUser user = null;
		List<IQuadrigaRole> userRole = null;
		IQuadrigaRole quadrigaRole = null;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();

		user = dbConnect.getUserDetails(sUserId);
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
					rolesList.add(quadrigaRole);
					break;
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

		return user;	
	}

	/**
	 * List all active users in the Quadriga database
	 * 
	 * @return List of all active user objects
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public List<IUser> getAllActiveUsers() throws QuadrigaStorageException
	{
		List<IUser> listUsers = null;

		//Find the ROLEDBID for Deactivated account
		String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		listUsers = dbConnect.getUsersNotInRole(sDeactiveRoleDBId);
		return listUsers;		
	}

	/**
	 * List all inactive users in the quadriga database
	 * 
	 * @return List of all inactive user objects
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public List<IUser> getAllInActiveUsers() throws QuadrigaStorageException
	{
		List<IUser> listUsers = null;

		//Find the ROLEDBID for Deactivated account
		String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		listUsers = dbConnect.getUsers(sDeactiveRoleDBId);
		return listUsers;		
	}

	/**
	 * List all the open user requests available in the quadriga database
	 * 
	 * @return List all open user requests
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public List<IUser> getUserRequests() throws QuadrigaStorageException
	{
		List<IUser> listUsers = null;

		listUsers = dbConnect.getUserRequests();
		return listUsers;		
	}


	/**
	 *  Deactivate a user account so that the particular user cannot access quadriga anymore.
	 *  
	 *  @param sUserId	The userid of the user whose account is to be deactivated.
	 *  @param sAdminId  The userid of the admin who is changing the user setting.
	 *  @return Return the status of the operation. 1- Success and 0 - Failure.
	 *  
	 *  @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public int deactivateUser(String sUserId,String sAdminId) throws QuadrigaStorageException {
		//Find the ROLEDBID for Deactivated account
		String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		//Add the new role to the user.
		int iResult = dbConnect.deactivateUser(sUserId, sDeactiveRoleDBId, sAdminId);

		if(iResult == SUCCESS)
		{
			logger.info("The admin "+sAdminId+" deactivated the account of "+sUserId);
			IUser user = this.getUserDetails(sUserId);

			//TODO:Remove test email setup
			user.setEmail("ramkumar007@gmail.com");

			if(user.getEmail()!=null && !user.getEmail().equals(""))
				emailManager.sendAccountDeactivationEmail(user, sAdminId);
			else
				logger.info("The user "+sUserId+" did not have an email address to send account deactivation email");
		}
		return iResult;
	}

	/**
	 * Approve a user request to access quadriga.
	 * 
	 * @param sUserId	The userid of the user whose account has been approved.
	 * @param sRoles 	The set of roles that are assigned to the user.
	 * @param sAdminId  The userid of the admin who is changing the user setting
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 * 
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public int approveUserRequest(String sUserId,String sRoles,String sAdminId) throws QuadrigaStorageException {

		int iResult = dbConnect.approveUserRequest(sUserId, sRoles, sAdminId);
		return iResult;
	}

	/**
	 * Deny a user request to access the quadriga.
	 * 
	 * @param sUserId	The userid of the user whose account has been denied.
	 * @param sAdminId  The userid of the admin who is changing the user setting.
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 * 
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public int denyUserRequest(String sUserId,String sAdminId) throws QuadrigaStorageException {

		int iResult = dbConnect.denyUserRequest(sUserId, sAdminId);
		return iResult;
	}

	/**
	 * Activate an already existing user so that the user can access quadriga.
	 * 	 
	 * @param sUserId	The userid of the user whose account has been activated. 
	 * @param sAdminId  The userid of the admin who is changing the user setting.
	 * @return Return the status of the operation. 1- Success and 0 - Failure.
	 * 
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public int activateUser(String sUserId,String sAdminId) throws QuadrigaStorageException {

		int iResult=0;

		//Find the deactivated role id and create a QuadrigaRole Object
		String sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		//Find all the roles of the user
		IUser user = null;
		user = dbConnect.getUserDetails(sUserId);

		//Remove the deactivated role from user roles
		if(user!=null)
		{
			IQuadrigaRole quadrigaRole = null;
			List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
			List<IQuadrigaRole> userRole = user.getQuadrigaRoles();
			for(int i=0;i<userRole.size();i++)
			{				
				if(!userRole.get(i).getDBid().equals(sDeactiveRoleDBId))
				{
					quadrigaRole = rolemanager.getQuadrigaRole(userRole.get(i).getDBid());
					rolesList.add(quadrigaRole);
				}
			}
			user.setQuadrigaRoles(rolesList);

			//Convert the user roles to one string with DBROLEIDs
			//Update the role in the Quadriga Database.
			iResult = dbConnect.updateUserRoles(sUserId, user.getQuadrigaRolesDBId(),sAdminId);
			if(iResult == SUCCESS)
			{
				logger.info("The admin "+sAdminId+" activated the account of "+sUserId);

				//TODO:Remove test email setup
				user.setEmail("ramkumar007@gmail.com");

				if(user.getEmail()!=null && !user.getEmail().equals(""))
					emailManager.sendAccountActivationEmail(user, sAdminId);
				else
					logger.info("The user "+sUserId+" did not have an email address to send account activation email");
			}
		}

		return iResult;
	}

	/**
	 * Add a new user request to access quadriga.
	 * 
	 * @param userId The user id of the user who needs access to quadriga 
	 * @return Integer value that specifies the status of the operation. 1 - Successfully place the request. 0 - An open request is already placed for the userid.
	 * 
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public int addAccountRequest(String userId) throws QuadrigaStorageException {
		int iUserStatus;

		//Get all open user requests
		List<IUser> listUsers = dbConnect.getUserRequests();

		//Check if an open request is already placed for the userid
		for(IUser user:listUsers)
		{
			if(user.getUserName().equalsIgnoreCase(userId))
			{
				iUserStatus = 0;
				return iUserStatus;
			}
		}

		//Place a new access request
		iUserStatus = dbConnect.addAccountRequest(userId);
		
		//Check the status of the request
		if(iUserStatus == SUCCESS)
		{
			String sAdminRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_ADMIN);
			List<IUser> listAdminUsers = dbConnect.getUsers(sAdminRoleDBId);

			//Ignore the user if the account is deactivated
			adminlabel:
			for(IUser admin:listAdminUsers)
			{
				List<IQuadrigaRole> roles = admin.getQuadrigaRoles();
				IQuadrigaRole quadrigaRole = null;

				for(IQuadrigaRole role: roles)
				{
					quadrigaRole = rolemanager.getQuadrigaRole(role.getDBid());
					if(quadrigaRole.getId().equals(RoleNames.ROLE_QUADRIGA_DEACTIVATED))
					{
						//Continue to the next user as this user account is deactivated
						continue adminlabel;
					}
				}

				//TODO:Remove test email setup
				admin.setEmail("ramkumar007@gmail.com");
				if(admin.getEmail()!=null && !admin.getEmail().equals(""))
				{
					emailManager.sendNewAccountRequestPlacementEmail(admin, userId);
				}
				else
					logger.info("The system tried to send email to the admin "+admin.getUserName()+" but the admin did not have an email setup");
			}
		}
		return iUserStatus;
	}

}