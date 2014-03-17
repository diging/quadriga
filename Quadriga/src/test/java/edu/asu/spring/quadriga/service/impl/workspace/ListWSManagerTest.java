package edu.asu.spring.quadriga.service.impl.workspace;

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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workspace.ListWSManagerDAO;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" ,"file:src/test/resources/hibernate.cfg.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class ListWSManagerTest {

	@Autowired
	IDBConnectionListWSManager dbConnect;
	
	@Autowired
	IWorkspaceFactory workspaceFactory;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	IListWSManager wsManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String[] databaseQuery = new String[10];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,updatedby,updateddate,createdby,createddate) VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','PUBLIC',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES('testprojws1','test workspace','WS_1','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[3] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES('testprojws2','test workspace','WS_2','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[4] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES('testprojws3','test workspace','WS_3','projuser',1,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[5] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES('testprojws4','test workspace','WS_4','projuser',0,1,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[6] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES('PROJ_2','WS_1','projuser',NOW(),'projuser',NOW())";
		databaseQuery[7] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES('PROJ_2','WS_2','projuser',NOW(),'projuser',NOW())";
		databaseQuery[8] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES('PROJ_2','WS_3','projuser',NOW(),'projuser',NOW())";
		databaseQuery[9] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES('PROJ_2','WS_4','projuser',NOW(),'projuser',NOW())";
		for(String query : databaseQuery)
		{
			((ListWSManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[4];
		databaseQuery[0] = "DELETE FROM tbl_project_workspace WHERE workspaceid IN ('WS_1','WS_2','WS_3','WS_4')";
		databaseQuery[1] = "DELETE FROM tbl_workspace WHERE workspaceid IN ('WS_1','WS_2','WS_3','WS_4')";
		databaseQuery[2] = "DELETE FROM tbl_project WHERE projectid = 'PROJ_2'";
		databaseQuery[3] = "DELETE FROM tbl_quadriga_user WHERE username = 'projuser'";
		for(String query : databaseQuery)
		{
			((ListWSManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}

	@Test
	public void testListWorkspace() throws QuadrigaStorageException {
		IWorkSpace workspace;
		IUser user;
		List<IWorkSpace> workspaceList;
		List<IWorkSpace> testWorkspaceList = new ArrayList<IWorkSpace>();
		
		workspaceList = dbConnect.listWorkspace("PROJ_2","projuser");
		
		//create workspace objects
		user = userManager.getUserDetails("projuser");
		
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
	public void testListActiveWorkspace() throws QuadrigaStorageException {
		IWorkSpace workspace;
		IUser user;
		List<IWorkSpace> workspaceList;
		List<IWorkSpace> testWorkspaceList = new ArrayList<IWorkSpace>();
		
		workspaceList = wsManager.listActiveWorkspace("PROJ_2","projuser");
		
		//create workspace objects
		user = userManager.getUserDetails("projuser");
		
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
	public void testListArchivedWorkspace() throws QuadrigaStorageException {
		IWorkSpace workspace;
		IUser user;
		List<IWorkSpace> workspaceList;
		List<IWorkSpace> testWorkspaceList = new ArrayList<IWorkSpace>();
		
		workspaceList = wsManager.listArchivedWorkspace("PROJ_2","projuser");
		
		//create workspace objects
		user = userManager.getUserDetails("projuser");
		
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
	public void testListDeactivatedWorkspace() throws QuadrigaStorageException {
		IWorkSpace workspace;
		IUser user;
		List<IWorkSpace> workspaceList;
		List<IWorkSpace> testWorkspaceList = new ArrayList<IWorkSpace>();
		
		workspaceList = wsManager.listDeactivatedWorkspace("PROJ_2","projuser");
		
		//create workspace objects
		user = userManager.getUserDetails("projuser");
		
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
	public void testGetWorkspaceDetails() throws QuadrigaStorageException, QuadrigaAccessException {
		IWorkSpace workspace;
		IWorkSpace testWorkspace;
		IUser user;
		
		workspace = wsManager.getWorkspaceDetails("WS_1","projuser");
		
		//create workspace objects
		user = userManager.getUserDetails("projuser");
		
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
