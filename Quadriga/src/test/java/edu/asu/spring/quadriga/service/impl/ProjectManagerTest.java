package edu.asu.spring.quadriga.service.impl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.IDBConnectionProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.service.IProjectManager;

import static org.junit.Assert.*;

/**
 * This class tests the {@link ProjectManager}
 * 
 * IMPORTANT: This test class will overwrite the data in 
 * 			  tbl_project
 * 
 * @author Kiran Kumar Batna
 * @author rohit sukleshwar pendbhaje
 *
 */
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
		"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectManagerTest {

	@Autowired
	@Qualifier("DBConnectionProjectManagerBean")
	private IDBConnectionProjectManager dbConnection;
	
	@Autowired
	private IProjectFactory projectFactory;
	
	@Autowired
	private IProjectManager projectManager;
	
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
	public void testGetProjectsOfUsers() {
		fail("Not yet implemented.");
	}
	
	@Test
	public void testUpdateProjectDetails() {
		fail("Once again, not yet implemented.");
	}
	
	@Test
	public void testDeleteProject() {
		fail("Not yet implemented.");
	}
	
	@Test
	public void testAddNewProject() 
	{
		String errmsg;
		IProject testProject = projectFactory.createProjectObject();
		
		testProject.setName("testProject");
		testProject.setId("projecttest");
		testProject.setDescription("Testing add project method");
		
		errmsg = projectManager.addNewProject(testProject);
		
		assertEquals(errmsg,"");
		
		//insert duplicate project
		errmsg = projectManager.addNewProject(testProject);
		
		assertNotSame(errmsg,"");
		
	}
	
	@Test
	public void testGetProject() {
		fail("This is not yet implemented.");
	}
}
