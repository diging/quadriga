package edu.asu.spring.quadriga.web;

import static org.junit.Assert.fail;

import java.security.Principal;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ConceptcollectionControllerTest {
	@Autowired
	ConceptcollectionController collectionContoller;

	
	Principal principal;	
	UsernamePasswordAuthenticationToken authentication;
	CredentialsContainer credentials;
	BindingAwareModelMap model;

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
	public void testInitBinder() {
		
		fail("Not yet implemented"); // TODO
		
		
	}

	@Test
	public void testConceptCollectionHandler() throws  QuadrigaStorageException {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testConceptDetailsHandler() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testConceptSearchHandler() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSaveItemsHandler() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddCollectionsForm() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddConceptCollection() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testDeleteItems() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testConceptUpdateHandler() {
		fail("Not yet implemented"); // TODO
	}

}
