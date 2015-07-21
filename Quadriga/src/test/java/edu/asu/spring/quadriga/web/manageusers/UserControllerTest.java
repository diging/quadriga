package edu.asu.spring.quadriga.web.manageusers;

import static org.junit.Assert.assertEquals;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.db.IUserManagerDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * This class tests the {@link UserController}.
 * 
 * IMPORTANT: This test class will overwrite the data in 
 * 			  tbl_quadriga_user
 * 			  tbl_quadriga_user_denied
 * 			  tbl_quadriga_user_requests
 * 
 * @author Ram Kumar Kumaresan
 *
 */
public class UserControllerTest {

    @InjectMocks
	private UserController userContoller;

	@Mock
	private IUserManager usermanager;
	@Mock
	private IQuadrigaRoleManager roleManager;

	Principal principal;	
	UsernamePasswordAuthenticationToken authentication;
	CredentialsContainer credentials;
	BindingAwareModelMap model;


	/**
	 * This method setsup the needed usermanager, model, principal and authentication objects
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	    usermanager = Mockito.mock(IUserManager.class);
	    roleManager = Mockito.mock(IQuadrigaRoleManager.class);
	    MockitoAnnotations.initMocks(this);
        
		model =  new BindingAwareModelMap();		
		principal = new Principal() {			
			@Override
			public String getName() {
				return "test";
			}
		};
		authentication = new UsernamePasswordAuthenticationToken(principal, credentials);
		
		 // assume there are three user requests
        List<IUser> requests = new ArrayList<IUser>();
        requests.add(new User());
        requests.add(new User());
        requests.add(new User());
        
        // assume there are two active users
        List<IUser> activeUsers = new ArrayList<IUser>();
        activeUsers.add(new User());
        activeUsers.add(new User());
        
        // asume there is on inactive user
        List<IUser> inactiveUser = new ArrayList<IUser>();
        inactiveUser.add(new User());
        
        Mockito.when(usermanager.getUserRequests()).thenReturn(requests);
        Mockito.when(usermanager.getAllActiveUsers()).thenReturn(activeUsers);
        Mockito.when(usermanager.getAllInActiveUsers()).thenReturn(inactiveUser);
	}


	/**
	 * This method tests if the appropriate paths are returned. It also checks if the correct
	 * number of users are returned for cases where the status are active, inactive and open requests.
	 * @throws QuadrigaStorageException 
	 * 
	 */
	@Test
	public void testManageUsers() throws QuadrigaStorageException {
	    //Check the return value
		assertEquals(userContoller.manageUsers(model, principal),"auth/users/manage");

		//User Requests
		List<IUser> userRequestsList = (List<IUser>) model.get("userRequestsList");
		assertEquals(3, userRequestsList.size());

		//Active Users
		List<IUser> activeList = (List<IUser>) model.get("activeUserList");
		assertEquals(2,activeList.size());

		//Inactive Users
		List<IUser> inactiveList = (List<IUser>) model.get("inactiveUserList");
		assertEquals(1,inactiveList.size());
	}

	/**
	 * This method tests if the appropriate path is returned and also evaluates the number of 
	 * open requests and also checks if the correct path is returned.
	 * @throws QuadrigaStorageException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	@DirtiesContext
	public void testUserRequestList() throws QuadrigaStorageException {
		//Check the return value
		assertEquals(userContoller.userRequestList(model, principal),"auth/users/requests");

		//Open Requests
		List<IUser> userRequestsList = (List<IUser>) model.get("userRequestsList");
		assertEquals(3,userRequestsList.size());

	}

	/**
	 * This method tests if a user is approved/denied access to quadriga and also checks if the correct path is returned.
	 * @throws QuadrigaStorageException 
	 */
	@Test
	@DirtiesContext
	public void testUserAccessHandler() throws QuadrigaStorageException {
		//Deny a user
		assertEquals(userContoller.userAccessHandler("deb-denied", model, principal),"redirect:/auth/users/manage");

		//Approve a user
		assertEquals(userContoller.userAccessHandler("dexter-approve-admin", model, principal),"redirect:/auth/users/manage");

	}

	/**
	 * This method tests if the correct number of active users are retrieved and if the appropriate path is also returned.
	 * @throws QuadrigaStorageException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	@DirtiesContext
	public void testUserActiveList() throws QuadrigaStorageException {
		//Check the return value
		assertEquals(userContoller.userActiveList(model, principal),"auth/users/active");

		//Active Users
		List<IUser> activeList = (List<IUser>) model.get("activeUserList");
		assertEquals(2,activeList.size());
	}

	/**
	 * This method tests if the correct number of inactive users are retrieved and if the appropriate path is also returned.
	 * @throws QuadrigaStorageException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	@DirtiesContext
	public void testUserInactiveList() throws QuadrigaStorageException {
		//Check the return value
		assertEquals(userContoller.userInactiveList(model, principal),"auth/users/inactive");

		//Inactive Users
		List<IUser> inactiveList = (List<IUser>) model.get("inactiveUserList");
		assertEquals(1,inactiveList.size());
	}

	/**
	 * This method tests if a user is deactivated and returns the appropriate path.
	 * @throws QuadrigaStorageException 
	 */
	@Test
	@DirtiesContext
	public void testDeactivateUser() throws QuadrigaStorageException {
		//Check the return value
		assertEquals(userContoller.deactivateUser("jdoe",model, principal),"redirect:/auth/users/manage");
	}

	/**
	 * This method tests if a user is activated and returns the appropriate path. 
	 * @throws QuadrigaStorageException 
	 */
	@Test
	@DirtiesContext
	public void testActivateUser() throws QuadrigaStorageException {
		//Check the return value
		assertEquals(userContoller.activateUser("jdoe",model, principal),"redirect:/auth/users/manage");
	}

}
