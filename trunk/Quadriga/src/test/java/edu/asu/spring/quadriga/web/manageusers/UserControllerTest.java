package edu.asu.spring.quadriga.web.manageusers;

import static org.junit.Assert.*;

import java.security.Principal;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.impl.UserManager;

/**
 * This class tests the {@link UserController}
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/spring-dbconnectionmanager.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/spring-security.xml",
"file:src/main/webapp/WEB-INF/spring/quadriga-roles.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

	UserController userContoller;

	@Autowired 
	IUserManager usermanager;

	Principal principal;	
	UsernamePasswordAuthenticationToken authentication;
	CredentialsContainer credentials;
	BindingAwareModelMap model;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * This method setsup the needed usermanager, model, principal and authentication objects
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		userContoller = new UserController();
		userContoller.setUsermanager(usermanager);

		model =  new BindingAwareModelMap();		
		principal = new Principal() {			
			@Override
			public String getName() {
				return "jdoe";
			}
		};
		authentication = new UsernamePasswordAuthenticationToken(principal, credentials);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This method tests if the appropriate paths are returned. It also checks if the correct
	 * number of users are returned for cases where the status are active, inactive and open requests.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testManageUsers() {

		//Check the return value
		assertEquals(userContoller.manageUsers(model, principal),"auth/users/manage");

		//User Requests
		List<IUser> userRequestsList = (List<IUser>) model.get("userRequestsList");
		assertEquals(2, userRequestsList.size());

		//Active Users
		List<IUser> activeList = (List<IUser>) model.get("activeUserList");
		assertEquals(1,activeList.size());

		//Inactive Users
		List<IUser> inactiveList = (List<IUser>) model.get("inactiveUserList");
		assertEquals(2,inactiveList.size());
	}

	/**
	 * This method tests if the appropriate path is returned and also evaluates the number of 
	 * open requests and also checks if the correct path is returned.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUserRequestList() {
		//Check the return value
		assertEquals(userContoller.userRequestList(model, principal),"auth/users/requests");

		//Open Requests
		List<IUser> userRequestsList = (List<IUser>) model.get("userRequestsList");
		assertEquals(2,userRequestsList.size());

	}

	/**
	 * This method tests if a user is approved/denied access to quadriga and also checks if the correct path is returned.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUserAccessHandler() {
		//Deny a user
		assertEquals(userContoller.userAccessHandler("charlie-denied", model, principal),"redirect:/auth/users/manage");

		//Approve a user
		assertEquals(userContoller.userAccessHandler("charlie-approve-admin", model, principal),"redirect:/auth/users/manage");

	}

	/**
	 * This method tests if the correct number of active users are retrieved and if the appropriate path is also returned.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUserActiveList() {
		//Check the return value
		assertEquals(userContoller.userActiveList(model, principal),"auth/users/active");

		//Active Users
		List<IUser> activeList = (List<IUser>) model.get("activeUserList");
		assertEquals(2,activeList.size());
	}

	/**
	 * This method tests if the correct number of inactive users are retrieved and if the appropriate path is also returned.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUserInactiveList() {
		//Check the return value
		assertEquals(userContoller.userInactiveList(model, principal),"auth/users/inactive");

		//Inactive Users
		List<IUser> inactiveList = (List<IUser>) model.get("inactiveUserList");
		assertEquals(2,inactiveList.size());
	}

	/**
	 * This method tests if a user is deactivated and returns the appropriate path.
	 */
	@Test
	public void testDeactivateUser() {
		//Check the return value
		assertEquals(userContoller.deactivateUser("jdoe",model, principal),"redirect:/auth/users/manage");
	}
	
	/**
	 * This method tests if a user is activated and returns the appropriate path. 
	 */
	@Test
	public void testActivateUser() {
		//Check the return value
		assertEquals(userContoller.activateUser("jdoe",model, principal),"redirect:/auth/users/manage");
	}

}
