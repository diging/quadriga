package edu.asu.spring.quadriga.db.sql.workbench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectConceptColleciton;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@ContextConfiguration(locations = {
		"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionProjectConceptCollectionTest {


	
	@Autowired
	private IProjectConceptCollectionManager projectConceptCollectionManager;

	
	@Autowired
	private IDBConnectionProjectConceptColleciton dbConnectionProjectConceptColleciton;
	
	@Autowired
	IDBConnectionDictionaryManager dbConnection;
	
	@Autowired
	IDBConnectionCCManager dbConnect;


	@Autowired
	private IConceptCollectionManager conceptCollectionManager;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private IModifyProjectManager modifyProjectManager;

	String sDatabaseSetup[];

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private static final Logger logger = LoggerFactory
			.getLogger(DBConnectionProjectConceptCollectionTest.class);

	private Connection connection;

	@Autowired
	private IRetrieveProjectManager retrieveProjectManager;

	private IUser user;

	
	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

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
					"delete from tbl_project_conceptcollection",
					"delete from tbl_project",
					"delete from tbl_conceptcollections_items",
					"delete from tbl_conceptcollections",
					"delete from tbl_quadriga_user_denied",
					"delete from tbl_quadriga_user",
					"delete from tbl_quadriga_user_requests",
					
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

	public void getConnection() {
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getDictionaryID(String name) {
		getConnection();
		String id = null;
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("select id from tbl_dictionary where dictionaryName='"
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
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetupTestEnvironment() throws QuadrigaStorageException {
		for (String singleQuery : sDatabaseSetup) {
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}
	

	@Test
	public void testAddProjectConceptCollection() throws QuadrigaStorageException {
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
			IConceptCollection conceptColleciton=conceptCollectionFactory.createConceptCollectionObject();			
			conceptColleciton.setName("Test CC");
			conceptColleciton.setDescription("description");
			conceptColleciton.setOwner(user);
			String msg = "";
			try {
				msg = conceptCollectionManager.addConceptCollection(conceptColleciton);
			} catch (QuadrigaStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting concept collection for user :"
						+ user.getUserName());
				List<IConceptCollection> ccList=null;
				try {
					ccList = dbConnect.getConceptsOwnedbyUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Iterator<IConceptCollection> I=ccList.iterator();
				
				String name = null;
				String desc = null;
				IUser userTest = null;

				while (I.hasNext()) {
					IConceptCollection conceptCollection = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollection = I.next();
					assertEquals((conceptCollection != null), true);
					if (conceptCollection != null) {
						name = conceptCollection.getName();
						desc = conceptCollection.getDescription();
						userTest = conceptCollection.getOwner();
					}
				}

				assertEquals(name.equals("Test CC"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("testAddProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
				fail("testAddProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				dbConnectionProjectConceptColleciton.addProjectConceptCollection("1", getCCID("Test CC"), "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<IConceptCollection> ccList1 = null;
			try {
				ccList1 = projectConceptCollectionManager.listProjectConceptCollection("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<IConceptCollection> I = ccList1.iterator();
			assertEquals(ccList1.size() > 0, true);
			while (I.hasNext()) {
				IConceptCollection cc = I.next();
				logger.info("Verifying the concept collection addition to project");
				assertEquals(cc.getName().equals("Test CC"), true);
			}
		}
		dbConnection.setupTestEnvironment("delete from tbl_project_conceptcollection");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		dbConnection.setupTestEnvironment("delete from tbl_conceptcollections");

	}

	@Test
	public void testListProjectConceptCollection() throws QuadrigaStorageException {
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
			IConceptCollection conceptColleciton=conceptCollectionFactory.createConceptCollectionObject();			
			conceptColleciton.setName("Test CC");
			conceptColleciton.setDescription("description");
			conceptColleciton.setOwner(user);
			String msg = "";
			try {
				msg = conceptCollectionManager.addConceptCollection(conceptColleciton);
			} catch (QuadrigaStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting Concept collection for user :"
						+ user.getUserName());
				List<IConceptCollection> ccList=null;
				try {
					ccList = dbConnect.getConceptsOwnedbyUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Iterator<IConceptCollection> I=ccList.iterator();
				
				String name = null;
				String desc = null;
				IUser userTest = null;

				while (I.hasNext()) {
					IConceptCollection conceptCollection = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollection = I.next();
					assertEquals((conceptCollection != null), true);
					if (conceptCollection != null) {
						name = conceptCollection.getName();
						desc = conceptCollection.getDescription();
						userTest = conceptCollection.getOwner();
					}
				}

				assertEquals(name.equals("Test CC"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("testListProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
				fail("testListProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				dbConnectionProjectConceptColleciton.addProjectConceptCollection("1", getCCID("Test CC"), "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<IConceptCollection> ccList1 = null;
			try {
				ccList1 = projectConceptCollectionManager.listProjectConceptCollection("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<IConceptCollection> I = ccList1.iterator();
			assertEquals(ccList1.size() > 0, true);
			while (I.hasNext()) {
				IConceptCollection cc = I.next();
				logger.info("Verifying the concept collection addition to project");
				assertEquals(cc.getName().equals("Test CC"), true);
			}
		}
		dbConnection.setupTestEnvironment("delete from tbl_project_conceptcollection");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		dbConnection.setupTestEnvironment("delete from tbl_conceptcollections");
	}

	@Test
	public void testDeleteProjectConceptCollection() throws QuadrigaStorageException {
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
			IConceptCollection conceptColleciton=conceptCollectionFactory.createConceptCollectionObject();			
			conceptColleciton.setName("Test CC");
			conceptColleciton.setDescription("description");
			conceptColleciton.setOwner(user);
			String msg = "";
			try {
				msg = conceptCollectionManager.addConceptCollection(conceptColleciton);
			} catch (QuadrigaStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting Concept collection for user :"
						+ user.getUserName());
				List<IConceptCollection> ccList=null;
				try {
					ccList = dbConnect.getConceptsOwnedbyUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Iterator<IConceptCollection> I=ccList.iterator();
				
				String name = null;
				String desc = null;
				IUser userTest = null;

				while (I.hasNext()) {
					IConceptCollection conceptCollection = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollection = I.next();
					assertEquals((conceptCollection != null), true);
					if (conceptCollection != null) {
						name = conceptCollection.getName();
						desc = conceptCollection.getDescription();
						userTest = conceptCollection.getOwner();
					}
				}

				assertEquals(name.equals("Test CC"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("testListProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
				fail("testListProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				dbConnectionProjectConceptColleciton.addProjectConceptCollection("1", getCCID("Test CC"), "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<IConceptCollection> ccList1 = null;
			try {
				ccList1 = projectConceptCollectionManager.listProjectConceptCollection("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<IConceptCollection> I = ccList1.iterator();
			assertEquals(ccList1.size() > 0, true);
			while (I.hasNext()) {
				IConceptCollection cc = I.next();
				logger.info("Verifying the concept collection addition to project");
				assertEquals(cc.getName().equals("Test CC"), true);
			}
		
			
			try {
				dbConnectionProjectConceptColleciton.deleteProjectConceptCollection("1", "jdoe", getCCID("Test CC"));
				
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ccList1 = null;
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
