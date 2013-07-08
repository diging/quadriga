package edu.asu.spring.quadriga.db.sql.workspace;

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

import edu.asu.spring.quadriga.db.workspace.IDBConnectionArchiveWSManger;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionArchiveWSMangerTest {

	@Autowired
	IDBConnectionArchiveWSManger  dbConnect;
	
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
		databaseQuery[6] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_1','testprojuser',NOW(),'testprojuser',NOW())";
		databaseQuery[7] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_2','testprojuser',NOW(),'testprojuser',NOW())";
		for(String query : databaseQuery)
		{
			dbConnect.setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArchiveWorkspace() throws QuadrigaStorageException 
	{
		String errmsg;
		errmsg = dbConnect.archiveWorkspace("WS_1", 1, "testprojuser");
		assertEquals("",errmsg);
	}
	
	@Test
	public void testArchiveActivate() throws QuadrigaStorageException
	{
		String errmsg;
		errmsg = dbConnect.archiveWorkspace("WS_1", 0, "testprojuser");
		assertEquals("",errmsg);
	}
	
	@Test
	public void testDeactivateWorkspace() throws QuadrigaStorageException
	{
		String errmsg;
		errmsg = dbConnect.deactivateWorkspace("WS_2", 1, "testprojuser");
		assertEquals("",errmsg);
	}
	
	@Test
	public void testActivateWorkspace() throws QuadrigaStorageException
	{
		String errmsg;
		errmsg = dbConnect.deactivateWorkspace("WS_2", 0, "testprojuser");
		assertEquals("",errmsg);
	}

}
