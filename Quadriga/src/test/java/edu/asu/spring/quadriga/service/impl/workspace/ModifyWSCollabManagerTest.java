package edu.asu.spring.quadriga.service.impl.workspace;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionRetrieveWSCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTO;
import edu.asu.spring.quadriga.dto.WorkspaceCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.WorkspaceCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.service.workspace.IModifyWSCollabManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml","file:src/test/resources/hibernate.cfg.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ModifyWSCollabManagerTest 
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private IModifyWSCollabManager dbConnect;

	@Autowired
	private WorkspaceDTOMapper workspaceDTOMapper;
	
	@Autowired
	private IDBConnectionRetrieveWSCollabManager dbRetrieveConnect;
	
	@Autowired
	private WorkspaceCollaboratorDTOMapper collaboratorMapper;
	
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
	@Transactional
	public void testAddWorkspaceCollaborator() throws QuadrigaStorageException 
	{
		boolean isSuccessful = true;
		List<WorkspaceCollaboratorDTO> wrkCollabList;
		
		dbConnect.addWorkspaceCollaborator("projuser3","wscollab_role1", "WS_1_Test", "projuser1");
		List<ICollaborator> collaborators = dbRetrieveConnect.getWorkspaceCollaborators("WS_1_Test");
		WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		wrkCollabList = workspaceDTO.getWorkspaceCollaboratorDTOList();
		List<ICollaborator> testCollaborators = collaboratorMapper.getWorkspaceCollaborators(wrkCollabList);
		for(ICollaborator user : testCollaborators)
		{
			if(!collaborators.contains(user))
			{
				isSuccessful = false;
			}
		}
		
		assertTrue(isSuccessful);
	}
	
	@Test
	@Transactional
	public void deleteWorkspaceCollaborator() throws QuadrigaStorageException
	{
		boolean isSuccessful = true;
		List<WorkspaceCollaboratorDTO> wrkCollabList;
		dbConnect.deleteWorkspaceCollaborator("projuser2","WS_1_Test");
		WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		wrkCollabList = workspaceDTO.getWorkspaceCollaboratorDTOList();
		List<ICollaborator> testCollaborators = collaboratorMapper.getWorkspaceCollaborators(wrkCollabList);
		for(ICollaborator collabUser : testCollaborators)
		{
			if(collabUser.getUserObj().getUserName().equals("projuser2"))
			{
				isSuccessful = false;
			}
		}
		assertTrue(isSuccessful);
	}

	@Test
	@Transactional
	public void updateWorkspaceCollaborator() throws QuadrigaStorageException
	{
		boolean isSuccessful = false;
		List<WorkspaceCollaboratorDTO> wrkCollabList;
		dbConnect.updateWorkspaceCollaborator("WS_1_Test", "projuser3","wscollab_role2", "projuser1");
		WorkspaceDTO workspaceDTO = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		wrkCollabList = workspaceDTO.getWorkspaceCollaboratorDTOList();
		List<ICollaborator> testCollaborators = collaboratorMapper.getWorkspaceCollaborators(wrkCollabList);
		for(ICollaborator user : testCollaborators)
		{
			if(user.getUserObj().getUserName().equals("projuser3")) 
			{
				for(ICollaboratorRole role : user.getCollaboratorRoles())
				{
					if(role.getRoleDBid().equals("wscollab_role2"))
						isSuccessful = true;
				}
			}
		}
		assertTrue(isSuccessful);
	}
	
}
