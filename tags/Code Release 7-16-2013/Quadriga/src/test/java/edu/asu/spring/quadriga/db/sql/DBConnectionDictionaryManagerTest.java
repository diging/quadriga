package edu.asu.spring.quadriga.db.sql;

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

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.web.login.RoleNames;


/**
 * This class tests the {@link DBConnectionDictionaryManager}. 
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
public class DBConnectionDictionaryManagerTest {

	@Autowired
	IDBConnectionDictionaryManager dbConnection;

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
		sDatabaseSetup = new String[]{"delete from tbl_quadriga_user_denied",
				"delete from tbl_dictionary_items",
				"delete from tbl_dictionary",
				"delete from tbl_quadriga_user",
				"delete from tbl_quadriga_user_requests",
				"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
				"INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())",
		};
	}
	@After
	public void tearDown() throws Exception {
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
	
	@Test
	public void getDictionaryOfUserTest() throws QuadrigaStorageException {
		testSetupTestEnvironment();

		IDictionary dictionary = dictionaryFactory.createDictionaryObject();
		dictionary.setName("testDictionary");
		dictionary.setDescription("description");
		dictionary.setOwner(user);
		String msg =dbConnection.addDictionary(dictionary);
		logger.info(" message : "+msg);
		if(msg.equals("")){
			logger.info("Getting dictionary for user :"+user.getUserName());
			List<IDictionary> dictionaryList=null;
			try {
				dictionaryList = dbConnection.getDictionaryOfUser(user.getUserName());
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<IDictionary> I = dictionaryList.iterator();
			String name=null;
			String desc=null;
			IUser userTest=null;

			while(I.hasNext()){
				IDictionary dictionaryTest = dictionaryFactory.createDictionaryObject();
				dictionaryTest=I.next();
				assertEquals((dictionaryTest!=null), true);
				if(dictionaryTest!=null){
					name =dictionaryTest.getName();
					desc =dictionaryTest.getDescription();
					userTest =dictionaryTest.getOwner();
				}
			}

			assertEquals(name.equals("testDictionary"),true);
			assertEquals(desc.equals("description"),true);
			dbConnection.setupTestEnvironment("delete from tbl_dictionary");
			try {
				dictionaryList=dbConnection.getDictionaryOfUser(user.getUserName());
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals((dictionaryList==null), true);
		}else{
			logger.info("getDictionaryOfUserTest: Create Dictionary Failed ; message :"+msg);
			fail("getDictionaryOfUserTest: Create Dictionary Failed ; message :"+msg);
		}
		dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
	}
	
	@Test
	public void deleteDictionaryTest() throws QuadrigaStorageException{
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dbConnection.addDictionary(dictionary);
			logger.info(" message : "+msg);
			assertEquals((msg.equals("")), true);
			String id= getDictionaryID("testDictionary");
			logger.info("Dictionary ID : "+id);
			try {
				msg= dbConnection.addDictionaryItems(id, 
						"dog", "9999999999999999999999999999999999999999999999999999999999999999999", "noun", "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals((msg.equals("")), true);
			msg= dbConnection.deleteDictionary("jdoe", id);
			assertEquals((msg.equals("")), true);
		}
//		dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
//		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
//		fail("Not implemented");
	}
	

	@Test
	public void deleteDictionaryItemsTest() throws QuadrigaStorageException{
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dbConnection.addDictionary(dictionary);
			logger.info(" message : "+msg);
			assertEquals((msg.equals("")), true);
			String id= getDictionaryID("testDictionary");
			logger.info("Dictionary ID : "+id);
			try {
				msg= dbConnection.addDictionaryItems(id, 
						"dog", "9999999999999999999999999999999999999999999999999999999999999999999", "noun", "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals((msg.equals("")), true);
			msg=dbConnection.deleteDictionaryItems(id, "9999999999999999999999999999999999999999999999999999999999999999999", "jdoe");
			assertEquals((msg.equals("")), true);
		}
		dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
	}
	
	@Test
	public void updateDictionaryItemsTest() throws QuadrigaStorageException{
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dbConnection.addDictionary(dictionary);
			logger.info(" message : "+msg);
			assertEquals((msg.equals("")), true);
			String id= getDictionaryID("testDictionary");
			logger.info("Dictionary ID : "+id);
			try {
				msg= dbConnection.addDictionaryItems(id, 
						"dog", "9999999999999999999999999999999999999999999999999999999999999999999", "noun", "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals((msg.equals("")), true);
			msg=dbConnection.updateDictionaryItems(id, "9999999999999999999999999999999999999999999999999999999999999999999", "dog1", "noun");
			assertEquals((msg.equals("")), true);
		}
		dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
	}
	
	@Test
	public void addDictionaryTest() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dbConnection.addDictionary(dictionary);
			logger.info(" message : "+msg);
			assertEquals((msg.equals("")), true);

		}
		{
			testSetupTestEnvironment();
			IDictionary dictionary = null;
			String msg =dbConnection.addDictionary(dictionary);
			logger.info(" message : "+msg);
			assertEquals((!msg.equals("")), true);
		}
		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
	}

	@Test
	public void getDictionaryItemsDetailsTest() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dbConnection.addDictionary(dictionary);
			logger.info(" message : "+msg);
			assertEquals((msg.equals("")), true);
			String id= getDictionaryID("testDictionary");
			logger.info("Dictionary ID : "+id);
			try {
				msg= dbConnection.addDictionaryItems(id, 
						"dog", "Dog id", "noun", "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals((msg.equals("")), true);
			List <IDictionaryItems> dictionaryItemsList = null;
			dictionaryItemsList=dbConnection.getDictionaryItemsDetails(id,"jdoe");
			Iterator<IDictionaryItems> I = dictionaryItemsList.iterator();
			assertEquals(I.hasNext(),true);
			logger.info("Checking if Iterator has objects  : "+I.hasNext());
			IDictionaryItems dictionaryItems=dictionaryItemsFactory.createDictionaryItemsObject();
			dictionaryItems=I.next();
			logger.info("Dictionary id :"+dictionaryItems.getId());
			logger.info("Dictionary Pos :"+dictionaryItems.getPos());
			logger.info("Dictionary Items :"+dictionaryItems.getItems());
			
			assertEquals(dictionaryItems.getId().equals("Dog id"),true);
			assertEquals(dictionaryItems.getPos().equals("noun"),true);
			assertEquals(dictionaryItems.getItems().equals("dog"),true);
			
		}
		dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
		
	}

	@Test
	public void getDictionaryNameTest() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dbConnection.addDictionary(dictionary);
			logger.info(" message : "+msg);
			assertEquals((msg.equals("")), true);
			String id= getDictionaryID("testDictionary");
			String name=dbConnection.getDictionaryName(id);
			assertEquals((name.equals("testDictionary")), true);
			dbConnection.setupTestEnvironment("delete from tbl_dictionary");
		}
		dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
	}

	@Test
	public void addDictionaryItemsTest() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dbConnection.addDictionary(dictionary);
			logger.info(" message : "+msg);
			assertEquals((msg.equals("")), true);
			String id= getDictionaryID("testDictionary");
			logger.info("Dictionary ID : "+id);
			try {
				msg= dbConnection.addDictionaryItems(id, 
						"dog", "9999999999999999999999999999999999999999999999999999999999999999999", "noun", "jdoe");
			} catch (QuadrigaStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals((msg.equals("")), true);
			dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
			dbConnection.setupTestEnvironment("delete from tbl_dictionary");	
		}
		dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
		dbConnection.setupTestEnvironment("delete from tbl_dictionary");
	}
}
