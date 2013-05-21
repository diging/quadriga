package edu.asu.spring.quadriga.service.develop;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRoles;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.QuadrigaRole;
import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.service.IUserManager;

//@Service("userManager")
public class MockupUserManager implements IUserManager {

	@Autowired
	private IUserFactory userFactory;
	
	@Override
	public IUser getUserDetails(String sUserId) throws SQLException {
		IUser user = userFactory.createUserObject();
		
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
			
			List<IQuadrigaRoles> roles = new ArrayList<IQuadrigaRoles>();
			
			IQuadrigaRoles role = new QuadrigaRole();
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
			
			List<IQuadrigaRoles> roles = new ArrayList<IQuadrigaRoles>();
			
			IQuadrigaRoles role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_DEACTIVATED");
			roles.add(role);
			user.setQuadrigaRoles(roles);
			//user.setActive(false);
		}
		// No such user in the Quad Db
		else {
			user.setUserName("no account user");
	List<IQuadrigaRoles> roles = new ArrayList<IQuadrigaRoles>();
			
			IQuadrigaRoles role = new QuadrigaRole();
			role.setId("ROLE_QUADRIGA_NOACCOUNT");
			roles.add(role);
			user.setQuadrigaRoles(roles);
		}
		
		return user;
	}

	@Override
	public String updateUserDetails(User existingUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteUser(String sUserId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addNewUser(User newUser) {
		// TODO Auto-generated method stub
		return 0;
	}

}
