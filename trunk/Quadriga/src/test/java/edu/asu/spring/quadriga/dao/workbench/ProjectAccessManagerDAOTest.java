package edu.asu.spring.quadriga.dao.workbench;

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
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectAccessManager;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class ProjectAccessManagerDAOTest {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private IDBConnectionProjectAccessManager dbConnect;

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
		
		//Create a project collaborator
		List<ProjectCollaboratorDTO> projectCollaboratorList = new ArrayList<ProjectCollaboratorDTO>();
		ProjectCollaboratorDTO projectCollaborator = new ProjectCollaboratorDTO();
		ProjectCollaboratorDTOPK projectCollaboratorKey = new ProjectCollaboratorDTOPK("PROJ_1_Test","projuser2","collaborator_role1");
		projectCollaborator.setProjectCollaboratorDTOPK(projectCollaboratorKey);
		project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser2");
		projectCollaborator.setProjectDTO(project);
		projectCollaborator.setQuadrigaUserDTO(user);
		projectCollaborator.setCreatedby("projuser2");
		projectCollaborator.setCreateddate(date);
		projectCollaborator.setUpdatedby("projuser2");
		projectCollaborator.setUpdateddate(date);
		sessionFactory.getCurrentSession().save(projectCollaborator);
		
		projectCollaboratorList.add(projectCollaborator);
		project.setProjectCollaboratorDTOList(projectCollaboratorList);
		sessionFactory.getCurrentSession().save(project);
		
	}

	@After
	public void tearDown() throws Exception 
	{
		ProjectCollaboratorDTOPK projectCollaboratorKey = new ProjectCollaboratorDTOPK("PROJ_1_Test","projuser2","collaborator_role1");
		ProjectCollaboratorDTO projectCollaborator = (ProjectCollaboratorDTO) sessionFactory.getCurrentSession().get(ProjectCollaboratorDTO.class, projectCollaboratorKey);
		sessionFactory.getCurrentSession().delete(projectCollaborator);
		
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
	public void testChkProjectOwner() throws QuadrigaStorageException 
	{
		boolean isOwner = false;
		isOwner = dbConnect.chkProjectOwner("projuser1", "PROJ_1_Test");
		assertTrue(isOwner);
	}
	
	@Test
	public void testChkIsProjectAssociated() throws QuadrigaStorageException
	{
		boolean isAssociated = false;
		isAssociated = dbConnect.chkIsProjectAssociated("projuser1");
		assertTrue(isAssociated);
	}
	
	@Test
	public void testChkIsCollaboratorProjectAssociated() throws QuadrigaStorageException
	{
		boolean isCollaboratorAssociated = false;
		isCollaboratorAssociated = dbConnect.chkIsCollaboratorProjectAssociated("projuser2","collaborator_role1");
		assertTrue(isCollaboratorAssociated);
	}
	
	@Test
	public void testChkProjectCollaborator() throws QuadrigaStorageException
	{
		boolean isProjectCollaborator = false;
		isProjectCollaborator = dbConnect.chkProjectCollaborator("projuser2","collaborator_role1", "PROJ_1_Test");
		assertTrue(isProjectCollaborator);
	}
	
	@Test
	public void testChkDuplicateProjUnixName() throws QuadrigaStorageException
	{
		boolean isDuplicate = false;
		isDuplicate = dbConnect.chkDuplicateProjUnixName("PROJ_11","PROJ_1_Test");
		assertTrue(isDuplicate);
	}
	
	@Test
	public void testChkProjectOwnerEditorRole() throws QuadrigaStorageException
	{
		boolean isEditor = false;
		isEditor = dbConnect.chkProjectOwnerEditorRole("projuser1","PROJ_1_Test");
		assertTrue(isEditor);
	}
	
	

}
