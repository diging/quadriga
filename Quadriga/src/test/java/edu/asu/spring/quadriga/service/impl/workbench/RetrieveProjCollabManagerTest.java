//package edu.asu.spring.quadriga.service.impl.workbench;
//
//import static org.junit.Assert.assertEquals;
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
//import edu.asu.spring.quadriga.domain.ICollaborator;
//import edu.asu.spring.quadriga.domain.IUser;
//import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
//import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
//import edu.asu.spring.quadriga.dto.ProjectDTO;
//import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
//import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
//import edu.asu.spring.quadriga.mapper.ProjectCollaboratorDTOMapper;
//import edu.asu.spring.quadriga.mapper.UserDTOMapper;
//import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
//
//@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
//"file:src/test/resources/root-context.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
//public class RetrieveProjCollabManagerTest 
//{
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	@Autowired
//	private IRetrieveProjCollabManager dbConnect;
//	
//	@Autowired
//	private ProjectCollaboratorDTOMapper projectCollaboratorMapper;
//	
//	@Autowired
//	private UserDTOMapper userMapper;
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
//				Date date = new Date();
//				QuadrigaUserDTO user = new QuadrigaUserDTO();
//				user.setUsername("projuser1");
//				user.setFullname("test project user");
//				user.setCreatedby("projuser1");
//				user.setCreateddate(date);
//				user.setUpdatedby("projuser1");
//				user.setUpdateddate(date);
//				user.setEmail("tpu@test.com");
//				user.setQuadrigarole("role1,role4");
//				sessionFactory.getCurrentSession().save(user);
//				
//				user = new QuadrigaUserDTO();
//				user.setUsername("projuser2");
//				user.setFullname("test project user");
//				user.setCreatedby("projuser2");
//				user.setCreateddate(date);
//				user.setUpdatedby("projuser2");
//				user.setUpdateddate(date);
//				user.setEmail("tpu@test.com");
//				user.setQuadrigarole("role1,role4");
//				sessionFactory.getCurrentSession().save(user);
//				
//				user = new QuadrigaUserDTO();
//				user.setUsername("projuser3");
//				user.setFullname("test project user");
//				user.setCreatedby("projuser3");
//				user.setCreateddate(date);
//				user.setUpdatedby("projuser3");
//				user.setUpdateddate(date);
//				user.setEmail("tpu@test.com");
//				user.setQuadrigarole("role1,role4");
//				sessionFactory.getCurrentSession().save(user);
//				
//				//create a project
//				user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser1");
//				ProjectDTO project = new ProjectDTO();
//				project.setProjectid("PROJ_1_Test");
//				project.setProjectname("testproject1");
//				project.setAccessibility("PUBLIC");
//				project.setCreatedby("projuser");
//				project.setCreateddate(date);
//				project.setUpdatedby("projuser");
//				project.setUpdateddate(date);
//				project.setUnixname("PROJ_1");
//				project.setProjectowner(user);
//				sessionFactory.getCurrentSession().save(project);
//				
//				user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser2");
//				List<ProjectCollaboratorDTO> projectCollaboratorList = new ArrayList<ProjectCollaboratorDTO>();
//				ProjectCollaboratorDTOPK projectCollaboratorKey = new ProjectCollaboratorDTOPK("PROJ_1_Test","projuser2","collaborator_role1");
//				ProjectCollaboratorDTO projectCollaborator = new ProjectCollaboratorDTO();
//				projectCollaborator.setProjectCollaboratorDTOPK(projectCollaboratorKey);
//				projectCollaborator.setQuadrigaUserDTO(user);
//				projectCollaborator.setProjectDTO(project);
//				projectCollaborator.setCreatedby("projuser2");
//				projectCollaborator.setCreateddate(date);
//				projectCollaborator.setUpdatedby("projuser2");
//				projectCollaborator.setUpdateddate(date);
//				sessionFactory.getCurrentSession().save(projectCollaborator);
//				
//				projectCollaboratorList.add(projectCollaborator);
//				project.setProjectCollaboratorDTOList(projectCollaboratorList);
//				sessionFactory.getCurrentSession().update(project);
//	}
//
//	@After
//	public void tearDown() throws Exception 
//	{
//		ProjectCollaboratorDTOPK projectCollaboratorKey = new ProjectCollaboratorDTOPK("PROJ_1_Test","projuser2","collaborator_role1");
//		ProjectCollaboratorDTO projectCollaborator = (ProjectCollaboratorDTO) sessionFactory.getCurrentSession().get(ProjectCollaboratorDTO.class, projectCollaboratorKey);
//		sessionFactory.getCurrentSession().delete(projectCollaborator);
//		
//		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
//		sessionFactory.getCurrentSession().delete(project);
//		
//		QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser1");
//		sessionFactory.getCurrentSession().delete(user);
//		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser2");
//		sessionFactory.getCurrentSession().delete(user);
//		user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser3");
//		sessionFactory.getCurrentSession().delete(user);
//	}
//
//	@Test
//	public void testGetProjectNonCollaborators() throws QuadrigaStorageException 
//	{
//		boolean isNonCollaborator  = false;
//		List<IUser> nonCollaborators = dbConnect.getProjectNonCollaborators("PROJ_1_Test");
//		
//		QuadrigaUserDTO user = (QuadrigaUserDTO) sessionFactory.getCurrentSession().get(QuadrigaUserDTO.class, "projuser3");
//		IUser testUser = userMapper.getUser(user);
//		
//		if(nonCollaborators.size() > 1)
//		{
//			fail();
//		}
//		
//		for(IUser tempUser : nonCollaborators)
//		{
//			if(tempUser.equals(testUser))
//				isNonCollaborator = true;
//		}
//		assertTrue(isNonCollaborator);
//	}
//
//	@Test
//	public void testGetProjectCollaborators() throws QuadrigaStorageException 
//	{
//		int numberOfCollaborators = 0;
//		List<ICollaborator> collaborators = dbConnect.getProjectCollaborators("PROJ_1_Test");
//		
//		ProjectDTO project = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class,"PROJ_1_Test");
//		
//		List<ICollaborator> testCollaborators = projectCollaboratorMapper.getProjectCollaboratorList(project);
//		
//		for(ICollaborator user : collaborators)
//		{
//			if(testCollaborators.contains(user))
//			{
//				numberOfCollaborators = numberOfCollaborators + 1;
//			}
//		}
//		
//		int size = collaborators.size();
//		
//		assertEquals(size,numberOfCollaborators);
//	}
//
//}
