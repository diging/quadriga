package edu.asu.spring.quadriga.db.sql.workbench;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionModifyProjectManagerTest {

	@Autowired
	IDBConnectionModifyProjectManager dbConnect;
	
	@Autowired
	IProjectFactory projectFactory;
	
	@Autowired
	IUserFactory userFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String[] databaseQuery = new String[4];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','testprojuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','testprojuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_project VALUES('testproject3','test case data','testproject3','PROJ_3','testprojuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[3] = "INSERT INTO tbl_project VALUES('testproject4','test case data','testproject4','PROJ_4','testprojuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		for(String query : databaseQuery)
		{
			dbConnect.setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[2];
		databaseQuery[0] = "DELETE FROM tbl_project WHERE projectid IN ('PROJ_2','PROJ_3','PROJ_4')";
		databaseQuery[1] = "DELETE FROM tbl_quadriga_user WHERE username = 'testprojuser'";
		for(String query : databaseQuery)
		{
			dbConnect.setupTestEnvironment(query);
		}	
	}

	@Test
	public void testAddProject() throws QuadrigaStorageException
	{
		IProject project;
		IUser owner;
		String errmsg;
		
		//create project object with the test data
		project = projectFactory.createProjectObject();
		project.setName("testproject1");
		project.setDescription("test case data");
		project.setUnixName("testproject1");
		owner = userFactory.createUserObject();
		owner.setUserName("testprojuser");
		project.setOwner(owner);
		project.setProjectAccess(EProjectAccessibility.ACCESSIBLE);
		
		errmsg = dbConnect.addProjectRequest(project);
		
		assertEquals("",errmsg);

	}
	
	@Test
	public void testUpdateProject() throws QuadrigaStorageException
	{
		IProject project;
		String owner;
		String errmsg;
		
		project = projectFactory.createProjectObject();
		project.setName("testupdateproject");
		project.setDescription("test case data");
		project.setUnixName("testproject2");
		project.setProjectAccess(EProjectAccessibility.ACCESSIBLE);
		project.setInternalid("PROJ_2");
		
		owner = "testprojuser";
        errmsg = dbConnect.updateProjectRequest(project, owner);
        
        assertEquals("",errmsg);
		
	}
	
	@Test
	public void testDeleteProject() throws QuadrigaStorageException
	{
		String projectIdList;
		String errmsg;
		projectIdList = "PROJ_3,PROJ_4";
		errmsg = dbConnect.deleteProjectRequest(projectIdList);
		assertEquals("",errmsg);
	}

}
