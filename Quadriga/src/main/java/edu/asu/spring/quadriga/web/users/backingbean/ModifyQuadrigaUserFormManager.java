package edu.asu.spring.quadriga.web.users.backingbean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Service
public class ModifyQuadrigaUserFormManager 
{
	
	@Autowired 
	private IUserManager usermanager;

	@Autowired
	private IQuadrigaRoleManager rolemanager;
	
	public ModifyQuadrigaUserForm modifyUserQuadrigaRolesManager() throws QuadrigaStorageException
	{
		
		ModifyQuadrigaUserForm userForm;
		List<ModifyQuadrigaUser> users;
		ModifyQuadrigaUser quadrigaUser;
		
		userForm = new ModifyQuadrigaUserForm();
		users = new ArrayList<ModifyQuadrigaUser>();
		try
		{
		//Get all Active Users
		List<IUser> activeUserList = usermanager.getAllActiveUsers();
		
		for(IUser activeUser : activeUserList)
		{
			quadrigaUser = new ModifyQuadrigaUser();
			quadrigaUser.setName(activeUser.getName());
			quadrigaUser.setUserName(activeUser.getUserName());
			quadrigaUser.setQuadrigaRoles(activeUser.getQuadrigaRoles());
			users.add(quadrigaUser);
		}
		
		userForm.setUsers(users);
		
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		return userForm;
		
	}

}
