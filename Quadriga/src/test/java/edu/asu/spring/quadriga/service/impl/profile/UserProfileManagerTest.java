package edu.asu.spring.quadriga.service.impl.profile;

import static org.junit.Assert.assertEquals;

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
import org.junit.Ignore;
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
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserProfileManagerTest {
	
	@Autowired
	private IDBConnectionProfileManager dbConnect;
	
	@Autowired
	private ISearchResultBackBeanfactory searchResultBackBeanfactory;
	
	@Autowired
	private IUserFactory userFactory;
	
	private Principal principal;
	
	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;
	
	private Connection connection;
	
	private String sDatabaseSetup [];
	
	@Autowired
	private DataSource dataSource;
	
	private IUser user;
	private SearchResultBackBean searchResultBackBean;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		user = userFactory.createUserObject();
		user.setUserName("test");
		
		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role3");
		roles.add(role);
		IQuadrigaRole role1 = quadrigaRoleFactory.createQuadrigaRoleObject();
		role1.setDBid("role4");
		roles.add(role1);
		
		String[] dbQueries = new String[1];
		dbQueries[0] = "INSERT INTO tbl_quadriga_user VALUES('test','test',null,'tpu@test.com','role3,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		//dbQueries[1] = "INSERT INTO tbl_quadriga_userprofile VALUES('test','edu.asu.viaf','dummy1','http://viaf.org/viaf/1',null,SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		//dbQueries[2] = "INSERT INTO tbl_quadriga_userprofile VALUES('test','edu.asu.viaf','dummy2','http://viaf.org/viaf/2',null,SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		//dbQueries[3] = "INSERT INTO tbl_quadriga_userprofile VALUES('test','edu.asu.viaf','dummy3','http://viaf.org/viaf/3',null,SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
	
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
		
		String dbQueries = "DELETE FROM tbl_quadriga_user";
		dbConnect.setupTestEnvironment(dbQueries);	
	}

	@Test
	public void testAddUserProfile() throws QuadrigaStorageException {
		
			searchResultBackBean = searchResultBackBeanfactory.createSearchResultBackBeanObject();
			
			searchResultBackBean.setDescription("abcd");
			searchResultBackBean.setId("http://viaf.org/viaf/1");
			searchResultBackBean.setWord("dummy1");
			System.out.println(principal.getName());
			dbConnect.addUserProfileDBRequest(principal.getName(),"edu.asu.viaf" , searchResultBackBean);
			
			List<SearchResultBackBean> searchresults = dbConnect.showProfileDBRequest(principal.getName());
			assertEquals(1,searchresults.size());
	
	}

	@Test
	public void testShowUserProfile() throws QuadrigaStorageException {
		
		searchResultBackBean = searchResultBackBeanfactory.createSearchResultBackBeanObject();
		searchResultBackBean.setDescription("abcd");
		searchResultBackBean.setId("http://viaf.org/viaf/1");
		searchResultBackBean.setWord("dummy1");
		System.out.println(principal.getName());
		dbConnect.addUserProfileDBRequest(principal.getName(),"edu.asu.viaf" , searchResultBackBean);
		
		List<SearchResultBackBean> searchresults = dbConnect.showProfileDBRequest(principal.getName());
		assertEquals(1,searchresults.size());
	}

	@Test
	public void testDeleteUserProfile() throws QuadrigaStorageException {
		
		searchResultBackBean = searchResultBackBeanfactory.createSearchResultBackBeanObject();
		searchResultBackBean.setDescription("abcd");
		searchResultBackBean.setId("http://viaf.org/viaf/1");
		searchResultBackBean.setWord("dummy1");
		System.out.println(principal.getName());
		dbConnect.addUserProfileDBRequest(principal.getName(),"edu.asu.viaf" , searchResultBackBean);
		
		List<SearchResultBackBean> searchresults = dbConnect.showProfileDBRequest(principal.getName());

		assertEquals(1,searchresults.size());
		
		for(SearchResultBackBean searchResultBackBean: searchresults)
		dbConnect.deleteUserProfileDBRequest(principal.getName(), "edu.asu.viaf", searchResultBackBean.getId());
		
		searchresults = dbConnect.showProfileDBRequest(principal.getName());
		assertEquals(0,searchresults.size());

	}

	
	@Test
	public void testRetrieveServiceId() throws QuadrigaStorageException {
		
		
		searchResultBackBean = searchResultBackBeanfactory.createSearchResultBackBeanObject();
		searchResultBackBean.setDescription("abcd");
		searchResultBackBean.setId("http://viaf.org/viaf/1");
		searchResultBackBean.setWord("dummy1");
		System.out.println(principal.getName());
		dbConnect.addUserProfileDBRequest(principal.getName(),"edu.asu.viaf" , searchResultBackBean);
		
		String serviceId = dbConnect.retrieveServiceIdRequest("edu.asu.viaf");
		assertEquals("http://viaf.org/viaf/1", serviceId);
		
		
	}

}
