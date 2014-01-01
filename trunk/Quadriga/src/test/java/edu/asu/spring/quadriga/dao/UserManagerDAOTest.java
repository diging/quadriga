package edu.asu.spring.quadriga.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@TransactionConfiguration( defaultRollback = false )
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserManagerDAOTest {
	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	@Autowired
	private IDBConnectionManager dbConnection;

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private IUser user;
	private String sDeactiveRoleDBId;
	private String sDatabaseSetup;

	@Before
	public void setUp() throws Exception {
		user = userFactory.createUserObject();
		user.setUserName("jdoe");
		user.setName("John Doe");
		user.setEmail("jdoe@lsa.asu.edu");

		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role3");
		role.setId(RoleNames.ROLE_QUADRIGA_ADMIN);
		role.setName("Quadriga Admin");
		role.setDescription("Can manage users and change Quadriga settings");
		roles.add(role);		

		role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role4");
		role.setId(RoleNames.ROLE_QUADRIGA_USER_STANDARD);
		role.setName("Quadriga Standard User");
		role.setDescription("A Standard Quadriga User can create projects, concept collections, and dictionaries, collaborate on projects, and can do any action related to projects (according to the project permissions).");		
		roles.add(role);

		user.setQuadrigaRoles(roles);

		//Find the deactivated role id and create a QuadrigaRole Object
		sDeactiveRoleDBId = rolemanager.getQuadrigaRoleDBId(RoleNames.ROLE_QUADRIGA_DEACTIVATED);

		//Setup the database with the proper data in the tables;
		sDatabaseSetup = "delete from tbl_conceptcollection&delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())";
	}

	public void testSetupTestEnvironment() throws QuadrigaStorageException
	{
		String[] sQuery = sDatabaseSetup.split("&");
		for(String singleQuery: sQuery)
		{
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}

	@Test
	public void testGetUserDetails() throws QuadrigaStorageException {

		testSetupTestEnvironment();

		//Check if correct user details are required
		IUser testUser = dbConnection.getUserDetails("jdoe");
		assertEquals(true, user.equals(testUser));

		//Check if no user is returned for wrong userid
		assertNull(dbConnection.getUserDetails("logan"));

		//Check if no user is returned for wrong userid
		assertNull(dbConnection.getUserDetails(null));
	}

	@Test
	public void testGetUsers() throws QuadrigaStorageException {
		testSetupTestEnvironment();

		List<IUser> users = dbConnection.getUsers("role1");
		assertEquals(1, users.size());

		users = dbConnection.getUsers(null);
		assertEquals(0, users.size());
	}

	@Test
	public void testAddAccountRequest() throws QuadrigaStorageException {
		testSetupTestEnvironment();

		IUser testUser = userFactory.createUserObject();
		testUser.setUserName("jack");
		assertEquals(SUCCESS, dbConnection.addAccountRequest("jack"));

		//Check if all user requests are fetched
		List<IUser> userRequests = dbConnection.getUserRequests();
		assertEquals(4, userRequests.size());

		//Check for null username
		assertEquals(FAILURE, dbConnection.addAccountRequest(null));

	}

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

	@Test
	public void testGetUsersNotInRole() throws QuadrigaStorageException {
		testSetupTestEnvironment();

		//Check for proper role
		List<IUser> users = dbConnection.getUsersNotInRole("role3");
		assertEquals(1, users.size());

		//Check for null role
		users = dbConnection.getUsersNotInRole(null);
		assertEquals(3, users.size());

		//Check for role id not in use - Should fetch all the users
		users = dbConnection.getUsersNotInRole("role100");
		assertEquals(3, users.size());

		//Check for random string
		users = dbConnection.getUsersNotInRole("role100");
		assertEquals(3, users.size());
	}

	@Test
	public void testDeactivateUser() throws QuadrigaStorageException {
		testSetupTestEnvironment();

		//Check if the user is deactivated
		assertEquals(1,dbConnection.deactivateUser("jdoe", sDeactiveRoleDBId, "test"));
		List<IUser> inactiveUsersList = dbConnection.getUsers(sDeactiveRoleDBId);
		assertEquals(inactiveUsersList.size(), 2);

		//Check if no user is present
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user");
		assertEquals(0,dbConnection.deactivateUser("jdoe", sDeactiveRoleDBId, "test"));
	}

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
		List<IUser> activeUsersList = dbConnection.getUsersNotInRole(sDeactiveRoleDBId);
		assertEquals(activeUsersList.size(), 3);

		//Remove all users from the database
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user");
		assertEquals(0, dbConnection.updateUserRoles("bob", user.getQuadrigaRolesDBId(),"test"));
	}

	@Test
	public void testApproveUserRequest() throws QuadrigaStorageException {
		testSetupTestEnvironment();

		//Check if approved correctly
		assertEquals(1,dbConnection.approveUserRequest("dexter", "role3", "test"));

		//Check if all user requests are updated
		List<IUser> userRequests = dbConnection.getUserRequests();
		assertEquals(2, userRequests.size());

		//Check the number of active users
		List<IUser> activeUsersList = dbConnection.getUsersNotInRole(sDeactiveRoleDBId);
		assertEquals(activeUsersList.size(), 3);

		//Remove all user requests from the database
		dbConnection.setupTestEnvironment("delete from tbl_quadriga_user_requests");
		assertEquals(0, dbConnection.approveUserRequest("dexter", "role3", "test"));
	}

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

	@Test
	public void testDeleteUser() throws QuadrigaStorageException {
		testSetupTestEnvironment();

		//Deactivate a user and then try to delete
		assertEquals(SUCCESS, dbConnection.deactivateUser("jdoe", sDeactiveRoleDBId, "test"));
		assertEquals(SUCCESS, dbConnection.deleteUser("jdoe", sDeactiveRoleDBId));

		//Try to Delte an invalid userid
		assertEquals(FAILURE, dbConnection.deleteUser("jdoe123", sDeactiveRoleDBId));

		//Try to Delete an active user
		assertEquals(FAILURE, dbConnection.deleteUser("jdoe", sDeactiveRoleDBId));
	}

}
