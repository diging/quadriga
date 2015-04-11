//package edu.asu.spring.quadriga.service.impl.workspace;
//
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.hibernate.SessionFactory;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
//import edu.asu.spring.quadriga.dto.DictionaryDTO;
//import edu.asu.spring.quadriga.dto.ProjectDTO;
//import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
//import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTOPK;
//import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
//import edu.asu.spring.quadriga.dto.WorkspaceDTO;
//import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTO;
//import edu.asu.spring.quadriga.dto.WorkspaceDictionaryDTOPK;
//import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
//import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
//import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;
//import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
//import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;
//
//@ContextConfiguration(locations = {
//		"file:src/test/resources/spring-dbconnectionmanager.xml",
//		"file:src/test/resources/root-context.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
//public class WorkspaceDictionaryManagerTest 
//{
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	@Autowired
//	private IWorkspaceDictionaryManager dbConnect;
//
//	@Autowired
//	private WorkspaceDTOMapper workspaceMapper;
//	
//	@Autowired
//	private DictionaryDTOMapper dictionaryMapper;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception 
//	{
//		//create a quadriga user
//		Date date = new Date();
//		QuadrigaUserDTO user = new QuadrigaUserDTO();
//		user.setUsername("projuser1");
//		user.setFullname("test project user");
//		user.setCreatedby("projuser1");
//		user.setCreateddate(date);
//		user.setUpdatedby("projuser1");
//		user.setUpdateddate(date);
//		user.setEmail("tpu@test.com");
//		user.setQuadrigarole("role1,role4");
//		sessionFactory.getCurrentSession().save(user);
//		
//		user = new QuadrigaUserDTO();
//		user.setUsername("projuser2");
//		user.setFullname("test project user");
//		user.setCreatedby("projuser2");
//		user.setCreateddate(date);
//		user.setUpdatedby("projuser2");
//		user.setUpdateddate(date);
//		user.setEmail("tpu@test.com");
//		user.setQuadrigarole("role1,role4");
//		sessionFactory.getCurrentSession().save(user);
//		
//		user = new QuadrigaUserDTO();
//		user.setUsername("projuser3");
//		user.setFullname("test project user");
//		user.setCreatedby("projuser3");
//		user.setCreateddate(date);
//		user.setUpdatedby("projuser3");
//		user.setUpdateddate(date);
//		user.setEmail("tpu@test.com");
//		user.setQuadrigarole("role1,role4");
//		sessionFactory.getCurrentSession().save(user);
//		
//		//create a project
//		List<ProjectWorkspaceDTO> projectWorkspaceList = new ArrayList<ProjectWorkspaceDTO>();
//		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser1");
//		ProjectDTO project = new ProjectDTO();
//		project.setProjectid("PROJ_1_Test");
//		project.setProjectname("testproject1");
//		project.setAccessibility("PUBLIC");
//		project.setCreatedby("projuser1");
//		project.setCreateddate(date);
//		project.setUpdatedby("projuser1");
//		project.setUpdateddate(date);
//		project.setUnixname("PROJ_1");
//		project.setProjectowner(user);
//		sessionFactory.getCurrentSession().save(project);
//		
//		//create a workspace
//		WorkspaceDTO workspace = new WorkspaceDTO();
//		workspace.setWorkspaceid("WS_1_Test");
//		workspace.setWorkspacename("WS_1");
//		workspace.setWorkspaceowner(user);
//		workspace.setCreatedby("projuser1");
//		workspace.setCreateddate(date);
//		workspace.setUpdatedby("projuser1");
//		workspace.setUpdateddate(date);
//		workspace.setIsarchived(false);
//		workspace.setIsdeactivated(false);
//        sessionFactory.getCurrentSession().save(workspace);
//        
//        //create project workspace mapping
//        ProjectWorkspaceDTO projectWorkspace = new ProjectWorkspaceDTO();
//        ProjectWorkspaceDTOPK projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_1_Test");
//        projectWorkspace.setProjectWorkspaceDTOPK(projectWorkspaceKey);
//        project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, "PROJ_1_Test");
//        projectWorkspace.setProjectDTO(project);
//        workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_1_Test");
//        projectWorkspace.setWorkspaceDTO(workspace);
//        projectWorkspace.setCreatedby("projuser1");
//        projectWorkspace.setCreateddate(date);
//        projectWorkspace.setUpdatedby("projuser1");
//        projectWorkspace.setUpdateddate(date);
//        sessionFactory.getCurrentSession().save(projectWorkspace);
//        projectWorkspaceList.add(projectWorkspace);
//        
//        project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
//        project.setProjectWorkspaceDTOList(projectWorkspaceList);
//        sessionFactory.getCurrentSession().update(project);
//        
//		DictionaryDTO dictionary = new DictionaryDTO();
//		dictionary.setDictionaryname("dictionary1");
//		dictionary.setDictionaryid("DICT_1_Test");
//		dictionary.setDictionaryowner(user);
//		dictionary.setCreatedby("projuser1");
//		dictionary.setCreateddate(date);
//		sessionFactory.getCurrentSession().save(dictionary);
//		
//		dictionary = new DictionaryDTO();
//		dictionary.setDictionaryname("dictionary2");
//		dictionary.setDictionaryid("DICT_2_Test");
//		dictionary.setDictionaryowner(user);
//		dictionary.setCreatedby("projuser1");
//		dictionary.setCreateddate(date);
//		sessionFactory.getCurrentSession().save(dictionary);
//
//	    List<WorkspaceDictionaryDTO> workspaceDictionaryList = new ArrayList<WorkspaceDictionaryDTO>();
//		WorkspaceDictionaryDTOPK workspaceDictionaryKey = new WorkspaceDictionaryDTOPK("WS_1_Test","DICT_1_Test");
//	    WorkspaceDictionaryDTO workspaceDictionary = new WorkspaceDictionaryDTO();
//	    workspaceDictionary.setWorkspaceDictionaryDTOPK(workspaceDictionaryKey);
//	    workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class, "WS_1_Test");
//	    workspaceDictionary.setWorkspaceDTO(workspace);
//	    dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,"DICT_1_Test");
//		workspaceDictionary.setDictionaryDTO(dictionary);
//		workspaceDictionary.setCreatedby("projuser1");
//		workspaceDictionary.setCreateddate(date);
//		workspaceDictionary.setUpdatedby("projuser1");
//		workspaceDictionary.setUpdateddate(date);
//		sessionFactory.getCurrentSession().save(workspaceDictionary);
//		
//		workspaceDictionaryList.add(workspaceDictionary);
//		workspace.setWorkspaceDictionaryDTOList(workspaceDictionaryList);
//		sessionFactory.getCurrentSession().update(workspaceDictionary);
//		
//		dictionary.setWsDictionaryDTOList(workspaceDictionaryList);
//		sessionFactory.getCurrentSession().update(dictionary);
//	}
//
//	@After
//	public void tearDown() throws Exception 
//	{
//		WorkspaceDictionaryDTOPK workspaceDictionaryKey = new WorkspaceDictionaryDTOPK("WS_1_Test","DICT_1_Test");
//		WorkspaceDictionaryDTO workspaceDictionary = (WorkspaceDictionaryDTO) sessionFactory.getCurrentSession().get(WorkspaceDictionaryDTO.class, workspaceDictionaryKey);
//		if(workspaceDictionary !=null)
//		{
//			sessionFactory.getCurrentSession().delete(workspaceDictionary);
//		}
//		
//		DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,"DICT_1_Test");
//		if(dictionary != null)
//		{
//			sessionFactory.getCurrentSession().delete(dictionary);
//		}
//		
//		dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,"DICT_2_Test" );
//		if(dictionary !=null)
//		{
//			sessionFactory.getCurrentSession().delete(dictionary);
//		}
//		
//	    ProjectWorkspaceDTOPK projectWorkspaceKey = new ProjectWorkspaceDTOPK("PROJ_1_Test","WS_1_Test");
//	    ProjectWorkspaceDTO projectWorkspace = (ProjectWorkspaceDTO) sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class, projectWorkspaceKey);
//	    if(projectWorkspace !=null)
//	    {
//	    	sessionFactory.getCurrentSession().delete(projectWorkspace);
//	    }
//	    
//	    WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
//	    if(workspace !=null)
//	    {
//	    	sessionFactory.getCurrentSession().delete(workspace);
//	    }
//	    
//	    ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
//	    if(project !=null)
//	    {
//	    	sessionFactory.getCurrentSession().delete(project);
//	    }
//	    
//		QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser1");
//		if(user!=null)
//		{
//		  sessionFactory.getCurrentSession().delete(user);
//		}
//	    user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser2");
//	    if(user!=null)
//		{
//		  sessionFactory.getCurrentSession().delete(user);
//		}
//		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser3");
//		if(user!=null)
//		{
//		  sessionFactory.getCurrentSession().delete(user);
//		}
//	}
//
//	@Test
//	public void testAddWorkspaceDictionary() throws QuadrigaAccessException, QuadrigaStorageException 
//	{
//		boolean isAdded = false;
//		dbConnect.addWorkspaceDictionary("WS_1_Test", "DICT_2_Test", "projuser1");
//		
//		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
//		List<WorkspaceDictionaryDTO> workspaceDictionaryList = workspace.getWorkspaceDictionaryDTOList();
//		DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,"DICT_2_Test");
//		
//		WorkspaceDictionaryDTO workspaceDictionary = workspaceMapper.getWorkspaceDictionary(workspace, dictionary, "projuser1");
//		
//		if(workspaceDictionaryList.contains(workspaceDictionary))
//		{
//			isAdded = true;
//		}
//		
//		assertTrue(isAdded);
//	}
//
//	@Test
//	public void testListWorkspaceDictionary() throws QuadrigaAccessException, QuadrigaStorageException 
//	{
//		boolean isRetrieved = true;
//		IDictionary dictionary = null;
//		List<IDictionary> testDictionaryList = new ArrayList<IDictionary>();
//		List<IDictionary> dictionaryList = dbConnect.listWorkspaceDictionary("WS_1_Test", "projuser1");
//		
//		WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
//		List<WorkspaceDictionaryDTO> workspaceDictionaryList = workspace.getWorkspaceDictionaryDTOList();
//		for(WorkspaceDictionaryDTO workspaceDictionary : workspaceDictionaryList)
//		{
//			WorkspaceDictionaryDTOPK workspacedictionaryKey = workspaceDictionary.getWorkspaceDictionaryDTOPK();
//			DictionaryDTO dictionaryDTO = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,workspacedictionaryKey.getDictionaryid());
//		    dictionary = dictionaryMapper.getDictionary(dictionaryDTO);
//		    testDictionaryList.add(dictionary);
//		}
//		
//		if(dictionaryList.size() != testDictionaryList.size())
//		{
//			fail();
//		}
//		
//		for(IDictionary tempDictionary : dictionaryList)
//		{
//			if(!testDictionaryList.contains(tempDictionary))
//			{
//				isRetrieved = false;
//			}
//		}
//		
//		assertTrue(isRetrieved);
//	}
//
//	@Test
//	public void testDeleteWorkspaceDictionary() throws QuadrigaAccessException, QuadrigaStorageException 
//	{
//		boolean isDeleted = true;
//		  dbConnect.deleteWorkspaceDictionary("WS_1_Test", "projuser1","DICT_1_Test");
//		  
//		  WorkspaceDTO workspace = (WorkspaceDTO) sessionFactory.getCurrentSession().get(WorkspaceDTO.class,"WS_1_Test");
//		  DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,"DICT_1_Test");
//		
//		  List<WorkspaceDictionaryDTO> workspaceDictionaryList = dictionary.getWsDictionaryDTOList();
//		  if(workspaceDictionaryList != null)
//		  {
//			  for(WorkspaceDictionaryDTO tempWorkspaceDictionary : workspaceDictionaryList)
//			  {
//				  WorkspaceDTO testWorkspace = tempWorkspaceDictionary.getWorkspaceDTO();
//				  
//				  if((testWorkspace!=null)&&(workspace.equals(testWorkspace)))
//				  {
//					  isDeleted = false;
//				  }
//			  }
//		  }
//		  assertTrue(isDeleted);
//	}
//
//}
