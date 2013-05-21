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

//@Service("userManager")
public class UserManager implements IUserManager {

	@Autowired
	@Qualifier("DBConnectionManagerBean")
	private IDBConnectionManager dbConnect;

	@Autowired
	private QuadrigaRoleManager rolemanager;
	
	@Autowired
	private IUserFactory userFactory;
	
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
			e.printStackTrace();
		}
	   return user;	
	}

	@Override
	public String updateUserDetails(User existingUser) {
		return null;
	}

	@Override
	public int deleteUser(String sUserId) {
		return 0;
	}

	@Override
	public int addNewUser(User newUser) {
		return 0;
	}

}
