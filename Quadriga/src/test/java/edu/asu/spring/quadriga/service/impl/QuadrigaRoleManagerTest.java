package edu.asu.spring.quadriga.service.impl;

import static org.junit.Assert.*;

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
import edu.asu.spring.quadriga.domain.implementation.QuadrigaRole;

@ContextConfiguration(locations={"file:///C:/Users/Ram/Documents/EclipseProjects/Quadriga/src/main/webapp/WEB-INF/spring/quadriga-roles.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class QuadrigaRoleManagerTest {

	@Autowired
	private List<IQuadrigaRole> quadrigaRoles;

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
	public void testGetQuadrigaRole() {
		
		//Check if values are loaded
		assertNotNull(quadrigaRoles);
		
	}

	@Test
	public void testSetQuadrigaRoles() {
		QuadrigaRoleManager qrManager = new QuadrigaRoleManager();
		
		//Assign null
		qrManager.setQuadrigaRoles(null);
		assertEquals(qrManager.getQuadrigaRoles(),null);
		
		//Assign a list of roles
		qrManager.setQuadrigaRoles(quadrigaRoles);
		assertEquals(qrManager.getQuadrigaRoles(),quadrigaRoles);		
	}
}
