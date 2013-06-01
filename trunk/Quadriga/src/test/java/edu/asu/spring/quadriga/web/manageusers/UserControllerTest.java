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

	@Test
	public void testManageUsers() {
		
		//Check the return type
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

	@Test
	public void testUserRequestList() {
				fail("Not yet implemented");
	}

	@Test
	public void testUserAccessHandler() {
				fail("Not yet implemented");
	}

	@Test
	public void testUserActiveList() {
				fail("Not yet implemented");
	}

	@Test
	public void testUserInactiveList() {
				fail("Not yet implemented");
	}

	@Test
	public void testDeactivateUser() {
				fail("Not yet implemented");
	}

	@Test
	public void testActivateUser() {
				fail("Not yet implemented");
	}

}
