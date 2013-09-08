/**
 * 
 */
package edu.asu.spring.quadriga.service.impl.workbench;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
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

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.impl.conceptcollection.ConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.sevice.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * @author Lohith Dwaraka
 *
 */
@ContextConfiguration(locations = {
		"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectConceptCollectionManagerTest {

	@Autowired
	private IProjectConceptCollectionManager projectConceptCollectionManager;

	@Autowired
	IDBConnectionDictionaryManager dbConnection;
	
	@Autowired
	IDBConnectionCCManager dbConnect;

	@Autowired
	private IDictionaryManager dictionaryManager;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private IConceptCollectionManager conceptCollectionManager;
	
	@Autowired
	private IModifyProjectManager modifyProjectManager;
	
	private Connection connection;

	@Autowired
	private IRetrieveProjectManager retrieveProjectManager;

	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;
	
	private IUser user;
	
	String sDatabaseSetup[];

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private static final Logger logger = LoggerFactory
			.getLogger(ProjectConceptCollectionManagerTest.class);
	
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
		// Setup a user object to compare with the object produced from
				// usermanager
				try {
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
					for (int i = 0; i < roles.size(); i++) {
						quadrigaRole = rolemanager.getQuadrigaRole(roles.get(i)
								.getDBid());

						// If user account is deactivated remove other roles
						if (quadrigaRole.getId().equals(
								RoleNames.ROLE_QUADRIGA_DEACTIVATED)) {
							rolesList.clear();
						}
						rolesList.add(quadrigaRole);
					}
					user.setQuadrigaRoles(rolesList);

					// Setup the database with the proper data in the tables;
					sDatabaseSetup = new String[] {
							"delete from tbl_quadriga_user_denied",
							"delete from tbl_quadriga_user",
							"delete from tbl_quadriga_user_requests",
							"delete from tbl_project_conceptcollection",
							"delete from tbl_project",
							"delete from tbl_conceptcollections_items",
							"delete from tbl_conceptcollections",
							"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
							"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
							"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
							"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
							"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
							"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())", };

					
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetupTestEnvironment() {
		for (String singleQuery : sDatabaseSetup) {
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}
	public void getConnection() {
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getCCID(String name) {
		getConnection();
		String id = null;
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("select id from tbl_conceptcollections where collectionname='"
					+ name + "'");
			ResultSet rs = stmt.getResultSet();
			if (rs != null) {
				while (rs.next()) {
					id = rs.getString(1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.workbench.ProjectConceptCollectionManager#addProjectConceptCollection(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddProjectConceptCollection() {
		testSetupTestEnvironment();
		{
			dbConnection
					.setupTestEnvironment("INSERT  INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES('projDict','description','unix','1','jdoe','ACCESSIBLE','jdoe',NOW(),'jdoe',NOW());");
			IProject project1 = null;
			try {
				project1 = retrieveProjectManager.getProjectDetails("1");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(project1.getDescription().equals("description"), true);
			assertEquals(project1.getName().equals("projDict"), true);
			assertEquals(project1.getUnixName().equals("unix"), true);
		}
		{
			IConceptCollection conceptCollection = conceptCollectionFactory.createConceptCollectionObject(); 
			conceptCollection.setName("Test CC");
			conceptCollection.setDescription("description");
			conceptCollection.setOwner(user);
			String msg = "";
			try {
				msg = conceptCollectionManager.addConceptCollection(conceptCollection);
			} catch (QuadrigaStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting dictionary for user :"
						+ user.getUserName());
				List<IConceptCollection> conceptCollectionList = null;
				try {
					conceptCollectionList = dbConnect.getConceptsOwnedbyUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Iterator<IConceptCollection> I = conceptCollectionList.iterator();
				String name = null;
				String desc = null;
				IUser userTest = null;

				while (I.hasNext()) {
					IConceptCollection conceptCollectionTest = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollectionTest = I.next();
					assertEquals((conceptCollectionTest != null), true);
					if (conceptCollectionTest != null) {
						name = conceptCollectionTest.getName();
						desc = conceptCollectionTest.getDescription();
						userTest = conceptCollectionTest.getOwner();
					}
				}

				assertEquals(name.equals("Test CC"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("testAddProjectConceptCollection: Create concept collection Failed ; message :"
						+ msg);
				fail("testAddProjectConceptCollection: Create concept collection Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				projectConceptCollectionManager.addProjectConceptCollection("1",
						getCCID("Test CC"), "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<IConceptCollection> CCList = null;
			try {
				CCList = projectConceptCollectionManager.listProjectConceptCollection("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<IConceptCollection> I = CCList.iterator();
			assertEquals(CCList.size() > 0, true);
			while (I.hasNext()) {
				IConceptCollection cc = I.next();
				logger.info("Verifying the dictionary addition to project");
				assertEquals(cc.getName().equals("Test CC"), true);
			}
		}
		dbConnection.setupTestEnvironment("delete from tbl_project_conceptcollection");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		dbConnection.setupTestEnvironment("delete from tbl_conceptcollections");
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.workbench.ProjectConceptCollectionManager#listProjectConceptCollection(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testListProjectConceptCollection() {
		testSetupTestEnvironment();
		{
			dbConnection
					.setupTestEnvironment("INSERT  INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES('projDict','description','unix','1','jdoe','ACCESSIBLE','jdoe',NOW(),'jdoe',NOW());");
			IProject project1 = null;
			try {
				project1 = retrieveProjectManager.getProjectDetails("1");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(project1.getDescription().equals("description"), true);
			assertEquals(project1.getName().equals("projDict"), true);
			assertEquals(project1.getUnixName().equals("unix"), true);
		}
		{
			IConceptCollection conceptCollection = conceptCollectionFactory.createConceptCollectionObject(); 
			conceptCollection.setName("Test CC");
			conceptCollection.setDescription("description");
			conceptCollection.setOwner(user);
			String msg = "";
			try {
				msg = conceptCollectionManager.addConceptCollection(conceptCollection);
			} catch (QuadrigaStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting dictionary for user :"
						+ user.getUserName());
				List<IConceptCollection> conceptCollectionList = null;
				try {
					conceptCollectionList = dbConnect.getConceptsOwnedbyUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Iterator<IConceptCollection> I = conceptCollectionList.iterator();
				String name = null;
				String desc = null;
				IUser userTest = null;

				while (I.hasNext()) {
					IConceptCollection conceptCollectionTest = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollectionTest = I.next();
					assertEquals((conceptCollectionTest != null), true);
					if (conceptCollectionTest != null) {
						name = conceptCollectionTest.getName();
						desc = conceptCollectionTest.getDescription();
						userTest = conceptCollectionTest.getOwner();
					}
				}

				assertEquals(name.equals("Test CC"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("testAddProjectConceptCollection: Create concept collection Failed ; message :"
						+ msg);
				fail("testAddProjectConceptCollection: Create concept collection Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				projectConceptCollectionManager.addProjectConceptCollection("1",
						getCCID("Test CC"), "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<IConceptCollection> CCList = null;
			try {
				CCList = projectConceptCollectionManager.listProjectConceptCollection("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<IConceptCollection> I = CCList.iterator();
			assertEquals(CCList.size() > 0, true);
			while (I.hasNext()) {
				IConceptCollection cc = I.next();
				logger.info("Verifying the dictionary addition to project");
				assertEquals(cc.getName().equals("Test CC"), true);
			}
		}
		dbConnection.setupTestEnvironment("delete from tbl_project_conceptcollection");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		dbConnection.setupTestEnvironment("delete from tbl_conceptcollections");
	}

	/**
	 * Test method for {@link edu.asu.spring.quadriga.service.impl.workbench.ProjectConceptCollectionManager#deleteProjectConceptCollection(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDeleteProjectConceptCollection() {
		testSetupTestEnvironment();
		{
			dbConnection
					.setupTestEnvironment("INSERT  INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES('projDict','description','unix','1','jdoe','ACCESSIBLE','jdoe',NOW(),'jdoe',NOW());");
			IProject project1 = null;
			try {
				project1 = retrieveProjectManager.getProjectDetails("1");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(project1.getDescription().equals("description"), true);
			assertEquals(project1.getName().equals("projDict"), true);
			assertEquals(project1.getUnixName().equals("unix"), true);
		}
		{
			IConceptCollection conceptCollection = conceptCollectionFactory.createConceptCollectionObject(); 
			conceptCollection.setName("Test CC");
			conceptCollection.setDescription("description");
			conceptCollection.setOwner(user);
			String msg = "";
			try {
				msg = conceptCollectionManager.addConceptCollection(conceptCollection);
			} catch (QuadrigaStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting dictionary for user :"
						+ user.getUserName());
				List<IConceptCollection> conceptCollectionList = null;
				try {
					conceptCollectionList = dbConnect.getConceptsOwnedbyUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Iterator<IConceptCollection> I = conceptCollectionList.iterator();
				String name = null;
				String desc = null;
				IUser userTest = null;

				while (I.hasNext()) {
					IConceptCollection conceptCollectionTest = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollectionTest = I.next();
					assertEquals((conceptCollectionTest != null), true);
					if (conceptCollectionTest != null) {
						name = conceptCollectionTest.getName();
						desc = conceptCollectionTest.getDescription();
						userTest = conceptCollectionTest.getOwner();
					}
				}

				assertEquals(name.equals("Test CC"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("testAddProjectConceptCollection: Create concept collection Failed ; message :"
						+ msg);
				fail("testAddProjectConceptCollection: Create concept collection Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				projectConceptCollectionManager.addProjectConceptCollection("1",
						getCCID("Test CC"), "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<IConceptCollection> CCList = null;
			try {
				CCList = projectConceptCollectionManager.listProjectConceptCollection("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<IConceptCollection> I = CCList.iterator();
			assertEquals(CCList.size() > 0, true);
			while (I.hasNext()) {
				IConceptCollection cc = I.next();
				logger.info("Verifying the dictionary addition to project");
				assertEquals(cc.getName().equals("Test CC"), true);
			}
			try {
				projectConceptCollectionManager.deleteProjectConceptCollection("1", "jdoe", getCCID("Test CC"));
			} catch (QuadrigaStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<IConceptCollection> ccList1 = null;
			try {
				ccList1 = projectConceptCollectionManager.listProjectConceptCollection("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			I = ccList1.iterator();
			assertEquals(ccList1.size() == 0, true);
		}
		
		dbConnection.setupTestEnvironment("delete from tbl_project_conceptcollection");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		dbConnection.setupTestEnvironment("delete from tbl_conceptcollections");
	}

}
