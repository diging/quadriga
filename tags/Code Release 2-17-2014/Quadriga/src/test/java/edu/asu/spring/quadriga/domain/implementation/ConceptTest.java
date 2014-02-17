package edu.asu.spring.quadriga.domain.implementation;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ConceptTest {

	@Autowired
	IConceptFactory factory;
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
		concept = factory.createConceptObject(); 
		concept.setId("test1");
		concept.setLemma("hellotest");
		concept.setPos("noun");
		concept.setDescription("desc");
	}
	

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testEqualsObject() {
		
		
		IConcept concept2 = factory.createConceptObject(); 
		concept2.setId("test1");
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
		assertEquals("test1", concept.getId()); 
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
		concept.setId("test2");
		assertEquals("test2", concept.getId()); 
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
