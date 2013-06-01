package edu.asu.spring.quadriga.db.sql;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;

/**
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/spring-dbconnectionmanager.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/quadriga-roles.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionManagerTest {

	@Autowired
	IDBConnectionManager dbConnection;
	
	@Autowired
	private IUserFactory userFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testUserAccount()
	{
		IUser user = null;
		IUser testUser = userFactory.createUserObject();
		testUser.setUserName("testuser");
		dbConnection.addAccountRequest("testuser");
		user = dbConnection.getUserDetails("testuser");
		assertEquals(user,testUser);
	}
	
	@Test
	public void testGetUserDetails() {
		fail("Needs to be implemented!!!");
	}
	
	@Test
	public void testGetAllActiveUsers() {
		fail("Needs to be implemnted as well!");
	}
	
	@Test
	public void testGetAllInactiveUser() {
		fail("And once again: implemented!");
	}
	
	@Test
	public void testDeactivateUser() {
		fail("Oh no! No implementation.");
	}
	
	@Test
	public void updateUserRoles() {
		fail("I'm starting to repeat myself...");
	}
	
	@Test
	public void testApproveUserRequest() {
		fail("Nothing there!!!");
	}
	
	@Test
	public void testDenyUserRequest() {
		fail("No implementation once again.");
	}
	
	@Test
	public void testGetUserRequests() {
		fail("No implementation, I'm almost crying...");
	}
	
	@Test
	public void testAddAccountRequest() {
		fail("Nothing here...");
	}
	
	@Test
	public void testUserRoles() {
		fail("Who spelled that with an upper case U? And no test implementation as well!");
	}
	
	@Test
	public void testGetProjectOfUser() {
		fail("No test implementation!!!!!!! WHAAAA");
	}
}
