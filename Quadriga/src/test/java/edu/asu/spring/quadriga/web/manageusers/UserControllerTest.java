package edu.asu.spring.quadriga.web.manageusers;

import static org.junit.Assert.assertEquals;

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
import org.springframework.security.core.CredentialsContainer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
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
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

	UserController userContoller;

	@Autowired
	IDBConnectionManager dbConnection;
	String sDatabaseSetup;
	
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
				return "test";
			}
		};
		authentication = new UsernamePasswordAuthenticationToken(principal, credentials);
		//Setup the database with the proper data in the tables;
				sDatabaseSetup = "delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())";
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
	 * This method tests if the appropriate paths are returned. It also checks if the correct
	 * number of users are returned for cases where the status are active, inactive and open requests.
	 * @throws QuadrigaStorageException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	@DirtiesContext
	public void testManageUsers() throws QuadrigaStorageException {

		testSetupTestEnvironment();
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
		testSetupTestEnvironment();
		
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
		testSetupTestEnvironment();
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
		testSetupTestEnvironment();
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
		testSetupTestEnvironment();
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
		testSetupTestEnvironment();
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
		testSetupTestEnvironment();
		//Check the return value
		assertEquals(userContoller.activateUser("jdoe",model, principal),"redirect:/auth/users/manage");
	}

}
