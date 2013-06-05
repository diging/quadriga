package edu.asu.spring.quadriga.service.impl;

import static org.junit.Assert.*;

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
import edu.asu.spring.quadriga.service.IUserManager;

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
		user = userFactory.createUserObject();
		user.setName("John Doe");
		user.setUserName("jdoe");
		user.setEmail("jdoe@lsa.asu.edu");
		List<IQuadrigaRole> userRole = dbConnection.UserRoles("role3,role4");
		user.setQuadrigaRoles(userRole);

		//Setup the database with the proper data in the tables;
		sDatabaseSetup = "delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())";
		String[] sQuery = sDatabaseSetup.split("&");
		for(String singleQuery: sQuery)
		{
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetUserDetails() {
		IUser testUser = usermanager.getUserDetails("jdoe");
		assertEquals(true, user.compareUserObjects(testUser));
	}

	@Test
	public void testGetAllActiveUsers() {
		List<IUser> activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 2);
	}

	@Test
	public void testGetAllInActiveUsers() {
		List<IUser> inactiveUsers = usermanager.getAllInActiveUsers();
		assertEquals(inactiveUsers.size(), 1);
	}

	@Test
	public void testGetUserRequests() {
		List<IUser> userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 3);
	}

	@Test
	public void testActivateUser() {
		List<IUser> activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 2);
		assertEquals(1,usermanager.activateUser("bob", "test"));
		activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 3);
	}
	
	@Test
	public void testDeactivateUser() {
		List<IUser> inactiveUsers = usermanager.getAllInActiveUsers();
		assertEquals(inactiveUsers.size(), 1);
		assertEquals(1,usermanager.deactivateUser("bob", "test"));
		inactiveUsers = usermanager.getAllInActiveUsers();
		assertEquals(inactiveUsers.size(), 1);
	}

	@Test
	public void testApproveUserRequest() {
		List<IUser> userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 3);
		
		List<IUser> activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 2);
		
		assertEquals(1,usermanager.approveUserRequest("dexter", "role3,role4,role5", "test"));
		
		userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 2);
		
		activeUsers = usermanager.getAllActiveUsers();
		assertEquals(activeUsers.size(), 3);
	}

	@Test
	public void testDenyUserRequest() {
		List<IUser> userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 3);
		
		assertEquals(1, usermanager.denyUserRequest("deb", "test"));
		
		userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 2);
	}

	@Test
	public void testAddAccountRequest() {
		List<IUser> userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 3);
		
		assertEquals(1,usermanager.addAccountRequest("joey"));
		
		userRequests = usermanager.getUserRequests();
		assertEquals(userRequests.size(), 4);
	}

}
