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
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
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
	
	@Autowired
	private WorkspaceDTOMapper workspaceDTOMapper;
	
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
		List<ProjectWorkspaceDTO> projectWorkspaceList = new ArrayList<ProjectWorkspaceDTO>();
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
		
		project = new ProjectDTO();
		project.setProjectid("PROJ_2_Test");
		project.setProjectname("testproject2");
		project.setAccessibility("PUBLIC");
		project.setCreatedby("projuser");
		project.setCreateddate(date);
		project.setUpdatedby("projuser");
		project.setUpdateddate(date);
		project.setUnixname("PROJ_2");
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
        
    	workspace = new WorkspaceDTO();
		workspace.setWorkspaceid("WS_4_Test");
		workspace.setWorkspacename("WS_4");
		workspace.setWorkspaceowner(user);
		workspace.setCreatedby("projuser");
		workspace.setCreateddate(date);
		workspace.setUpdatedby("projuser");
		workspace.setUpdateddate(date);
		workspace.setIsarchived(false);
		workspace.setIsdeactivated(false);
        sessionFactory.getCurrentSession().save(workspace);
        
    	workspace = new WorkspaceDTO();
		workspace.setWorkspaceid("WS_5_Test");
		workspace.setWorkspacename("WS_5");
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
        projectWorkspaceList.add(projectWorkspace);
        
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
        projectWorkspaceList.add(projectWorkspace);
        
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
        projectWorkspaceList.add(projectWorkspace);
        
        projectWorkspace = new ProjectWorkspaceDTO();
        projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_4_Test");
        projectWorkspace.setProjectWorkspaceDTOPK(projectWorkspaceKey);
        project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_1_Test");
        projectWorkspace.setProjectDTO(project);
        workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_4_Test");
        projectWorkspace.setWorkspaceDTO(workspace);
        projectWorkspace.setCreatedby("projuser");
        projectWorkspace.setCreateddate(date);
        projectWorkspace.setUpdatedby("projuser");
        projectWorkspace.setUpdateddate(date);
        sessionFactory.getCurrentSession().save(projectWorkspace);
        projectWorkspaceList.add(projectWorkspace);
        
        projectWorkspace = new ProjectWorkspaceDTO();
        projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_5_Test");
        projectWorkspace.setProjectWorkspaceDTOPK(projectWorkspaceKey);
        project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_1_Test");
        projectWorkspace.setProjectDTO(project);
        workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_5_Test");
        projectWorkspace.setWorkspaceDTO(workspace);
        projectWorkspace.setCreatedby("projuser");
        projectWorkspace.setCreateddate(date);
        projectWorkspace.setUpdatedby("projuser");
        projectWorkspace.setUpdateddate(date);
        sessionFactory.getCurrentSession().save(projectWorkspace);
        projectWorkspaceList.add(projectWorkspace);
        project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
        project.setProjectWorkspaceDTOList(projectWorkspaceList);
        sessionFactory.getCurrentSession().update(project);
        
        List<WorkspaceCollaboratorDTO> workspaceCollaboratorList = new ArrayList<WorkspaceCollaboratorDTO>();
        WorkspaceCollaboratorDTO workspaceCollaborator = new WorkspaceCollaboratorDTO(); 
        WorkspaceCollaboratorDTOPK workspaceCollaboratorKey = new WorkspaceCollaboratorDTOPK("WS_5_Test","projcollab","wscollab_role1");
        workspaceCollaborator.setWorkspaceCollaboratorDTOPK(workspaceCollaboratorKey);
        workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_5_Test");
        workspaceCollaborator.setWorkspaceDTO(workspace);
        user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projcollab");
        workspaceCollaborator.setQuadrigaUserDTO(user);
        workspaceCollaborator.setCreatedby("projuser");
        workspaceCollaborator.setCreateddate(date);
        workspaceCollaborator.setUpdatedby("projuser");
        workspaceCollaborator.setUpdateddate(date);
        sessionFactory.getCurrentSession().save(workspaceCollaborator);
        workspaceCollaboratorList.add(workspaceCollaborator);
        
        workspace.setWorkspaceCollaboratorDTOList(workspaceCollaboratorList);
        sessionFactory.getCurrentSession().update(workspace);
	}

	@After
	public void tearDown() throws Exception {
		
		WorkspaceCollaboratorDTOPK workspaceCollaboratorKey = new WorkspaceCollaboratorDTOPK("WS_5_Test","projcollab","wscollab_role1");
		WorkspaceCollaboratorDTO workspaceCollaborator = (WorkspaceCollaboratorDTO) sessionFactory.getCurrentSession().get(WorkspaceCollaboratorDTO.class, workspaceCollaboratorKey);
		sessionFactory.getCurrentSession().delete(workspaceCollaborator);
		
		ProjectWorkspaceDTOPK projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_1_Test");
		ProjectWorkspaceDTO projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,projectWorkspaceKey);
		sessionFactory.getCurrentSession().delete(projectWorkspace);
		
		projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_2_Test");
		projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,projectWorkspaceKey);
		sessionFactory.getCurrentSession().delete(projectWorkspace);
		
		projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_3_Test");
		projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,projectWorkspaceKey);
		sessionFactory.getCurrentSession().delete(projectWorkspace);
		
		projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_4_Test");
		projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,projectWorkspaceKey);
		sessionFactory.getCurrentSession().delete(projectWorkspace);
		
		projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_5_Test");
		projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,projectWorkspaceKey);
		sessionFactory.getCurrentSession().delete(projectWorkspace);
		
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_2_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_3_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_4_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_5_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
		sessionFactory.getCurrentSession().delete(project);
		
		QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser");
		sessionFactory.getCurrentSession().delete(user);
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projcollab");
		sessionFactory.getCurrentSession().delete(user);
	}

	@Test
	public void testAddWorkSpace() throws QuadrigaStorageException {
		boolean isExists = false;
		IUser user;
		IWorkSpace workspace;
		IWorkSpace testWorkspace;
		
		//create workspace objects
		user = userManager.getUserDetails("projuser");
		
		workspace = workspaceFactory.createWorkspaceObject();
		workspace.setName("testprojws2");
		workspace.setDescription("test workspace");
		workspace.setOwner(user);
		
		dbConnect.addWorkSpaceRequest(workspace, "PROJ_2_Test");
		
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_2_Test");
		List<ProjectWorkspaceDTO> projectWorkspace = project.getProjectWorkspaceDTOList();
		for(ProjectWorkspaceDTO tempProjectWorkspace : projectWorkspace)
		{
			testWorkspace = workspaceDTOMapper.getWorkSpace(tempProjectWorkspace.getWorkspaceDTO());
			if(testWorkspace.getName().equals(workspace.getName()) && testWorkspace.getOwner().equals(user))
			{
				isExists = true;
			}
		}
		
		assertTrue(isExists);
	}
	
	@Test
	public void testDeleteWorkspace() throws QuadrigaStorageException
	{
		String errmsg;
		
		errmsg = dbConnect.deleteWorkspaceRequest("WS_1_Test,WS_2_Test");
		assertEquals("",errmsg);
		
	}
	
	@Test
	public void testUpdateWorkspace() throws QuadrigaStorageException
	{
		String errmsg;
		WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_2_Test");
		IWorkSpace workspace = workspaceDTOMapper.getWorkSpace(workspaceDTO);
		workspace.setName("Test Updating workspace");
		errmsg = dbConnect.updateWorkspaceRequest(workspace);
	    assertEquals("",errmsg);
	}

	public void testTransferWorkspace() throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		String owner;
		dbConnect.transferWSOwnerRequest("WS_6_Test", "projuser", "projcollab", "wscollab_role1");
		
		//retrieve the workspace details
		workspace = dbRetrieveConnect.getWorkspaceDetails("WS_6_Test", "projuser");
		
		owner = workspace.getOwner().getUserName();
		
		assertEquals("projcollab",owner);
	}
	
}
