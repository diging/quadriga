package edu.asu.spring.quadriga.dao.workbench;

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

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectConceptColleciton;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;

@ContextConfiguration(locations = {
		"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProjectConceptCollectionDAOTest {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ConceptCollectionDTOMapper collectionMapper;
	
	@Autowired
	private IDBConnectionProjectConceptColleciton dbConnect;
	
	@Autowired
	private ProjectDTOMapper projectMapper;
	
	
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
		user.setEmail("tpu@test.com");
		user.setQuadrigarole("role1,role4");
		sessionFactory.getCurrentSession().save(user);
		
		user = new QuadrigaUserDTO();
		user.setUsername("projuser3");
		user.setFullname("test project user");
		user.setCreatedby("projuser3");
		user.setCreateddate(date);
		user.setUpdatedby("projuser3");
		user.setUpdateddate(date);
		user.setEmail("tpu@test.com");
		user.setQuadrigarole("role1,role4");
		sessionFactory.getCurrentSession().save(user);
		
		//create a project
		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser1");
		List<ProjectConceptCollectionDTO> projectConceptCollectionList = new ArrayList<ProjectConceptCollectionDTO>();
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
		
		conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,"CC_1_Test");
		ProjectConceptCollectionDTOPK projectConceptCollectionKey = new ProjectConceptCollectionDTOPK("PROJ_1_Test","CC_1_Test");
		ProjectConceptCollectionDTO projectConceptCollection = new ProjectConceptCollectionDTO();
		projectConceptCollection.setProjectConceptcollectionDTOPK(projectConceptCollectionKey);
		projectConceptCollection.setProjectDTO(project);
		projectConceptCollection.setConceptCollection(conceptCollection);
		projectConceptCollection.setCreatedby("projuser1");
		projectConceptCollection.setCreateddate(date);
		projectConceptCollection.setUpdatedby("projuser1");
		projectConceptCollection.setUpdateddate(date);
		sessionFactory.getCurrentSession().save(projectConceptCollection);
		
		projectConceptCollectionList.add(projectConceptCollection);
		project.setProjectConceptCollectionDTOList(projectConceptCollectionList);
		sessionFactory.getCurrentSession().update(project);
		
		conceptCollection.setProjConceptCollectionDTOList(projectConceptCollectionList);
		sessionFactory.getCurrentSession().update(conceptCollection);
	}
	
	@After
	public void tearDown() throws Exception
	{
		ProjectConceptCollectionDTOPK projectConceptCollectionKey = new ProjectConceptCollectionDTOPK("PROJ_1_Test","CC_1_Test");
		ProjectConceptCollectionDTO projectConceptCollection = (ProjectConceptCollectionDTO) sessionFactory.getCurrentSession().get(ProjectConceptCollectionDTO.class, projectConceptCollectionKey);
		if(projectConceptCollection !=null)
		{
			sessionFactory.getCurrentSession().delete(projectConceptCollection);
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
	    
	    ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
		if(project!=null)
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
	public void testAddProjectConceptCollection() throws QuadrigaStorageException {
			boolean isAdded = false;
			dbConnect.addProjectConceptCollection("PROJ_1_Test", "CC_2_Test", "projuser1");
			
			ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
			List<ProjectConceptCollectionDTO> projectConceptCollection = project.getProjectConceptCollectionDTOList();
			
			ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,"CC_2_Test");
			
			 //add the concept collection to the project
			 ProjectConceptCollectionDTO testProjectConceptCollection = projectMapper.getProjectConceptCollection(project, conceptCollection, "projuser1");
			
			 if(projectConceptCollection.contains(testProjectConceptCollection))
			 {
				 isAdded = true;
			 }
			 
			 assertTrue(isAdded);
	}
	
	@Test
	public void testListProjectConceptCollection() throws QuadrigaStorageException {
			boolean isRetrieved = true;
			IConceptCollection collection = null;
			List<IConceptCollection> conceptCollectionList = new ArrayList<IConceptCollection>();
			List<IConceptCollection> conceptCollection = dbConnect.listProjectConceptCollection("PROJ_1_Test", "projuser1");
			
			
			ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
			List<ProjectConceptCollectionDTO> projectConceptCollectionList = project.getProjectConceptCollectionDTOList();
			
			//retrieve the concept collection details for every concept collection id
			for(ProjectConceptCollectionDTO tempProjectConceptCollection : projectConceptCollectionList)
			{
				ProjectConceptCollectionDTOPK projectConceptCollectionKey = tempProjectConceptCollection.getProjectConceptcollectionDTOPK();
				ConceptCollectionDTO collectionDTO = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,projectConceptCollectionKey.getConceptcollectionid());
				collection = collectionMapper.getConceptCollection(collectionDTO);
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
	public void testDeleteProjectConceptCollection() throws QuadrigaStorageException 
	{
        boolean isDeleted = true;
		dbConnect.deleteProjectConceptCollection("PROJ_1_Test", "projuser1", "CC_1_Test");
		
		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
		
		ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,"CC_1_Test");
		List<ProjectConceptCollectionDTO> testProjectConceptCollection =  conceptCollection.getProjConceptCollectionDTOList();
		
		for(ProjectConceptCollectionDTO tempProjectConceptCollection : testProjectConceptCollection )
		{
			ProjectDTO testProject = tempProjectConceptCollection.getProjectDTO();
			if(testProject !=null)
			{
			if(project.equals(testProject))
			{
				isDeleted = false;
			}
			}
		}
		
		assertTrue(isDeleted);
	}
}
