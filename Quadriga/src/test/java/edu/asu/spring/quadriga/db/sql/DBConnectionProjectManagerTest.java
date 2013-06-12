package edu.asu.spring.quadriga.db.sql;

import static org.junit.Assert.*;

import java.sql.SQLException;

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
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;

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
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/spring-dbconnectionmanager.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
"file:src/main/webapp/WEB-INF/spring/quadriga-roles.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionProjectManagerTest {

	@Autowired
	IDBConnectionProjectManager dbConnection;
	
	@Autowired
	private IProjectFactory projectFactory;
	
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
	public void testAddProjectRequest() {
		
		IProject testProject = projectFactory.createProjectObject();
		IProject project = projectFactory.createProjectObject();
		
		testProject.setName("testProject");
		testProject.setId("projecttest");
		testProject.setDescription("Testing add project method");
		testProject.setInternalid(99);
		
		//call the add project method
		dbConnection.addProjectRequest(testProject);
		
		try
			{
			//retrieve the added project and compare the objects
			project = dbConnection.getProjectDetails(testProject.getInternalid());
			
			assertEquals(testProject,project);
			}
			catch(SQLException e)
			{
				fail("SQL Exception");
			}
		
	}
}

