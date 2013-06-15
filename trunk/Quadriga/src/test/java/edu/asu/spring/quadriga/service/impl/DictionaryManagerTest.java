package edu.asu.spring.quadriga.service.impl;

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
import edu.asu.spring.quadriga.db.sql.DBConnectionDictionaryManagerTest;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.WordpowerReply;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)

public class DictionaryManagerTest {

	@Autowired
	IDBConnectionDictionaryManager dbConnection;

	@Autowired
	IDictionaryManager dictionaryManager;

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
	public void getDictionariesListTest(){
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dictionaryManager.addNewDictionary(dictionary);
			logger.info(" message : "+msg);
			if(msg.equals("")){
				logger.info("Getting dictionary for user :"+user.getUserName());
				List <IDictionary> dictionaryList=dbConnection.getDictionaryOfUser(user.getUserName());
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
				dictionaryList=dbConnection.getDictionaryOfUser(user.getUserName());
				assertEquals((dictionaryList==null), true);
			}else{
				logger.info("getDictionaryOfUserTest: Create Dictionary Failed ; message :"+msg);
				fail("getDictionaryOfUserTest: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

	@Test
	public void addNewDictionariesItemsTest(){
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dictionaryManager.addNewDictionary(dictionary);
			logger.info(" message : "+msg);
			if(msg.equals("")){
				logger.info("Adding dictionary for user :"+user.getUserName());
				dictionaryManager.addNewDictionariesItems(getDictionaryID("testDictionary"), "dog", "http://www.digitalhps.org/dictionary/XID-dog-n", "noun", user.getUserName());
				List<IDictionaryItems> dictionaryItemsList=dictionaryManager.getDictionariesItems(getDictionaryID("testDictionary"));
				Iterator <IDictionaryItems> I = dictionaryItemsList.iterator();
				assertEquals(I.hasNext(),true);
				IDictionaryItems dictionaryItems = I.next();
				assertEquals(dictionaryItems.getItems(), "dog");
				assertEquals(dictionaryItems.getPos(), "noun");
				assertEquals(dictionaryItems.getId(), "http://www.digitalhps.org/dictionary/XID-dog-n");
				dbConnection.setupTestEnvironment("delete from tbl_dictionary_items where id = "+getDictionaryID("testDictionary"));
				dbConnection.setupTestEnvironment("delete from tbl_dictionary");
			}else{
				logger.info("addNewDictionariesItemsTest: Create Dictionary Failed ; message :"+msg);
				fail("addNewDictionariesItemsTest: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

	@Test
	public void addNewDictionaryTest(){
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dictionaryManager.addNewDictionary(dictionary);
			logger.info(" message : "+msg);
			if(msg.equals("")){
				logger.info("Getting dictionary for user :"+user.getUserName());
				List <IDictionary> dictionaryList=dbConnection.getDictionaryOfUser(user.getUserName());
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
			}else{
				logger.info("addNewDictionaryTest: Create Dictionary Failed ; message :"+msg);
				fail("addNewDictionaryTest: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

	@Test
	public void getDictionariesItemsTest(){
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dictionaryManager.addNewDictionary(dictionary);
			logger.info(" message : "+msg);
			if(msg.equals("")){
				logger.info("Adding dictionary for user :"+user.getUserName());
				dictionaryManager.addNewDictionariesItems(getDictionaryID("testDictionary"), "dog", "http://www.digitalhps.org/dictionary/XID-dog-n", "noun", user.getUserName());
				List<IDictionaryItems> dictionaryItemsList=dictionaryManager.getDictionariesItems(getDictionaryID("testDictionary"));
				Iterator <IDictionaryItems> I = dictionaryItemsList.iterator();
				assertEquals(I.hasNext(),true);
				IDictionaryItems dictionaryItems = I.next();
				assertEquals(dictionaryItems.getItems(), "dog");
				assertEquals(dictionaryItems.getPos(), "noun");
				assertEquals(dictionaryItems.getId(), "http://www.digitalhps.org/dictionary/XID-dog-n");
				dbConnection.setupTestEnvironment("delete from tbl_dictionary_items where id = "+getDictionaryID("testDictionary"));
				dbConnection.setupTestEnvironment("delete from tbl_dictionary");
			}else{
				logger.info("getDictionariesItemsTest: Create Dictionary Failed ; message :"+msg);
				fail("getDictionariesItemsTest: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

	@Test
	public void getDictionaryNameTest(){
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dictionaryManager.addNewDictionary(dictionary);
			logger.info(" message : "+msg);
			if(msg.equals("")){
				logger.info("Getting dictionary for user :"+user.getUserName());
				List <IDictionary> dictionaryList=dbConnection.getDictionaryOfUser(user.getUserName());
				Iterator<IDictionary> I = dictionaryList.iterator();
				String id=null;

				while(I.hasNext()){
					IDictionary dictionaryTest = dictionaryFactory.createDictionaryObject();
					dictionaryTest=I.next();
					assertEquals((dictionaryTest!=null), true);
					if(dictionaryTest!=null){
						id =getDictionaryID(dictionaryTest.getName());
						assertEquals(dictionaryManager.getDictionaryName(id), dictionaryTest.getName());
					}
				}

				dbConnection.setupTestEnvironment("delete from tbl_dictionary");
			}else{
				logger.info("getDictionaryNameTest: Create Dictionary Failed ; message :"+msg);
				fail("getDictionaryNameTest: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

	@Test
	public void searchWordPowerTest(){
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dictionaryManager.addNewDictionary(dictionary);
			logger.info(" message : "+msg);
			if(msg.equals("")){
				logger.info("Getting dictionary for user :"+user.getUserName());
				List <IDictionary> dictionaryList=dbConnection.getDictionaryOfUser(user.getUserName());
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
				WordpowerReply.DictionaryEntry dictionaryEntry=dictionaryManager.searchWordPower("dog", "noun");
				assertEquals(dictionaryEntry.getLemma(), "dog");
				assertEquals(dictionaryEntry.getPos(), "noun");
				dbConnection.setupTestEnvironment("delete from tbl_dictionary");
			}else{
				logger.info("addNewDictionaryTest: Create Dictionary Failed ; message :"+msg);
				fail("addNewDictionaryTest: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

	@Test
	public void deleteDictionariesItemsTest(){
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dictionaryManager.addNewDictionary(dictionary);
			logger.info(" message : "+msg);
			if(msg.equals("")){
				logger.info("Adding dictionary for user :"+user.getUserName());
				dictionaryManager.addNewDictionariesItems(getDictionaryID("testDictionary"), "dog", "http://www.digitalhps.org/dictionary/XID-dog-n", "noun", user.getUserName());
				List<IDictionaryItems> dictionaryItemsList=dictionaryManager.getDictionariesItems(getDictionaryID("testDictionary"));
				Iterator <IDictionaryItems> I = dictionaryItemsList.iterator();
				assertEquals(I.hasNext(),true);
				IDictionaryItems dictionaryItems = I.next();
				assertEquals(dictionaryItems.getItems(), "dog");
				assertEquals(dictionaryItems.getPos(), "noun");
				assertEquals(dictionaryItems.getId(), "http://www.digitalhps.org/dictionary/XID-dog-n");
				dictionaryManager.deleteDictionariesItems(getDictionaryID("testDictionary"), "http://www.digitalhps.org/dictionary/XID-dog-n");
				dictionaryItemsList=dictionaryManager.getDictionariesItems(getDictionaryID("testDictionary"));
				I = dictionaryItemsList.iterator();
				assertEquals(I.hasNext(),false);
				dbConnection.setupTestEnvironment("delete from tbl_dictionary");
			}else{
				logger.info("getDictionariesItemsTest: Create Dictionary Failed ; message :"+msg);
				fail("getDictionariesItemsTest: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

	@Test
	public void updateDictionariesItemsTest(){
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dictionaryManager.addNewDictionary(dictionary);
			logger.info(" message : "+msg);
			if(msg.equals("")){
				logger.info("Adding dictionary for user :"+user.getUserName());
				dictionaryManager.addNewDictionariesItems(getDictionaryID("testDictionary"), "cat", "http://www.digitalhps.org/dictionary/XID-dog-n", "noun", user.getUserName());
				List<IDictionaryItems> dictionaryItemsList=dictionaryManager.getDictionariesItems(getDictionaryID("testDictionary"));
				Iterator <IDictionaryItems> I = dictionaryItemsList.iterator();
				assertEquals(I.hasNext(),true);
				IDictionaryItems dictionaryItems = I.next();
				assertEquals(dictionaryItems.getItems(), "cat");
				assertEquals(dictionaryItems.getPos(), "noun");
				assertEquals(dictionaryItems.getId(), "http://www.digitalhps.org/dictionary/XID-dog-n");
				WordpowerReply.DictionaryEntry dictionaryEntry=dictionaryManager.getUpdateFromWordPower(getDictionaryID("testDictionary"), "http://www.digitalhps.org/dictionary/XID-dog-n");
				assertEquals(dictionaryEntry.getLemma(),"dog");
				assertEquals(dictionaryEntry.getPos(),"NOUN");
				String msg1=dictionaryManager.updateDictionariesItems(getDictionaryID("testDictionary"), "http://www.digitalhps.org/dictionary/XID-dog-n", dictionaryEntry.getLemma(), dictionaryEntry.getPos());
				assertEquals(msg1.equals(""), true);
				dictionaryItemsList=dictionaryManager.getDictionariesItems(getDictionaryID("testDictionary"));
				I = dictionaryItemsList.iterator();
				assertEquals(I.hasNext(),true);
				dictionaryItems = I.next();
				assertEquals(dictionaryItems.getItems(), "dog");
				assertEquals(dictionaryItems.getPos(), "NOUN");
				assertEquals(dictionaryItems.getId(), "http://www.digitalhps.org/dictionary/XID-dog-n");
				
				dbConnection.setupTestEnvironment("delete from tbl_dictionary_items where id = "+getDictionaryID("testDictionary"));
				dbConnection.setupTestEnvironment("delete from tbl_dictionary");
			}else{
				logger.info("addNewDictionariesItemsTest: Create Dictionary Failed ; message :"+msg);
				fail("addNewDictionariesItemsTest: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

	@Test
	public void  getUpdateFromWordPowerTest(){
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			String msg =dictionaryManager.addNewDictionary(dictionary);
			logger.info(" message : "+msg);
			if(msg.equals("")){
				logger.info("Adding dictionary for user :"+user.getUserName());
				dictionaryManager.addNewDictionariesItems(getDictionaryID("testDictionary"), "cat", "http://www.digitalhps.org/dictionary/XID-dog-n", "noun", user.getUserName());
				List<IDictionaryItems> dictionaryItemsList=dictionaryManager.getDictionariesItems(getDictionaryID("testDictionary"));
				Iterator <IDictionaryItems> I = dictionaryItemsList.iterator();
				assertEquals(I.hasNext(),true);
				IDictionaryItems dictionaryItems = I.next();
				assertEquals(dictionaryItems.getItems(), "cat");
				assertEquals(dictionaryItems.getPos(), "noun");
				assertEquals(dictionaryItems.getId(), "http://www.digitalhps.org/dictionary/XID-dog-n");
				WordpowerReply.DictionaryEntry dictionaryEntry=dictionaryManager.getUpdateFromWordPower(getDictionaryID("testDictionary"), "http://www.digitalhps.org/dictionary/XID-dog-n");
				assertEquals(dictionaryEntry.getLemma(),"dog");
				assertEquals(dictionaryEntry.getPos(),"NOUN");
				dbConnection.setupTestEnvironment("delete from tbl_dictionary_items where id = "+getDictionaryID("testDictionary"));
				dbConnection.setupTestEnvironment("delete from tbl_dictionary");
			}else{
				logger.info("addNewDictionariesItemsTest: Create Dictionary Failed ; message :"+msg);
				fail("addNewDictionariesItemsTest: Create Dictionary Failed ; message :"+msg);
			}
		}
	}

}
