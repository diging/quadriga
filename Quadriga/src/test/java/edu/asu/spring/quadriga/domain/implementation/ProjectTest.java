package edu.asu.spring.quadriga.domain.implementation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;

public class ProjectTest {
	
	private Project project;
	private List<ICollaborator> collaborators;
	private IUser owner;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		this.project = new Project();
		
		this.collaborators = new ArrayList<ICollaborator>();
		collaborators.add(new Collaborator());
		collaborators.add(new Collaborator());
		
		this.owner = new User();
	}

	@After
	public void tearDown() throws Exception {
	}
	

	@Test
	public void testGetName() {
		
		project.setName(null);
		assertEquals(project.getName(), null);
		
		project.setName("jerry");
		assertEquals(project.getName(), "jerry");
	}

	
	@Test
	public void testGetDescription() {
		
		project.setDescription(null);
		assertEquals(project.getDescription(), null);
		
		project.setDescription("newUser");
		assertEquals(project.getDescription(), "newUser");
	}

	
	@Test
	public void testGetId() {
		
		project.setId(null);
		assertEquals(project.getId(), null);
		
		project.setId("1234");
		assertEquals(project.getId(), "1234");
	}

	

	@Test
	public void testGetInternalid() {
		
		project.setInternalid(null);
		assertEquals(project.getInternalid(), null);
		
		project.setInternalid("34@56");
		assertEquals(project.getInternalid(), "34@56");
	}

	
	@Test
	public void testGetOwner() {
		
		project.setOwner(null);
		assertEquals(project.getOwner(),null);
		
		project.setOwner(owner);
		assertEquals(project.getOwner(),owner);
		
	}

	
	@Test
	public void testGetCollaborators() {
		
		project.setCollaborators(null);
		assertEquals(project.getCollaborators(),null);
		
		project.setCollaborators(collaborators);
		assertEquals(project.getCollaborators(), collaborators);
		
	}

	

}
