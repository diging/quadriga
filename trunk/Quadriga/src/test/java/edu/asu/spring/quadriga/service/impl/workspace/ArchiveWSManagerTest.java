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
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionArchiveWSManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IArchiveWSManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ArchiveWSManagerTest {

	@Autowired
	IDBConnectionArchiveWSManager  dbConnect;
	
	@Autowired
	IArchiveWSManager wsManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String[] databaseQuery = new String[6];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,updatedby,updateddate,createdby,createddate) VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','PUBLIC',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES('testprojws1','test workspace','WS_1','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[3] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES('testprojws2','test workspace','WS_2','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[4] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES('PROJ_2','WS_1','projuser',NOW(),'projuser',NOW())";
		databaseQuery[5] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES('PROJ_2','WS_2','projuser',NOW(),'projuser',NOW())";
		for(String query : databaseQuery)
		{
			dbConnect.setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[4];
		databaseQuery[0] = "DELETE FROM tbl_project_workspace WHERE workspaceid IN ('WS_1','WS_2')";
		databaseQuery[1] = "DELETE FROM tbl_workspace WHERE workspaceid IN ('WS_1','WS_2')";
		databaseQuery[2] = "DELETE FROM tbl_project WHERE projectid = 'PROJ_2'";
		databaseQuery[3] = "DELETE FROM tbl_quadriga_user WHERE username = 'projuser'";
		for(String query : databaseQuery)
		{
			dbConnect.setupTestEnvironment(query);
		}
	}

	@Test
	@Transactional
	public void testArchiveWorkspace() throws QuadrigaStorageException {
		wsManager.archiveWorkspace("WS_1", "projuser");
		assertTrue(true);
	}

	@Test
	@Transactional
	public void testUnArchiveWorkspace() throws QuadrigaStorageException {
		wsManager.unArchiveWorkspace("WS_1", "projuser");
		assertTrue(true);
	}

	@Test
	@Transactional
	public void testDeactivateWorkspace() throws QuadrigaStorageException {
		wsManager.deactivateWorkspace("WS_2","projuser");
		assertTrue(true);
	}

	@Test
	@Transactional
	public void testActivateWorkspace() throws QuadrigaStorageException {
		wsManager.activateWorkspace("WS_2","projuser");
		assertTrue(true);
	}

}
