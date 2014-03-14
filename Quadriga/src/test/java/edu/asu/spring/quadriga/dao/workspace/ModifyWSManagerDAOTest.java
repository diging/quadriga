package edu.asu.spring.quadriga.dao.workspace;

import static org.junit.Assert.*;

import java.util.Date;

import org.hibernate.SessionFactory;
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

import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ModifyWSManagerDAOTest {

	@Autowired
	IDBConnectionModifyWSManager dbConnect;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	IWorkspaceFactory workspaceFactory;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	IDBConnectionListWSManager dbRetrieveConnect;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		//create a quadriga user
		Date date = new Date();
		QuadrigaUserDTO user = new QuadrigaUserDTO();
		user.setUsername("projuser");
		user.setFullname("test project user");
		user.setCreatedby("projuser");
		user.setCreateddate(date);
		user.setUpdatedby("projuser");
		user.setUpdateddate(date);
		user.setEmail("tpu@test.com");
		user.setQuadrigarole("role1,role4");
		sessionFactory.getCurrentSession().save(user);
		
		user = new QuadrigaUserDTO();
		user.setUsername("projcollab");
		user.setFullname("test project collab user");
		user.setCreatedby("projcollab");
		user.setCreateddate(date);
		user.setUpdatedby("projcollab");
		user.setUpdateddate(date);
		user.setEmail("tpucollab@test.com");
		user.setQuadrigarole("role1,role4");
		sessionFactory.getCurrentSession().save(user);
		
		//create a project
		ProjectDTO project = new ProjectDTO();
		project.setProjectid("PROJ_1_Test");
		project.setProjectname("testproject1");
		project.setAccessibility("PUBLIC");
		project.setCreatedby("projuser");
		project.setCreateddate(date);
		project.setUpdatedby("projuser");
		project.setUpdateddate(date);
		project.setUnixname("PROJ_1");
		project.setProjectowner(user);
		sessionFactory.getCurrentSession().save(project);
		
		//create a workspace
		WorkspaceDTO workspace = new WorkspaceDTO();
		workspace.setWorkspaceid("WS_1_Test");
		workspace.setWorkspacename("WS_1");
		workspace.setWorkspaceowner(user);
		workspace.setCreatedby("projuser");
		workspace.setCreateddate(date);
		workspace.setUpdatedby("projuser");
		workspace.setUpdateddate(date);
		workspace.setIsarchived(false);
		workspace.setIsdeactivated(false);
        sessionFactory.getCurrentSession().save(workspace);
        
		workspace = new WorkspaceDTO();
		workspace.setWorkspaceid("WS_2_Test");
		workspace.setWorkspacename("WS_2");
		workspace.setWorkspaceowner(user);
		workspace.setCreatedby("projuser");
		workspace.setCreateddate(date);
		workspace.setUpdatedby("projuser");
		workspace.setUpdateddate(date);
		workspace.setIsarchived(false);
		workspace.setIsdeactivated(false);
        sessionFactory.getCurrentSession().save(workspace);
        
		workspace = new WorkspaceDTO();
		workspace.setWorkspaceid("WS_3_Test");
		workspace.setWorkspacename("WS_3");
		workspace.setWorkspaceowner(user);
		workspace.setCreatedby("projuser");
		workspace.setCreateddate(date);
		workspace.setUpdatedby("projuser");
		workspace.setUpdateddate(date);
		workspace.setIsarchived(false);
		workspace.setIsdeactivated(false);
        sessionFactory.getCurrentSession().save(workspace);
        
        //create project workspace mapping
        ProjectWorkspaceDTO projectWorkspace = new ProjectWorkspaceDTO();
        ProjectWorkspaceDTOPK projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_1_Test");
        projectWorkspace.setProjectWorkspaceDTOPK(projectWorkspaceKey);
        project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_1_Test");
        projectWorkspace.setProjectDTO(project);
        workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_1_Test");
        projectWorkspace.setWorkspaceDTO(workspace);
        projectWorkspace.setCreatedby("projuser");
        projectWorkspace.setCreateddate(date);
        projectWorkspace.setUpdatedby("projuser");
        projectWorkspace.setUpdateddate(date);
        sessionFactory.getCurrentSession().save(projectWorkspace);
        
        projectWorkspace = new ProjectWorkspaceDTO();
        projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_2_Test");
        projectWorkspace.setProjectWorkspaceDTOPK(projectWorkspaceKey);
        project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_1_Test");
        projectWorkspace.setProjectDTO(project);
        workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_2_Test");
        projectWorkspace.setWorkspaceDTO(workspace);
        projectWorkspace.setCreatedby("projuser");
        projectWorkspace.setCreateddate(date);
        projectWorkspace.setUpdatedby("projuser");
        projectWorkspace.setUpdateddate(date);
        sessionFactory.getCurrentSession().save(projectWorkspace);
        
        projectWorkspace = new ProjectWorkspaceDTO();
        projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_3_Test");
        projectWorkspace.setProjectWorkspaceDTOPK(projectWorkspaceKey);
        project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_1_Test");
        projectWorkspace.setProjectDTO(project);
        workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_3_Test");
        projectWorkspace.setWorkspaceDTO(workspace);
        projectWorkspace.setCreatedby("projuser");
        projectWorkspace.setCreateddate(date);
        projectWorkspace.setUpdatedby("projuser");
        projectWorkspace.setUpdateddate(date);
        sessionFactory.getCurrentSession().save(projectWorkspace);
        
        
		
		
		String[] databaseQuery = new String[14];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_project VALUES('testproject2','test case data','testproject2','PROJ_2','projuser','ACCESSIBLE',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_workspace VALUES('testprojws1','test workspace','WS_1','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[3] = "INSERT INTO tbl_workspace VALUES('testprojws3','test workspace','WS_3','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[4] = "INSERT INTO tbl_workspace VALUES('testprojws4','test workspace','WS_4','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[5] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_1','projuser',NOW(),'projuser',NOW())";
		databaseQuery[6] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_3','projuser',NOW(),'projuser',NOW())";
		databaseQuery[7] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_4','projuser',NOW(),'projuser',NOW())";
		databaseQuery[8] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projcollab',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[9] = "INSERT INTO tbl_workspace VALUES('testprojws5','test workspace','WS_5','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[10] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_5','projuser',NOW(),'projuser',NOW())";
		databaseQuery[11] = "INSERT INTO tbl_workspace VALUES('testprojws6','test workspace','WS_6','projuser',0,0,'projuser',NOW(),'projuser',NOW())";
		databaseQuery[12] = "INSERT INTO tbl_project_workspace VALUES('PROJ_2','WS_6','projuser',NOW(),'projuser',NOW())";
		databaseQuery[13] = "INSERT INTO tbl_workspace_collaborator(workspaceid,username,collaboratorrole,updatedby,updateddate,createdby,createddate) VALUES('WS_6','projcollab','wscollab_role1','projuser',NOW(),'proj_user',NOW())";
		
		for(String query : databaseQuery)
		{
			((ModifyWSManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[6];
		databaseQuery[0] = "DELETE FROM tbl_workspace_collaborator WHERE workspaceid = 'WS_6'";
		databaseQuery[1] = "DELETE FROM tbl_project_workspace WHERE projectid = 'PROJ_2'";
		databaseQuery[2] = "DELETE FROM tbl_workspace WHERE workspaceid IN ('WS_1','WS_3','WS_4','WS_5','WS_6')";
		databaseQuery[3] = "DELETE FROM tbl_workspace where workspaceowner IN ('projuser','projcollab')";
		databaseQuery[4] = "DELETE FROM tbl_project WHERE projectid = 'PROJ_2'";
		databaseQuery[5] = "DELETE FROM tbl_quadriga_user WHERE username IN ('projuser','projcollab')";
		for(String query : databaseQuery)
		{
			((ModifyWSManagerDAO)dbConnect).setupTestEnvironment(query);
		}
	}

	@Test
	public void testAddWorkSpace() throws QuadrigaStorageException {
		IUser user;
		IWorkSpace workspace;
		
		//create workspace objects
		user = userManager.getUserDetails("projuser");
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws2");
		workspace.setDescription("test workspace");
		workspace.setOwner(user);
		
		dbConnect.addWorkSpaceRequest(workspace, "PROJ_2");
	}
	
	@Test
	public void testDeleteWorkspace() throws QuadrigaStorageException
	{
		String errmsg;
		
		errmsg = dbConnect.deleteWorkspaceRequest("WS_3,WS_4");
		assertEquals("",errmsg);
		
	}
	
	@Test
	public void testUpdateWorkspace() throws QuadrigaStorageException
	{
		String errmsg;
		IWorkSpace workspace = workspaceFactory.createWorkspaceObject();
		IUser user = userFactory.createUserObject();
		user.setUserName("projuser");
		workspace.setName("testprojws51");
		workspace.setDescription("test workspace");
		workspace.setOwner(user);
		workspace.setId("WS_5");
		errmsg = dbConnect.updateWorkspaceRequest(workspace);
	    assertEquals("",errmsg);
	}

	public void testTransferWorkspace() throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		String owner;
		dbConnect.transferWSOwnerRequest("WS_6", "projuser", "projcollab", "wscollab_role1");
		
		//retrieve the workspace details
		workspace = dbRetrieveConnect.getWorkspaceDetails("WS_6", "projuser");
		
		owner = workspace.getOwner().getUserName();
		
		assertEquals("projcollab",owner);
	}
	
}
