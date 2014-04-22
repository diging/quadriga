package edu.asu.spring.quadriga.dao.workspace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.service.IUserManager;
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ListWSManagerDAOTest {

	@Autowired
	IDBConnectionListWSManager dbConnect;
	
	@Autowired
	IWorkspaceFactory workspaceFactory;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	private WorkspaceDTOMapper workspaceDTOMapper;
	
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
        
        project.setProjectWorkspaceDTOList(projectWorkspaceList);
        sessionFactory.getCurrentSession().update(project);
	}

	@After
	public void tearDown() throws Exception {
		
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
		
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_2_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_3_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_4_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
		sessionFactory.getCurrentSession().delete(project);
		
		QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser");
		sessionFactory.getCurrentSession().delete(user);
	}

	@Test
	public void testListWorkspace() throws QuadrigaStorageException 
	{
		IWorkSpace workspace;
		List<IWorkSpace> workspaceList;
		List<ProjectWorkspaceDTO> projectWorkspaceList;
		ProjectDTO project;
		
		workspaceList = dbConnect.listWorkspace("PROJ_1_Test","projuser");
		
		//create workspace objects
		project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_1_Test");
		projectWorkspaceList = project.getProjectWorkspaceDTOList();
		
		for(ProjectWorkspaceDTO projectWorkspace : projectWorkspaceList)
		{
			workspace = workspaceDTOMapper.getWorkSpace(projectWorkspace.getWorkspaceDTO());
			assertTrue(workspaceList.contains(workspace));
		}
	}
	
	@Test
	public void testListActiveWorkspace() throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		WorkspaceDTO workspaceDTO;
		List<IWorkSpace> workspaceList;
		List<ProjectWorkspaceDTO> projectWorkspaceList;
		ProjectDTO project;
		
		workspaceList = dbConnect.listActiveWorkspaceOfOwner("PROJ_1_Test","projuser");
		
		//create workspace objects
		project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_1_Test");
		projectWorkspaceList = project.getProjectWorkspaceDTOList();
				
		for(ProjectWorkspaceDTO projectWorkspace : projectWorkspaceList)
		{
			workspaceDTO = projectWorkspace.getWorkspaceDTO();
			if(!workspaceDTO.getIsdeactivated())
			{
				workspace = workspaceDTOMapper.getWorkSpace(workspaceDTO);
				assertTrue(workspaceList.contains(workspace));
			}
		}
	}
	
	@Test
	public void testListArchivedWorkspace() throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		WorkspaceDTO workspaceDTO;
		List<IWorkSpace> workspaceList;
		List<ProjectWorkspaceDTO> projectWorkspaceList;
		ProjectDTO project;
		
		workspaceList = dbConnect.listArchivedWorkspace("PROJ_1_Test","projuser");
		
		//create workspace objects
		project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_1_Test");
		projectWorkspaceList = project.getProjectWorkspaceDTOList();
				
		for(ProjectWorkspaceDTO projectWorkspace : projectWorkspaceList)
		{
			workspaceDTO = projectWorkspace.getWorkspaceDTO();
			if(workspaceDTO.getIsarchived())
			{
				workspace = workspaceDTOMapper.getWorkSpace(workspaceDTO);
				assertTrue(workspaceList.contains(workspace));
			}
		}
	}
	
	@Test
	public void testlistDeactivatedWorkspace() throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		WorkspaceDTO workspaceDTO;
		List<IWorkSpace> workspaceList;
		List<ProjectWorkspaceDTO> projectWorkspaceList;
		ProjectDTO project;
		
		workspaceList = dbConnect.listDeactivatedWorkspace("PROJ_1_Test","projuser");
		
		//create workspace objects
		project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_1_Test");
		projectWorkspaceList = project.getProjectWorkspaceDTOList();
				
		for(ProjectWorkspaceDTO projectWorkspace : projectWorkspaceList)
		{
			workspaceDTO = projectWorkspace.getWorkspaceDTO();
			if(workspaceDTO.getIsdeactivated())
			{
				workspace = workspaceDTOMapper.getWorkSpace(workspaceDTO);
				assertTrue(workspaceList.contains(workspace));
			}
		}
	}
	
	@Test
	public void testgetWorkspaceDetails() throws QuadrigaStorageException
	{
		IWorkSpace workspace;
		IWorkSpace testWorkspace;
		
		workspace = dbConnect.getWorkspaceDetails("WS_1_Test","projuser");
		
		WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		testWorkspace = workspaceDTOMapper.getWorkSpace(workspaceDTO);
		
		assertEquals(testWorkspace,workspace);
	}

}
