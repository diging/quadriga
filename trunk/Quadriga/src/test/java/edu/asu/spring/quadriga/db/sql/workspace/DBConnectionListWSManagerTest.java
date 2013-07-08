package edu.asu.spring.quadriga.db.sql.workspace;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionListWSManagerTest {

	@Autowired
	IDBConnectionListWSManager dbConnect;
	
	@Autowired
	IWorkspaceFactory workspaceFactory;
	
	@Autowired
	IUserManager userManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String[] databaseQuery = new String[10];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','testprojuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','testprojuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_workspace VALUES('testprojws1','test workspace','WS_1','testprojuser',0,0,'testprojuser',NOW(),'testprojuser',NOW())";
		databaseQuery[3] = "INSERT INTO tbl_workspace VALUES('testprojws2','test workspace','WS_2','testprojuser',0,0,'testprojuser',NOW(),'testprojuser',NOW())";
		databaseQuery[4] = "INSERT INTO tbl_workspace VALUES('testprojws3','test workspace','WS_3','testprojuser',1,0,'testprojuser',NOW(),'testprojuser',NOW())";
		databaseQuery[5] = "INSERT INTO tbl_workspace VALUES('testprojws4','test workspace','WS_4','testprojuser',0,1,'testprojuser',NOW(),'testprojuser',NOW())";
		databaseQuery[6] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_1','testprojuser',NOW(),'testprojuser',NOW())";
		databaseQuery[7] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_2','testprojuser',NOW(),'testprojuser',NOW())";
		databaseQuery[8] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_3','testprojuser',NOW(),'testprojuser',NOW())";
		databaseQuery[9] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_4','testprojuser',NOW(),'testprojuser',NOW())";
		for(String query : databaseQuery)
		{
			dbConnect.setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[4];
		databaseQuery[0] = "DELETE FROM tbl_project_workspace WHERE workspaceid IN ('WS_1','WS_2','WS_3','WS_4')";
		databaseQuery[1] = "DELETE FROM tbl_workspace WHERE workspaceid IN ('WS_1','WS_2','WS_3','WS_4')";
		databaseQuery[2] = "DELETE FROM tbl_project WHERE projectid = 'PROJ_2'";
		databaseQuery[3] = "DELETE FROM tbl_quadriga_user WHERE username = 'testprojuser'";
		for(String query : databaseQuery)
		{
			dbConnect.setupTestEnvironment(query);
		}
	}

	@Test
	public void testListWorkspace() throws QuadrigaStorageException 
	{
		IWorkSpace workspace;
		IUser user;
		List<IWorkSpace> workspaceList;
		List<IWorkSpace> testWorkspaceList = new ArrayList<IWorkSpace>();
		
		workspaceList = dbConnect.listWorkspace("PROJ_2");
		
		//create workspace objects
		user = userManager.getUserDetails("testprojuser");
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws1");
		workspace.setDescription("test workspace");
		workspace.setId("WS_1");
		workspace.setOwner(user);
		testWorkspaceList.add(workspace);
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws2");
		workspace.setDescription("test workspace");
		workspace.setId("WS_2");
		workspace.setOwner(user);
		testWorkspaceList.add(workspace);
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws3");
		workspace.setDescription("test workspace");
		workspace.setId("WS_3");
		workspace.setOwner(user);
		testWorkspaceList.add(workspace);
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws4");
		workspace.setDescription("test workspace");
		workspace.setId("WS_4");
		workspace.setOwner(user);
		testWorkspaceList.add(workspace);
		
		if(workspaceList == null)
		{
			fail();
		}
		for(IWorkSpace testworkspace : testWorkspaceList )
		{
			assertTrue(workspaceList.contains(testworkspace));
		}
	}
	
	@Test
	public void testListActiveWorkspace() throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		IUser user;
		List<IWorkSpace> workspaceList;
		List<IWorkSpace> testWorkspaceList = new ArrayList<IWorkSpace>();
		
		workspaceList = dbConnect.listActiveWorkspace("PROJ_2");
		
		//create workspace objects
		user = userManager.getUserDetails("testprojuser");
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws1");
		workspace.setDescription("test workspace");
		workspace.setId("WS_1");
		workspace.setOwner(user);
		testWorkspaceList.add(workspace);
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws2");
		workspace.setDescription("test workspace");
		workspace.setId("WS_2");
		workspace.setOwner(user);
		testWorkspaceList.add(workspace);
		
		if(workspaceList == null)
		{
			fail();
		}
		for(IWorkSpace testworkspace : testWorkspaceList )
		{
			assertTrue(workspaceList.contains(testworkspace));
		}
		
	}
	
	@Test
	public void testListArchivedWorkspace() throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		IUser user;
		List<IWorkSpace> workspaceList;
		List<IWorkSpace> testWorkspaceList = new ArrayList<IWorkSpace>();
		
		workspaceList = dbConnect.listArchivedWorkspace("PROJ_2");
		
		//create workspace objects
		user = userManager.getUserDetails("testprojuser");
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws3");
		workspace.setDescription("test workspace");
		workspace.setId("WS_3");
		workspace.setOwner(user);
		testWorkspaceList.add(workspace);
		
		if(workspaceList == null)
		{
			fail();
		}
		for(IWorkSpace testworkspace : testWorkspaceList )
		{
			assertTrue(workspaceList.contains(testworkspace));
		}
		
	}
	
	@Test
	public void testlistDeactivatedWorkspace() throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		IUser user;
		List<IWorkSpace> workspaceList;
		List<IWorkSpace> testWorkspaceList = new ArrayList<IWorkSpace>();
		
		workspaceList = dbConnect.listDeactivatedWorkspace("PROJ_2");
		
		//create workspace objects
		user = userManager.getUserDetails("testprojuser");
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws4");
		workspace.setDescription("test workspace");
		workspace.setId("WS_4");
		workspace.setOwner(user);
		testWorkspaceList.add(workspace);
		
		if(workspaceList == null)
		{
			fail();
		}
		for(IWorkSpace testworkspace : testWorkspaceList )
		{
			assertTrue(workspaceList.contains(testworkspace));
		}
		
	}
	
	@Test
	public void testgetWorkspaceDetails() throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		IWorkSpace testWorkspace;
		IUser user;
		
		workspace = dbConnect.getWorkspaceDetails("WS_1");
		
		//create workspace objects
		user = userManager.getUserDetails("testprojuser");
		
		testWorkspace = workspaceFactory.createWorkspaceObject();
		testWorkspace.setName("testprojws1");
		testWorkspace.setDescription("test workspace");
		testWorkspace.setId("WS_1");
		testWorkspace.setOwner(user);
		
		if(workspace == null)
		{
			fail();
		}
		assertTrue(workspace.equals(testWorkspace));
		
	}

}
