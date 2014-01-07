package edu.asu.spring.quadriga.dao.workbench;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.RetrieveProjectManagerDAO;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/hibernate.cfg.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class RetrieveProjectManagerDAOTest {
	
	@Autowired
	IProjectFactory projectFactory;
	
	@Autowired
    private IUserManager userManager;
	
	@Autowired
	IRetrieveProjectManager retrieveProjectManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String[] databaseQuery = new String[3];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project VALUES('testproject1','test case data','testproject1','PROJ_1','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		for(String query : databaseQuery)
		{
			((RetrieveProjectManagerDAO)retrieveProjectManager).setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[2];
		databaseQuery[0] = "DELETE FROM tbl_project WHERE projectid IN ('PROJ_1','PROJ_2')";
		databaseQuery[1] = "DELETE FROM tbl_quadriga_user WHERE username = 'projuser'";
		for(String query : databaseQuery)
		{
			((RetrieveProjectManagerDAO)retrieveProjectManager).setupTestEnvironment(query);
		}		
	}

	/*@Test
	public void testgetProjectList() throws QuadrigaStorageException
	{
		List<IProject> projectList;
		List<IProject> testProjectList = new ArrayList<IProject>();
		IProject project;
		
		projectList = retrieveProjectManagerDAO.getProjectList("projuser",DBConstants.PROJECT_LIST_AS_OWNER);
		
		//create project object with the test data
		project = projectFactory.createProjectObject();
		project.setName("testproject1");
		project.setDescription("test case data");
		project.setUnixName("testproject1");
		project.setInternalid("PROJ_1");
		testProjectList.add(project);
		
		project = projectFactory.createProjectObject();
		project.setName("testproject2");
		project.setDescription("test case data");
		project.setUnixName("testproject2");
		project.setInternalid("PROJ_2");
		testProjectList.add(project);
		
		if(projectList== null)
		{
			fail();
		}
		
		for(IProject testProject : testProjectList)
		{
			assertTrue(projectList.contains(testProject));
		}
	}*/
	
	@Test
	public void testgetProjectDetails() throws QuadrigaStorageException
	{
		IProject project;
		IProject testProject;
		IUser user;
		
		//create project object with the test data
		project = projectFactory.createProjectObject();
		project.setName("testproject1");
		project.setDescription("test case data");
		project.setUnixName("testproject1");
		project.setInternalid("PROJ_1");
		project.setProjectAccess(EProjectAccessibility.valueOf("ACCESSIBLE"));
		user = userManager.getUserDetails("projuser");
		project.setOwner(user);
		testProject = retrieveProjectManager.getProjectDetails("PROJ_1");
		if(testProject == null)
		{
			fail();
		}
		
		assertTrue(project.equals(testProject));
	}

}
