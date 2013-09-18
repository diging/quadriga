package edu.asu.spring.quadriga.web.dictionary;

import static org.junit.Assert.*;

import java.security.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItem;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
import edu.asu.spring.quadriga.domain.implementation.CollaboratorRole;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItem;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.manageusers.UserController;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DictionaryItemCollabControllerTest {

	UserController userContoller;

	DictionaryItemController dictionaryItemController;
	
	DictionaryItemCollabController dictionaryItemCollabController;

	private Connection connection;

	@Autowired
	private IDictionaryFactory dictionaryFactory;

	private IUser user;
	private IUser userCollab;

	@Autowired
	IDictionaryManager dictonaryManager;

	
	@Autowired
	ICollaboratorRoleManager collabRoleManager;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	IDBConnectionDictionaryManager dbConnection;

	String sDatabaseSetup [];

	@Autowired
	IUserManager usermanager;

	MockHttpServletRequest mock;
	
	Principal principal;	
	UsernamePasswordAuthenticationToken authentication;
	CredentialsContainer credentials;
	BindingAwareModelMap model;
	HttpServletRequest req;
	
	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private static final Logger logger = LoggerFactory.getLogger(DictionaryItemControllerTest.class);

	@Autowired
	private IUserFactory userFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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
	
	@Before
	public void setUp() throws Exception {
		userContoller = new UserController();
		userContoller.setUsermanager(usermanager);
		dictionaryItemCollabController = new DictionaryItemCollabController();
		dictionaryItemCollabController.setDictonaryManager(dictonaryManager);
		dictionaryItemCollabController.setUsermanager(usermanager);
		dictionaryItemCollabController.setCollaboratorRoleManager(collabRoleManager);
		model =  new BindingAwareModelMap();	
		mock = new MockHttpServletRequest();

		principal = new Principal() {			
			@Override
			public String getName() {
				return "jdoe";
			}
		};
		authentication = new UsernamePasswordAuthenticationToken(principal, credentials);
		//Setup a user object to compare with the object produced from usermanager
		user = userFactory.createUserObject();
		user.setUserName("jdoe");
		user.setName("John Doe");

		userCollab = userFactory.createUserObject();
		userCollab.setUserName("test");
		userCollab.setName("Test User");
		
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
		userCollab.setQuadrigaRoles(rolesList);

		//Setup the database with the proper data in the tables;
		sDatabaseSetup = new String[]{
				"delete from tbl_dictionary_items",
				"delete from tbl_dictionary",
				"delete from tbl_quadriga_user_denied",
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

	@Test
	public void testSetupTestEnvironment()
	{
		for(String singleQuery: sDatabaseSetup)
		{
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDictionaryCollabPage() throws QuadrigaStorageException, QuadrigaAccessException {
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			dbConnection.addDictionary(dictionary);
			ICollaborator collaborator = new Collaborator();
			collaborator.setUserObj(userCollab);
			ICollaboratorRole collabRole = new CollaboratorRole();
			collabRole.setRoleDBid("dict_role3");
			List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
			roles.add(collabRole);
			collaborator.setCollaboratorRoles(roles);
			dbConnection.addCollaborators(collaborator, getDictionaryID("testDictionary"), userCollab.getUserName(), user.getUserName());
			principal = new Principal() {			
				@Override
				public String getName() {
					return "test";
				}
			};
			assertEquals(dictionaryItemCollabController.getDictionaryCollabPage(getDictionaryID("testDictionary"), model,principal),"auth/dictionary/dictionarycollab");
			int roleAccess = (Integer) model.get("roleAccess");
			List <IDictionaryItem> dictionaryItemList = (List <IDictionaryItem>)model.get("dictionaryItemList");
			String dictName =(String) model.get("dictName");
			String dictionaryid =(String) model.get("dictionaryid");
			Iterator <IDictionaryItem> I = dictionaryItemList.iterator();
			assertEquals(I.hasNext(),false);
			assertEquals(roleAccess,1);
			assertEquals(dictName,"testDictionary");
			assertEquals(dictionaryid,getDictionaryID("testDictionary"));
			dbConnection.deleteDictionary("jdoe", getDictionaryID("testDictionary"));
			
		}
	}
	
	@Test
	public void testDeleteDictionaryItems() throws QuadrigaStorageException{
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			dbConnection.addDictionary(dictionary);
			dbConnection.addDictionaryItems(getDictionaryID("testDictionary"), "dog", "http://www.digitalhps.org/dictionary/XID-dog-n", "noun","jdoe");
			String values ="http://www.digitalhps.org/dictionary/XID-dog-n";
			try{
				mock.setParameter("selected", values);
			}catch(Exception e){
				logger.error("",e);
			}
			ICollaborator collaborator = new Collaborator();
			collaborator.setUserObj(userCollab);
			ICollaboratorRole collabRole = new CollaboratorRole();
			collabRole.setRoleDBid("dict_role3");
			List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
			roles.add(collabRole);
			collaborator.setCollaboratorRoles(roles);
			dbConnection.addCollaborators(collaborator, getDictionaryID("testDictionary"), userCollab.getUserName(), user.getUserName());
			principal = new Principal() {			
				@Override
				public String getName() {
					return "test";
				}
			};
			assertEquals(dictionaryItemCollabController.deleteDictionaryItem(mock, getDictionaryID("testDictionary"), model, principal), "auth/dictionary/dictionarycollab");
			int roleAccess = (Integer) model.get("roleAccess");
			List <IDictionaryItem> dictionaryItemList = (List <IDictionaryItem>)model.get("dictionaryItemList");
			String dictName =(String) model.get("dictName");
			String dictionaryid =(String) model.get("dictID");
			Iterator <IDictionaryItem> I = dictionaryItemList.iterator();
			assertEquals(I.hasNext(),false);
			assertEquals(roleAccess,1);
			assertEquals(dictName,"testDictionary");
			assertEquals(dictionaryid,getDictionaryID("testDictionary"));
			dbConnection.deleteDictionary("jdoe", getDictionaryID("testDictionary"));
			
		}
	}
	
	@Test
	public void testUpdateDictionaryItem() throws QuadrigaStorageException{
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			dbConnection.addDictionary(dictionary);
			dbConnection.addDictionaryItems(getDictionaryID("testDictionary"), "dog", "http://www.digitalhps.org/dictionary/XID-dog-n", "noun","jdoe");
			String values ="http://www.digitalhps.org/dictionary/XID-dog-n";
			try{
				mock.setParameter("selected", values);
			}catch(Exception e){
				logger.error("",e);
			}
			ICollaborator collaborator = new Collaborator();
			collaborator.setUserObj(userCollab);
			ICollaboratorRole collabRole = new CollaboratorRole();
			collabRole.setRoleDBid("dict_role3");
			List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
			roles.add(collabRole);
			collaborator.setCollaboratorRoles(roles);
			dbConnection.addCollaborators(collaborator, getDictionaryID("testDictionary"), userCollab.getUserName(), user.getUserName());
			principal = new Principal() {			
				@Override
				public String getName() {
					return "test";
				}
			};
			assertEquals(dictionaryItemCollabController.updateDictionaryItem(mock, getDictionaryID("testDictionary"), model, principal), "auth/dictionary/dictionarycollab");
			int roleAccess = (Integer) model.get("roleAccess");
			List <IDictionaryItem> dictionaryItemList = (List <IDictionaryItem>)model.get("dictionaryItemList");
			String dictName =(String) model.get("dictName");
			String dictionaryid =(String) model.get("dictID");
			Iterator <IDictionaryItem> I = dictionaryItemList.iterator();
			assertEquals(I.hasNext(),true);
			IDictionaryItem dictionaryItems = I.next();
			
			assertEquals(roleAccess,1);
			assertEquals(dictName,"testDictionary");
			assertEquals(dictionaryid,getDictionaryID("testDictionary"));
			dbConnection.deleteDictionaryItems(getDictionaryID("testDictionary"), dictionaryItems.getId(), "jdoe");
			//dbConnection.setupTestEnvironment("delete from tbl_dictionary_items");
			dbConnection.deleteDictionary("jdoe", getDictionaryID("testDictionary"));
			
		}
	}
	
	@Test
	public void testSearchDictionaryItemRestHandle() throws QuadrigaStorageException, QuadrigaAccessException{
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			dbConnection.addDictionary(dictionary);
			ICollaborator collaborator = new Collaborator();
			collaborator.setUserObj(userCollab);
			ICollaboratorRole collabRole = new CollaboratorRole();
			collabRole.setRoleDBid("dict_role3");
			List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
			roles.add(collabRole);
			collaborator.setCollaboratorRoles(roles);
			dbConnection.addCollaborators(collaborator, getDictionaryID("testDictionary"), userCollab.getUserName(), user.getUserName());
			principal = new Principal() {			
				@Override
				public String getName() {
					return "test";
				}
			};
			assertEquals(dictionaryItemCollabController.searchDictionaryItemRestHandle(getDictionaryID("testDictionary"), "dog", "noun", model,principal), "auth/dictionaries/addDictionaryItemsCollab");
			int collab = (Integer) model.get("collab");
			List <IDictionaryItem> dictionaryItemList = (List <IDictionaryItem>)model.get("dictionaryEntryList");
			String dictName =(String) model.get("dictName");
			String dictionaryid =(String) model.get("dictionaryid");
			Iterator <IDictionaryItem> I = dictionaryItemList.iterator();
			assertEquals(I.hasNext(),true);
			
			assertEquals(collab,1);
			assertEquals(dictName,"testDictionary");
			assertEquals(dictionaryid,getDictionaryID("testDictionary"));
			dbConnection.deleteDictionary("jdoe", getDictionaryID("testDictionary"));
			
		}
	}
	
	@Test
	public void testAddDictionaryItemPage() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			dbConnection.addDictionary(dictionary);
			principal = new Principal() {			
				@Override
				public String getName() {
					return "test";
				}
			};
			assertEquals(dictionaryItemCollabController.addDictionaryPage(getDictionaryID("testDictionary"), model), "auth/dictionaries/addDictionaryItemsCollab");
			String dictionaryId=(String) model.get("dictionaryid");
			assertEquals(dictionaryId, getDictionaryID("testDictionary"));
			dbConnection.deleteDictionary("jdoe", getDictionaryID("testDictionary"));
		}
	}
	
	@Test
	public void testAddDictionaryItem() throws QuadrigaStorageException, QuadrigaAccessException {
		testSetupTestEnvironment();
		{
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			dbConnection.addDictionary(dictionary);
			ICollaborator collaborator = new Collaborator();
			collaborator.setUserObj(userCollab);
			ICollaboratorRole collabRole = new CollaboratorRole();
			collabRole.setRoleDBid("dict_role3");
			List<ICollaboratorRole> roles = new ArrayList<ICollaboratorRole>();
			roles.add(collabRole);
			collaborator.setCollaboratorRoles(roles);
			dbConnection.addCollaborators(collaborator, getDictionaryID("testDictionary"), userCollab.getUserName(), user.getUserName());
			String values ="http://www.digitalhps.org/dictionary/XID-dog-n";
			try{
				mock.setParameter("selected", values);
			}catch(Exception e){
				logger.error("",e);
			}
			DictionaryItem dictionaryItems = new DictionaryItem();
			dictionaryItems.setId(values);
			dictionaryItems.setItems("dog");
			dictionaryItems.setPos("noun");
			dictionaryItems.setDescription("something");
			principal = new Principal() {			
				@Override
				public String getName() {
					return "test";
				}
			};
			assertEquals(dictionaryItemCollabController.addDictionaryItem(mock, getDictionaryID("testDictionary"), dictionaryItems, model, principal), "auth/dictionary/dictionarycollab");
			String dictionaryId=(String) model.get("dictID");
			String dictionaryName=(String) model.get("dictName");
			assertEquals(dictionaryId, getDictionaryID("testDictionary"));
			assertEquals(dictionaryName, "testDictionary");
			List<IDictionaryItem> dictionaryItemList = (List<IDictionaryItem> )model.get("dictionaryItemList");
			Iterator <IDictionaryItem> I =dictionaryItemList.iterator();
			assertEquals(I.hasNext(),true);
			IDictionaryItem di = I.next();
			assertEquals(di.getItems(),"dog");
			assertEquals(di.getPos(),"noun");
			dbConnection.deleteDictionary("jdoe", getDictionaryID("testDictionary"));
		}
	}

}
