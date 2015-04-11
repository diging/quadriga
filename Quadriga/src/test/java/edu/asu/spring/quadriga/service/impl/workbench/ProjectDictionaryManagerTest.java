//package edu.asu.spring.quadriga.service.impl.workbench;
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
//import edu.asu.spring.quadriga.dto.ProjectDictionaryDTO;
//import edu.asu.spring.quadriga.dto.ProjectDictionaryDTOPK;
//import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
//import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
//import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;
//import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;
//import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;
//
//@ContextConfiguration(locations = {
//		"file:src/test/resources/spring-dbconnectionmanager.xml",
//		"file:src/test/resources/root-context.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
//public class ProjectDictionaryManagerTest {
//	
//	@Autowired
//	private IProjectDictionaryManager dbConnect;
//	
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	@Autowired
//	private ProjectDTOMapper projectMapper;
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
//		
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
//		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser1");
//		List<ProjectDictionaryDTO> projectDictionaryList = new ArrayList<ProjectDictionaryDTO>();
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
//		dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, "DICT_1_Test");
//		ProjectDictionaryDTOPK projectDictionaryKey = new ProjectDictionaryDTOPK("PROJ_1_Test","DICT_1_Test");
//		ProjectDictionaryDTO projectDictionary = new ProjectDictionaryDTO();
//		projectDictionary.setProjectDictionaryDTOPK(projectDictionaryKey);
//		projectDictionary.setProject(project);
//		projectDictionary.setDictionary(dictionary);
//		projectDictionary.setCreatedby("projuser1");
//		projectDictionary.setCreateddate(date);
//		projectDictionary.setUpdatedby("projuser1");
//		projectDictionary.setUpdateddate(date);
//		sessionFactory.getCurrentSession().save(projectDictionary);
//		
//		projectDictionaryList.add(projectDictionary);
//		project.setProjectDictionaryDTOList(projectDictionaryList);
//		sessionFactory.getCurrentSession().update(project);
//		
//		dictionary.setProjectDictionaryDTOList(projectDictionaryList);
//		sessionFactory.getCurrentSession().update(dictionary);
//		
//	}
//
//	@After
//	public void tearDown() throws Exception 
//	{
//		ProjectDictionaryDTOPK projectDictionaryKey = new ProjectDictionaryDTOPK("PROJ_1_Test","DICT_1_Test");
//		ProjectDictionaryDTO projectDictionary = (ProjectDictionaryDTO) sessionFactory.getCurrentSession().get(ProjectDictionaryDTO.class, projectDictionaryKey);
//		if(projectDictionary!=null)
//		{
//		  sessionFactory.getCurrentSession().delete(projectDictionary);
//		}
//		
//		DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,"DICT_1_Test");
//		if(dictionary!=null)
//		{
//			sessionFactory.getCurrentSession().delete(dictionary);
//		}
//		
//		dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,"DICT_2_Test");
//		if(dictionary!=null)
//		{
//			sessionFactory.getCurrentSession().delete(dictionary);
//		}
//		
//	    ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
//		if(project!=null)
//		{
//	      sessionFactory.getCurrentSession().delete(project);
//		}
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
//	public void testAddProjectDictionary() throws QuadrigaStorageException 
//	{
//		boolean isAdded = false;
//		dbConnect.addDictionaryToProject("PROJ_1_Test", "DICT_2_Test", "projuser1");
//		
//		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
//		List<ProjectDictionaryDTO> projectDictionaryList = project.getProjectDictionaryDTOList();
//		
//		DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, "DICT_2_Test");
//		
//		ProjectDictionaryDTO projectDictionary = projectMapper.getProjectDictionary(project, dictionary, "projuser1");
//		
//		if((projectDictionaryList !=null) && (projectDictionaryList.contains(projectDictionary)))
//		{
//			isAdded = true;
//		}
//		
//		assertTrue(isAdded);
//	}
//
//	@Test
//	public void testListProjectDictionary() throws QuadrigaStorageException 
//	{
//		boolean isRetrieved = true;
//		IDictionary dictionary = null;
//		DictionaryDTO dictionaryDTO = null;
//		List<IDictionary> dictionaryList = null;
//				
//		List<IDictionary> testDictionaryList = dbConnect.listProjectDictionary("PROJ_1_Test","projuser1");
//		
//		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
//		List<ProjectDictionaryDTO> projectDictionaryList = project.getProjectDictionaryDTOList();
//		
//		dictionaryList = new ArrayList<IDictionary>();
//		 for(ProjectDictionaryDTO projectDictionary: projectDictionaryList)	
//		    {
//		    	ProjectDictionaryDTOPK projectDictionaryKey = projectDictionary.getProjectDictionaryDTOPK();
//		    	dictionaryDTO = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, projectDictionaryKey.getDictionaryid());
//		    	dictionary = dictionaryMapper.getDictionary(dictionaryDTO);
//		    	dictionaryList.add(dictionary);
//		    }
//		 
//		 if(dictionaryList.size() != testDictionaryList.size())
//		 {
//			 fail();
//		 }
//		 
//		 for(IDictionary tempDictionary : testDictionaryList)
//		 {
//			 if(!dictionaryList.contains(tempDictionary))
//			 {
//				 isRetrieved = false;
//			 }
//		 }
//		 assertTrue(isRetrieved);
//	}
//
//	@Test
//	public void testDeleteProjectDictionary() throws QuadrigaStorageException 
//	{
//		 boolean isDeleted = true;
//		 dbConnect.deleteProjectDictionary("PROJ_1_Test", "projuser1", "DICT_1_Test");
//		 
//		 ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
//		 
//		 DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,"DICT_1_Test");
//		 List<ProjectDictionaryDTO> projectDictionaryList = dictionary.getProjectDictionaryDTOList();
//		 
//		 for(ProjectDictionaryDTO projectDictionary : projectDictionaryList)
//		 {
//			 ProjectDTO testProject = projectDictionary.getProject();
//			 if((testProject !=null) && (project.equals(testProject)))
//			 {
//				 isDeleted = false;
//			 }
//		 }
//         assertTrue(isDeleted);		
//	}
//
//}
