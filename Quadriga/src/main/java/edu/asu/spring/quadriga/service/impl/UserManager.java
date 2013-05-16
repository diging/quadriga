package edu.asu.spring.quadriga.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.sql.DBConnectionManager;
import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.service.IUserManager;

//@Service
public class UserManager implements IUserManager {

	DBConnectionManager dbConnect;

	public UserManager() {
		dbConnect = new DBConnectionManager();
	}

	@Override
	public User getUserDetails(String sUserId) throws SQLException {
		User user = new User();

		// JD: all the test code is now in MockupUserManager
		// TODO: Remove these and code logic to check in Quad DB.
		List<String> listActiveUser = new ArrayList<String>();
		listActiveUser.add("test");
		listActiveUser.add("john");
		listActiveUser.add("bob");
		List<String> listInActiveUser = new ArrayList<String>();
		listInActiveUser.add("jack");
		listInActiveUser.add("jill");
		listInActiveUser.add("tom");

		user = dbConnect.getUserDetails(sUserId);

		// TODO Remove: Code implemented for test class but changed later :(
		// The user is active in Quad DB
		// if(listActiveUser.contains(sUserId))
		// {
		// user.setUserName(sUserId);
		// //user.setActive(true);
		// }
		// //The user account is deactivated in the Quad DB
		// else if(listInActiveUser.contains(sUserId))
		// {
		// user.setUserName(null);
		// //user.setActive(false);
		// }
		// //No such user in the Quad Db
		// else
		// {
		// user = null;
		// }

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
