package edu.asu.spring.quadriga.service.impl;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * 
 * @author Who wrote this?
 *
 */
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/spring-dbconnectionmanager.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/quadriga-roles.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserManagerTest 
{

	@Autowired
	IUserManager usermanager;
	
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
	public void testNoAccountUserRole() throws SQLException
	{
		IUser user = null;
		user = usermanager.getUserDetails("hello");
		assertEquals(user.getQuadrigaRoles().get(0).getId(),"ROLE_QUADRIGA_NOACCOUNT");
	}
	
	@Test
	public void testDeactivatedUserRole() throws SQLException
	{
		IUser user = null;
		user = usermanager.getUserDetails("jdoe");
		assertEquals(user.getQuadrigaRoles().get(0).getId(),"ROLE_QUADRIGA_DEACTIVATED");
	}
	
	@Test
	public void testAdminUserRole() throws SQLException
	{
		IUser user = null;
		user = usermanager.getUserDetails("test");
		assertFalse(user.getQuadrigaRoles().size()==1);
	}

}
