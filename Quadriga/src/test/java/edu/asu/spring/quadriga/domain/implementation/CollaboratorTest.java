package edu.asu.spring.quadriga.domain.implementation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.Collaborator;
import edu.asu.spring.quadriga.domain.impl.QuadrigaRole;
import edu.asu.spring.quadriga.domain.impl.User;

/*
* @Description 	: tests getters and setters for collaboratorRoles, userObj
* 
* @author		: Rohit Pendbhaje
* 
*/

public class CollaboratorTest {
	
	private Collaborator collaborator ;
	private List<IQuadrigaRole> collaboratorRoles;
	private IUser userObj;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		
		this.collaborator = new Collaborator();
		this.collaboratorRoles = new ArrayList<IQuadrigaRole>();
					
		collaboratorRoles.add(new QuadrigaRole());
		collaboratorRoles.add(new QuadrigaRole());
	
		this.userObj = new User();
			
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDescription() {
		
		collaborator.setDescription(null);
		assertEquals(collaborator.getDescription(), null);
		
		collaborator.setDescription("editor");
		assertEquals(collaborator.getDescription(), "editor");
	}

	
	@Test
	public void testGetCollaboratorRoles() {
		
		collaborator.setCollaboratorRoles(null);
		assertEquals(collaborator.getCollaboratorRoles(),null); 
		
		
		collaborator.setCollaboratorRoles(collaboratorRoles);
		assertEquals(collaborator.getCollaboratorRoles(),collaboratorRoles);
		
	}

		
	@Test
	public void testGetUserObj() {
		
		collaborator.setUserObj(null);
		assertEquals(collaborator.getUserObj(),null);
		
		collaborator.setUserObj(userObj);
		assertEquals(collaborator.getUserObj(),userObj);
	}
	
	
}
