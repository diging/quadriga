package edu.asu.spring.quadriga.web;

import static org.junit.Assert.fail;

import java.security.Principal;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;

@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ConceptcollectionControllerTest {

	@Autowired
	IDictionaryManager dictonaryManager;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	IDBConnectionCCManager dbConnection;

	
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
	@Ignore
	public void testInitBinder() {
		
		fail("Not yet implemented"); // TODO
		
		
	}

	@Test
	@Ignore
	public void testConceptCollectionHandler() throws  QuadrigaStorageException {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public void testConceptDetailsHandler() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public void testConceptSearchHandler() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public void testSaveItemsHandler() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public void testAddCollectionsForm() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public void testAddConceptCollection() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public void testDeleteItems() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public void testConceptUpdateHandler() {
		fail("Not yet implemented"); // TODO
	}

}
