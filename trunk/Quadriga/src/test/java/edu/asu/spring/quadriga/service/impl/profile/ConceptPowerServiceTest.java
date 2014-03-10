package edu.asu.spring.quadriga.service.impl.profile;

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
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.service.IUserProfileManager;
import edu.asu.spring.quadriga.service.profile.ISearchResultBackBeanFormManager;

@ContextConfiguration(locations={"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConceptPowerServiceTest {
	
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
	private IService conceptPowerService;
	
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
	}

	@Test
	public void testSearch() {
		
		List<ISearchResult> actualSearchResultList = new ArrayList<ISearchResult>();
		ISearchResult searchResult = searchResultFactory.getSearchResultObject();
		searchResult.setDescription("massive powerful herbivorous odd-toed ungulate of southeast Asia and"
				                     +"Africa having very thick skin and one or two horns on the snout");
		searchResult.setId("WID-02391994-N-02-rhino");
		searchResult.setName("rhino");
		actualSearchResultList.add(searchResult);
		
		List<ISearchResult> expectedSearchResultList = conceptPowerService.search("rhino");
		
		assertEquals(actualSearchResultList.size(), expectedSearchResultList.size());
	}

}
