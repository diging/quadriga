package edu.asu.spring.quadriga.dao.sql.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

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
public class NetworkManagerDAOTest {

	@Autowired
	private IDBConnectionNetworkManager dbConnect;
	
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
		user.setUserName("jdoe");
		user.setName("John Doe");
		user.setEmail("jdoe@lsa.asu.edu");
		
		//Setup the database with the proper data in the tables;
		sDatabaseSetup = "delete from tbl_conceptcollection&delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())";
		
		/**
		 * Yet to perfect this query.
		 * 
		 * insert into tbl_project values("proj1", "proj desc", "proj1","p1","test","","test",CURTIME(),"test",CURTIME());
		 * insert into tbl_project_workspace values("p1","w1","test",curtime(),"test",curtime());
		 */
	}
	
	@Test
	public void testAddNetworkRequest() throws QuadrigaStorageException {
		dbConnect.addNetworkRequest("n1", user, "");
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
	public void testGetProjectIdForWorkspaceId() {
		fail("Not yet implemented");
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
