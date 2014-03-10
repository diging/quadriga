package edu.asu.spring.quadriga.dao.profile;

import static org.junit.Assert.*;

import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResultBackBeanfactory;
import edu.asu.spring.quadriga.service.IUserProfileManager;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

@ContextConfiguration(locations={"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProfileManagerDAOTest {
	
	@Autowired
	private IDBConnectionProfileManager dbConnect;
	
	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;
	
	@Autowired
	private ISearchResultBackBeanfactory searchResultBackBeanfactory;
	
	@Autowired
	private IUserProfileManager userProfileManager;
	
	@Autowired
	private DataSource dataSource;
	
	private IUser user;
	private Principal principal;
	private Connection connection;
	private String sDatabaseSetup [];
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		user = userFactory.createUserObject();
		user.setName("test");
		
		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role3");
		roles.add(role);
		IQuadrigaRole role1 = quadrigaRoleFactory.createQuadrigaRoleObject();
		role1.setDBid("role4");
		roles.add(role1);
		
		String[] dbQueries = new String[1];
		dbQueries[0] = "INSERT INTO tbl_quadriga_user VALUES('test','test',null,'tpu@test.com','role3,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
	
		for(String query : dbQueries)
		{
			dbConnect.setupTestEnvironment(query);
		}
	
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
	

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddUserProfileDBRequest() throws QuadrigaStorageException {
				
		SearchResultBackBean searchResultBackBean = searchResultBackBeanfactory.createSearchResultBackBeanObject();
		searchResultBackBean.setDescription("dummydesc");
		searchResultBackBean.setId("dummyid");
		searchResultBackBean.setWord("dummyword");
		searchResultBackBean.setIsChecked(false);
		
		userProfileManager.addUserProfile("test", "edu.asu.viaf", searchResultBackBean);
		List<SearchResultBackBean> resultBackBeans = userProfileManager.showUserProfile("test");
		assertEquals(1, resultBackBeans.size());

	}

	@Test
	public void testShowProfileDBRequest() throws QuadrigaStorageException {
		
		SearchResultBackBean searchResultBackBean = searchResultBackBeanfactory.createSearchResultBackBeanObject();
		searchResultBackBean.setDescription("dummydesc");
		searchResultBackBean.setId("dummyid");
		searchResultBackBean.setWord("dummyword");
		searchResultBackBean.setIsChecked(false);
		
		userProfileManager.addUserProfile("test", "edu.asu.viaf", searchResultBackBean);
		List<SearchResultBackBean> resultBackBeans = userProfileManager.showUserProfile("test");
		assertEquals(1, resultBackBeans.size());

	}

	@Test
	public void testDeleteUserProfileDBRequest() throws QuadrigaStorageException {
		
		SearchResultBackBean searchResultBackBean = searchResultBackBeanfactory.createSearchResultBackBeanObject();
		searchResultBackBean.setDescription("dummydesc");
		searchResultBackBean.setId("dummyid");
		searchResultBackBean.setWord("dummyword");
		searchResultBackBean.setIsChecked(false);
		
		userProfileManager.addUserProfile("test", "edu.asu.viaf", searchResultBackBean);
		List<SearchResultBackBean> resultAfteradd = userProfileManager.showUserProfile("test");
		assertEquals(1, resultAfteradd.size());
		
		userProfileManager.deleteUserProfile("test", "edu.asu.viaf", "dummyid");
		List<SearchResultBackBean> resultAfterDelete = userProfileManager.showUserProfile("test");
		assertEquals(0, resultAfterDelete.size());

	}

	@Test
	public void testRetrieveServiceIdRequest() throws QuadrigaStorageException {

		SearchResultBackBean searchResultBackBean = searchResultBackBeanfactory.createSearchResultBackBeanObject();
		searchResultBackBean.setDescription("dummydesc");
		searchResultBackBean.setId("dummyid");
		searchResultBackBean.setWord("dummyword");
		searchResultBackBean.setIsChecked(false);
		
		userProfileManager.addUserProfile("test", "edu.asu.viaf", searchResultBackBean);
		String serviceid = userProfileManager.retrieveServiceId("dummyid");
		assertEquals("edu.asu.viaf", serviceid);
		
	}

}
