package edu.asu.spring.quadriga.web;

import static org.junit.Assert.*;

import java.security.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.db.sql.DBConnectionDictionaryManagerTest;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class tests the {@link DictionaryController}. 
 * 
 * IMPORTANT: This test class will overwrite the data in 
 * 			  tbl_dictionary
 * 			  tbl_dictionary_items
 * 
 * @author      Lohith Dwaraka
 *
 * 
 *
 */
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })

@RunWith(SpringJUnit4ClassRunner.class)
public class DictionaryControllerTest {

	DictionaryController dictionaryController;
	
	@Autowired
	IDBConnectionDictionaryManager dbConnection;

	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	
	@Autowired
	@Qualifier("UserManager")
	IUserManager userManager;
	
	
	BindingAwareModelMap model;
	
	

	String sDatabaseSetup [];
	
	UsernamePasswordAuthenticationToken authentication;

	
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionDictionaryManagerTest.class);

	Principal principal;
	CredentialsContainer credentials;
	
	
	
	@Autowired
	private IDictionaryFactory dictionaryFactory;

	@Autowired
	private IDictionaryItemsFactory dictionaryItemsFactory;

	private IUser user;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dictionaryController = new DictionaryController();
		dictionaryController.setUsermanager(userManager);
		
		//Setup a user object to compare with the object produced from usermanager
		user = userFactory.createUserObject();
		user.setUserName("jdoe");
		user.setName("John Doe");

		model =  new BindingAwareModelMap();
		principal = new Principal() {			
			@Override
			public String getName() {
				return "jdoe";
			}
		};
		authentication = new UsernamePasswordAuthenticationToken(principal, credentials);
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
				"delete from tbl_conceptcollections",
				"delete from tbl_quadriga_user_denied",
				
				"delete from tbl_quadriga_user",
				"delete from tbl_quadriga_user_requests",
				"delete from tbl_dictionary_items",
				"delete from tbl_dictionary",
				"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
		};
	}
	
	/**
	 * Load the required data into the dependent tables
	 * @author Ram Kumar Kumaresan
	 */
	@Test
	public void testSetupTestEnvironment()
	{
		for(String singleQuery: sDatabaseSetup)
		{
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
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
	
	public String getDictionaryID(String name){
		getConnection();
		String id=null;
		try{
			Statement stmt = connection.createStatement();
			stmt.execute("select id from tbl_dictionary where dictionaryName='"+name+"'");
			ResultSet rs =stmt.getResultSet();
			if(rs!=null){
				while (rs.next()) { 
					id=rs.getString(1);
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListDictionary() {
		{
			testSetupTestEnvironment();

			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg="";
			try {
				msg = dbConnection.addDictionary(dictionary);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info(" message : "+msg);
			if(msg.equals("")){
				//Check the return value
				assertEquals(dictionaryController.listDictionary(model),"auth/dictionaries");
				dbConnection.setupTestEnvironment("delete from tbl_dictionary");
			}else{
				logger.info("testListDictionary: Create Dictionary Failed ; message :"+msg);
				fail("testListDictionary: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

	@Test
	public void testGetDictionaryPage() {
		{
			testSetupTestEnvironment();

			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg="";
			try {
				msg = dbConnection.addDictionary(dictionary);
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info(" message : "+msg);
			if(msg.equals("")){
				//Check the return value
				try {
					assertEquals(dictionaryController.getDictionaryPage(getDictionaryID("testDictionary"), model),"auth/dictionary/dictionary");
				} catch (QuadrigaStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dbConnection.setupTestEnvironment("delete from tbl_dictionary");
			}else{
				logger.info("testListDictionary: Create Dictionary Failed ; message :"+msg);
				fail("testListDictionary: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

	@Test
	public void testAddDictionaryForm() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddDictionaryHandle() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddDictionaryPage() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddDictionaryItemForm() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddDictionaryItem() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testDeleteDictionaryItem() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testHandleNullException() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUpdateDictionaryItem() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSearchDictionaryItemRestHandle() {
		fail("Not yet implemented"); // TODO
	}

}
