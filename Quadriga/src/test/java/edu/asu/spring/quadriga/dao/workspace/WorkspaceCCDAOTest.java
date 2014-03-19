package edu.asu.spring.quadriga.dao.workspace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWorkspaceCC;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
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
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;

@ContextConfiguration(locations = {
		"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WorkspaceCCDAOTest 
{
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private IWorkspaceCCManager workspaceConceptCollectionManager;
	
	@Autowired
	private IDBConnectionWorkspaceCC dbConnectionWorkspaceConceptColleciton;
	
	@Autowired
	IDBConnectionCCManager dbConnect;

	@Autowired
	private IConceptCollectionManager conceptCollectionManager;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private IModifyProjectManager modifyProjectManager;

	String sDatabaseSetup[];

	@Autowired
	IDBConnectionDictionaryManager dbConnection;
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IListWSManager wsManager;
	
	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private static final Logger logger = LoggerFactory
			.getLogger(WorkspaceCCDAOTest.class);

	private Connection connection;

	@Autowired
	private IRetrieveProjectManager retrieveProjectManager;

	private IUser user;

	
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
	    	sessionFactory.getCurrentSession().get(ProjectWorkspaceDTO.class,projectWorkspace);
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

	public void getConnection() {
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getCCID(String name) {
		getConnection();
		String id = null;
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("select id from tbl_conceptcollections where collectionname='"
					+ name + "'");
			ResultSet rs = stmt.getResultSet();
			if (rs != null) {
				while (rs.next()) {
					id = rs.getString(1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	



	@Test
	public void testSetupTestEnvironment() throws QuadrigaStorageException {
		for (String singleQuery : sDatabaseSetup) {
			assertEquals(1, dbConnection.setupTestEnvironment(singleQuery));
		}
	}
	
	@Test
	public void testAddWorkspaceCC() throws QuadrigaAccessException, QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			dbConnection
					.setupTestEnvironment("INSERT  INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES('projDict','description','unix','1','jdoe','PUBLIC','jdoe',NOW(),'jdoe',NOW());");
			IProject project1 = null;
			try {
				project1 = retrieveProjectManager.getProjectDetails("1");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
			assertEquals(project1.getDescription().equals("description"), true);
			assertEquals(project1.getName().equals("projDict"), true);
			assertEquals(project1.getUnixName().equals("unix"), true);
		}
		
		
		{
			dbConnection
					.setupTestEnvironment("INSERT  INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES('workDict','description','1','jdoe','1','1','jdoe',NOW(),'jdoe',NOW());");
			dbConnection
			.setupTestEnvironment("INSERT  INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES('1','1','jdoe',NOW(),'jdoe',NOW());");
			IWorkSpace workspace1 = null;
			try {
				workspace1 = wsManager.getWorkspaceDetails("1", "jdoe");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
			assertEquals(workspace1.getDescription().equals("description"), true);
			assertEquals(workspace1.getName().equals("workDict"), true);
		}
		{
			IConceptCollection conceptColleciton=conceptCollectionFactory.createConceptCollectionObject();			
			conceptColleciton.setName("Test CC");
			conceptColleciton.setDescription("description");
			conceptColleciton.setOwner(user);
			String msg = "";
			try {
				conceptCollectionManager.addConceptCollection(conceptColleciton);
			} catch (QuadrigaStorageException e1) {
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting concept collection for user :"
						+ user.getUserName());
				List<IConceptCollection> ccList=null;
				try {
					ccList = dbConnect.getConceptsOwnedbyUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					e.printStackTrace();
				}
				
				Iterator<IConceptCollection> I=ccList.iterator();
				
				String name = null;
				String desc = null;

				while (I.hasNext()) {
					IConceptCollection conceptCollection = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollection = I.next();
					assertEquals((conceptCollection != null), true);
					if (conceptCollection != null) {
						name = conceptCollection.getName();
						desc = conceptCollection.getDescription();
					}
				}

				assertEquals(name.equals("Test CC"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("testAddProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
				fail("testAddProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				dbConnectionWorkspaceConceptColleciton.addWorkspaceCC("1", getCCID("Test CC"), "jdoe");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}

			List<IConceptCollection> ccList1 = null;
			try {
				ccList1 = workspaceConceptCollectionManager.listWorkspaceCC("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
			Iterator<IConceptCollection> I = ccList1.iterator();
			assertEquals(ccList1.size() > 0, true);
			while (I.hasNext()) {
				IConceptCollection cc = I.next();
				logger.info("Verifying the concept collection addition to project");
				assertEquals(cc.getName().equals("Test CC"), true);
			}
		}
		dbConnection.setupTestEnvironment("delete from tbl_workspace_conceptcollection");
		dbConnection.setupTestEnvironment("delete from tbl_project_workspace");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		dbConnection.setupTestEnvironment("delete from tbl_workspace");
		dbConnection.setupTestEnvironment("delete from tbl_conceptcollections");
	}

	@Test
	public void testListWorkspaceCC() throws QuadrigaAccessException, QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			dbConnection
					.setupTestEnvironment("INSERT  INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES('projDict','description','unix','1','jdoe','PUBLIC','jdoe',NOW(),'jdoe',NOW());");
			IProject project1 = null;
			try {
				project1 = retrieveProjectManager.getProjectDetails("1");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
			assertEquals(project1.getDescription().equals("description"), true);
			assertEquals(project1.getName().equals("projDict"), true);
			assertEquals(project1.getUnixName().equals("unix"), true);
		}
		
		
		{
			dbConnection
					.setupTestEnvironment("INSERT  INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES('workDict','description','1','jdoe','1','1','jdoe',NOW(),'jdoe',NOW());");
			dbConnection
			.setupTestEnvironment("INSERT  INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES('1','1','jdoe',NOW(),'jdoe',NOW());");
			IWorkSpace workspace1 = null;
			try {
				workspace1 = wsManager.getWorkspaceDetails("1", "jdoe");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
			assertEquals(workspace1.getDescription().equals("description"), true);
			assertEquals(workspace1.getName().equals("workDict"), true);
		}
		{
			IConceptCollection conceptColleciton=conceptCollectionFactory.createConceptCollectionObject();			
			conceptColleciton.setName("Test CC");
			conceptColleciton.setDescription("description");
			conceptColleciton.setOwner(user);
			String msg = "";
			try {
				conceptCollectionManager.addConceptCollection(conceptColleciton);
			} catch (QuadrigaStorageException e1) {
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting concept collection for user :"
						+ user.getUserName());
				List<IConceptCollection> ccList=null;
				try {
					ccList = dbConnect.getConceptsOwnedbyUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					e.printStackTrace();
				}
				
				Iterator<IConceptCollection> I=ccList.iterator();
				
				String name = null;
				String desc = null;

				while (I.hasNext()) {
					IConceptCollection conceptCollection = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollection = I.next();
					assertEquals((conceptCollection != null), true);
					if (conceptCollection != null) {
						name = conceptCollection.getName();
						desc = conceptCollection.getDescription();
					}
				}

				assertEquals(name.equals("Test CC"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("testAddProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
				fail("testAddProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				dbConnectionWorkspaceConceptColleciton.addWorkspaceCC("1", getCCID("Test CC"), "jdoe");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}

			List<IConceptCollection> ccList1 = null;
			try {
				ccList1 = workspaceConceptCollectionManager.listWorkspaceCC("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
			Iterator<IConceptCollection> I = ccList1.iterator();
			assertEquals(ccList1.size() > 0, true);
			while (I.hasNext()) {
				IConceptCollection cc = I.next();
				logger.info("Verifying the concept collection addition to project");
				assertEquals(cc.getName().equals("Test CC"), true);
			}
		}
		dbConnection.setupTestEnvironment("delete from tbl_workspace_conceptcollection");
		dbConnection.setupTestEnvironment("delete from tbl_project_workspace");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		dbConnection.setupTestEnvironment("delete from tbl_workspace");
		dbConnection.setupTestEnvironment("delete from tbl_conceptcollections");
	}

	@Test
	public void testDeleteWorkspaceCC() throws QuadrigaStorageException, QuadrigaAccessException {
		testSetupTestEnvironment();
		{
			dbConnection
					.setupTestEnvironment("INSERT  INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES('projDict','description','unix','1','jdoe','PUBLIC','jdoe',NOW(),'jdoe',NOW());");
			IProject project1 = null;
			try {
				project1 = retrieveProjectManager.getProjectDetails("1");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
			assertEquals(project1.getDescription().equals("description"), true);
			assertEquals(project1.getName().equals("projDict"), true);
			assertEquals(project1.getUnixName().equals("unix"), true);
		}
		
		
		{
			dbConnection
					.setupTestEnvironment("INSERT  INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES('workDict','description','1','jdoe','1','1','jdoe',NOW(),'jdoe',NOW());");
			dbConnection
			.setupTestEnvironment("INSERT  INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES('1','1','jdoe',NOW(),'jdoe',NOW());");
			IWorkSpace workspace1 = null;
			try {
				workspace1 = wsManager.getWorkspaceDetails("1", "jdoe");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
			assertEquals(workspace1.getDescription().equals("description"), true);
			assertEquals(workspace1.getName().equals("workDict"), true);
		}
		{
			IConceptCollection conceptColleciton=conceptCollectionFactory.createConceptCollectionObject();			
			conceptColleciton.setName("Test CC");
			conceptColleciton.setDescription("description");
			conceptColleciton.setOwner(user);
			String msg = "";
			try {
				conceptCollectionManager.addConceptCollection(conceptColleciton);
			} catch (QuadrigaStorageException e1) {
				e1.printStackTrace();
			}
			logger.info(" message : " + msg);
			if (msg.equals("")) {
				logger.info("Getting concept collection for user :"
						+ user.getUserName());
				List<IConceptCollection> ccList=null;
				try {
					ccList = dbConnect.getConceptsOwnedbyUser(user
							.getUserName());
				} catch (QuadrigaStorageException e) {
					e.printStackTrace();
				}
				
				Iterator<IConceptCollection> I=ccList.iterator();
				
				String name = null;
				String desc = null;

				while (I.hasNext()) {
					IConceptCollection conceptCollection = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollection = I.next();
					assertEquals((conceptCollection != null), true);
					if (conceptCollection != null) {
						name = conceptCollection.getName();
						desc = conceptCollection.getDescription();
					}
				}

				assertEquals(name.equals("Test CC"), true);
				assertEquals(desc.equals("description"), true);
			} else {
				logger.info("testAddProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
				fail("testAddProjectConceptCollection: Create Concept collection Failed ; message :"
						+ msg);
			}
		}

		{
			try {
				String conceptCollectionId =  getCCID("Test CC");
				System.out.println("Testing :"+conceptCollectionId);
				dbConnectionWorkspaceConceptColleciton.addWorkspaceCC("1",conceptCollectionId , "jdoe");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}

			List<IConceptCollection> ccList1 = null;
			try {
				ccList1 = workspaceConceptCollectionManager.listWorkspaceCC("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
			Iterator<IConceptCollection> I = ccList1.iterator();
			assertEquals(ccList1.size() > 0, true);
			while (I.hasNext()) {
				IConceptCollection cc = I.next();
				logger.info("Verifying the concept collection addition to project");
				assertEquals(cc.getName().equals("Test CC"), true);
			}
			workspaceConceptCollectionManager.deleteWorkspaceCC("1", "jdoe", getCCID("Test CC"));
			ccList1 = null;
			try {
				ccList1 = workspaceConceptCollectionManager.listWorkspaceCC("1",
						"jdoe");
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}
			I = ccList1.iterator();
			assertEquals(ccList1.size() > 0, false);
		}
		dbConnection.setupTestEnvironment("delete from tbl_workspace_conceptcollection");
		dbConnection.setupTestEnvironment("delete from tbl_project_workspace");
		dbConnection.setupTestEnvironment("delete from tbl_project");
		dbConnection.setupTestEnvironment("delete from tbl_workspace");
		dbConnection.setupTestEnvironment("delete from tbl_conceptcollections");
	}

}
