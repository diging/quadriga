package edu.asu.spring.quadriga.web.manageusers;

import static org.junit.Assert.assertEquals;

import java.security.Principal;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.LoginController;

/**
 * Test for {@link RequestAccountController}.
 * @author Julia Damerow
 *
 */
@ContextConfiguration(locations={"file:src/test/resources/spring-dbconnectionmanager.xml",
"file:src/test/resources/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RequestAccountControllerTest {
	
	private Principal principalUsername;	
	private Principal principleNoUsername;
	private BindingAwareModelMap model;
	private RequestAccountController requestAccountController;
	
	@Autowired
	private IUserManager userManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * This method sets up the needed objects for testing the {@link LoginController}
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		
		model =  new BindingAwareModelMap();		
		principalUsername = new Principal() {			
			@Override
			public String getName() {
				return "jdoe";
			}
		};
		
		principleNoUsername = new Principal() {			
			@Override
			public String getName() {
				return null;
			}
		};
		
		requestAccountController = new RequestAccountController();
		requestAccountController.setUserManager(userManager);

	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSubmitAccountRequest() throws QuadrigaStorageException {	
		
		String result = requestAccountController.submitAccountRequest(model, principleNoUsername);
		assertEquals(result, "requests/error");
		
		result = requestAccountController.submitAccountRequest(model, principalUsername);
		assertEquals(result, "requests/accountRequested");
		assertEquals(model.get("requestStatus"), 1);
		assertEquals(model.get("username"), "jdoe");
		
		result = requestAccountController.submitAccountRequest(model, principalUsername);
		assertEquals(result, "requests/accountRequested");
		assertEquals(model.get("requestStatus"), 0);
		assertEquals(model.get("username"), "jdoe");
	}
}
