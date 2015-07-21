package edu.asu.spring.quadriga.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;



import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.impl.QuadrigaRole;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;

/**
 * 
 * @author Ram Kumar Kumaresan
 *
 */
public class QuadrigaRoleManagerTest {

	
	private List<IQuadrigaRole> quadrigaRoles;

	
	@Before
	public void setUp() throws Exception {
	    quadrigaRoles = new ArrayList<IQuadrigaRole>();
	    quadrigaRoles.add(new QuadrigaRole());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetQuadrigaRole() {
		
		//Check if values are loaded
		assertNotNull(quadrigaRoles);
		
	}

	@Test
	public void testSetQuadrigaRoles() {
		IQuadrigaRoleManager qrManager = new QuadrigaRoleManager();
		
		//Assign null
		qrManager.setQuadrigaRoles(null);
		assertEquals(qrManager.getQuadrigaRoles(),null);
		
		//Assign a list of roles
		qrManager.setQuadrigaRoles(quadrigaRoles);
		assertEquals(qrManager.getQuadrigaRoles(),quadrigaRoles);		
	}
}
