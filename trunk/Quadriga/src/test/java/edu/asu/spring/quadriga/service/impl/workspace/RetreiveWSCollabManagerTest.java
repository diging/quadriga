package edu.asu.spring.quadriga.service.impl.workspace;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workspace.WorkspaceCollaboratorManagerDAO;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml","file:src/test/resources/hibernate.cfg.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RetreiveWSCollabManagerTest {

	@Autowired
	IModifyWSCollabManager dbConnection;

	@Autowired
	IDBConnectionModifyWSCollabManager dbConnect;
	
	@Autowired
	private	IRetrieveWSCollabManager wsCollabManager;

	@Before
	public void setUp() throws Exception {
		String[] databaseQuery = new String[5];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,updatedby,updateddate,createdby,createddate) VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate)  VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','PUBLIC',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate)  VALUES('testprojws1','test workspace','WS_1','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[3] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES('PROJ_2','WS_1','projuser',NOW(),'projuser',NOW())";
		databaseQuery[4] = "INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,updatedby,updateddate,createdby,createddate) VALUES('test project user','projcollab',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		for(String query : databaseQuery)
		{
			((WorkspaceCollaboratorManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[5];
		databaseQuery[0] = "DELETE FROM tbl_project_collaborator WHERE projectid IN ('PROJ_2')";
		databaseQuery[1] = "DELETE FROM tbl_project_workspace WHERE workspaceid IN ('WS_1')";
		databaseQuery[2] = "DELETE FROM tbl_project WHERE projectid IN ('PROJ_2')";
		databaseQuery[3] = "DELETE FROM tbl_workspace WHERE workspaceid IN ('WS_1')";
		databaseQuery[4] = "DELETE FROM tbl_quadriga_user WHERE username IN ('projuser','projcollab')";
		for(String query : databaseQuery)
		{
			((WorkspaceCollaboratorManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}

	@Test
	@Transactional
	public void testAddWorkspaceCollaborator() throws QuadrigaStorageException 
	{
			dbConnect.addWorkspaceCollaborator("projcollab", "role1", "WS_1", "projuser");
			List<ICollaborator> collaborators = wsCollabManager.getWorkspaceCollaborators("WS_1");
			assertEquals(1,collaborators.size());
	}
	
	@Test
	@Transactional
	public void deleteWorkspaceCollaborator() throws QuadrigaStorageException
	{
		dbConnect.deleteWorkspaceCollaborator("projcollab", "WS_1");
		List<ICollaborator> collaborators = wsCollabManager.getWorkspaceCollaborators("WS_1");
		assertEquals(0,collaborators.size());
	}

	@Test
	@Transactional
	public void updateWorkspaceCollaborator() throws QuadrigaStorageException
	{
		dbConnect.updateWorkspaceCollaborator("WS_1","projcollab","role4","projcollab");
		List<ICollaborator> collaborators = wsCollabManager.getWorkspaceCollaborators("projcollab");
		assertEquals(1,collaborators.size());
	}
	
}
