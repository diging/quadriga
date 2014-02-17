package edu.asu.spring.quadriga.service.impl.workbench;

import static org.junit.Assert.*;

import java.security.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.jdbc.log.Log;

import edu.asu.spring.quadriga.db.sql.DBConnectionDictionaryManagerTest;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjCollabManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ModifyProjCollabManagerTest {
	
	@Autowired
	IDBConnectionModifyProjCollabManager dbConnection;
	
	@Autowired
	IDBConnectionModifyProjectManager dbProjectConnection;
	
	@Autowired
	IDBConnectionRetrieveProjCollabManager dbRetrieveProjectConn;
	
	@Autowired
	IModifyProjectManager modifyProjectManager;	
	
	@Autowired
	IModifyProjCollabManager modifyProjCollabManager;
	
	private Connection connection;
	
	@Autowired
	private DataSource dataSource;

	String sDatabaseSetup [];
	
	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionDictionaryManagerTest.class);

	@Autowired
	private IProjectFactory projectFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
	private IUser user,user1; 
	Principal principal;	
	List<ICollaborator> collaboratorList;
	ICollaborator collaborator;
	ICollaborator collaborator1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		user = userFactory.createUserObject();
		user.setUserName("projuser1");
		
		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role3");
		roles.add(role);
		IQuadrigaRole role1 = quadrigaRoleFactory.createQuadrigaRoleObject();
		role1.setDBid("role4");
		roles.add(role1);
		
		IQuadrigaRole quadrigaRole = null;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
		for(int i=0;i<roles.size();i++)
		{
			quadrigaRole = rolemanager.getQuadrigaRole(roles.get(i).getDBid());
			
			//If user account is deactivated remove other roles 
			if(quadrigaRole.getId().equals(RoleNames.ROLE_QUADRIGA_DEACTIVATED))
			{
				rolesList.clear();
			}
			
			rolesList.add(quadrigaRole);
		}
			
		user.setQuadrigaRoles(rolesList);
		
		//setting roles for collaborator
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		ICollaboratorRole collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
		collaboratorRole.setRoleDBid("cc_role1");
		collaboratorRoles.add(collaboratorRole);
		
		collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
		collaboratorRole.setRoleDBid("cc_role2");
		collaboratorRoles.add(collaboratorRole);
		
		//setting collaborator object
	    collaborator = collaboratorFactory.createCollaborator();
		collaborator.setCollaboratorRoles(collaboratorRoles);
		collaborator.setUserObj(user);
		
		//setting roles for collaborator
		List<ICollaboratorRole> collaboratorRoles1 = new ArrayList<ICollaboratorRole>();
		ICollaboratorRole collaboratorRole1 = collaboratorRoleFactory.createCollaboratorRoleObject();
		collaboratorRole1.setRoleDBid("cc_role2");
		collaboratorRoles1.add(collaboratorRole);
		
		//setting collaborator object
		collaborator1 = collaboratorFactory.createCollaborator();
		collaborator1.setCollaboratorRoles(collaboratorRoles1);
		collaborator1.setUserObj(user1);
		
		//adding both collaborators in list
		collaboratorList = new ArrayList<ICollaborator>(); 
		collaboratorList.add(collaborator);
		collaboratorList.add(collaborator1);
		
		//Setup the database with the proper data in the tables;
		String[] databaseQuery = new String[4];
		databaseQuery[0] = "INSERT INTO tbl_quadriga_user VALUES('test project user','projuser',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[1] = "INSERT INTO tbl_quadriga_user VALUES('test project user 1','projuser1',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[2] = "INSERT INTO tbl_quadriga_user VALUES('test project user 2','projuser2',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
		databaseQuery[3] = "INSERT INTO tbl_quadriga_user VALUES('test project user 3','projuser3',null,'tpu@test.com','role1,role4',SUBSTRING_INDEX(USER(),'@',1),NOW(),SUBSTRING_INDEX(USER(),'@',1),NOW())";
	
		for(String query : databaseQuery)
		{
//			((DBConnectionModifyProjCollabManager)dbConnection).setupTestEnvironment(query);
		}
		
		principal = new Principal() {
			@Override
			public String getName() {
			return "projuser";
			}					
		};	
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
		String id=null;
		
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("select projectid from tbl_project where projectname='"+name+"'");
			ResultSet resultSet = stmt.getResultSet();
			if(resultSet!=null)
			{
				while(resultSet.next())
				{
					id = resultSet.getString(1);
				}
			}
		logger.info("id "+id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
		

	@After
	public void tearDown() throws Exception {
		String[] databaseQuery = new String[3];
		databaseQuery[0] = "DELETE FROM tbl_project_collaborator";
		databaseQuery[1] = "DELETE FROM tbl_project";
		databaseQuery[2] = "DELETE FROM tbl_quadriga_user WHERE username IN ('projuser','projuser1','projuser2','projuser3')";
		for(String query : databaseQuery)
		{
			dbConnection.setupTestEnvironment(query);
		} 
	}

	@Test
	public void testAddCollaboratorRequest() throws QuadrigaStorageException {
		IProject project = projectFactory.createProjectObject();

		project.setName("test project");
		project.setDescription("projectDescription");
		project.setUnixName("123");


		IUser owner = userFactory.createUserObject();
		owner.setUserName(principal.getName());
		project.setOwner(owner);

		project.setProjectAccess(EProjectAccessibility.ACCESSIBLE);
		project.setCollaborators(collaboratorList);

		dbProjectConnection.addProjectRequest(project,project.getOwner().getUserName());

		dbConnection.addCollaboratorRequest(collaborator, getProjectId(project.getName()), principal.getName());
		List<ICollaborator> collaborators = dbRetrieveProjectConn.getProjectCollaborators(getProjectId(project.getName()));

		assertEquals(1,collaborators.size());
		
	
	}
	
	@Test
	public void testDeleteCollaboratorRequest() throws QuadrigaStorageException{
		
		IProject project = projectFactory.createProjectObject();

		project.setName("test project");
		project.setDescription("projectDescription");
		project.setUnixName("123");


		IUser owner = userFactory.createUserObject();
		owner.setUserName(principal.getName());
		project.setOwner(owner);

		project.setProjectAccess(EProjectAccessibility.ACCESSIBLE);
		project.setCollaborators(collaboratorList);

		dbProjectConnection.addProjectRequest(project,project.getOwner().getUserName());

		dbConnection.addCollaboratorRequest(collaborator, getProjectId(project.getName()), principal.getName());
		List<ICollaborator> collaborators = dbRetrieveProjectConn.getProjectCollaborators(getProjectId(project.getName()));
		assertEquals(1,collaborators.size());
		dbConnection.deleteColloratorRequest(collaborator.getUserObj().getUserName(), getProjectId(project.getName()));
		List<ICollaborator> collaborators1 = dbRetrieveProjectConn.getProjectCollaborators(getProjectId(project.getName()));
		assertEquals(0,collaborators1.size());

	}
	
	

}
