package edu.asu.spring.quadriga.db.sql;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.IDBConnectionProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;

/**
 * This class tests the {@link DBConnectionProjectManager}. 
 * 
 * IMPORTANT: This test class will overwrite the data in 
 * 			  tbl_project
 * 
 * @author      Kiran Kumar Batna
 * @author 		rohit sukleshwar pendbhaje
 * 
 *
 */
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionProjectManagerTest {

	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	IDBConnectionProjectManager dbConnection;
	
	@Autowired
	private IProjectFactory projectFactory;
	
	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetProjectOfUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testSplitAndCreateCollaboratorRoles() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProjectDetails() {
		fail("Not yet implemented");
	}

	@Test
	public void testShowCollaboratorsRequest() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddCollaboratorRequest() {
		fail("Not yet implemented");
	}

	/**
	 * @description  : this method test the adding of project.
	 *                 First it inserts a project into the database and later 
	 *                 retrieves the same to compare.
	 *                 
	 * @author       : Kiran Kumar Batna                
	 */
	@Test
	public void testAddProjectRequest() throws Exception{
		String errmsg;
		IUser user;
		user = userFactory.createUserObject();
		IProject testProject = projectFactory.createProjectObject();
		
		//create a user
		user.setUserName("testuser");
		user.setName("testing purpose");
		
		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role3");
		roles.add(role);
		role = quadrigaRoleFactory.createQuadrigaRoleObject();
		role.setDBid("role4");
		roles.add(role);

		user.setQuadrigaRoles(roles);
		
		
		testProject.setName("testProject");
		testProject.setId("projecttest");
		testProject.setDescription("Testing add project method");
		testProject.setOwner(user);
		
		//call the add project method
		errmsg = dbConnection.addProjectRequest(testProject);
		
		assertEquals(errmsg,"");
		 
		
	}
}

