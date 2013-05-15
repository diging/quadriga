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

public class DictionaryTest {
	
	private Dictionary dictionary;
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
		
		this.dictionary = new Dictionary();
		this.owner = new User();
		
		this.collaborators = new ArrayList<ICollaborator>();
		collaborators.add(new Collaborator());
		collaborators.add(new Collaborator());
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetName() {
		
		dictionary.setName(null);
		assertEquals(dictionary.getName(), null);
		
		dictionary.setName("jane");
		assertEquals(dictionary.getName(),"jane");
		
	}

	
	@Test
	public void testGetDescription() {
		
		dictionary.setDescription(null);
		assertEquals(dictionary.getDescription(), null);
		
		dictionary.setDescription("jane");
		assertEquals(dictionary.getDescription(),"jane");
		
	}

	
	@Test
	public void testGetId() {
		
		dictionary.setId(null);
		assertEquals(dictionary.getId(), null);
		
		dictionary.setId("2058");
		assertEquals(dictionary.getId(), "2058");
		
	}

	
	@Test
	public void testGetOwner() {
		
		dictionary.setOwner(null);
		assertEquals(dictionary.getOwner(), null);
		
		dictionary.setOwner(owner);
		assertEquals(dictionary.getOwner(), owner);
		
		
	}

	
	@Test
	public void testGetCollaborators() {
		
		dictionary.setCollaborators(null);
		assertEquals(dictionary.getCollaborators(), null);
		
		dictionary.setCollaborators(collaborators);
		assertEquals(dictionary.getCollaborators(), collaborators);
		
	}

	
}
