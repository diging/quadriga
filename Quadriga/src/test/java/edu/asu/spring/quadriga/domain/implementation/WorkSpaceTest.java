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
//import edu.asu.spring.quadriga.domain.impl.workspace.WorkSpace;
//import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
//
///*
//* @Description 	: tests getters and setters for workspace name, id, collaborators, description 
//* 				  and owner
//* 
//* @author		: Rohit Pendbhaje
//* 
//*/
//
//public class WorkSpaceTest {
//	
//	private IWorkSpace workspace;
//	private IUser owner;
//	private List<ICollaborator> collaborators;
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
//		this.workspace = new WorkSpace();
//		this.owner = new User();
//		
//		this.collaborators = new ArrayList<ICollaborator>();
//		collaborators.add(new Collaborator());
//		collaborators.add(new Collaborator());
//		
//		
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testGetName() {
//		
//		workspace.setWorkspaceName(null);
//		assertEquals(workspace.getWorkspaceName(), null);
//		
//		workspace.setWorkspaceName("jade");
//		assertEquals(workspace.getWorkspaceName(),"jade");
//				
//		
//	}
//
//	
//	@Test
//	public void testGetDescription() {
//		
//		workspace.setDescription(null);
//		assertEquals(workspace.getDescription(), null);
//		
//		workspace.setDescription("abc");
//		assertEquals(workspace.getDescription(),"abc");
//		
//		
//	}
//
//	@Test
//	public void testGetId() {
//		
//		workspace.setWorkspaceId(null);
//		assertEquals(workspace.getWorkspaceId(), null);
//		
//		workspace.setWorkspaceId("593");
//		assertEquals(workspace.getWorkspaceId(), "593");
//		
//		
//	}
//
//	
//
//	@Test
//	public void testGetOwner() {
//		
//		workspace.setOwner(null);
//		assertEquals(workspace.getOwner(), null);
//		
//		workspace.setOwner(owner);
//		assertEquals(workspace.getOwner(), owner);
//	}
//
//	
//	@Test
//	public void testGetCollaborators() {
//		
//		workspace.setCollaborators(null);
//		assertEquals(workspace.getCollaborators(), null);
//		
//		workspace.setCollaborators(collaborators);
//		assertEquals(workspace.getCollaborators(), collaborators);
//		
//	}
//
//	
//
//}
