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

import edu.asu.spring.quadriga.db.workspace.IDBConnectionRetrieveWSCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.UserDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RetrieveWSCollabManagerDAOTest {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private IDBConnectionRetrieveWSCollabManager dbConnect;
	
	@Autowired
	private WorkspaceDTOMapper workspaceDTOMapper;
	
	@Autowired
	private UserDTOMapper userDTOMapper;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
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
		user.setUsername("projuser1");
		user.setFullname("test project user");
		user.setCreatedby("projuser1");
		user.setCreateddate(date);
		user.setUpdatedby("projuser1");
		user.setUpdateddate(date);
		user.setEmail("tpu1@test.com");
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
		
		user = new QuadrigaUserDTO();
		user.setUsername("projuser4");
		user.setFullname("test project user");
		user.setCreatedby("projuser4");
		user.setCreateddate(date);
		user.setUpdatedby("projuser4");
		user.setUpdateddate(date);
		user.setEmail("tpu4@test.com");
		user.setQuadrigarole("role1,role4");
		sessionFactory.getCurrentSession().save(user);
		
		user = new QuadrigaUserDTO();
		user.setUsername("projuser5");
		user.setFullname("test project user");
		user.setCreatedby("projuser5");
		user.setCreateddate(date);
		user.setUpdatedby("projuser5");
		user.setUpdateddate(date);
		user.setEmail("tpu5@test.com");
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
        
        workspaceCollaborator = new WorkspaceCollaboratorDTO(); 
        workspaceCollaboratorKey = new WorkspaceCollaboratorDTOPK("WS_1_Test","projuser3","wscollab_role1");
        workspaceCollaborator.setWorkspaceCollaboratorDTOPK(workspaceCollaboratorKey);
        workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_1_Test");
        workspaceCollaborator.setWorkspaceDTO(workspace);
        user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser3");
        workspaceCollaborator.setQuadrigaUserDTO(user);
        workspaceCollaborator.setCreatedby("projuser3");
        workspaceCollaborator.setCreateddate(date);
        workspaceCollaborator.setUpdatedby("projuser3");
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
		
		workspaceCollaboratorKey = new WorkspaceCollaboratorDTOPK("WS_1_Test","projuser3","wscollab_role1");
		workspaceCollaborator = (WorkspaceCollaboratorDTO) sessionFactory.getCurrentSession().get(WorkspaceCollaboratorDTO.class, workspaceCollaboratorKey);
		sessionFactory.getCurrentSession().delete(workspaceCollaborator);
		
		ProjectWorkspaceDTOPK projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_1_Test");
		ProjectWorkspaceDTO projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,projectWorkspaceKey);
		sessionFactory.getCurrentSession().delete(projectWorkspace);
		
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
		sessionFactory.getCurrentSession().delete(project);
		
		QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser1");
		sessionFactory.getCurrentSession().delete(user);
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser2");
		sessionFactory.getCurrentSession().delete(user);
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser3");
		sessionFactory.getCurrentSession().delete(user);
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser4");
		sessionFactory.getCurrentSession().delete(user);
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser5");
		sessionFactory.getCurrentSession().delete(user);
	}

	@Test
	public void testGetWorkspaceCollaborators() throws QuadrigaStorageException
	{
		boolean isValid = true;
		List<ICollaborator> collaborators;
		List<ICollaborator> testCollaborators;
		collaborators = dbConnect.getWorkspaceCollaborators("WS_1_Test");
		WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		IWorkSpace workspace = workspaceDTOMapper.getWorkSpace(workspaceDTO);
		testCollaborators = workspace.getCollaborators();
		for(ICollaborator collaborator : collaborators)
		{
			if(!testCollaborators.contains(collaborator))
			{
				isValid = false;
			}
		}
		assertTrue(isValid);
	}
	
	@Test
	public void testGetWorkspaceNonCollaborators() throws QuadrigaStorageException
	{
		boolean isValid = true;
		List<IUser> nonCollaborators;
		List<IUser> testNonCollaborators;
		testNonCollaborators = new ArrayList<IUser>();
		nonCollaborators = dbConnect.getWorkspaceNonCollaborators("WS_1_Test");
		QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser4");
		testNonCollaborators.add(userDTOMapper.getUser(user));
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser5");
		testNonCollaborators.add(userDTOMapper.getUser(user));
		for(IUser testUser : nonCollaborators)
		{
			if(!testNonCollaborators.contains(testUser))
			{
				isValid = false;
			}
		}
		assertTrue(isValid);
	}
	
	@Test
	public void testGetCollaboratorDBRoleIdList()
	{
		boolean isValid = true;
		List<ICollaboratorRole> collaboratorRoleList = dbConnect.getCollaboratorDBRoleIdList("wscollab_role1,wscollab_role2");
		List<ICollaboratorRole> testCollaboratorRoleList = new ArrayList<ICollaboratorRole>();
		ICollaboratorRole collaboratorRole;
		collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
		collaboratorRole.setRoleDBid("wscollab_role1");
		testCollaboratorRoleList.add(collaboratorRole);
		collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
		collaboratorRole.setRoleDBid("wscollab_role2");
		testCollaboratorRoleList.add(collaboratorRole);
		for(ICollaboratorRole role : testCollaboratorRoleList)
		{
			if(!collaboratorRoleList.contains(role))
			{
				isValid = false;
			}
		}
		assertTrue(isValid);
	}
	
}
