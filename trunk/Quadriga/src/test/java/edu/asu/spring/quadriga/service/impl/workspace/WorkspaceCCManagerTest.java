package edu.asu.spring.quadriga.service.impl.workspace;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTO;
import edu.asu.spring.quadriga.dto.WorkspaceConceptcollectionDTOPK;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;

@ContextConfiguration(locations = {
		"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WorkspaceCCManagerTest {

	@Autowired
	private IWorkspaceCCManager workspaceConceptCollectionManager;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private IWorkspaceCCManager dbConnect;
	
	@Autowired
	private ConceptCollectionDTOMapper collectionMapper;
	
	@Autowired
	private WorkspaceDTOMapper workspaceMapper;
	
	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;
	
	
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
        
        ConceptCollectionDTO conceptCollection = new ConceptCollectionDTO();
		conceptCollection.setCollectionname("conceptCollection1");
		conceptCollection.setCollectionowner(user);
		conceptCollection.setConceptCollectionid("CC_1_Test");
		conceptCollection.setCreatedby("projuser1");
		conceptCollection.setCreateddate(date);
		sessionFactory.getCurrentSession().save(conceptCollection);
		
		conceptCollection = new ConceptCollectionDTO();
		conceptCollection.setCollectionname("conceptCollection2");
		conceptCollection.setCollectionowner(user);
		conceptCollection.setConceptCollectionid("CC_2_Test");
		conceptCollection.setCreatedby("projuser1");
		conceptCollection.setCreateddate(date);
		sessionFactory.getCurrentSession().save(conceptCollection);
		
		List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionList;
		WorkspaceConceptcollectionDTOPK workspaceConceptCollectionKey = new WorkspaceConceptcollectionDTOPK("WS_1_Test","CC_1_Test");
		WorkspaceConceptcollectionDTO workspaceConceptCollection = new WorkspaceConceptcollectionDTO();
		workspaceConceptCollection.setWorkspaceConceptcollectionDTOPK(workspaceConceptCollectionKey);
		conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,"CC_1_Test");
		workspaceConceptCollection.setConceptCollectionDTO(conceptCollection);
		workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_1_Test");
		workspaceConceptCollection.setWorkspaceDTO(workspace);
		workspaceConceptCollection.setCreatedby("projuser1");
		workspaceConceptCollection.setCreateddate(date);
		workspaceConceptCollection.setUpdatedby("projuser1");
		workspaceConceptCollection.setUpdateddate(date);
		sessionFactory.getCurrentSession().update(workspaceConceptCollection);
		
		workspaceConceptCollectionList = workspace.getWorkspaceConceptCollectionDTOList();
		if(workspaceConceptCollectionList ==null)
		{
			workspaceConceptCollectionList = new ArrayList<WorkspaceConceptcollectionDTO>();
		}
		workspace.setWorkspaceConceptCollectionDTOList(workspaceConceptCollectionList);
		sessionFactory.getCurrentSession().update(workspace);
	}
	
	@After
	public void tearDown() throws Exception 
	{
		WorkspaceConceptcollectionDTOPK workspaceConceptCollectionKey = new WorkspaceConceptcollectionDTOPK("WS_1_Test","CC_1_Test");
		WorkspaceConceptcollectionDTO workspaceConceptCollection = (WorkspaceConceptcollectionDTO) sessionFactory.getCurrentSession().get(WorkspaceConceptcollectionDTO.class, workspaceConceptCollectionKey);
		if(workspaceConceptCollection !=null)
		{
			sessionFactory.getCurrentSession().delete(workspaceConceptCollection);
		}
		
		ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,"CC_1_Test");
	    if(conceptCollection !=null)
	    {
	    	sessionFactory.getCurrentSession().delete(conceptCollection);
	    }
	    conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,"CC_2_Test");
	    if(conceptCollection !=null)
	    {
	    	sessionFactory.getCurrentSession().delete(conceptCollection);
	    }
	    
	    ProjectWorkspaceDTOPK projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_1_Test");
	    ProjectWorkspaceDTO projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class, projectWorkspaceKey);
	    if(projectWorkspace !=null)
	    {
	    	sessionFactory.getCurrentSession().delete(projectWorkspace);
	    }
	    
	    WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
	    if(workspace !=null)
	    {
	    	sessionFactory.getCurrentSession().delete(workspace);
	    }
	    
	    ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
	    if(project !=null)
	    {
	    	sessionFactory.getCurrentSession().delete(project);
	    }
	    
		QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser1");
		if(user!=null)
		{
		  sessionFactory.getCurrentSession().delete(user);
		}
	    user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser2");
	    if(user!=null)
		{
		  sessionFactory.getCurrentSession().delete(user);
		}
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser3");
		if(user!=null)
		{
		  sessionFactory.getCurrentSession().delete(user);
		}
	}

	@Test
	public void testAddWorkspaceCC() throws QuadrigaAccessException, QuadrigaStorageException 
	{
		boolean isAdded = false;
		dbConnect.addWorkspaceCC("WS_1_Test", "CC_2_Test", "projuser1");
		
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		List<WorkspaceConceptcollectionDTO> workspaceConceptCollection = workspace.getWorkspaceConceptCollectionDTOList();
		
		ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,"CC_2_Test");
		
		WorkspaceConceptcollectionDTO testWorkspaceConceptCollection = workspaceMapper.getWorkspaceConceptCollection(workspace, conceptCollection, "projuser1");
		
		if(workspaceConceptCollection.contains(testWorkspaceConceptCollection))
		{
			isAdded = true;
		}
		
		assertTrue(isAdded);
	}

	@Test
	public void testListWorkspaceCC() throws QuadrigaAccessException, QuadrigaStorageException
	{
		boolean isRetrieved = true;
		IConceptCollection collection = null;
		List<IConceptCollection> conceptCollectionList = new ArrayList<IConceptCollection>();
		List<IConceptCollection> conceptCollection = dbConnect.listWorkspaceCC("WS_1_Test", "projuser1");
		
		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		
		List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionList = workspace.getWorkspaceConceptCollectionDTOList();
		
		for(WorkspaceConceptcollectionDTO workspaceConceptCollection : workspaceConceptCollectionList)
		{
			WorkspaceConceptcollectionDTOPK workspaceConceptCollectionKey = workspaceConceptCollection.getWorkspaceConceptcollectionDTOPK();
			ConceptCollectionDTO conceptCollectionDTO = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class, workspaceConceptCollectionKey.getConceptcollectionid());
		    collection = collectionMapper.getConceptCollection(conceptCollectionDTO);
		    conceptCollectionList.add(collection);
		}
		
		if(conceptCollectionList.size() != conceptCollection.size())
		{
			fail();
		}
		
		for(IConceptCollection tempCollection : conceptCollection)
		{
			if(!conceptCollectionList.contains(tempCollection))
			{
				isRetrieved = false;
			}
		}
		assertTrue(isRetrieved);
	}

	@Test
	public void testDeleteWorkspaceCC() throws QuadrigaAccessException, QuadrigaStorageException {
		boolean isDeleted = true;
		  dbConnect.deleteWorkspaceCC("WS_1_Test", "projuser1", "CC_1_Test");
		  
		  WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
		  ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,"CC_1_Test");
		  
		  List<WorkspaceConceptcollectionDTO> workspaceConceptCollectionList =conceptCollection.getWsConceptCollectionDTOList();
		  if(workspaceConceptCollectionList !=null)
		  {
		  for(WorkspaceConceptcollectionDTO tempWSConceptCollection : workspaceConceptCollectionList)
		  {
			  WorkspaceDTO testWorkspace = tempWSConceptCollection.getWorkspaceDTO();
			  
			  if((testWorkspace!=null)&&(workspace.equals(testWorkspace)))
			  {
				  isDeleted = false;
			  }
		  }
		  }
		  assertTrue(isDeleted);
	}

}
