package edu.asu.spring.quadriga.service.impl.profile;

import static org.junit.Assert.*;

import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.service.IUserProfileManager;
import edu.asu.spring.quadriga.service.profile.ISearchResultBackBeanFormManager;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

@ContextConfiguration(locations={
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ViafServiceTest {

	@Autowired
	private ISearchResult searchResult;
	
	@Autowired
	private IDBConnectionProfileManager dbConnect;
	
	@Autowired
	private ISearchResultFactory searchResultFactory;
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;
	
	@Autowired
	private IUserProfileManager userProfileManager;
	
	@Autowired
	private ISearchResultBackBeanFormManager backBeanFormManager;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private IService viafService;
	
	@Inject
	@Named("restTemplateViaf")
	private RestTemplate restTemplate;

	@Autowired
	@Qualifier("viafURL")
	private String viafURL;

	@Autowired
	@Qualifier("searchViafURLPath")
	private String searchViafURLPath;
	
	@Autowired
	@Qualifier("searchViafURLPath1")
	private String searchViafURLPath1;
	
	@Autowired
	@Qualifier("searchViafURLPath2")
	private String searchViafURLPath2;
	
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
		
		String[] dbQueries = new String[3];
		dbQueries[0] = "INSERT INTO tbl_quadriga_user VALUES('test','test',null,'tpu@test.com','role3,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		dbQueries[1] = "INSERT INTO tbl_quadriga_userprofile VALUES('test','edu.asu.viaf','Tennant, Jeff','http://viaf.org/viaf/41080217','Mon, 14 Feb 2014 18:24:06 GMT',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		dbQueries[2] = "INSERT INTO tbl_quadriga_userprofile VALUES('test','edu.asu.viaf','Tennant, Jeff','http://viaf.org/viaf/207603897','Mon, 14 Feb 2014 18:24:06 GMT',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
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
		
		String[] dbQueries = new String[2];
		String dbQuery = "DELETE FROM tbl_quadriga_user";
		String dbQuery1 = "DELETE FROM tbl_quadriga_userprofile";
		
		dbConnect.setupTestEnvironment(dbQuery);
		dbConnect.setupTestEnvironment(dbQuery1);
	}

	@Test
	public void testSearch() throws QuadrigaStorageException {
		
		
		List<SearchResultBackBean> backBeansList =  userProfileManager.showUserProfile("test");
		
		List<SearchResultBackBean> viafResults = backBeanFormManager.getsearchResultBackBeanList("edu.asu.viaf","jeff tennant");
	
		assertEquals(backBeansList.size(),viafResults.size());
		
	}

}
