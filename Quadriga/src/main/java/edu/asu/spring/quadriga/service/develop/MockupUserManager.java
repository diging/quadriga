package edu.asu.spring.quadriga.service.develop;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.QuadrigaRole;
import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.impl.UserManager;

/**
 * This class is a mock up for the {@link UserManager} for development and testing purposes.
 * @author Julia Damerow
 *
 */
@Service("userManager")
public class MockupUserManager implements IUserManager {

	@Autowired
	private IUserFactory userFactory;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IUser getUserDetails(String sUserId){
		IUser user = userFactory.createUserObject();
		//userFactory.cloneUserObject(null);
		
		// TODO: Remove these and code logic to check in Quad DB.
		List<String> listActiveUser = new ArrayList<String>();
		listActiveUser.add("jdoe");
		listActiveUser.add("bob");
		List<String> listInActiveUser = new ArrayList<String>();
		listInActiveUser.add("test");
		listInActiveUser.add("jill");
		listInActiveUser.add("tom");

		// TODO Remove: Code implemented for test class but changed later :(
		// The user is active in Quad DB
		if (listActiveUser.contains(sUserId)) {
			user.setUserName(sUserId);
			user.setName("John Doe");
			
			List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
			
			IQuadrigaRole role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_USER_ADMIN");
			roles.add(role);
			
			role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_USER_STANDARD");
			roles.add(role);
			
			user.setQuadrigaRoles(roles);
			
			//user.setActive(true);
		}
		// The user account is deactivated in the Quad DB
		else if (listInActiveUser.contains(sUserId)) {
			user.setUserName("Test User");
			
			List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
			
			IQuadrigaRole role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_USER_STANDARD");
			roles.add(role);
			user.setQuadrigaRoles(roles);
			//user.setActive(false);
		}
		// No such user in the Quad Db
		else {
			user.setUserName("no account user");
			List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
			
			IQuadrigaRole role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_NOACCOUNT");
			roles.add(role);
			user.setQuadrigaRoles(roles);
		}
		
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updateUserDetails(User existingUser) {
		throw new NotImplementedException();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addNewUser(User newUser) {
		throw new NotImplementedException("addNewUser() is not yet implemented");
	}

	@Override
	public List<IUser> getAllActiveUsers() {
		throw new NotImplementedException("getAllActiveUsers() is not yet implemented");
	}

	@Override
	public List<IUser> getAllInActiveUsers() {
		throw new NotImplementedException("getAllInactivaeUsers is not yet implemented");
	}

	@Override
	public int deactivateUser(String sUserId) {
		throw new NotImplementedException("dectivateUser() is not yet implemented");
	}

	@Override
	public int activateUser(String sUserId) {
		throw new NotImplementedException("activateUser() is not yet implemented");
	}

}
