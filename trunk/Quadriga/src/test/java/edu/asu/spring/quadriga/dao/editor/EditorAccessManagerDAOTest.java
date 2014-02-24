package edu.asu.spring.quadriga.dao.editor;

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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.editor.EditorAccessManagerDAO;
import edu.asu.spring.quadriga.db.editor.IDBConnectionEditorAccessManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/hibernate.cfg.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class EditorAccessManagerDAOTest {
	
	@Autowired
	private IDBConnectionEditorAccessManager dbConnect;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {

		String[] databaseQuery = new String[4];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_quadriga_user VALUES('test project collab','projcollab',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[3] = "INSERT INTO tbl_project_editor VALUES('PROJ_2','projuser','projuser',NOW(),'projuser',NOW())";
		
		for(String query : databaseQuery)
		{
			((EditorAccessManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[3];
		databaseQuery[0] = "DELETE FROM tbl_project_editor WHERE projectid IN ('PROJ_2')";
		databaseQuery[1] = "DELETE FROM tbl_project WHERE projectid IN ('PROJ_2')";
		databaseQuery[2] = "DELETE FROM tbl_quadriga_user WHERE username IN ('projuser','projcollab')";
		
		for(String query : databaseQuery)
		{
			((EditorAccessManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}	
	
	@Test
	public void chkIsNetworkEditor() throws QuadrigaStorageException
	{
	
	}
	
	@Test
	public void chkIsEditor() throws QuadrigaStorageException
	{
		Boolean result = dbConnect.chkIsEditor("projuser");
		assertEquals(Boolean.TRUE, result);
	}
	
}
