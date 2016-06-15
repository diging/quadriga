package edu.asu.spring.quadriga.web.manageusers;

import static org.junit.Assert.assertEquals;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.support.BindingAwareModelMap;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.LoginController;

/**
 * Test for {@link RequestAccountController}.
 * @author Julia Damerow
 *
 */
public class RequestAccountControllerTest {
	
	private Principal principalUsername;	
	private Principal principleNoUsername;
	private BindingAwareModelMap model;
	
	@Mock
    private IUserManager userManager;

	@InjectMocks
	private RequestAccountController requestAccountController;

	/**
	 * This method sets up the needed objects for testing the {@link LoginController}
	 * 
	 */
	@Before
	public void setUp() throws Exception {
	    userManager = Mockito.mock(IUserManager.class);
	    MockitoAnnotations.initMocks(this);
		
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
	}

	@Test
	public void testSubmitAccountRequest() throws QuadrigaStorageException {	
		
		String result = requestAccountController.submitAccountRequest(model, principleNoUsername);
		assertEquals(result, "requests/error");
		
		// first time the user doesn't exist yet
		Mockito.when(userManager.addAccountRequest(Mockito.anyString())).thenReturn(1);
		result = requestAccountController.submitAccountRequest(model, principalUsername);
		assertEquals(result, "requests/accountRequested");
		assertEquals(model.get("requestStatus"), 1);
		assertEquals(model.get("username"), "jdoe");
		
		// now it should exist, so Mockito has to return 0
		Mockito.when(userManager.addAccountRequest(Mockito.anyString())).thenReturn(0);
		result = requestAccountController.submitAccountRequest(model, principalUsername);
		assertEquals(result, "requests/accountRequested");
		assertEquals(model.get("requestStatus"), 0);
		assertEquals(model.get("username"), "jdoe");
	}
}
