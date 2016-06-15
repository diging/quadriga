//package edu.asu.spring.quadriga.domain.implementation;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import edu.asu.spring.quadriga.domain.ICollaborator;
//import edu.asu.spring.quadriga.domain.IUser;
//import edu.asu.spring.quadriga.domain.impl.workbench.Project;
//import edu.asu.spring.quadriga.domain.workbench.IProject;
//
///*
//* @Description 	: tests getters and setters for project name, decription, id, internalid, collaborators
//* 				  and owner
//* 
//* @author		: Rohit Pendbhaje
//* 
//*/
//
//public class ProjectTest {
//	
//	private IProject project;
//	private List<ICollaborator> collaborators;
//	private IUser owner;
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
//	public void setUp() throws Exception {
//		
//		this.project = new Project();
//		
//		this.collaborators = new ArrayList<ICollaborator>();
//		collaborators.add(new Collaborator());
//		collaborators.add(new Collaborator());
//		
//		this.owner = new User();
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//	
//
//	@Test
//	public void testGetName() {
//		
//		project.setProjectName(null);
//		assertEquals(project.getProjectName(), null);
//		
//		project.setProjectName("jerry");
//		assertEquals(project.getProjectName(), "jerry");
//	}
//
//	
//	@Test
//	public void testGetDescription() {
//		
//		project.setDescription(null);
//		assertEquals(project.getDescription(), null);
//		
//		project.setDescription("newUser");
//		assertEquals(project.getDescription(), "newUser");
//	}
//
//	
//	@Test
//	public void testGetId() {
//		
//		project.setUnixName(null);
//		assertEquals(project.getUnixName(), null);
//		
//		project.setUnixName("1234");
//		assertEquals(project.getUnixName(), "1234");
//	}
//
//	
//
//	@Test
//	public void testGetInternalid() {
//		
//		//project.setInternalid(null);
//		//assertEquals(project.getInternalid(), null);
//		
//		project.setProjectId("44");
//		assertEquals(project.getProjectId(), "44");
//	}
//
//	
//	@Test
//	public void testGetOwner() {
//		
//		project.setOwner(null);
//		assertEquals(project.getOwner(),null);
//		
//		project.setOwner(owner);
//		assertEquals(project.getOwner(),owner);
//		
//	}
//
//	
//	@Test
//	public void testGetCollaborators() {
//		
//		project.setCollaborators(null);
//		assertEquals(project.getCollaborators(),null);
//		
//		project.setCollaborators(collaborators);
//		assertEquals(project.getCollaborators(), collaborators);
//		
//	}
//
//	
//
//}
