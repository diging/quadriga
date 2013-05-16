package edu.asu.spring.quadriga.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConnectionManager;
import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.service.IUserManager;

//@Service
public class UserManager implements IUserManager {

	IDBConnectionManager dbConnect;

	public UserManager() {
		dbConnect = new DBConnectionManager();
	}

	@Override
	public User getUserDetails(String sUserId) throws SQLException {
		User user = new User();
		user = dbConnect.getUserDetails(sUserId);
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
