package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRoles;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * @description : UserManager class implementing the User
 *                functionality
 * 
 * @author      : Kiran
 *
 */
//@Service("userManager")
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
		List<IQuadrigaRoles> userRole = null;
		IQuadrigaRoles quadrigaRole = null;
		List<IQuadrigaRoles> rolesList = new ArrayList<IQuadrigaRoles>();
		try
		{
			user = dbConnect.getUserDetails(sUserId);
			
			if(user!=null)
			{
				userRole = user.getQuadrigaRoles();
				
				for(i=0;i<userRole.size();i++)
				{
					quadrigaRole = rolemanager.getQuadrigaRole(userRole.get(i).getDBid());
					rolesList.add(quadrigaRole);
				}
				user.setQuadrigaRoles(rolesList);
			}
			else
			{
				user = userFactory.createUserObject();
				quadrigaRole = rolemanager.getNoAccountRole(RoleNames.DB_ROLE_QUADRIGA_NOACCOUNT);
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
	 *  @description   : update the user details
	 *                   (Not yet implemented)
	 */
	@Override
	public String updateUserDetails(User existingUser) {
		return null;
	}

	/**
	 *  @description   : delete a user
	 *                   (Not yet implemented)
	 */
	@Override
	public int deleteUser(String sUserId) {
		return 0;
	}

	/**
	 *  @description   : adding new user
	 *                   (Not yet implemented)
	 */
	@Override
	public int addNewUser(User newUser) {
		return 0;
	}

}
