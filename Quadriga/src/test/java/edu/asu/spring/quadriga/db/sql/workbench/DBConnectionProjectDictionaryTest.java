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

import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@ContextConfiguration(locations = {
		"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionProjectDictionaryTest {

	@Autowired
	private IProjectDictionaryManager projectDictionaryManager;

	@Autowired
	private IDBConnectionProjectDictionary dbConnectionProjectDictionary;
	
	@Autowired
	IDBConnectionDictionaryManager dbConnection;

	@Autowired
	private IDictionaryManager dictionaryManager;

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
			.getLogger(DBConnectionProjectDictionaryTest.class);

	private Connection connection;

	@Autowired
	private IRetrieveProjectManager retrieveProjectManager;

	private IUser user;

	@Autowired
	private IDictionaryFactory dictionaryFactory;

	@Autowired
	private IDictionaryItemsFactory dictionaryItemsFactory;
	
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
					"delete from tbl_dictionary_items",
					"delete from tbl_dictionary",
					"delete from tbl_project_dictionary",
					"delete from tbl_project",
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
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetupTestEnvironment() {
		for (String singleQuery : sDatabaseSetup) {
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}
	

	@Test
	public void testAddProjectDictionary() {
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
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg = "";
			try {
				msg = dictionaryManager.addNewDictionary(dictionary);
			} catch (QuadrigaStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting dictionary for user :"
						+ user.getUserName());
				List<IDictionary> dictionaryList = null;
				try {
					dictionaryList = dbConnection.getDictionaryOfUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Iterator<IDictionary> I = dictionaryList.iterator();
				String name = null;
				String desc = null;
				IUser userTest = null;

				while (I.hasNext()) {
					IDictionary dictionaryTest = dictionaryFactory
							.createDictionaryObject();
					dictionaryTest = I.next();
					assertEquals((dictionaryTest != null), true);
					if (dictionaryTest != null) {
						name = dictionaryTest.getName();
						desc = dictionaryTest.getDescription();
						userTest = dictionaryTest.getOwner();
					}
				}

				assertEquals(name.equals("testDictionary"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("addNewDictionaryTest: Create Dictionary Failed ; message :"
						+ msg);
				fail("addNewDictionaryTest: Create Dictionary Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				dbConnectionProjectDictionary.addProjectDictionary("1",
						getDictionaryID("testDictionary"), "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<IDictionary> dictList = null;
			try {
				dictList = projectDictionaryManager.listProjectDictionary("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<IDictionary> I = dictList.iterator();
			assertEquals(dictList.size() > 0, true);
			while (I.hasNext()) {
				IDictionary dict = I.next();
				logger.info("Verifying the dictionary addition to project");
				assertEquals(dict.getName().equals("testDictionary"), true);
			}
		}
		dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
		dbConnection.setupTestEnvironment("delete from tbl_project_dictionary");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		

	}

	@Test
	public void testListProjectDictionary() {
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
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg = "";
			try {
				msg = dictionaryManager.addNewDictionary(dictionary);
			} catch (QuadrigaStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting dictionary for user :"
						+ user.getUserName());
				List<IDictionary> dictionaryList = null;
				try {
					dictionaryList = dbConnection.getDictionaryOfUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Iterator<IDictionary> I = dictionaryList.iterator();
				String name = null;
				String desc = null;
				IUser userTest = null;

				while (I.hasNext()) {
					IDictionary dictionaryTest = dictionaryFactory
							.createDictionaryObject();
					dictionaryTest = I.next();
					assertEquals((dictionaryTest != null), true);
					if (dictionaryTest != null) {
						name = dictionaryTest.getName();
						desc = dictionaryTest.getDescription();
						userTest = dictionaryTest.getOwner();
					}
				}

				assertEquals(name.equals("testDictionary"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("addNewDictionaryTest: Create Dictionary Failed ; message :"
						+ msg);
				fail("addNewDictionaryTest: Create Dictionary Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				projectDictionaryManager.addProjectDictionary("1",
						getDictionaryID("testDictionary"), "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<IDictionary> dictList = null;
			try {
				dictList = dbConnectionProjectDictionary.listProjectDictionary("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<IDictionary> I = dictList.iterator();
			assertEquals(dictList.size() > 0, true);
			while (I.hasNext()) {
				IDictionary dict = I.next();
				logger.info("Verifying the dictionary addition to project");
				assertEquals(dict.getName().equals("testDictionary"), true);
			}
		}
		dbConnection.setupTestEnvironment("delete from tbl_project_dictionary");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
	}

	@Test
	public void testDeleteProjectDictionary() {
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
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg = "";
			try {
				msg = dictionaryManager.addNewDictionary(dictionary);
			} catch (QuadrigaStorageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting dictionary for user :"
						+ user.getUserName());
				List<IDictionary> dictionaryList = null;
				try {
					dictionaryList = dbConnection.getDictionaryOfUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Iterator<IDictionary> I = dictionaryList.iterator();
				String name = null;
				String desc = null;
				IUser userTest = null;

				while (I.hasNext()) {
					IDictionary dictionaryTest = dictionaryFactory
							.createDictionaryObject();
					dictionaryTest = I.next();
					assertEquals((dictionaryTest != null), true);
					if (dictionaryTest != null) {
						name = dictionaryTest.getName();
						desc = dictionaryTest.getDescription();
						userTest = dictionaryTest.getOwner();
					}
				}

				assertEquals(name.equals("testDictionary"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("addNewDictionaryTest: Create Dictionary Failed ; message :"
						+ msg);
				fail("addNewDictionaryTest: Create Dictionary Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				projectDictionaryManager.addProjectDictionary("1",
						getDictionaryID("testDictionary"), "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<IDictionary> dictList = null;
			try {
				dictList = projectDictionaryManager.listProjectDictionary("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<IDictionary> I = dictList.iterator();
			assertEquals(dictList.size() > 0, true);
			while (I.hasNext()) {
				IDictionary dict = I.next();
				logger.info("Verifying the dictionary addition to project");
				assertEquals(dict.getName().equals("testDictionary"), true);
			}
			
			try {
				dbConnectionProjectDictionary.deleteProjectDictionary("1", "jdoe", getDictionaryID("testDictionary"));
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dictList = null;
			try {
				dictList = projectDictionaryManager.listProjectDictionary("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			I = dictList.iterator();
			assertEquals(dictList.size() == 0, true);
		}
		dbConnection.setupTestEnvironment("delete from tbl_project_dictionary");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
	}

}
