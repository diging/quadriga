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

import edu.asu.spring.quadriga.db.workspace.IDBConnectionArchiveWSManager;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ArchiveWorkspaceManagerDAOTest {

	@Autowired
	IDBConnectionArchiveWSManager  dbConnect;
	
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
	}

	@After
	public void tearDown() throws Exception {
		
		ProjectWorkspaceDTOPK projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_1_Test");
		ProjectWorkspaceDTO projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,projectWorkspaceKey);
		sessionFactory.getCurrentSession().delete(projectWorkspace);
		
		projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_2_Test");
		projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,projectWorkspaceKey);
		sessionFactory.getCurrentSession().delete(projectWorkspace);
		
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_2_Test");
		sessionFactory.getCurrentSession().delete(workspace);
		
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
		sessionFactory.getCurrentSession().delete(project);
		
		QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class,"projuser");
		sessionFactory.getCurrentSession().delete(user);
	}

	@Test
	public void testArchiveWorkspace() throws QuadrigaStorageException 
	{
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		workspace.setIsarchived(true);
	    dbConnect.archiveWorkspace("WS_1_Test", Boolean.TRUE, "projuser");
	    WorkspaceDTO modifiedWorkspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
	    assertEquals(workspace,modifiedWorkspace);
	}
	
	@Test
	public void testArchiveActivate() throws QuadrigaStorageException
	{
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		workspace.setIsarchived(false);
		dbConnect.archiveWorkspace("WS_1_Test", Boolean.FALSE, "projuser");
		WorkspaceDTO modifiedWorkspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		assertEquals(workspace,modifiedWorkspace);
	}
	
	@Test
	public void testDeactivateWorkspace() throws QuadrigaStorageException
	{
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_2_Test");
		workspace.setIsdeactivated(true);
		dbConnect.deactivateWorkspace("WS_2_Test", Boolean.TRUE, "projuser");
		WorkspaceDTO modifiedWorkspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_2_Test");
	    assertEquals(workspace,modifiedWorkspace);
	}
	
	@Test
	public void testActivateWorkspace() throws QuadrigaStorageException
	{
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_2_Test");
		workspace.setIsdeactivated(false);
		dbConnect.deactivateWorkspace("WS_2_Test", Boolean.FALSE, "projuser");
		WorkspaceDTO modifiedWorkspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_2_Test");
		assertEquals(workspace,modifiedWorkspace);
	}

}
