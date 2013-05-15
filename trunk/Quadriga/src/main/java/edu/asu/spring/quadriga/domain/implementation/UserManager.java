package edu.asu.spring.quadriga.domain.implementation;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUserManager;

public class UserManager implements IUserManager{
	
	DBConnection dbconnect;
	
	public UserManager()
	{
		dbconnect = new DBConnection();
	}
	
	@Override
	public User getUserDetails(String sUserId)
	{
		User user = new User();
		
		//TODO: Remove these and code logic to check in Quad DB.
		List<String> listActiveUser = new ArrayList<String>();		
		listActiveUser.add("test");
		listActiveUser.add("john");
		listActiveUser.add("bob");
		List<String> listInActiveUser = new ArrayList<String>();
		listInActiveUser.add("jack");
		listInActiveUser.add("jill");
		listInActiveUser.add("tom");
		
		
		
		user = dbconnect.getUserDetails(sUserId);
		
		if(user!=null)
		{
			
		}
		//The user is active in Quad DB
		if(listActiveUser.contains(sUserId))
		{
			user.setUserName(sUserId);
			user.setActive(true);
		}
		//The user account is deactivated in the Quad DB
		else if(listInActiveUser.contains(sUserId))
		{
			user.setUserName(sUserId);
			user.setActive(false);
		}
		//No such user in the Quad Db
		else
		{
			user = null;
		}
		
		return user;
	}

	@Override
	public String updateUserDetails(User existingUser)
	{
		return null;
	}

	@Override
	public int deleteUser(String sUserId)
	{
		return 0;
	}

	@Override
	public  int addNewUser(User newUser)
	{
		return 0;
	}

}
