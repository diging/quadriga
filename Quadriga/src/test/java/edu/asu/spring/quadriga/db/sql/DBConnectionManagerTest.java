package edu.asu.spring.quadriga.db.sql;

import static org.junit.Assert.*;

import java.sql.SQLException;
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
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.QuadrigaRole;
import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.service.impl.QuadrigaRoleManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class tests the {@link DBConnectionManager}. The test database environment should be setup properly before this test class can be run.
 * 
 * @author      Kiran Kumar Batna
 * @author 		Ram Kumar Kumaresan
 * 
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

	@Autowired
	private QuadrigaRoleManager rolemanager;

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
		IQuadrigaRole role = new QuadrigaRole();
		role.setDBid("role3");
		roles.add(role);
		role = new QuadrigaRole();
		roles.add(role);

		user.setQuadrigaRoles(roles);

		//Find the deactivated role id and create a QuadrigaRole Object
		sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);
		
		//Setup the database with the proper data in the tables;
		sDatabaseSetup = "delete from tbl_quadriga_user_denied;delete from tbl_quadriga_user;delete from tbl_quadriga_user_requests;INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE());";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetupTestEnvironment()
	{
		assertEquals(1, dbConnection.setupTestEnvironment(sDatabaseSetup));
	}
	
	/**
	 * Test whether the correct user details are fetched from the database.
	 */
	@Test
	public void testGetUserDetails() {

		IUser testUser = dbConnection.getUserDetails("jdoe");

		//Check username
		assertEquals(user.getUserName(), testUser.getUserName());

		//Check name
		assertEquals(user.getName(),testUser.getName());

	}

	/**
	 * Test whether the correct number of active users are fetched from the database.
	 */
	@Test
	public void testGetAllActiveUsers() {

		//Check the number of active users
		List<IUser> activeUsersList = dbConnection.getAllActiveUsers(sDeactiveRoleDBId);
		assertEquals(activeUsersList.size(), 2);
	}

	/**
	 * Test whether the correct number of inactive users are fetched from the database.
	 */
	@Test
	public void testGetAllInactiveUser() {

		//Check the number of inactive users
		List<IUser> inactiveUsersList = dbConnection.getAllInActiveUsers(sDeactiveRoleDBId);
		assertEquals(inactiveUsersList.size(), 1);
	}

	/**
	 * Test whether a given account is deactivated and added to the inactive list.
	 */
	@Test
	public void testDeactivateUser() {

		assertEquals(1,dbConnection.deactivateUser("jdoe", sDeactiveRoleDBId, "test"));
		List<IUser> inactiveUsersList = dbConnection.getAllInActiveUsers(sDeactiveRoleDBId);
		assertEquals(inactiveUsersList.size(), 2);		
	}

	/**
	 * Test whether a given account is activated and added to the active list.
	 */
	@Test
	public void testUpdateUserRoles() {


		//Find all the roles of the user
		IUser user = null;
		List<IQuadrigaRole> userRole = null;
		IQuadrigaRole quadrigaRole = null;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();

		user = dbConnection.getUserDetails("jdoe");

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
		assertEquals(1, dbConnection.updateUserRoles("jdoe", user.getQuadrigaRolesDBId(),"test"));

		//Check the number of active users
		List<IUser> activeUsersList = dbConnection.getAllActiveUsers(sDeactiveRoleDBId);
		assertEquals(activeUsersList.size(), 2);
	}

	/**
	 * Test whether a give open request is approved and added to the active user list.
	 */
	@Test
	public void testApproveUserRequest() {
		assertEquals(1,dbConnection.approveUserRequest("dexter", "role3", "TestClass"));

		//Check if all user requests are updated
		List<IUser> userRequests = dbConnection.getUserRequests();
		assertEquals(2, userRequests.size());

		//Check the number of active users
		List<IUser> activeUsersList = dbConnection.getAllActiveUsers(sDeactiveRoleDBId);
		assertEquals(activeUsersList.size(), 3);
	}

	/**
	 * Test whether a give open request is denied and the user requests are updated.
	 */
	@Test
	public void testDenyUserRequest() {
		assertEquals(1,dbConnection.denyUserRequest("deb", "TestClass"));

		//Check if all user requests are fetched
		List<IUser> userRequests = dbConnection.getUserRequests();
		assertEquals(1, userRequests.size());
	}

	/**
	 * Test whether all the open user requests are fetched.
	 */
	@Test
	public void testGetUserRequests() {
		//Check if all user requests are fetched
		List<IUser> userRequests = dbConnection.getUserRequests();
		assertEquals(1, userRequests.size());
	}

	/**
	 * Test whether a new account request is added to already existing user requests.
	 */
	@Test
	public void testAddAccountRequest() {
		IUser testUser = userFactory.createUserObject();
		testUser.setUserName("jack");
		dbConnection.addAccountRequest("jack");

		//Check if all user requests are fetched
		List<IUser> userRequests = dbConnection.getUserRequests();
		assertEquals(2, userRequests.size());
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
