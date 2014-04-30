package edu.asu.spring.quadriga.web;

import static org.junit.Assert.assertEquals;

import java.security.Principal;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


public class HomeControllerTest {

	private HomeController homeController;
	private Model model;
	private Principal principal;
	
	/**
	 * This method sets up the needed objects for testing the {@link LoginController}
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		homeController = new HomeController();

		model =  new BindingAwareModelMap();		
		principal = new Principal() {			
			@Override
			public String getName() {
				return "jdoe";
			}
		};		
	}
	
	@Test
	public void homeTest() throws QuadrigaStorageException {
		
		assertEquals("auth/home", homeController.home(new Locale("us"), model, principal));
		assertEquals(principal.getName(), ((ModelMap)model).get("username"));
		
	}
}
