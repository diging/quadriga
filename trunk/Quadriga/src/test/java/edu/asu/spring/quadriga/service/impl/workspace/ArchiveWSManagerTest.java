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

import edu.asu.spring.quadriga.db.sql.workspace.DBConnectionArchiveWSManger;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionArchiveWSManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IArchiveWSManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
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
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_workspace VALUES('testprojws1','test workspace','WS_1','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[3] = "INSERT INTO tbl_workspace VALUES('testprojws2','test workspace','WS_2','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[4] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_1','projuser',NOW(),'projuser',NOW())";
		databaseQuery[5] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_2','projuser',NOW(),'projuser',NOW())";
		for(String query : databaseQuery)
		{
			((DBConnectionArchiveWSManger)dbConnect).setupTestEnvironment(query);
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
			((DBConnectionArchiveWSManger)dbConnect).setupTestEnvironment(query);
		}
	}

	@Test
	public void testArchiveWorkspace() throws QuadrigaStorageException {
		String errmsg;
		errmsg = wsManager.archiveWorkspace("WS_1", "projuser");
		assertEquals("",errmsg);
	}

	@Test
	public void testUnArchiveWorkspace() throws QuadrigaStorageException {
		String errmsg;
		errmsg = wsManager.unArchiveWorkspace("WS_1", "projuser");
		assertEquals("",errmsg);
	}

	@Test
	public void testDeactivateWorkspace() throws QuadrigaStorageException {
		String errmsg;
		errmsg = wsManager.deactivateWorkspace("WS_2","projuser");
		assertEquals("",errmsg);
	}

	@Test
	public void testActivateWorkspace() throws QuadrigaStorageException {
		String errmsg;
		errmsg = wsManager.activateWorkspace("WS_2","projuser");
		assertEquals("",errmsg);
	}

}
