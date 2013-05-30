package edu.asu.spring.quadriga.service.develop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.QuadrigaRole;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.impl.UserManager;

/**
 * This class is a mock up for the {@link UserManager} for development and
 * testing purposes.
 * 
 * @author Julia Damerow
 * 
 */
//@Service("userManager")
public class MockupUserManager implements IUserManager {

	@Autowired
	private IUserFactory userFactory;

	private Map<String, IUser> users;
	private List<String> activeUsers;
	private List<String> inactiveUsers;
	private List<String> userRequests;

	public MockupUserManager() {
		
	}
	
	@PostConstruct
	public void init() {
		users = new HashMap<String, IUser>();
		activeUsers = new ArrayList<String>();
		inactiveUsers = new ArrayList<String>();
		userRequests = new ArrayList<String>();

		// Add John Doe
		{
			IUser user = userFactory.createUserObject();
			user.setUserName("jdoe");
			user.setName("John Doe");

			List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();

			IQuadrigaRole role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_USER_ADMIN");
			roles.add(role);

			role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_USER_STANDARD");
			roles.add(role);

			user.setQuadrigaRoles(roles);

			users.put("jdoe", user);
		}

		// add test
		{
			IUser user = userFactory.createUserObject();
			user.setUserName("test");
			user.setName("Test User");

			List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();

			IQuadrigaRole role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_USER_STANDARD");
			roles.add(role);
			user.setQuadrigaRoles(roles);

			users.put("test", user);
		}

		// add test
		{
			IUser user = userFactory.createUserObject();
			user.setUserName("test");
			user.setName("Test User");

			List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();

			IQuadrigaRole role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_USER_STANDARD");
			roles.add(role);
			user.setQuadrigaRoles(roles);

			users.put("test", user);
		}

		// add bob
		{
			IUser user = userFactory.createUserObject();
			user.setUserName("bob");
			user.setName("Bob Doe");

			List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();

			IQuadrigaRole role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_USER_STANDARD");
			roles.add(role);
			user.setQuadrigaRoles(roles);

			users.put("bob", user);
		}
		
		// initialize active user list
		activeUsers.add("jdoe");
		
		// initialize inactive user list
		inactiveUsers.add("test");
		inactiveUsers.add("bob");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IUser getUserDetails(String sUserId) {
		
		// The user is active in Quad DB
		if (activeUsers.contains(sUserId)) {
			if (users.get(sUserId) != null)
				return users.get(sUserId);
		}
		// The user account is deactivated in the Quad DB
		else if (inactiveUsers.contains(sUserId)) {
			IUser user = users.get(sUserId);
			
			if (user != null) {

				List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();

				IQuadrigaRole role = new QuadrigaRole();
				role.setId("ROLE_QUADRIGA_USER_STANDARD");
				roles.add(role);
				user.setQuadrigaRoles(roles);
				
				return user;
			}
		}
		// No such user in the Quad Db
		else {
			IUser user = userFactory.createUserObject();
			user.setUserName(sUserId);
			user.setUserName("no account user");
			List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();

			IQuadrigaRole role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_NOACCOUNT");
			roles.add(role);
			user.setQuadrigaRoles(roles);
			return user;
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updateUserDetails(IUser existingUser) {
		throw new NotImplementedException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addNewUser(IUser newUser) {
		users.put(newUser.getUserName(), newUser);
		return 0;
	}

	@Override
	public List<IUser> getAllActiveUsers() {
		throw new NotImplementedException(
				"getAllActiveUsers() is not yet implemented");
	}

	@Override
	public List<IUser> getAllInActiveUsers() {
		throw new NotImplementedException(
				"getAllInactivaeUsers is not yet implemented");
	}

	@Override
	public int deactivateUser(String sUserId, String sAdminId) {
		throw new NotImplementedException(
				"dectivateUser() is not yet implemented");
	}

	@Override
	public int activateUser(String sUserId, String sAdminId) {
		throw new NotImplementedException(
				"activateUser() is not yet implemented");
	}

	@Override
	public List<IUser> getUserRequests() {
		throw new NotImplementedException(
				"getUserRequest() is not yet implemented");
	}
	
	public IUserFactory getUserFactory() {
		return userFactory;
	}

	public void setUserFactory(IUserFactory userFactory) {
		this.userFactory = userFactory;
	}
	
	public List<String> getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(List<String> activeUsers) {
		this.activeUsers = activeUsers;
	}

	public List<String> getInactiveUsers() {
		return inactiveUsers;
	}

	public void setInactiveUsers(List<String> inactiveUsers) {
		this.inactiveUsers = inactiveUsers;
	}

	@Override
	public int approveUserRequest(String sUserId, String sRoles, String sAdminId) {
		throw new NotImplementedException("approveUserRequest() is not yet implemented");
	}

	@Override
	public int denyUserRequest(String sUserId, String sAdminId) {
		throw new NotImplementedException("denyUserRequest() is not yet implemented");
	}

	@Override
	public void setUserDetails(String name, String username, String email,
			String roles) {
		throw new NotImplementedException("setUserDetails() is not yet implemented");
		
	}

	@Override
	public int addAccountRequest(String userId) {
		userRequests.add(userId);
		return 1;
	}

}
