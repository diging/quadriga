package edu.asu.spring.quadriga.db.sql;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IUser;

@ContextConfiguration(locations={"file:///E:/LSA/Quadriga/Quadriga/src/main/webapp/WEB-INF/spring/spring-dbconnectionmanager.xml",
		"file:///E:/LSA/Quadriga/Quadriga/src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:///E:/LSA/Quadriga/Quadriga/src/main/webapp/WEB-INF/spring/quadriga-roles.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DBConnectionManagerTest {

	@Autowired
	IDBConnectionManager dbConnection;
	
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
	public void testNoUserAccount()
	{
		IUser user = null;
		user = dbConnection.getUserDetails("hello");
		assertEquals(user,null);
	}
	
	@Test
	public void testUserAcount()
	{
		IUser user = null;
		user = dbConnection.getUserDetails("jdoe");
		assertEquals(user.getUserName(),"jdoe");
	}
}
