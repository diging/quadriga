package edu.asu.spring.quadriga.dao.workspace;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import edu.asu.spring.quadriga.db.workspace.IDBConnectionWSAccessManager;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WorkspaceAccessManagerDAOTest {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private IDBConnectionWSAccessManager dbConnect;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception 
	{
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
		user.setUsername("projuser2");
		user.setFullname("test project user");
		user.setCreatedby("projuser2");
		user.setCreateddate(date);
		user.setUpdatedby("projuser2");
		user.setUpdateddate(date);
		user.setEmail("tpu2@test.com");
		user.setQuadrigarole("role1,role4");
		sessionFactory.getCurrentSession().save(user);
		
		user = new QuadrigaUserDTO();
		user.setUsername("projuser3");
		user.setFullname("test project user");
		user.setCreatedby("projuser3");
		user.setCreateddate(date);
		user.setUpdatedby("projuser3");
		user.setUpdateddate(date);
		user.setEmail("tpu3@test.com");
		user.setQuadrigarole("role1,role4");
		sessionFactory.getCurrentSession().save(user);
		
		//create a project
		List<ProjectWorkspaceDTO> projectWorkspaceList = new ArrayList<ProjectWorkspaceDTO>();
		ProjectDTO project = new ProjectDTO();
		project.setProjectid("PROJ_1_Test");
		project.setProjectname("testproject1");
		project.setAccessibility("PUBLIC");
		project.setCreatedby("projuser1");
		project.setCreateddate(date);
		project.setUpdatedby("projuser1");
		project.setUpdateddate(date);
		project.setUnixname("PROJ_1");
		project.setProjectowner(user);
		sessionFactory.getCurrentSession().save(project);
		
		//Assigning the user as project editor
		List<ProjectEditorDTO> projectEditorList = new ArrayList<ProjectEditorDTO>();
		ProjectEditorDTOPK projectEditorKey = new ProjectEditorDTOPK("PROJ_1_Test","projuser1");
		ProjectEditorDTO projectEditor = new ProjectEditorDTO();
		projectEditor.setProjectEditorDTOPK(projectEditorKey);
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser1");
		project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
		projectEditor.setQuadrigaUserDTO(user);
		projectEditor.setProject(project);
		projectEditor.setCreatedby("projuser1");
		projectEditor.setCreateddate(date);
		projectEditor.setUpdatedby("projuser1");
		projectEditor.setUpdateddate(date);
		sessionFactory.getCurrentSession().save(projectEditor);
		projectEditorList.add(projectEditor);
		
		project.setProjectEditorDTOList(projectEditorList);
		sessionFactory.getCurrentSession().update(project);
		
		//create a workspace
		WorkspaceDTO workspace = new WorkspaceDTO();
		workspace.setWorkspaceid("WS_1_Test");
		workspace.setWorkspacename("WS_1");
		workspace.setWorkspaceowner(user);
		workspace.setCreatedby("projuser1");
		workspace.setCreateddate(date);
		workspace.setUpdatedby("projuser1");
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
        projectWorkspace.setCreatedby("projuser1");
        projectWorkspace.setCreateddate(date);
        projectWorkspace.setUpdatedby("projuser1");
        projectWorkspace.setUpdateddate(date);
        sessionFactory.getCurrentSession().save(projectWorkspace);
        projectWorkspaceList.add(projectWorkspace);
        
        project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
        project.setProjectWorkspaceDTOList(projectWorkspaceList);
        sessionFactory.getCurrentSession().update(project);
        
        List<WorkspaceCollaboratorDTO> workspaceCollaboratorList = new ArrayList<WorkspaceCollaboratorDTO>();
        WorkspaceCollaboratorDTO workspaceCollaborator = new WorkspaceCollaboratorDTO(); 
        WorkspaceCollaboratorDTOPK workspaceCollaboratorKey = new WorkspaceCollaboratorDTOPK("WS_1_Test","projuser2","wscollab_role1");
        workspaceCollaborator.setWorkspaceCollaboratorDTOPK(workspaceCollaboratorKey);
        workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_1_Test");
        workspaceCollaborator.setWorkspaceDTO(workspace);
        user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser2");
        workspaceCollaborator.setQuadrigaUserDTO(user);
        workspaceCollaborator.setCreatedby("projuser2");
        workspaceCollaborator.setCreateddate(date);
        workspaceCollaborator.setUpdatedby("projuser2");
        workspaceCollaborator.setUpdateddate(date);
        sessionFactory.getCurrentSession().save(workspaceCollaborator);
        workspaceCollaboratorList.add(workspaceCollaborator);
        
        workspace.setWorkspaceCollaboratorDTOList(workspaceCollaboratorList);
        sessionFactory.getCurrentSession().update(workspace);
	}

	@After
	public void tearDown() throws Exception 
	{
		WorkspaceCollaboratorDTOPK workspaceCollaboratorKey = new WorkspaceCollaboratorDTOPK("WS_1_Test","projuser2","wscollab_role1");
		WorkspaceCollaboratorDTO workspaceCollaborator = (WorkspaceCollaboratorDTO) sessionFactory.getCurrentSession().get(WorkspaceCollaboratorDTO.class, workspaceCollaboratorKey);
		sessionFactory.getCurrentSession().delete(workspaceCollaborator);
		
		ProjectWorkspaceDTOPK projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_1_Test");
		ProjectWorkspaceDTO projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,projectWorkspaceKey);
		sessionFactory.getCurrentSession().delete(projectWorkspace);
		
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		
		ProjectEditorDTOPK projectEditorKey = new ProjectEditorDTOPK("PROJ_1_Test","projuser1");
		ProjectEditorDTO projectEditor = (ProjectEditorDTO) sessionFactory.getCurrentSession().get(ProjectEditorDTO.class, projectEditorKey);
		sessionFactory.getCurrentSession().delete(projectEditor);
		
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
		sessionFactory.getCurrentSession().delete(project);
		
		QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser1");
		sessionFactory.getCurrentSession().delete(user);
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser2");
		sessionFactory.getCurrentSession().delete(user);
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser3");
		sessionFactory.getCurrentSession().delete(user);
		
	}

	@Test
	public void testchkWorkspaceOwner(String userName, String workspaceId) throws QuadrigaStorageException{
		boolean isOwner = false;
		isOwner = dbConnect.chkWorkspaceOwner("projuser1", "WS_1_Test");
		assertTrue(isOwner);
	}
	
	@Test
	public void testChkWorkspaceOwnerEditorRole(String userName,
			String workspaceId) throws QuadrigaStorageException 
	{
		boolean isEditor = false;
		isEditor = dbConnect.chkWorkspaceOwnerEditorRole("projuser1", "WS_1_Test");
		assertTrue(isEditor);
	}
	
	@Test
	public void testChkWorkspaceProjectInheritOwnerEditorRole(String userName,
			String workspaceId) throws QuadrigaStorageException
	{
		boolean isEditor = false;
		isEditor = dbConnect.chkWorkspaceProjectInheritOwnerEditorRole("projuser1", "WS_1_Test");
		assertTrue(isEditor);
	}
	
	@Test
	public void testChkWorkspaceExists(String workspaceId) throws QuadrigaStorageException
	{
		boolean isExists = false;
		isExists = dbConnect.chkWorkspaceExists("WS_1_Test");
		assertTrue(isExists);
	}
	
	@Test
	public void testChkIsWorkspaceAssocaited(String userName) throws QuadrigaStorageException
	{
		boolean isAssociated = false;
		isAssociated = dbConnect.chkIsWorkspaceAssocaited("projuser1");
		assertTrue(isAssociated);
		
	}
	
	@Test
	public void testChkIsCollaboratorWorkspaceAssociated(String userName,
			String role) throws QuadrigaStorageException, QuadrigaAccessException
	{
		boolean isAssociated = false;
		isAssociated = dbConnect.chkIsCollaboratorWorkspaceAssociated("projuser2","wscollab_role1");
		assertTrue(isAssociated);
	}

}
