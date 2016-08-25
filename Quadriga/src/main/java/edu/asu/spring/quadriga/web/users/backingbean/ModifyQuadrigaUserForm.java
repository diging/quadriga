package edu.asu.spring.quadriga.web.users.backingbean;

import java.util.List;

/**
 * This class holds the list of users to act as a Model
 * attribute to display in the UI pages.
 *
 */
public class ModifyQuadrigaUserForm 
{
	private List<ModifyQuadrigaUser> users;

	public List<ModifyQuadrigaUser> getUsers() {
		return users;
	}

	public void setUsers(List<ModifyQuadrigaUser> users) {
		this.users = users;
	}
	
	

}
