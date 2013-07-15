/**
 * 
 */
package edu.asu.spring.quadriga.service.impl;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.IDBConnectionCCManager;
import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.db.sql.DBConnectionDictionaryManagerTest;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * @author satyaswaroop
 *
 */

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)

public class ConceptCollectionManagerTest {

	
	@Autowired
	IDBConnectionCCManager dbConnection;
	
	@Autowired
	IConceptCollectionManager collectionManager;

	private Connection connection;

	@Autowired
	private DataSource dataSource;

	String sDatabaseSetup [];

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionDictionaryManagerTest.class);

	@Autowired
	private IConceptCollectionFactory conceptcollectionFactory;

	@Autowired
	private IConceptFactory conceptFactory;

	private IUser user;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
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
				sDatabaseSetup = new String[]{
						"delete from tbl_conceptcollections_items",
						"delete from tbl_conceptcollections",						
						"delete from tbl_quadriga_user",
						"delete from tbl_quadriga_user_requests",
						"delete from tbl_quadriga_user_denied",
						"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
						"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
						"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
						"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
						"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
						"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
						
				};
		
	}
	

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		dbConnection.setupTestEnvironment(sDatabaseSetup);
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#getCollectionsOwnedbyUser(java.lang.String)}.
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testGetCollectionsOwnedbyUser() throws QuadrigaStorageException {
		dbConnection.setupTestEnvironment(sDatabaseSetup);
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		collectionManager.addConceptCollection(collection);
		List<IConceptCollection> list = collectionManager.getCollectionsOwnedbyUser(user.getUserName());
		assertEquals(collection.getName(),list.get(0).getName());
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#updateConceptCollection(edu.asu.spring.quadriga.domain.implementation.ConceptCollection)}.
	 */
	@Test
	public void testUpdateConceptCollection() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#deleteConceptCollection(java.lang.String)}.
	 */
	@Test
	public void testDeleteConceptCollection() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#getUserCollaborations(java.lang.String)}.
	 */
	@Test
	public void testGetUserCollaborations() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#getCollectionDetails(edu.asu.spring.quadriga.domain.IConceptCollection)}.
	 * @throws QuadrigaStorageException 
	 * @throws QuadrigaAccessException 
	 */
	@Test
	public void testGetCollectionDetails() throws QuadrigaStorageException, QuadrigaAccessException {
		dbConnection.setupTestEnvironment(sDatabaseSetup);
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		collectionManager.addConceptCollection(collection);
		List<IConceptCollection> list = collectionManager.getCollectionsOwnedbyUser(user.getUserName());
		assertEquals(collection.getName(),list.get(0).getName());
		collection.setId(list.get(0).getId());
		collectionManager.addItems("lemma", "testid", "red", "hello", collection.getId(),user.getName());
		collectionManager.getCollectionDetails(collection, user.getUserName());
		IConcept concept = conceptFactory.createConceptObject();
		concept.setId("testid");
		assertEquals(concept,collection.getItems().get(0));
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#search(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSearch() {
		ConceptpowerReply rep = collectionManager.search("dog", "noun");
		assertNotNull(rep);
		
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#update(java.lang.String[], edu.asu.spring.quadriga.domain.IConceptCollection)}.
	 * @throws QuadrigaStorageException 
	 * @throws QuadrigaAccessException 
	 */
	@Test
	public void testUpdate() throws QuadrigaStorageException, QuadrigaAccessException {
		dbConnection.setupTestEnvironment(sDatabaseSetup);
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		collectionManager.addConceptCollection(collection);
		List<IConceptCollection> list = collectionManager.getCollectionsOwnedbyUser(user.getUserName());
		assertEquals(collection.getName(),list.get(0).getName());
		collection.setId(list.get(0).getId());
		ConceptpowerReply rep = collectionManager.search("dog", "noun");
		IConcept concept = conceptFactory.createConceptObject();
		concept.setDescription(rep.getConceptEntry().get(0).getDescription());
		concept.setId(rep.getConceptEntry().get(0).getId());
		concept.setLemma(rep.getConceptEntry().get(0).getLemma());
		concept.setPos(rep.getConceptEntry().get(0).getPos());
		collection.getItems().add(concept);
		collectionManager.addItems("lemma", rep.getConceptEntry().get(0).getId(), "red", "hello", collection.getId(),user.getName());
		collectionManager.update(new String[]{rep.getConceptEntry().get(0).getId()}, collection,user.getUserName());
		collectionManager.getCollectionDetails(collection, user.getUserName());
		assertEquals(concept.getLemma(),collection.getItems().get(0).getLemma());
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#addItems(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws QuadrigaStorageException 
	 * @throws QuadrigaAccessException 
	 */
	@Test
	public void testAddItems() throws QuadrigaStorageException, QuadrigaAccessException {
		dbConnection.setupTestEnvironment(sDatabaseSetup);
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		collectionManager.addConceptCollection(collection);
		List<IConceptCollection> list = collectionManager.getCollectionsOwnedbyUser(user.getUserName());
		assertEquals(collection.getName(),list.get(0).getName());
		collection.setId(list.get(0).getId());
		ConceptpowerReply rep = collectionManager.search("dog", "noun");
		IConcept concept = conceptFactory.createConceptObject();
		concept.setDescription(rep.getConceptEntry().get(0).getDescription());
		concept.setId(rep.getConceptEntry().get(0).getId());
		concept.setLemma(rep.getConceptEntry().get(0).getLemma());
		concept.setPos(rep.getConceptEntry().get(0).getPos());
		collectionManager.addItems(rep.getConceptEntry().get(0).getLemma(), rep.getConceptEntry().get(0).getId(), rep.getConceptEntry().get(0).getPos(), rep.getConceptEntry().get(0).getDescription(), collection.getId(),user.getName());
		collectionManager.getCollectionDetails(collection, user.getUserName());
		assertEquals(concept.getId(),collection.getItems().get(0).getId());
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#addConceptCollection(edu.asu.spring.quadriga.domain.IConceptCollection)}.
	 * @throws QuadrigaStorageException 
	 */
	@Test
	public void testAddConceptCollection() throws QuadrigaStorageException {
		dbConnection.setupTestEnvironment(sDatabaseSetup);
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		collectionManager.addConceptCollection(collection);
		
		List<IConceptCollection> list =  collectionManager.getCollectionsOwnedbyUser(user.getUserName());
		
		assertEquals(collection.getName(),list.get(0).getName());
		collection.setId(list.get(0).getId());
		assertEquals(collection,list.get(0));
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#deleteItem(java.lang.String, java.lang.String)}.
	 * @throws QuadrigaStorageException 
	 * @throws QuadrigaAccessException 
	 */
	@Test
	public void testDeleteItem() throws QuadrigaStorageException, QuadrigaAccessException {
		dbConnection.setupTestEnvironment(sDatabaseSetup);
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setDescription("Hello This is a test");
		collection.setName("Collection Test");
		collection.setOwner(user);
		collectionManager.addConceptCollection(collection);
		List<IConceptCollection> list = collectionManager.getCollectionsOwnedbyUser(user.getUserName());
		assertEquals(collection.getName(),list.get(0).getName());
		collection.setId(list.get(0).getId());
		ConceptpowerReply rep = collectionManager.search("dog", "noun");
		IConcept concept = conceptFactory.createConceptObject();
		concept.setDescription(rep.getConceptEntry().get(0).getDescription());
		concept.setId(rep.getConceptEntry().get(0).getId());
		concept.setLemma(rep.getConceptEntry().get(0).getLemma());
		concept.setPos(rep.getConceptEntry().get(0).getPos());
		
		collectionManager.addItems(rep.getConceptEntry().get(0).getLemma(), rep.getConceptEntry().get(0).getId(), rep.getConceptEntry().get(0).getPos(), rep.getConceptEntry().get(0).getDescription(), collection.getId(), user.getName());
		collectionManager.getCollectionDetails(collection, user.getUserName());
		
		assertEquals(concept.getId(),collection.getItems().get(0).getId());
		collectionManager.deleteItem(concept.getId(), collection.getId(),user.getUserName());
		List<IConceptCollection> clist = collectionManager.getCollectionsOwnedbyUser(user.getUserName());
		
		assertEquals(collection.getName(),clist.get(0).getName());
		collection.setId(clist.get(0).getId());
		collection = clist.get(0);
		collectionManager.getCollectionDetails(collection, user.getUserName());
		assertEquals(0,collection.getItems().size());
		
	}

}
