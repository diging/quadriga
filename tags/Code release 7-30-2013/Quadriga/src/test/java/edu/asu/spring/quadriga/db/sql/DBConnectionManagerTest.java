package edu.asu.spring.quadriga.db.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class tests the {@link DBConnectionManager}. 
 * 
 * IMPORTANT: This test class will overwrite the data in 
 * 			  tbl_quadriga_user
 * 			  tbl_quadriga_user_denied
 * 			  tbl_quadriga_user_requests
 * 			  tbl_conceptcollections
 * 
 * @author      Kiran Kumar Batna
 * @author 		Ram Kumar Kumaresan
 * 
 *
 */
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionManagerTest {

	@Autowired
	IDBConnectionManager dbConnection;

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	IUser user;
	String sDeactiveRoleDBId;
	String sDatabaseSetup;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
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

		user.setQuadrigaRoles(roles);

		//Find the deactivated role id and create a QuadrigaRole Object
		sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		//Setup the database with the proper data in the tables;
		sDatabaseSetup = "delete from tbl_conceptcollections&delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())";
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Load the required data into the dependent tables
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testSetupTestEnvironment() throws QuadrigaStorageException
	{
		String[] sQuery = sDatabaseSetup.split("&");
		for(String singleQuery: sQuery)
		{
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}

	/**
	 * Test whether the correct user details are fetched from the database.
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testGetUserDetails() throws QuadrigaStorageException {

		testSetupTestEnvironment();

		//Check if correct user details are required
		IUser testUser = dbConnection.getUserDetails("jdoe");
		assertEquals(true, user.compareUserObjects(testUser));

		//Check if no user is returned for wrong userid
		assertNull(dbConnection.getUserDetails("logan"));
	}

	/**
	 * Test whether the correct number of active users are fetched from the database.
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testGetAllActiveUsers() throws QuadrigaStorageException {

		testSetupTestEnvironment();

		//Check the number of active users
		List<IUser> activeUsersList = dbConnection.getAllActiveUsers(sDeactiveRoleDBId);
		assertEquals(activeUsersList.size(), 2);

		//Check if no user is retrieved
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user");
		activeUsersList = dbConnection.getAllActiveUsers(sDeactiveRoleDBId);
		assertEquals(activeUsersList.size(), 0);

	}

	/**
	 * Test whether the correct number of inactive users are fetched from the database.
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testGetAllInactiveUser() throws QuadrigaStorageException {

		testSetupTestEnvironment();

		//Check the number of inactive users
		List<IUser> inactiveUsersList = dbConnection.getAllInActiveUsers(sDeactiveRoleDBId);
		assertEquals(inactiveUsersList.size(), 1);

		//Check if no user is retrieved
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user");
		inactiveUsersList = dbConnection.getAllInActiveUsers(sDeactiveRoleDBId);
		assertEquals(inactiveUsersList.size(), 0);

	}

	/**
	 * Test whether a given account is deactivated and added to the inactive list.
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testDeactivateUser() throws QuadrigaStorageException {

		testSetupTestEnvironment();

		//Check if the user is deactivated
		assertEquals(1,dbConnection.deactivateUser("jdoe", sDeactiveRoleDBId, "test"));
		List<IUser> inactiveUsersList = dbConnection.getAllInActiveUsers(sDeactiveRoleDBId);
		assertEquals(inactiveUsersList.size(), 2);

		//Check if no user is present
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user");
		assertEquals(0,dbConnection.deactivateUser("jdoe", sDeactiveRoleDBId, "test"));
	}

	/**
	 * Test whether a given account is activated and added to the active list.
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testUpdateUserRoles() throws QuadrigaStorageException {

		testSetupTestEnvironment();

		//Find all the roles of the user
		IUser user = null;
		List<IQuadrigaRole> userRole = null;
		IQuadrigaRole quadrigaRole = null;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();

		user = dbConnection.getUserDetails("bob");

		//Remove the deactivated role from user roles
		if(user!=null)
		{
			userRole = user.getQuadrigaRoles();
			for(int i=0;i<userRole.size();i++)
			{				
				if(!userRole.get(i).getDBid().equals(sDeactiveRoleDBId))
				{
					quadrigaRole = rolemanager.getQuadrigaRole(userRole.get(i).getDBid());
					rolesList.add(quadrigaRole);
				}
			}
			user.setQuadrigaRoles(rolesList);
		}

		//Convert the user roles to one string with DBROLEIDs
		//Update the role in the Quadriga Database.
		assertEquals(1, dbConnection.updateUserRoles("bob", user.getQuadrigaRolesDBId(),"test"));

		//Check the number of active users
		List<IUser> activeUsersList = dbConnection.getAllActiveUsers(sDeactiveRoleDBId);
		assertEquals(activeUsersList.size(), 3);

		//Remove all users from the database
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user");
		assertEquals(0, dbConnection.updateUserRoles("bob", user.getQuadrigaRolesDBId(),"test"));
	}

	/**
	 * Test whether a give open request is approved and added to the active user list.
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testApproveUserRequest() throws QuadrigaStorageException {

		testSetupTestEnvironment();

		//Check if approved correctly
		assertEquals(1,dbConnection.approveUserRequest("dexter", "role3", "test"));

		//Check if all user requests are updated
		List<IUser> userRequests = dbConnection.getUserRequests();
		assertEquals(2, userRequests.size());

		//Check the number of active users
		List<IUser> activeUsersList = dbConnection.getAllActiveUsers(sDeactiveRoleDBId);
		assertEquals(activeUsersList.size(), 3);

		//Remove all user requests from the database
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user_requests");
		assertEquals(0, dbConnection.approveUserRequest("dexter", "role3", "test"));
	}

	/**
	 * Test whether a give open request is denied and the user requests are updated.
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testDenyUserRequest() throws QuadrigaStorageException {

		testSetupTestEnvironment();

		assertEquals(1,dbConnection.denyUserRequest("deb", "test"));

		//Check if all user requests are fetched
		List<IUser> userRequests = dbConnection.getUserRequests();
		assertEquals(2, userRequests.size());

		//Remove all user requests from the database
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user_requests");
		assertEquals(0, dbConnection.denyUserRequest("deb", "test"));
	}

	/**
	 * Test whether all the open user requests are fetched.
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testGetUserRequests() throws QuadrigaStorageException {
		
		testSetupTestEnvironment();
		
		//Check if all user requests are fetched
		List<IUser> userRequests = dbConnection.getUserRequests();
		assertEquals(3, userRequests.size());

		//Remove all user requests from the database
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user_requests");
		userRequests = dbConnection.getUserRequests();
		assertEquals(0, userRequests.size());
	}

	/**
	 * Test whether a new account request is added to already existing user requests.
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testAddAccountRequest() throws QuadrigaStorageException {
		
		testSetupTestEnvironment();
		
		IUser testUser = userFactory.createUserObject();
		testUser.setUserName("jack");
		dbConnection.addAccountRequest("jack");

		//Check if all user requests are fetched
		List<IUser> userRequests = dbConnection.getUserRequests();
		assertEquals(4, userRequests.size());
	}

	@Test
	public void testListQuadrigaUserRoles() throws QuadrigaStorageException 
	{
		List<IQuadrigaRole> rolesList;
		List<IQuadrigaRole> testRolesList = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole userRole = null;
		rolesList = dbConnection.listQuadrigaUserRoles("role1,role3");
		//forming the list of quadriga roles
		userRole = quadrigaRoleFactory.createQuadrigaRoleObject();
		userRole.setDBid("role1");
		testRolesList.add(userRole);
		userRole = quadrigaRoleFactory.createQuadrigaRoleObject();
		userRole.setDBid("role3");
		testRolesList.add(userRole);
		assertEquals(testRolesList.get(0),rolesList.get(0));
		assertEquals(testRolesList.get(1),rolesList.get(1));
	}
}
