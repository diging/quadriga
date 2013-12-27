/**
 * 
 */
package edu.asu.spring.quadriga.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCCollaboratorManager;
import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.db.sql.DBConnectionDictionaryManagerTest;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
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
	@Qualifier("cCManagerDAO")
	IDBConnectionCCManager dbConnection;
	
	@Autowired
	IDBConnectionCCCollaboratorManager dbCollaboratorConnection;
	
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
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;

	private IUser user,user1; 
	Principal principal;	
	List<ICollaborator> collaboratorList;
	ICollaborator collaborator;
	ICollaborator collaborator1;

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
				
				user1 = userFactory.createUserObject();
				user1.setUserName("bob");
				user1.setName("bob");

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
				
				//setting roles for collaborator
				List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
				ICollaboratorRole collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
				collaboratorRole.setRoleDBid("cc_role1");
				collaboratorRoles.add(collaboratorRole);
				
				collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
				collaboratorRole.setRoleDBid("cc_role2");
				collaboratorRoles.add(collaboratorRole);
				
				//setting collaborator object
			    collaborator = collaboratorFactory.createCollaborator();
				collaborator.setCollaboratorRoles(collaboratorRoles);
				collaborator.setUserObj(user);
				
				//setting roles for collaborator
				List<ICollaboratorRole> collaboratorRoles1 = new ArrayList<ICollaboratorRole>();
				ICollaboratorRole collaboratorRole1 = collaboratorRoleFactory.createCollaboratorRoleObject();
				collaboratorRole1.setRoleDBid("cc_role2");
				collaboratorRoles1.add(collaboratorRole);
				
				//setting collaborator object
				collaborator1 = collaboratorFactory.createCollaborator();
				collaborator1.setCollaboratorRoles(collaboratorRoles1);
				collaborator1.setUserObj(user1);
				
				collaboratorList = new ArrayList<ICollaborator>(); 
				collaboratorList.add(collaborator);
				collaboratorList.add(collaborator1);

				//Setup the database with the proper data in the tables;
				sDatabaseSetup = new String[]{
						"delete from tbl_conceptcollections_collaborator",
						"delete from tbl_conceptcollection_items",
						"delete from tbl_conceptcollection",						
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
				
				
				principal = new Principal() {
					@Override
					public String getName() {
					return "test";
					}					
				};
		
	}
	
	public void getConnection(){
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		//dbConnection.setupTestEnvironment(sDatabaseSetup);
	}
	
	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.conceptcollection.ConceptCollectionManager#getCollectionsOwnedbyUser(java.lang.String)}.
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
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.conceptcollection.ConceptCollectionManager#updateConceptCollection(edu.asu.spring.quadriga.domain.implementation.ConceptCollection)}.
	 */
	@Test
	@Ignore
	public void testUpdateConceptCollection() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.conceptcollection.ConceptCollectionManager#deleteConceptCollection(java.lang.String)}.
	 */
	@Test
	@Ignore
	public void testDeleteConceptCollection() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.conceptcollection.ConceptCollectionManager#getUserCollaborations(java.lang.String)}.
	 */
	@Test
	@Ignore
	public void testGetUserCollaborations() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.conceptcollection.ConceptCollectionManager#getCollectionDetails(edu.asu.spring.quadriga.domain.IConceptCollection)}.
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
		logger.info("---"+user.getUserName());
		collectionManager.addItems("lemma", "testid", "red", "hello", collection.getId(),user.getUserName());
		collectionManager.getCollectionDetails(collection, user.getUserName());
		IConcept concept = conceptFactory.createConceptObject();
		concept.setId("testid");
		assertEquals(concept,collection.getItems().get(0));
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.conceptcollection.ConceptCollectionManager#search(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSearch() {
		ConceptpowerReply rep = collectionManager.search("dog", "noun");
		assertNotNull(rep);
		
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.conceptcollection.ConceptCollectionManager#update(java.lang.String[], edu.asu.spring.quadriga.domain.IConceptCollection)}.
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
		collectionManager.addItems("lemma", rep.getConceptEntry().get(0).getId(), "red", "hello", collection.getId(),user.getUserName());
		collectionManager.update(new String[]{rep.getConceptEntry().get(0).getId()}, collection,user.getUserName());
		collectionManager.getCollectionDetails(collection, user.getUserName());
		assertEquals(concept.getLemma(),collection.getItems().get(0).getLemma());
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.conceptcollection.ConceptCollectionManager#addItems(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
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
		collectionManager.addItems(rep.getConceptEntry().get(0).getLemma(), rep.getConceptEntry().get(0).getId(), rep.getConceptEntry().get(0).getPos(), rep.getConceptEntry().get(0).getDescription(), collection.getId(),user.getUserName());
		collectionManager.getCollectionDetails(collection, user.getUserName());
		assertEquals(concept.getId(),collection.getItems().get(0).getId());
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.ConceptCollectionManager#addConceptCollection(edu.asu.spring.quadriga.domain.IConceptCollection)}.
	 * @throws QuadrigaStorageException 
	 */
	/*@Test
	@Ignore
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
	}*/

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.conceptcollection.ConceptCollectionManager#deleteItem(java.lang.String, java.lang.String)}.
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
		concept.setPos(rep.getConceptEntry().
				get(0).getPos());
		
		collectionManager.addItems(rep.getConceptEntry().get(0).getLemma(), rep.getConceptEntry().get(0).getId(), rep.getConceptEntry().get(0).getPos(), rep.getConceptEntry().get(0).getDescription(), collection.getId(), user.getUserName());
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
	
	@Test
	public void showNonCollaboratingUsersTest() throws QuadrigaStorageException{
	    dbConnection.setupTestEnvironment(sDatabaseSetup);
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setName("testcollection1");
		collection.setDescription("abcde");
		IUser owner = userFactory.createUserObject();
		owner.setUserName("test");
		collection.setOwner(owner);
		dbConnection.addCollection(collection);
		List<IConceptCollection> ccList= dbConnection.getConceptsOwnedbyUser(owner.getUserName());
		Iterator<IConceptCollection> I = ccList.iterator();
		logger.info("---i.hasnext "+I.hasNext());
		
		List<IConceptCollection> list =  collectionManager.getCollectionsOwnedbyUser(owner.getUserName());
		collection.setId(list.get(0).getId());
		dbCollaboratorConnection.addCollaboratorRequest(collaborator, collection.getId(), principal.getName());
		
		List<IUser> collaborators = collectionManager.showNonCollaboratingUsers(collection.getId());
		assertEquals(1, collaborators.size());
		
		dbCollaboratorConnection.deleteCollaboratorRequest(collaborator.getUserObj().getUserName(), collection.getId());
	}
	
	
	@Test
	public void  showCollaboratingUsersTest() throws QuadrigaStorageException{
		
		dbConnection.setupTestEnvironment(sDatabaseSetup);
		
		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setCollaborators(collaboratorList);
		collection.setName("mytestcollection");
		collection.setDescription("description");
		IUser owner = userFactory.createUserObject();
		owner.setUserName("test");
		collection.setOwner(owner);		
		dbConnection.addCollection(collection);
		
		List<IConceptCollection>list = collectionManager.getCollectionsOwnedbyUser(owner.getUserName());
		collection.setId(list.get(0).getId());
		dbCollaboratorConnection.addCollaboratorRequest(collaborator, collection.getId(), principal.getName());
		
		List<ICollaborator> collabUsers = collectionManager.showCollaboratingUsers(collection.getId());
		assertEquals(1, collabUsers.size());
		
		dbCollaboratorConnection.deleteCollaboratorRequest(collaborator.getUserObj().getUserName(), collection.getId());	
	}
	
	@Test
	public void deleteCollaboratorsTest() throws QuadrigaStorageException{
		
		dbConnection.setupTestEnvironment(sDatabaseSetup);

		IConceptCollection collection = conceptcollectionFactory.createConceptCollectionObject();
		collection.setCollaborators(collaboratorList);
		collection.setName("mytestcollection");
		collection.setDescription("description");
		IUser owner = userFactory.createUserObject();
		owner.setUserName("test");
		collection.setOwner(owner);		
		dbConnection.addCollection(collection);
		
		List<IConceptCollection>list = collectionManager.getCollectionsOwnedbyUser(owner.getUserName());
		collection.setId(list.get(0).getId());
		dbCollaboratorConnection.addCollaboratorRequest(collaborator, collection.getId(), principal.getName());
		
		List<ICollaborator> collabUsers = collectionManager.showCollaboratingUsers(collection.getId());
		assertEquals(1, collabUsers.size());
		
		dbCollaboratorConnection.deleteCollaboratorRequest(collaborator.getUserObj().getUserName(), collection.getId());	

		List<ICollaborator> collabUsers1 = collectionManager.showCollaboratingUsers(collection.getId());
		assertEquals(0, collabUsers1.size());	
	}
	

}
