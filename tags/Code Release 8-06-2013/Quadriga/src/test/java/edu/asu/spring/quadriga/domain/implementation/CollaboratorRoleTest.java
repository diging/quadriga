package edu.asu.spring.quadriga.domain.implementation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * @Description : tests getters and setters for roleId, roleName and roleDescription
 *  
 * @author		: Rohit Pendbhaje
 * 
 * 
 */
public class CollaboratorRoleTest {
	
	private CollaboratorRole CollaboratorRole;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		this.CollaboratorRole = new CollaboratorRole();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetRoleid() {
		
		CollaboratorRole.setRoleid(null);
		assertEquals(CollaboratorRole.getRoleid(), null);
		
		CollaboratorRole.setRoleid("3333");
		assertEquals(CollaboratorRole.getRoleid(), "3333");
	}

	
	@Test
	public void testGetRolename() {
		
		CollaboratorRole.setRolename(null);
		assertEquals(CollaboratorRole.getRolename(), null);
		
		CollaboratorRole.setRolename("owner");
		assertEquals(CollaboratorRole.getRolename(), "owner");
	}

	
	@Test
	public void testGetRoledescription() {
		
		CollaboratorRole.setRoledescription(null);
		assertEquals(CollaboratorRole.getRoledescription(), null);
		
		CollaboratorRole.setRoledescription("create project");
		assertEquals(CollaboratorRole.getRoledescription(), "create project");
	
	}

	}
