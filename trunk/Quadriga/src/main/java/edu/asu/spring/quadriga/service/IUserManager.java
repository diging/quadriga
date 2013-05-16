package edu.asu.spring.quadriga.service;

import java.sql.SQLException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.User;

public interface IUserManager {

	public abstract IUser getUserDetails(String sUserId) throws SQLException;
	
	public abstract String updateUserDetails(User existingUser);
	
	public abstract int deleteUser(String sUserId);
	
	public abstract int addNewUser(User newUser);
}
