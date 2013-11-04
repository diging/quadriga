package edu.asu.spring.quadriga.service.impl.workbench;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.sql.workbench.DBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjCollabManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml","file:src/test/resources/hibernate.cfg.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RetrieveProjectManagerTest {

	@Autowired
	IDBConnectionRetrieveProjectManager dbConnect;
	
	@Autowired
	IProjectFactory projectFactory;
	
	@Autowired
    private IUserManager userManager;
	
	@Autowired
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private IDBConnectionRetrieveProjCollabManager databaseConnect;
	
	@Autowired
	private ICollaboratorRoleManager roleMapper;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String[] databaseQuery = new String[9];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_quadriga_user VALUES('test project user 1','projuser1',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_quadriga_user VALUES('test project user 2','projuser2',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[3] = "INSERT INTO tbl_quadriga_user VALUES('test project user 3','projuser3',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[4] = "INSERT INTO tbl_project VALUES('testproject1','test case data','testproject1','PROJ_1','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[5] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[6] = "INSERT INTO tbl_project VALUES('testproject4','test case data','testproject4','PROJ_4','projuser3','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[7] = "INSERT INTO tbl_project_collaborator VALUES('PROJ_4','projuser1','collaborator_role1',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[8] = "INSERT INTO tbl_project_collaborator VALUES('PROJ_4','projuser2','collaborator_role2',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		
		for(String query : databaseQuery)
		{
			((DBConnectionRetrieveProjectManager)dbConnect).setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[3];
		databaseQuery[0] = "DELETE FROM tbl_project_collaborator WHERE projectid = 'PROJ_4'";
		databaseQuery[1] = "DELETE FROM tbl_project WHERE projectid IN ('PROJ_1','PROJ_2','PROJ_4')";
		databaseQuery[2] = "DELETE FROM tbl_quadriga_user WHERE username IN ('projuser','projuser1','projuser2','projuser3')";
		for(String query : databaseQuery)
		{
			((DBConnectionRetrieveProjectManager)dbConnect).setupTestEnvironment(query);
		}
	}

	@Test
	public void testGetProjectList() throws QuadrigaStorageException {
		List<IProject> projectList;
		List<IProject> testProjectList = new ArrayList<IProject>();
		IProject project;
		
		projectList = projectManager.getProjectList("projuser");
		
		//create project object with the test data
		project = projectFactory.createProjectObject();
		project.setName("testproject1");
		project.setDescription("test case data");
		project.setUnixName("testproject1");
		project.setInternalid("PROJ_1");
		project.setProjectAccess(EProjectAccessibility.valueOf("ACCESSIBLE"));
		testProjectList.add(project);
		
		project = projectFactory.createProjectObject();
		project.setName("testproject2");
		project.setDescription("test case data");
		project.setUnixName("testproject2");
		project.setInternalid("PROJ_2");
		project.setProjectAccess(EProjectAccessibility.valueOf("ACCESSIBLE"));
		testProjectList.add(project);
		
		if(projectList== null)
		{
			fail();
		}
		
		for(IProject testProject : testProjectList)
		{
			assertTrue(projectList.contains(testProject));
		}
	}

	@Test
	public void testGetProjectDetails() throws QuadrigaStorageException {
		IProject project;
		IProject testProject;
		IUser user;
		List<ICollaborator> collaboratorList;
				
		//create project object with the test data
		project = projectFactory.createProjectObject();
		project.setName("testproject1");
		project.setDescription("test case data");
		project.setUnixName("testproject1");
		project.setInternalid("PROJ_1");
		project.setProjectAccess(EProjectAccessibility.valueOf("ACCESSIBLE"));
		user = userManager.getUserDetails("projuser");
		project.setOwner(user);
		
		//retrieve the collaborators associated with project
		collaboratorList = databaseConnect.getProjectCollaborators("PROJ_1");
		
		//map the collaborators to UI XML values
		for (ICollaborator collaborator : collaboratorList) 
		{
			for (ICollaboratorRole collaboratorRole : collaborator.getCollaboratorRoles()) {
				roleMapper.fillProjectCollaboratorRole(collaboratorRole);
			}
		}
		//assigning the collaborators to the project
		project.setCollaborators(collaboratorList);
		
		testProject = projectManager.getProjectDetails("PROJ_1");
		
		
		if(testProject == null)
		{
			fail();
		}
		
		assertTrue(project.equals(testProject));
	}

	@Test
	@Ignore
	public void testGetCollaboratingUsers() throws QuadrigaStorageException {
		fail("Not implemented");
	}

}
