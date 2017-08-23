package edu.asu.spring.quadriga.domain.implementation;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.impl.Concept;
public class ConceptTest {

	IConcept concept;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp()
	{
		concept = new Concept(); 
		concept.setConceptId("test1");
		concept.setLemma("hellotest");
		concept.setPos("noun");
		concept.setDescription("desc");
	}
	

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testEqualsObject() {
		
		
		IConcept concept2 = new Concept(); 
		concept2.setConceptId("test1");
		assertEquals(true, concept.equals(concept2));
		
	}

	@Test
	public void testGetLemma() {
			assertEquals("hellotest", concept.getLemma());
		}

	@Test
	public void testSetLemma() {
		concept.setLemma("test2");
		assertEquals("test2", concept.getLemma()); 
	}

	@Test
	public void testGetId() {
		assertEquals("test1", concept.getConceptId()); 
	}

	@Test
	public void testGetPos() {
		assertEquals("noun", concept.getPos()); 
	}

	@Test
	public void testGetDescription() {
		assertEquals("desc", concept.getDescription()); 
	}

	@Test
	public void testSetId() {
		concept.setConceptId("test2");
		assertEquals("test2", concept.getConceptId()); 
	}

	@Test
	public void testSetPos() {
		concept.setPos("verb");
		assertEquals("verb", concept.getPos());
	}

	@Test
	public void testSetDescription() {
		concept.setDescription("desc2");
		assertEquals("desc2", concept.getDescription()); 
	}

}
