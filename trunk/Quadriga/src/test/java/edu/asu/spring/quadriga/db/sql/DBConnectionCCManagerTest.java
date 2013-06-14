package edu.asu.spring.quadriga.db.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp.DelegatingConnection;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class tests the {@link DBConnectionCCManagerTest}. 
 * 
 * IMPORTANT: This test class will overwrite the data in 
 * 			  tbl_conceptcollections
 * 
 * @author    satyaswaroop boddu
 * 
 *
 */
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionCCManagerTest {

	@Autowired
	IDBConnectionCCManager dbConnection;
	
	
	
	@Autowired
	private IConceptCollectionFactory collectionFactory;
	
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
		sDatabaseSetup = "delete from tbl_conceptcollections_items&delete from tbl_conceptcollections&delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_conceptcollections(collectionname,description,collectionowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES ('test','desc','jdoe',0,'jdoe',NOW(),'jdoe',NOW())&INSERT INTO tbl_conceptcollections_items(id, item, lemma, pos, description,updateddate,createddate) VALUES (1, 'test', 'testlemma', 'testpos', 'testdesc',NOW(),NOW())";
	}
	/**
	 * Load the required data into the dependent tables
	 * @author Satya Swaroop Boddu
	 */
	@Test
	public void testSetupTestEnvironment()
	{
		String[] sQuery = sDatabaseSetup.split("&");
		for(String singleQuery: sQuery)
		{
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}
	
	@Test
	public void getConceptsOwnedbyUserTest() {
		testSetupTestEnvironment();
		IConceptCollection collection = collectionFactory.createConceptCollectionObject();
		collection.setName("test");
		
		List<IConceptCollection> list = dbConnection.getConceptsOwnedbyUser("jdoe");
		assertEquals(true,list.get(0).equals(collection));
	}
	
	

	@Test
	public void getCollaboratedConceptsofUserTest() {
		fail("unimplemented");
	}
	
	@Test
	public void getCollectionDetailsTest() {
		fail("unimplemented");
	}
	
	
	/**
	 * Run the String addCollection(IConceptCollection) method test.
	 *
	 * @throws Exception
	 *
	 * 
	 */
	@Test
	public void testAddCollection()
		throws Exception {
		
		IConceptCollection con = new ConceptCollection();
		con.setName("name2");
		con.setOwner(user);
		con.setDescription("sdads");
		String result = dbConnection.addCollection(con);

		
		assertEquals("", result);
	}
}
