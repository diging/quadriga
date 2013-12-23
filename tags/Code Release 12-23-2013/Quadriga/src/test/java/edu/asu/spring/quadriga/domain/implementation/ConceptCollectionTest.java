package edu.asu.spring.quadriga.domain.implementation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.factories.impl.ConceptFactory;

/*
* @Description 	: tests getters and setters for concept description, concept id , concept owner and
* 				  concept collaborators 
* 
* @author		: Rohit Pendbhaje
* 
*/

public class ConceptCollectionTest {
	
	private IConceptCollection conceptcollection;
	private IUser owner;
	private List<ICollaborator> collaborators;
	private List<IConcept> items = new ArrayList<IConcept>();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		this.conceptcollection = new ConceptCollection();
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
		
		conceptcollection.setName(null);
		assertEquals(conceptcollection.getName(), null);
		
		conceptcollection.setName("jane");
		assertEquals(conceptcollection.getName(),"jane");
				
	}

	@Test
	public void testGetDescription() {
		
		conceptcollection.setDescription(null);
		assertEquals(conceptcollection.getDescription(), null);
		
		conceptcollection.setDescription("xyz");
		assertEquals(conceptcollection.getDescription(), "xyz");
		
	}

	@Test
	public void testGetId() {
		
		conceptcollection.setName(null);
		assertEquals(conceptcollection.getName(), null);
		
		conceptcollection.setName("3333");
		assertEquals(conceptcollection.getName(), "3333");
		
	}

	
	@Test
	public void testGetOwner() {
		
		conceptcollection.setOwner(null);
		assertEquals(conceptcollection.getOwner(), null);
		
		conceptcollection.setOwner(owner);
		assertEquals(conceptcollection.getOwner(), owner);
				
	}

	@Test
	public void testGetCollaborators() {
		
		conceptcollection.setCollaborators(null);
		assertEquals(conceptcollection.getCollaborators(), null);
		
		conceptcollection.setCollaborators(collaborators);
		assertEquals(conceptcollection.getCollaborators(), collaborators);
		
	}
	@Test
	public void testGetItems() {
		
		IConcept concept=(new ConceptFactory().createConceptObject());
		conceptcollection.addItem(concept);
		concept.setId("hellotest");
		assertEquals(conceptcollection.getItems().get(0).equals(concept),true );
		conceptcollection.getItems().remove(0);
		assertEquals(conceptcollection.getItems().size(),0 );
				
	}

}
