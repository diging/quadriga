package edu.asu.spring.quadriga.web;

import static org.junit.Assert.assertEquals;

import java.security.Principal;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.web.LoginController;

/**
 * This class test the {@link LoginController}
 * 
 * @author Ram Kumar Kumaresan
 *
 */
public class LoginControllerTest {

	LoginController loginController;
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

	/**
	 * This method sets up the needed objects for testing the {@link LoginController}
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		loginController = new LoginController();

		model =  new BindingAwareModelMap();		
		principal = new Principal() {			
			@Override
			public String getName() {
				return "jdoe";
			}
		};		
		authentication = new UsernamePasswordAuthenticationToken(principal, credentials);

	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This method checks if the {@link LoginController} returns the appropriate paths for the valid user.
	 * 
	 */
	@Test
	public void testValidUserHandle() throws SQLException {

		//Valid and Active user
		assertEquals(loginController.validUserHandle(model, principal, authentication),"redirect:home");

	}

	/**
	 * This method checks if the {@link LoginController} returns the login path for a failed login.
	 */
	@Test
	public void testLoginerror() {
		assertEquals(loginController.loginError(model),"login");
	}

}
