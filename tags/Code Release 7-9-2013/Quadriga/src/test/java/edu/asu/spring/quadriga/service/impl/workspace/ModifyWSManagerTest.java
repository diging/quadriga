package edu.asu.spring.quadriga.service.impl.workspace;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManger;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ModifyWSManagerTest {

	@Autowired
	IDBConnectionModifyWSManger dbConnect;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	IWorkspaceFactory workspaceFactory;
	
	@Autowired
	IModifyWSManager wsManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String[] databaseQuery = new String[8];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_workspace VALUES('testprojws1','test workspace','WS_1','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[3] = "INSERT INTO tbl_workspace VALUES('testprojws3','test workspace','WS_3','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[4] = "INSERT INTO tbl_workspace VALUES('testprojws4','test workspace','WS_4','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[5] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_1','projuser',NOW(),'projuser',NOW())";
		databaseQuery[6] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_3','projuser',NOW(),'projuser',NOW())";
		databaseQuery[7] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_4','projuser',NOW(),'projuser',NOW())";
		for(String query : databaseQuery)
		{
			dbConnect.setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[4];
		databaseQuery[0] = "DELETE FROM tbl_project_workspace WHERE projectid = 'PROJ_2'";
		databaseQuery[1] = "DELETE FROM tbl_workspace WHERE workspaceid IN ('WS_1','WS_3','WS_4')";
		databaseQuery[1] = "DELETE FROM tbl_workspace where workspaceowner = 'projuser'";
		databaseQuery[2] = "DELETE FROM tbl_project WHERE projectid = 'PROJ_2'";
		databaseQuery[3] = "DELETE FROM tbl_quadriga_user WHERE username = 'projuser'";
		for(String query : databaseQuery)
		{
			dbConnect.setupTestEnvironment(query);
		}
	}

	@Test
	public void testAddWorkSpaceRequest() throws QuadrigaStorageException {
		IUser user;
		IWorkSpace workspace;
		String errmsg;
		
		//create workspace objects
		user = userManager.getUserDetails("projuser");
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws2");
		workspace.setDescription("test workspace");
		workspace.setOwner(user);
		
		errmsg = wsManager.addWorkSpaceRequest(workspace, "PROJ_2");
		
		assertEquals("",errmsg);
	}

	@Test
	public void testDeleteWorkspaceRequest() throws QuadrigaStorageException {
		String errmsg;
		
		errmsg = wsManager.deleteWorkspaceRequest("WS_3,WS_4");
		assertEquals("",errmsg);
	}

}
