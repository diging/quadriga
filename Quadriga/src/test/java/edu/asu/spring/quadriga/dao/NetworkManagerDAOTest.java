package edu.asu.spring.quadriga.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;

@TransactionConfiguration( defaultRollback = false )
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NetworkManagerDAOTest {

	@Autowired
	private IDBConnectionManager dbConnection;
	
	@Autowired
	private IDBConnectionNetworkManager dbConnectNetwork;
	
	private IUser user;
	private String sDatabaseSetup;
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;
	@Before
	public void setUp() throws Exception {
		user = userFactory.createUserObject();
		user.setUserName("test");
		user.setName("Tet User");
		user.setEmail("test@lsa.asu.edu");
		
		//Setup the database with the proper data in the tables;
		sDatabaseSetup = "delete from tbl_conceptcollection&delete from tbl_quadriga_user&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())";
		
		//Create a new project, workspace and link them together.
		sDatabaseSetup += "&delete from tbl_project_workspace&delete from tbl_workspace&delete from tbl_project&insert into tbl_project values(\"proj1\", \"proj desc\", \"proj1\",\"f651d817-8c47-4ec6-8f7c-0df5dddc9841\",\"jdoe\",\"ACCESSIBLE\",\"jdoe\",CURTIME(),\"jdoe\",CURTIME())&insert into tbl_workspace values(\"workspace1\",\"workspace1\",\"ad905f5f-14bf-4405-a612-f1bb2a3077a7\",\"jdoe\",0,0,\"jdoe\",CURTIME(),\"jdoe\",CURTIME())&insert into tbl_project_workspace values(\"f651d817-8c47-4ec6-8f7c-0df5dddc9841\",\"ad905f5f-14bf-4405-a612-f1bb2a3077a7\",\"test\",curtime(),\"test\",curtime())";
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
	public void testAddNetworkRequestValid() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		
		assertNotNull(dbConnectNetwork.addNetworkRequest("n1", user, "ad905f5f-14bf-4405-a612-f1bb2a3077a7"));
	}
	
	@Test(expected=QuadrigaStorageException.class)
	public void testAddNetworkRequestInValidUser() throws QuadrigaStorageException {
		dbConnectNetwork.addNetworkRequest("n1", null, "ad905f5f-14bf-4405-a612-f1bb2a3077a7");		
	}
	
	@Test(expected=QuadrigaStorageException.class)
	public void testAddNetworkRequestInValidWorkspace() throws QuadrigaStorageException {
		dbConnectNetwork.addNetworkRequest("n1", user, null);		
	}
	
	@Test(expected=QuadrigaStorageException.class)
	public void testAddNetworkRequestInValidNetworkName() throws QuadrigaStorageException {
		dbConnectNetwork.addNetworkRequest(null, user, "ad905f5f-14bf-4405-a612-f1bb2a3077a7");		
	}

	@Test
	public void testAddNetworkStatement() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNetworkStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNetworkList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProjectIdForWorkspaceId() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		
		//Check for a valid workspace
		assertEquals("f651d817-8c47-4ec6-8f7c-0df5dddc9841", dbConnectNetwork.getProjectIdForWorkspaceId("ad905f5f-14bf-4405-a612-f1bb2a3077a7"));
		
		//Check for a invalid workspace
		assertNull(dbConnectNetwork.getProjectIdForWorkspaceId("invalid"));
		
		//Check for null handling
		assertNull(dbConnectNetwork.getProjectIdForWorkspaceId(null));
	}

	@Test
	public void testGetNetworksForProjectId() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasNetworkName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNetworkTopNodes() {
		fail("Not yet implemented");
	}

	@Test
	public void testArchiveNetworkStatement() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllNetworkNodes() {
		fail("Not yet implemented");
	}

	@Test
	public void testArchiveNetwork() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNetworkOldVersionDetails() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNetworkOldVersionTopNodes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNetworkDetails() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEditorNetworkList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAssignNetworkToUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAssignedNetworkListOfOtherEditors() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetfinishedNetworkListOfOtherEditors() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAssignNetworkOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetApprovedNetworkOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRejectedNetworkOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateNetworkStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAssignedNetworkStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAnnotationToNetwork() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAnnotation() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAnnotationToNetwork() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateNetworkName() {
		fail("Not yet implemented");
	}

}
