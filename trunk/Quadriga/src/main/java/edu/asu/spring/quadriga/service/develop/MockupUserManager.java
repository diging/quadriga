package edu.asu.spring.quadriga.service.develop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.QuadrigaRole;
import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
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

	private List<String> deniedList;
	public MockupUserManager() {

	}

	@PostConstruct
	public void init() {
		users = new HashMap<String, IUser>();
		activeUsers = new ArrayList<String>();
		inactiveUsers = new ArrayList<String>();
		userRequests = new ArrayList<String>();
		deniedList = new ArrayList<String>();

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

		//initialize user requests list
		userRequests.add("dexter");
		userRequests.add("deb");
		
		deniedList.add("trinity");
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


	@Override
	public List<IUser> getAllActiveUsers() {
		List<IUser> listUser = new ArrayList<IUser>();

		for(String sUsername: activeUsers)
		{
			IUser user = new User();
			user.setUserName(sUsername);
			listUser.add(user);
		}

		return listUser;
	}

	@Override
	public List<IUser> getAllInActiveUsers() {
		List<IUser> listUser = new ArrayList<IUser>();

		for(String sUsername: inactiveUsers)
		{
			IUser user = new User();
			user.setUserName(sUsername);
			listUser.add(user);
		}

		return listUser;
	}

	@Override
	public int deactivateUser(String sUserId, String sAdminId) {
		activeUsers.remove(sUserId);
		inactiveUsers.add(sUserId);
		return 1;
	}

	@Override
	public int activateUser(String sUserId, String sAdminId) {
		inactiveUsers.add(sUserId);
		activeUsers.add(sUserId);		
		return 1;
	}

	@Override
	public List<IUser> getUserRequests() {
		List<IUser> listUser = new ArrayList<IUser>();

		for(String sUsername: userRequests)
		{
			IUser user = new User();
			user.setUserName(sUsername);
			listUser.add(user);
		}

		return listUser;
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
		userRequests.remove(sUserId);
		activeUsers.add(sUserId);
		return 1;
	}

	@Override
	public int denyUserRequest(String sUserId, String sAdminId) {
		userRequests.remove(sUserId);
		deniedList.add(sUserId);
		return 1;
	}


	@Override
	public int addAccountRequest(String userId) {
		if (userRequests.contains(userId))
			return 0;
		
		userRequests.add(userId);
		return 1;
	}

	public void deleteUser(String sUserId, String sAdminId) {
	}

	public boolean checkWorkbenchAssociated(String deleteUser)
			throws QuadrigaStorageException {
		return false;
	}

	public void updateUserQuadrigaRoles(String userName, String quadrigaRoles, String loggedInUser)
			throws QuadrigaStorageException {
	}

	public void insertQuadrigaAdminUser(String userName)
			throws QuadrigaStorageException {
	}

}
