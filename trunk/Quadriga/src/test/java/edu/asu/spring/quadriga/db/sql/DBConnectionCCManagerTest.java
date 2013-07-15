package edu.asu.spring.quadriga.db.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
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


	@Autowired
	private IConceptCollectionFactory conceptcollectionFactory;


	@Autowired
	private IConceptCollectionManager collectionManager;


	@Autowired
	private IConceptFactory conceptFactory;
	
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
		sDatabaseSetup = "delete from tbl_conceptcollections_items&delete from tbl_conceptcollections&delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())";
	}
	/**
	 * Load the required data into the dependent tables
	 * @author Satya Swaroop Boddu
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testSetupTestEnvironment() throws QuadrigaStorageException
	{
		String[] sQuery = sDatabaseSetup.split("&");
		
			assertEquals(1, dbConnection.setupTestEnvironment(sQuery));
		
	}
	
	@Test
	public void getConceptsOwnedbyUserTest() throws QuadrigaStorageException {
		dbConnection.setupTestEnvironment(sDatabaseSetup.split("&"));
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		dbConnection.addCollection(collection);
		List<IConceptCollection> list = dbConnection.getConceptsOwnedbyUser(user.getUserName());
		assertEquals(collection.getName(),list.get(0).getName());
		testSetupTestEnvironment();
	}
	
	

	@Test
	public void getCollaboratedConceptsofUserTest() {
		fail("unimplemented");
	}
	
	@Test
	public void getCollectionDetailsTest() throws QuadrigaStorageException, QuadrigaAccessException {
		dbConnection.setupTestEnvironment(sDatabaseSetup.split("&"));
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		dbConnection.addCollection(collection);
		List<IConceptCollection> list = dbConnection.getConceptsOwnedbyUser(user.getUserName());
		assertEquals(collection.getName(),list.get(0).getName());
		collection.setId(list.get(0).getId());
		dbConnection.saveItem("lemma", "testid", "red", "hello", collection.getId(),user.getUserName());
		dbConnection.getCollectionDetails(collection, user.getUserName());
		IConcept concept = conceptFactory.createConceptObject();
		concept.setId("testid");
		assertEquals(concept,collection.getItems().get(0));
		testSetupTestEnvironment();
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
		testSetupTestEnvironment();
	}

	/**
	 * Test method for save items
	 * @throws QuadrigaStorageException 
	 * @throws QuadrigaAccessException 
	 *
	 * @throws Exception
	 *
	 * 
	 */
	@Test
	public void testSaveItem() throws QuadrigaStorageException, QuadrigaAccessException
	{
		dbConnection.setupTestEnvironment(sDatabaseSetup.split("&"));
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		dbConnection.addCollection(collection);
		List<IConceptCollection> list = dbConnection.getConceptsOwnedbyUser(user.getUserName());
		assertEquals(collection.getName(),list.get(0).getName());
		collection.setId(list.get(0).getId());
		ConceptpowerReply rep = collectionManager.search("dog", "noun");
		IConcept concept = conceptFactory.createConceptObject();
		concept.setDescription(rep.getConceptEntry().get(0).getDescription());
		concept.setId(rep.getConceptEntry().get(0).getId());
		concept.setLemma(rep.getConceptEntry().get(0).getLemma());
		concept.setPos(rep.getConceptEntry().get(0).getPos());
		
		dbConnection.saveItem(rep.getConceptEntry().get(0).getLemma(), rep.getConceptEntry().get(0).getId(), rep.getConceptEntry().get(0).getPos(), rep.getConceptEntry().get(0).getDescription(), collection.getId(),user.getUserName());
		
		dbConnection.getCollectionDetails(collection,user.getUserName());
		assertEquals(concept.getId(),collection.getItems().get(0).getId());
		testSetupTestEnvironment();
	}
	
	@Test
	public void testDeleteItem() throws QuadrigaStorageException, QuadrigaAccessException {
		dbConnection.setupTestEnvironment(sDatabaseSetup.split("&"));
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		
		dbConnection.addCollection(collection);
		List<IConceptCollection> list = dbConnection.getConceptsOwnedbyUser(user.getUserName());
		assertEquals(collection.getName(),list.get(0).getName());
		collection.setId(list.get(0).getId());
		ConceptpowerReply rep = collectionManager.search("dog", "noun");
		IConcept concept = conceptFactory.createConceptObject();
		concept.setDescription(rep.getConceptEntry().get(0).getDescription());
		concept.setId(rep.getConceptEntry().get(0).getId());
		concept.setLemma(rep.getConceptEntry().get(0).getLemma());
		concept.setPos(rep.getConceptEntry().get(0).getPos());
		
		dbConnection.saveItem(rep.getConceptEntry().get(0).getLemma(), rep.getConceptEntry().get(0).getId(), rep.getConceptEntry().get(0).getPos(), rep.getConceptEntry().get(0).getDescription(), collection.getId(),user.getUserName());
		dbConnection.getCollectionDetails(collection, user.getUserName());
		
		assertEquals(concept.getId(),collection.getItems().get(0).getId());
		dbConnection.deleteItems(concept.getId(), collection.getId(), user.getUserName());
		List<IConceptCollection> clist = dbConnection.getConceptsOwnedbyUser(user.getUserName());
		
		assertEquals(collection.getName(),clist.get(0).getName());
		collection.setId(clist.get(0).getId());
		collection = clist.get(0);
		dbConnection.getCollectionDetails(collection, user.getUserName());
		assertEquals(0,collection.getItems().size());
		testSetupTestEnvironment();
	}
	
	@Test
	public void testUpdate() throws QuadrigaStorageException, QuadrigaAccessException {
		
		dbConnection.setupTestEnvironment(sDatabaseSetup.split("&"));
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		dbConnection.addCollection(collection);
		ConceptpowerReply rep = collectionManager.search("dog", "noun");
		IConcept concept = conceptFactory.createConceptObject();
		concept.setDescription(rep.getConceptEntry().get(0).getDescription());
		concept.setId(rep.getConceptEntry().get(0).getId());
		concept.setLemma(rep.getConceptEntry().get(0).getLemma());
		concept.setPos(rep.getConceptEntry().get(0).getPos());
		collection.getItems().add(concept);
		List<IConceptCollection> list = dbConnection.getConceptsOwnedbyUser(user.getUserName());
		assertEquals(collection.getName(),list.get(0).getName());
		collection.setId(list.get(0).getId());
		dbConnection.saveItem("lemma", rep.getConceptEntry().get(0).getId(), "red", "hello", collection.getId(),user.getUserName());
		
		concept.setLemma("updatedlemma");
		dbConnection.updateItem(concept, collection.getId(),user.getUserName());
		
		collectionManager.getCollectionDetails(collection, user.getUserName());
		assertEquals("updatedlemma",collection.getItems().get(0).getLemma());
		testSetupTestEnvironment();
	}
	
	@Test
	public void testValidateid() throws QuadrigaStorageException
	{
		dbConnection.setupTestEnvironment(sDatabaseSetup.split("&"));
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		String ret = dbConnection.validateId("Collection Test");
		assertNull(ret);
		ret  = dbConnection.addCollection(collection);
		assertNotNull(ret);
		testSetupTestEnvironment();
		
	}
	
	
	
}
