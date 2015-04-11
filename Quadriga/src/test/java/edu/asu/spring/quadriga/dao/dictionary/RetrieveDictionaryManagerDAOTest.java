//package edu.asu.spring.quadriga.dao.dictionary;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import edu.asu.spring.quadriga.db.dictionary.IDBConnectionRetrieveDictionaryManager;
//import edu.asu.spring.quadriga.domain.IQuadrigaRole;
//import edu.asu.spring.quadriga.domain.IUser;
//import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
//import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
//import edu.asu.spring.quadriga.domain.factories.IUserFactory;
//import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
//import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
//import edu.asu.spring.quadriga.web.login.RoleNames;
//
//@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
//"file:src/test/resources/hibernate.cfg.xml",
//"file:src/test/resources/root-context.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration
//@Transactional
//public class RetrieveDictionaryManagerDAOTest {
//	
//	@Autowired
//	private IDBConnectionRetrieveDictionaryManager dbConnect;
//	
//	private IUser user;
//	
//	@Autowired
//	private IUserFactory userFactory;
//	
//	@Autowired
//	private IQuadrigaRoleFactory quadrigaRoleFactory;
//	
//	@Autowired
//	private IQuadrigaRoleManager rolemanager;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		String[] databaseQuery = new String[3];
//		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
//		databaseQuery[1] = "INSERT INTO tbl_quadriga_user VALUES('test project user2','projuser2',null,'tpu2@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
//		databaseQuery[2] = "INSERT INTO tbl_dictionary VALUES('TEST_DICT','TEST_DESC','TEST_DICT_ID','projuser','ACCESSIBLE','proj_user',NOW(),'proj_user',NOW())";
//		
//		for(String query : databaseQuery)
//		{
//			((ModifyDictionaryManagerDAO)dbConnect).setupTestEnvironment(query);
//		}
//		
//		user = userFactory.createUserObject();
//		user.setUserName("projuser");
//		user.setName("projuser");
//
//		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
//		IQuadrigaRole role = quadrigaRoleFactory.createQuadrigaRoleObject();
//		role.setDBid("role1");
//		roles.add(role);
//
//		IQuadrigaRole quadrigaRole = null;
//		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
//		for(int i=0;i<roles.size();i++)
//		{
//			quadrigaRole = rolemanager.getQuadrigaRole(roles.get(i).getDBid());
//
//			//If user account is deactivated remove other roles 
//			if(quadrigaRole.getId().equals(RoleNames.ROLE_QUADRIGA_DEACTIVATED))
//			{
//				rolesList.clear();
//			}
//			rolesList.add(quadrigaRole);
//		}
//		user.setQuadrigaRoles(rolesList);
//	}
//	@After
//	public void tearDown() throws Exception {
//		String[] databaseQuery = new String[3];
//		databaseQuery[0] = "DELETE FROM tbl_dictionary_collaborator WHERE dictionaryid IN ('TEST_DICT_ID')";
//		databaseQuery[1] = "DELETE FROM tbl_dictionary WHERE dictionaryid IN ('TEST_DICT_ID')";
//		databaseQuery[2] = "DELETE FROM tbl_quadriga_user WHERE username IN ('projuser','projuser2')";
//		for(String query : databaseQuery)
//		{
//			((ModifyDictionaryManagerDAO)dbConnect).setupTestEnvironment(query);
//		}
//	}
//
//	@Test
//	public void getDictionaryDetails() throws QuadrigaStorageException 
//	{
//		IDictionary dictionary = dbConnect.getDictionaryDetails("TEST_DICT_ID");
//		assertEquals("TEST_DICT_ID", dictionary.getDictionaryId());
//	}
//	
//}
