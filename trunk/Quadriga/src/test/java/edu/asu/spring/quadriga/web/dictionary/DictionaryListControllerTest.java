package edu.asu.spring.quadriga.web.dictionary;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.impl.dictionary.Dictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.impl.DictionaryManagerTest;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.manageusers.UserController;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DictionaryListControllerTest {

	UserController userContoller;

	DictionaryListController dictionaryListController;

	private Connection connection;

	@Autowired
	private IDictionaryFactory dictionaryFactory;

	private IUser user;

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

	private static final Logger logger = LoggerFactory.getLogger(DictionaryListControllerTest.class);


	@Autowired
	private IUserFactory userFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	

	@Before
	public void setUp() throws Exception {
		userContoller = new UserController();
		userContoller.setUsermanager(usermanager);
		dictionaryListController = new DictionaryListController();
		dictionaryListController.setDictonaryManager(dictonaryManager);
		dictionaryListController.setDictionaryFactory(dictionaryFactory);
		dictionaryListController.setCollabRoleManager(collabRoleManager);
		dictionaryListController.setUsermanager(usermanager);
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

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetupTestEnvironment() throws QuadrigaStorageException
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
			stmt.execute("select dictionaryid from tbl_dictionary where dictionaryName='"+name+"'");
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
	@DirtiesContext
	public void testListDictionary() throws QuadrigaStorageException,Exception {
		testSetupTestEnvironment();
		{
			
			IDictionary dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setDictionaryName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			dbConnection.addDictionary(dictionary);
			assertEquals(dictionaryListController.listDictionary(model,principal),"auth/dictionaries");
			
			logger.info(" --- "+ model.containsAttribute("dictinarylist"));
			List<IDictionary> dictionaryList = (List<IDictionary>) model.get("dictinarylist");
			Iterator<IDictionary> I = dictionaryList.iterator();
			assertEquals(I.hasNext(),true);
			dbConnection.deleteDictionary("jdoe", getDictionaryID("testDictionary"));
		}
		
	}

	@Test
	public void testAddDictionaryForm() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			assertEquals(dictionaryListController.addDictionaryForm(model),"auth/dictionaries/addDictionary");
			IDictionary dictionary= (IDictionary)model.get("dictionary");
			assertEquals(dictionary!=null, true);
		}
	}

	@Test
	public void testAddDictionaryHandle() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			Dictionary dictionary = new Dictionary();
			dictionary.setDictionaryName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			assertEquals(dictionaryListController.addDictionaryHandle(dictionary, model, principal),"auth/dictionaries");
			List<IDictionary> dictionaryList = (List<IDictionary>) model.get("dictinarylist");
			String userId = (String) model.get("userId");
			Iterator<IDictionary> I = dictionaryList.iterator();
			assertEquals(I.hasNext(),true);
			assertEquals(userId,"jdoe");
			dbConnection.deleteDictionary("jdoe", getDictionaryID("testDictionary"));
		}
	}

	@Test
	public void testDeleteDictionaryModel() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			Dictionary dictionary = new Dictionary();
			dictionary.setDictionaryName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			assertEquals(dictionaryListController.addDictionaryHandle(dictionary, model, principal),"auth/dictionaries");
			List<IDictionary> dictionaryList = (List<IDictionary>) model.get("dictinarylist");
			String userId = (String) model.get("userId");
			Iterator<IDictionary> I = dictionaryList.iterator();
			assertEquals(I.hasNext(),true);
			assertEquals(userId,"jdoe");
			assertEquals(dictionaryListController.deleteDictionaryGet(model),"auth/dictionaries/deleteDictionary");
			dictionaryList = (List<IDictionary>) model.get("dictinarylist");
			userId = (String) model.get("userId");
			I = dictionaryList.iterator();
			assertEquals(I.hasNext(),true);
			assertEquals(userId,"jdoe");
			
			dbConnection.deleteDictionary("jdoe", getDictionaryID("testDictionary"));
		}
	}

	@Test
	public void testDeleteDictionaryHttpServletRequestModelMapPrincipal() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			Dictionary dictionary = new Dictionary();
			dictionary.setDictionaryName("testDictionary");
			dictionary.setDescription("description");
			dictionary.setOwner(user);
			assertEquals(dictionaryListController.addDictionaryHandle(dictionary, model, principal),"auth/dictionaries");
			List<IDictionary> dictionaryList = (List<IDictionary>) model.get("dictinarylist");
			String userId = (String) model.get("userId");
			Iterator<IDictionary> I = dictionaryList.iterator();
			assertEquals(I.hasNext(),true);
			assertEquals(userId,"jdoe");
			String values =getDictionaryID("testDictionary");
			try{
				mock.setParameter("selected", values);
			}catch(Exception e){
				logger.error("",e);
			}

			assertEquals(dictionaryListController.deleteDictionary(mock, model, principal),"auth/dictionaries");
			dictionaryList = (List<IDictionary>) model.get("dictinarylist");
			userId = (String) model.get("userId");
			I = dictionaryList.iterator();
			assertEquals(I.hasNext(),false);
			assertEquals(userId,"jdoe");
			
		}
	}


}
