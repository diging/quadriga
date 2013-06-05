package edu.asu.spring.quadriga.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class tests the {@link UserManager}
 * 
 * IMPORTANT: This test class will overwrite the data in 
 * 			  tbl_quadriga_user
 * 			  tbl_quadriga_user_denied
 * 			  tbl_quadriga_user_requests
 * 
 * @author Kiran Kumar Batna
 * @author Ram Kumar Kumaresan
 *
 */
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/spring-dbconnectionmanager.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
"file:src/main/webapp/WEB-INF/spring/quadriga-roles.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class UserManagerTest {

	@Autowired
	IUserManager usermanager;

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	IDBConnectionManager dbConnection;

	@Autowired
	private QuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	String sDatabaseSetup;

	private IUser user;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//Setup a user object to compare with the object produced from usermanager
		user = userFactory.createUserObject();
		user.setUserName("jdoe");
		user.setName("John Doe");

		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role3");
		roles.add(role);
		role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role4");
		roles.add(role);

		IQuadrigaRole quadrigaRole = null;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
		for(int i=0;i<roles.size();i++)
		{
			quadrigaRole = rolemanager.getQuadrigaRole(roles.get(i).getDBid());

			//If user account is deactivated remove other roles 
			if(quadrigaRole.getId().equals(RoleNames.ROLE_QUADRIGA_DEACTIVATED))
			{
				rolesList.clear();
			}
			rolesList.add(quadrigaRole);
		}
		user.setQuadrigaRoles(rolesList);

		//Setup the database with the proper data in the tables;
		sDatabaseSetup = "delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())";
	}

	/**
	 * Load the required data into the dependent tables
	 * @author Ram Kumar Kumaresan
	 */
	public void testSetupTestEnvironment()
	{
		String[] sQuery = sDatabaseSetup.split("&");
		for(String singleQuery: sQuery)
		{
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	@Test
	public void testGetUserDetails() {

		testSetupTestEnvironment();

		//A valid user
		IUser testUser = usermanager.getUserDetails("jdoe");
		assertEquals(true, user.compareUserObjects(testUser));

		//Invalid user
		IUser failUser = userFactory.createUserObject();
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
		rolesList.add(rolemanager.getQuadrigaRole(RoleNames.DB_ROLE_QUADRIGA_NOACCOUNT));
		failUser.setQuadrigaRoles(rolesList);
		testUser = usermanager.getUserDetails("123");
		assertEquals(true, failUser.compareUserObjects(testUser));

	}

	/**
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	@Test
	public void testGetAllActiveUsers() {

		testSetupTestEnvironment();

		//Check if all active users are retrieved
		List<IUser> activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 2);

		//Check if no user is retrieved
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user");
		activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 0);

	}

	/**
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	@Test
	public void testGetAllInActiveUsers() {

		testSetupTestEnvironment();

		//Check if all inactive users are retrieved
		List<IUser> inactiveUsers = usermanager.getAllInActiveUsers();
		assertEquals(inactiveUsers.size(), 1);

		//Check if no user is retrieved
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user");
		inactiveUsers = usermanager.getAllInActiveUsers();
		assertEquals(inactiveUsers.size(), 0);
	}

	/**
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	@Test
	public void testGetUserRequests() {

		testSetupTestEnvironment();

		//Check if all open requests are retrieved
		List<IUser> userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 3);

		//Check if no requests are retrieved
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user_requests");
		userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 0);
	}

	/**
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	@Test
	public void testActivateUser() {

		testSetupTestEnvironment();

		//Check if a user is activated
		List<IUser> activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 2);
		assertEquals(1,usermanager.activateUser("bob", "test"));
		activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 3);

		//Check if it handles an invalid user
		assertEquals(0,usermanager.activateUser("123", "test"));
		
		//Check if no users are present to activate
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user");
		assertEquals(0,usermanager.activateUser("123", "test"));
	}

	/**
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	@Test
	public void testDeactivateUser() {

		testSetupTestEnvironment();

		//Check if a valid user is activated
		List<IUser> inactiveUsers = usermanager.getAllInActiveUsers();
		assertEquals(inactiveUsers.size(), 1);
		assertEquals(1,usermanager.deactivateUser("bob", "test"));
		inactiveUsers = usermanager.getAllInActiveUsers();
		assertEquals(inactiveUsers.size(), 1);
		
		//Check if it handles an invalid user id
		assertEquals(0,usermanager.deactivateUser("123", "test"));

		//Check if no user is retrieved
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user");
		assertEquals(0,usermanager.deactivateUser("123", "test"));
	}

	/**
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	@Test
	public void testApproveUserRequest() {

		testSetupTestEnvironment();

		//Check for a valid approve request
		List<IUser> userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 3);
		List<IUser> activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 2);
		assertEquals(1,usermanager.approveUserRequest("dexter", "role3,role4,role5", "test"));
		userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 2);
		activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 3);
		
		//Check for an invalid user id
		assertEquals(0,usermanager.approveUserRequest("123", "role3,role4,role5", "test"));
		
		//Check if no requests are retrieved
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user_requests");
		assertEquals(0,usermanager.approveUserRequest("dexter", "role3,role4,role5", "test"));
	}

	/**
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	@Test
	public void testDenyUserRequest() {

		testSetupTestEnvironment();

		List<IUser> userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 3);
		assertEquals(1, usermanager.denyUserRequest("deb", "test"));
		userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 2);
		
		//Check for an invalid user id
		assertEquals(0, usermanager.denyUserRequest("123", "test"));
		
		//Check if no requests are retrieved
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user_requests");
		assertEquals(0, usermanager.denyUserRequest("deb", "test"));
	}

	/**
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	@Test
	public void testAddAccountRequest() {

		testSetupTestEnvironment();

		//Check if the request is added
		List<IUser> userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 3);
		assertEquals(1,usermanager.addAccountRequest("joey"));
		userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 4);
		
		//Provide duplicate request
		assertEquals(0,usermanager.addAccountRequest("joey"));
				
	}
}
