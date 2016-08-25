package edu.asu.spring.quadriga.domain.implementation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.impl.QuadrigaRole;

/*
* @Description 	: tests getters and setters for quadriga roleId, name, and description
* 
* @author		: Rohit Pendbhaje
* 
*/

public class QuadrigaRoleTest {
	
	private IQuadrigaRole quadrigaRole; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		this.quadrigaRole = new QuadrigaRole();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetId() {
		
		quadrigaRole.setId(null);
		assertEquals(quadrigaRole.getId(), null);
		
		quadrigaRole.setId("8888");
		assertEquals(quadrigaRole.getId(), "8888");
	}

	
	@Test
	public void testGetName() {
		
		quadrigaRole.setName(null);
		assertEquals(quadrigaRole.getName(), null);
		
		quadrigaRole.setName("john");
		assertEquals(quadrigaRole.getName(), "john");
		
	}

	@Test
	public void testGetDescription() {
		
		quadrigaRole.setDescription(null);
		assertEquals(quadrigaRole.getDescription(), null);
		
		quadrigaRole.setDescription("admin");
		assertEquals(quadrigaRole.getDescription(), "admin");
		
	}

}
