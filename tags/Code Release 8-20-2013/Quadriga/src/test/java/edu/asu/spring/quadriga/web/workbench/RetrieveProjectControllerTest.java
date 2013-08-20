package edu.asu.spring.quadriga.web.workbench;

import static org.junit.Assert.assertEquals;

import java.security.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import com.mysql.jdbc.Statement;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RetrieveProjectControllerTest {
	
	RetrieveProjectController retrieveProjectController;
	
	@Autowired
	IDBConnectionRetrieveProjectManager dbConnection;
	
	@Autowired
	IDBConnectionModifyProjectManager dbModifyConnect;
	
	@Autowired
	IDBConnectionModifyWSManager dbWorkspaceConnect;
	
	String sDatabaseUsersSetup;
	String sDatabaseProjectSetup;
	String sDatabaseProjectWorkspaceSetup;
	String sDatabaseWorkspaceSetup;

	
	private Connection connection;
	
	private IUser user;
	
	@Autowired 
	IRetrieveProjectManager retrieveProjectManager;
	
	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	IProjectFactory projectFactory;
	
	@Autowired
	IWorkspaceFactory workspaceFactory;
	
	@Autowired
	private DataSource dataSource;
	
	Principal principal;	
	UsernamePasswordAuthenticationToken authentication;
	CredentialsContainer credentials;
	BindingAwareModelMap model;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		retrieveProjectController = new RetrieveProjectController();
		retrieveProjectController.setProjectManager(retrieveProjectManager);
		retrieveProjectController.setWsManager(wsManager);
		
		model =  new BindingAwareModelMap();		

		principal = new Principal() {			
			@Override
			public String getName() {
				return "test";
			}
		};
		
		authentication = new UsernamePasswordAuthenticationToken(principal, credentials);
		
		user = userFactory.createUserObject();
		user.setUserName("test");
		
		sDatabaseProjectWorkspaceSetup = "delete from tbl_project_workspace";
		sDatabaseWorkspaceSetup = "delete from tbl_workspace";
		sDatabaseProjectSetup = "delete from tbl_project";
		sDatabaseUsersSetup = "delete from tbl_quadriga_user_denied&delete from tbl_quadriga_user&delete from tbl_quadriga_user_requests&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Bob','bob',NULL,'bob@lsa.asu.edu','role5,role1',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('Test User','test',NULL,'test2@lsa.asu.edu','role4,role3',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user(fullname,username,passwd,email,quadrigarole,createdby,createddate,updatedby,updateddate)VALUES('John Doe','jdoe',NULL,'jdoe@lsa.asu.edu','role3,role4',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('dexter','dexter',NULL,'dexter@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('deb','deb',NULL,'deb@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())&INSERT INTO tbl_quadriga_user_requests(fullname, username,passwd,email,createdby,createddate,updatedby,updateddate)VALUES('harrison','harrison',NULL,'harrison@lsa.asu.edu',SUBSTRING_INDEX(USER(),'@',1),CURDATE(),SUBSTRING_INDEX(USER(),'@',1),CURDATE())";
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSetupTestEnvironment() throws QuadrigaStorageException
	{	
			String[] projectworkspaceQuery = sDatabaseProjectWorkspaceSetup.split("&");
			for(String singlquery:projectworkspaceQuery)
			assertEquals(1, dbConnection.setupTestEnvironment(singlquery));		
			
			String[] workspaceQuery = sDatabaseWorkspaceSetup.split("&");
			for(String singlquery:workspaceQuery)
			assertEquals(1, dbConnection.setupTestEnvironment(singlquery));	
				
			String[] projectQuery = sDatabaseProjectSetup.split("&");
			for(String singlquery:projectQuery)
			assertEquals(1, dbConnection.setupTestEnvironment(singlquery));
				
			String[] userquery = sDatabaseUsersSetup.split("&"); 
			for(String singlequery:userquery)
			assertEquals(1, dbConnection.setupTestEnvironment(singlequery));	
	}
	
	public void getConnection(){
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getProjectId(String name){

		getConnection();

		String id = null;

		try {
			Statement stmt = (Statement) connection.createStatement();

			stmt.execute("select projectid from tbl_project where projectname='"+name+"'");

			ResultSet resultSet = stmt.getResultSet();
			if(resultSet != null)
			{			

				while(resultSet.next())
				{
					id = resultSet.getString(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
	public String getWorkspaceId(String workspaceName){
		
		getConnection();
		
		String id = null;
		
		try {
			Statement stmt = (Statement) connection.createStatement();
			
			stmt.execute("select workspaceid from tbl_workspace where workspacename='"+workspaceName+"'");
			
			ResultSet resultSet = stmt.getResultSet();
			
			if(resultSet != null)
			{
				while(resultSet.next())
				{
					id = resultSet.getString(1);
				}
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	

	@SuppressWarnings("unchecked")
	@Test
	public void testGetProjectList() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			IProject project1 = projectFactory.createProjectObject();
			project1.setDescription("description");
			project1.setName("testProject1");
			project1.setUnixName("*111");
			project1.setOwner(user);
			project1.setProjectAccess(EProjectAccessibility.ACCESSIBLE);
			dbModifyConnect.addProjectRequest(project1);
			
			IProject project2 = projectFactory.createProjectObject();
			project2.setDescription("description");
			project2.setName("testProject2");
			project2.setUnixName("*222");
			project2.setOwner(user);
			project2.setProjectAccess(EProjectAccessibility.ACCESSIBLE);
			dbModifyConnect.addProjectRequest(project2);
			
			assertEquals(retrieveProjectController.getProjectList(model, principal), "auth/workbench");
			
			List<IProject> projectlist = (List<IProject>) model.get("projectlist");
			assertEquals(2, projectlist.size());
			
			dbModifyConnect.deleteProjectRequest(getProjectId("testProject1"));
			dbModifyConnect.deleteProjectRequest(getProjectId("testProject2"));

			
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetProjectDetails() throws QuadrigaStorageException {
		testSetupTestEnvironment();
		{
			IProject project = projectFactory.createProjectObject();
			project.setDescription("abc");
			project.setName("testproject1");
			project.setUnixName("*333");
			project.setOwner(user);
			project.setProjectAccess(EProjectAccessibility.ACCESSIBLE);
			dbModifyConnect.addProjectRequest(project);

			IWorkSpace workspace = workspaceFactory.createWorkspaceObject();
			workspace.setDescription("abc");
			workspace.setName("workspace1");
			workspace.setOwner(user);
			dbWorkspaceConnect.addWorkSpaceRequest(workspace, getProjectId("testproject1"));
			
			IWorkSpace workspace1 = workspaceFactory.createWorkspaceObject();
			workspace1.setDescription("xyz");
			workspace1.setName("workspace2");
			workspace1.setOwner(user);
			dbWorkspaceConnect.addWorkSpaceRequest(workspace1, getProjectId("testproject1"));
			
			assertEquals(retrieveProjectController.getProjectDetails(getProjectId("testproject1"), model, principal), "auth/workbench/project");

			List<IWorkSpace> wslist = (List<IWorkSpace>) model.get("workspaceList");
			assertEquals(2, wslist.size());
			
			dbWorkspaceConnect.deleteWorkspaceRequest(getWorkspaceId("workspace1"));
			dbWorkspaceConnect.deleteWorkspaceRequest(getWorkspaceId("workspace2"));
			
			dbModifyConnect.deleteProjectRequest(getProjectId("testproject1"));
		}
		
	}

}
