package edu.asu.spring.quadriga.service.impl.workbench;

import static org.junit.Assert.*;

import java.util.ArrayList;

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

import edu.asu.spring.quadriga.db.sql.workbench.DBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml","file:src/test/resources/hibernate.cfg.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class ModifyProjectManagerTest {

	@Autowired
	IDBConnectionModifyProjectManager dbConnect;
	
	@Autowired
	IProjectFactory projectFactory;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	IModifyProjectManager projectManager;
	
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
		String[] databaseQuery = new String[7];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_project VALUES('testproject3','test case data','testproject3','PROJ_3','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[3] = "INSERT INTO tbl_project VALUES('testproject4','test case data','testproject4','PROJ_4','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[4] = "INSERT INTO tbl_quadriga_user VALUES('test project collab','projcollab',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[5] = "INSERT INTO tbl_project VALUES('testproject5','test case data','testproject5','PROJ_5','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[6] = "INSERT INTO tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,updatedby,updateddate,createdby,createddate) VALUES ('PROJ_5','projcollab','collaborator_role3','projcollab',NOW(),'projcollab',NOW())";
		
		for(String query : databaseQuery)
		{
			((DBConnectionModifyProjectManager)dbConnect).setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[4];
		databaseQuery[0] = "DELETE FROM tbl_project_collaborator WHERE projectid = 'PROJ_5'";
		databaseQuery[1] = "DELETE FROM tbl_project WHERE projectid IN ('PROJ_2','PROJ_3','PROJ_4','PROJ_5')";
		databaseQuery[2] = "DELETE FROM tbl_project WHERE projectowner IN ('projuser','projcollab')";
		databaseQuery[3] = "DELETE FROM tbl_quadriga_user WHERE username IN ('projuser','projcollab')";
		for(String query : databaseQuery)
		{
			((DBConnectionModifyProjectManager)dbConnect).setupTestEnvironment(query);
		}
	}

	@Test
	public void testAddProjectRequest() throws QuadrigaStorageException {
		IProject project;
		IUser owner;
		
		//create project object with the test data
		project = projectFactory.createProjectObject();
		project.setName("testproject1");
		project.setDescription("test case data");
		project.setUnixName("testproject1");
		owner = userFactory.createUserObject();
		owner.setUserName("projuser");
		project.setOwner(owner);
		project.setProjectAccess(EProjectAccessibility.ACCESSIBLE);
		
		projectManager.addProjectRequest(project,"projuser");
		
		assertTrue(true);
	}

	@Test
	public void testUpdateProjectRequest() throws QuadrigaStorageException {
		IProject project;
		String owner;
		
		project = projectFactory.createProjectObject();
		project.setName("testupdateproject");
		project.setDescription("test case data");
		project.setUnixName("testproject2");
		project.setProjectAccess(EProjectAccessibility.ACCESSIBLE);
		project.setInternalid("PROJ_2");
		
		owner = "projuser";
        projectManager.updateProjectRequest("PROJ_2","testupdateproject","test case data",EProjectAccessibility.ACCESSIBLE.name(),"testproject2",owner);
        assertTrue(true);
	}

	@Test
	public void testDeleteProjectRequest() throws QuadrigaStorageException {
		ArrayList<String> projectIdList = new ArrayList<String>();
		projectIdList.add("PROJ_3");
		projectIdList.add("PROJ_4");
		projectManager.deleteProjectRequest(projectIdList);
		assertTrue(true);
	}
	
	@Test
	public void testTransferProjectRequest() throws QuadrigaStorageException
	{
		IProject project;
		String owner;
		
		project = retrieveProjectManager.getProjectDetails("PROJ_5");
		
		projectManager.transferProjectOwnerRequest("PROJ_5", "projuser", "projcollab", "collaborator_role3");
		
		//retrieve the project details
	   project = retrieveProjectManager.getProjectDetails("PROJ_5");
	   
	   if(project == null)
	   {
		   fail();
	   }
	   
	   owner = project.getOwner().getUserName();
	   assertEquals("projcollab",owner);
	}
	
	

}
