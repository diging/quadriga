package edu.asu.spring.quadriga.domain;

import edu.asu.spring.quadriga.domain.implementation.User;

public interface IUserManager {

	public abstract User getUserDetails(String sUserId);
	
	public abstract String updateUserDetails(User existingUser);
	
	public abstract int deleteUser(String sUserId);
	
	public abstract int addNewUser(User newUser);
}
