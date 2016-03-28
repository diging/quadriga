//package edu.asu.spring.quadriga.web.workbench;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.codehaus.jettison.json.JSONException;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.web.servlet.ModelAndView;
//
//import edu.asu.spring.quadriga.accesschecks.ICheckProjectSecurity;
//import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjectManager;
//import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
//import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManager;
//import edu.asu.spring.quadriga.domain.ICollaborator;
//import edu.asu.spring.quadriga.domain.IUser;
//import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
//import edu.asu.spring.quadriga.domain.factories.IUserFactory;
//import edu.asu.spring.quadriga.domain.factory.workbench.IProjectFactory;
//import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceFactory;
//import edu.asu.spring.quadriga.domain.workbench.IProject;
//import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
//import edu.asu.spring.quadriga.service.IUserManager;
//import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
//import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
//import edu.asu.spring.quadriga.service.workspace.IListWSManager;
//
//@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
//"file:src/test/resources/root-context.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class RetrieveProjectControllerTest {
//	
//	RetrieveProjectController retrieveProjectController;
//	
//	@Autowired
//	IDBConnectionRetrieveProjectManager dbConnect;
//	
//	@Autowired
//	IDBConnectionModifyProjectManager dbModifyConnect;
//	
//	@Autowired
//	IDBConnectionModifyWSManager dbWorkspaceConnect;
//	
//	@Autowired 
//	IRetrieveProjectManager retrieveProjectManager;
//	
//	@Autowired
//	IListWSManager wsManager;
//	
//	@Autowired
//	IUserFactory userFactory;
//	
//	@Autowired
//	IUserManager userManager;
//	
//	@Autowired
//	IProjectFactory projectFactory;
//	
//	@Autowired
//	IWorkspaceFactory workspaceFactory;
//	
//	@Autowired
//	ICheckProjectSecurity projectSecurity;
//	
//	@Autowired
//	private IRetrieveProjCollabManager projectManager;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		
//		retrieveProjectController = new RetrieveProjectController();
//		retrieveProjectController.setProjectManager(retrieveProjectManager);
//		retrieveProjectController.setWsManager(wsManager);
//		retrieveProjectController.setProjectSecurity(projectSecurity);
//		
//		String[] databaseQuery = new String[20];
//		databaseQuery[0] = "INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('user1','user1',NULL,'user1@lsa.asu.edu','role5,role1','user1',CURDATE(),'user1',CURDATE())";
//		databaseQuery[1] = "INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('user2','user2',NULL,'user2@lsa.asu.edu','role5,role1','user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[2] = "INSERT INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES ('proj1','test project','proj1','PROJ_1','user1','ACCESSIBLE','user1',CURDATE(),'user1',CURDATE())";
//		databaseQuery[3] = "INSERT INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES ('proj2','test project','proj2','PROJ_2','user1','ACCESSIBLE','user1',CURDATE(),'user1',CURDATE())";
//		databaseQuery[4] = "INSERT INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES ('proj3','test project','proj3','PROJ_3','user2','ACCESSIBLE','user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[5] = "INSERT INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES ('proj4','test project','proj4','PROJ_4','user2','ACCESSIBLE','user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[6] = "INSERT INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES ('proj5','test project','proj5','PROJ_5','user2','ACCESSIBLE','user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[7] = "INSERT INTO tbl_project(projectname,description,unixname,projectid,projectowner,accessibility,updatedby,updateddate,createdby,createddate) VALUES ('proj6','test project','proj6','PROJ_6','user2','ACCESSIBLE','user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[8] = "INSERT INTO tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,updatedby,updateddate,createdby,createddate) VALUES ('PROJ_3','user1','collaborator_role1','user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[9] = "INSERT INTO tbl_project_collaborator(projectid,collaboratoruser,collaboratorrole,updatedby,updateddate,createdby,createddate) VALUES ('PROJ_4','user1','collaborator_role2','user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[10] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES ('ws1','testing workspace','WS_1','user1',0,0,'user1',CURDATE(),'user1',CURDATE())";
//		databaseQuery[11] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES ('ws2','testing workspace','WS_2','user1',0,0,'user1',CURDATE(),'user1',CURDATE())";
//		databaseQuery[12] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES ('ws3','testing workspace','WS_3','user2',0,0,'user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[13] = "INSERT INTO tbl_workspace(workspacename,description,workspaceid,workspaceowner,isarchived,isdeactivated,updatedby,updateddate,createdby,createddate) VALUES ('ws4','testing workspace','WS_4','user2',0,0,'user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[14] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES ('PROJ_5','WS_1','user1',CURDATE(),'user1',CURDATE())";
//		databaseQuery[15] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES ('PROJ_5','WS_2','user1',CURDATE(),'user1',CURDATE())";
//		databaseQuery[16] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES ('PROJ_6','WS_3','user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[17] = "INSERT INTO tbl_project_workspace(projectid,workspaceid,updatedby,updateddate,createdby,createddate) VALUES ('PROJ_6','WS_4','user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[18] = "INSERT INTO tbl_workspace_collaborator(workspaceid,username,collaboratorrole,updatedby,updateddate,createdby,createddate) VALUES ('WS_3','user1','wscollab_role1','user2',CURDATE(),'user2',CURDATE())";
//		databaseQuery[19] = "INSERT INTO tbl_workspace_collaborator(workspaceid,username,collaboratorrole,updatedby,updateddate,createdby,createddate) VALUES ('WS_4','user1','wscollab_role1','user2',CURDATE(),'user2',CURDATE())";
//		for(String query : databaseQuery)
//		{
////			((DBConnectionRetrieveProjectManager)dbConnect).setupTestEnvironment(query);
//		}
//	}
//
//	@After
//	public void tearDown() throws Exception 
//	{
//		String[] databaseQuery = new String[6];
//		databaseQuery[0] = "DELETE FROM tbl_workspace_collaborator WHERE workspaceid IN ('WS_3','WS_4')";
//		databaseQuery[1] = "DELETE FROM tbl_project_workspace WHERE projectid IN ('PROJ_5','PROJ_6')";
//		databaseQuery[2] = "DELETE FROM tbl_workspace WHERE workspaceid IN ('WS_1','WS_2','WS_3','WS_4')";
//		databaseQuery[3] = "DELETE FROM tbl_project_collaborator WHERE projectid IN ('PROJ_3','PROJ_4')";
//		databaseQuery[4] = "DELETE FROM tbl_project WHERE projectid IN ('PROJ_1','PROJ_2','PROJ_3','PROJ_4','PROJ_5','PROJ_6')";
//		databaseQuery[5] = "DELETE FROM tbl_quadriga_user WHERE username IN ('user1','user2')";
//		for(String query : databaseQuery)
//		{
////			((DBConnectionRetrieveProjectManager)dbConnect).setupTestEnvironment(query);
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testGetProjectListByOwner() throws QuadrigaStorageException, JSONException
//	{
//		//create the project objects and compare it with the retrieved objects
//		List<IProject> tempProjectList = new ArrayList<IProject>();
//		List<IProject> projectList = null;
//		IProject project = null;
//		IUser user = null;
//		ModelAndView model;
//		boolean success = false;
//		Principal principal = new Principal() {			
//			@Override
//			public String getName() {
//				return "user1";
//			}
//		};
//		
//		project = projectFactory.createProjectObject();
//		project.setProjectId("PROJ_1");
//		project.setProjectName("proj1");
//		project.setDescription("test project");
//		project.setUnixName("proj1");
//		project.setProjectAccess(EProjectAccessibility.PUBLIC);
//		user = userManager.getUserDetails("user1");
//		project.setOwner(user);
//		tempProjectList.add(project);
//		
//		project = projectFactory.createProjectObject();
//		project.setProjectId("PROJ_2");
//		project.setProjectName("proj2");
//		project.setDescription("test project");
//		project.setUnixName("proj2");
//		project.setProjectAccess(EProjectAccessibility.PUBLIC);
//		user = userManager.getUserDetails("user1");
//		project.setOwner(user);
//		tempProjectList.add(project);
//		
//		model = retrieveProjectController.getProjectList(principal);
//		
//		projectList = (List<IProject>) model.getModelMap().get("projectlistasowner");
//		
//		for (IProject tempproject : projectList)
//		{
//			if(tempProjectList.contains(tempproject))
//			{
//				tempProjectList.remove(tempproject);
//			}
//		}
//		
//		if(tempProjectList.size() == 0)
//		{
//		   success = true;	
//		}
//		
//		assertTrue(success);
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testGetProjectListByCollaborator() throws QuadrigaStorageException, JSONException
//	{
//		//create the project objects and compare it with the retrieved objects
//		List<IProject> tempProjectList = new ArrayList<IProject>();
//		List<IProject> projectList = null;
//		IProject project = null;
//		IUser user = null;
//		ModelAndView model;
//		boolean success = false;
//		Principal principal = new Principal() {			
//			@Override
//			public String getName() {
//				return "user1";
//			}
//		};
//		
//		project = projectFactory.createProjectObject();
//		project.setProjectId("PROJ_3");
//		project.setProjectName("proj3");
//		project.setDescription("test project");
//		project.setUnixName("proj3");
//		project.setProjectAccess(EProjectAccessibility.PUBLIC);
//		user = userManager.getUserDetails("user2");
//		project.setOwner(user);
//		tempProjectList.add(project);
//		
//		project = projectFactory.createProjectObject();
//		project.setProjectId("PROJ_4");
//		project.setProjectName("proj4");
//		project.setDescription("test project");
//		project.setUnixName("proj4");
//		project.setProjectAccess(EProjectAccessibility.PUBLIC);
//		user = userManager.getUserDetails("user2");
//		project.setOwner(user);
//		tempProjectList.add(project);
//		
//		model = retrieveProjectController.getProjectList(principal);
//		
//		projectList = (List<IProject>) model.getModelMap().get("projectlistascollaborator");
//		
//		for (IProject tempproject : projectList)
//		{
//			if(tempProjectList.contains(tempproject))
//			{
//				tempProjectList.remove(tempproject);
//			}
//		}
//		
//		if(tempProjectList.size() == 0)
//		{
//		   success = true;	
//		}
//		
//		assertTrue(success);
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testGetProjectListByWorkspaceOwner() throws QuadrigaStorageException, JSONException
//	{
//		//create the project objects and compare it with the retrieved objects
//				List<IProject> tempProjectList = new ArrayList<IProject>();
//				List<IProject> projectList = null;
//				IProject project = null;
//				IUser user = null;
//				ModelAndView model;
//				boolean success = false;
//				Principal principal = new Principal() {			
//					@Override
//					public String getName() {
//						return "user1";
//					}
//				};
//				
//				project = projectFactory.createProjectObject();
//				project.setProjectId("PROJ_5");
//				project.setProjectName("proj5");
//				project.setDescription("test project");
//				project.setUnixName("proj5");
//				project.setProjectAccess(EProjectAccessibility.PUBLIC);
//				user = userManager.getUserDetails("user2");
//				project.setOwner(user);
//				tempProjectList.add(project);
//				
//				model = retrieveProjectController.getProjectList(principal);
//				
//				projectList = (List<IProject>) model.getModelMap().get("projectlistaswsowner");
//				
//				for (IProject tempproject : projectList)
//				{
//					if(tempProjectList.contains(tempproject))
//					{
//						tempProjectList.remove(tempproject);
//					}
//				}
//				
//				if(tempProjectList.size() == 0)
//				{
//				   success = true;	
//				}
//				
//				assertTrue(success);
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testGetProjectListByWSCollaborator() throws QuadrigaStorageException, JSONException
//	{
//		//create the project objects and compare it with the retrieved objects
//		List<IProject> tempProjectList = new ArrayList<IProject>();
//		List<IProject> projectList = null;
//		IProject project = null;
//		IUser user = null;
//		ModelAndView model;
//		boolean success = false;
//		Principal principal = new Principal() {			
//			@Override
//			public String getName() {
//				return "user1";
//			}
//		};
//		
//		project = projectFactory.createProjectObject();
//		project.setProjectId("PROJ_6");
//		project.setProjectName("proj6");
//		project.setDescription("test project");
//		project.setUnixName("proj6");
//		project.setProjectAccess(EProjectAccessibility.PUBLIC);
//		user = userManager.getUserDetails("user2");
//		project.setOwner(user);
//		tempProjectList.add(project);
//		
//		model = retrieveProjectController.getProjectList(principal);
//		
//		projectList = (List<IProject>) model.getModelMap().get("projectlistaswscollaborator");
//		
//		for (IProject tempproject : projectList)
//		{
//			if(tempProjectList.contains(tempproject))
//			{
//				tempProjectList.remove(tempproject);
//			}
//		}
//		
//		if(tempProjectList.size() == 0)
//		{
//		   success = true;	
//		}
//		
//		assertTrue(success);
//	}
//	
//	@Test
//	public void testGetProjectDetails() throws QuadrigaStorageException {
//		{
//			//create the project objects and compare it with the retrieved objects
//			IProject project = null;
//			IProject tempProject = null;
//			IUser user = null;
//			ModelAndView model;
//			List<ICollaborator> collaboratorList;
//			
//			Principal principal = new Principal() {			
//				@Override
//				public String getName() {
//					return "user1";
//				}
//			};
//			
//			project = projectFactory.createProjectObject();
//			project.setProjectId("PROJ_5");
//			project.setProjectName("proj5");
//			project.setDescription("test project");
//			project.setUnixName("proj5");
//			project.setProjectAccess(EProjectAccessibility.PUBLIC);
//			user = userManager.getUserDetails("user2");
//			project.setOwner(user);
//			
//			collaboratorList = projectManager.getProjectCollaborators("PROJ_5");
//			project.setCollaborators(collaboratorList);
//			
//			model = retrieveProjectController.getProjectDetails("PROJ_5", principal);
//			
//			tempProject = (IProject) model.getModelMap().get("project");
//			
//			assertEquals(project,tempProject);
//		}
//		
//	}
//
//}
