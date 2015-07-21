package edu.asu.spring.quadriga.web;

import static org.junit.Assert.assertEquals;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import edu.asu.spring.quadriga.web.login.QuadrigaGrantedAuthority;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class tests the {@link AccessForbiddenController}. 
 * 
 * @author Julia Damerow
 *
 */
public class AccessForbiddenControllerTest {

    private Principal principal;
	private BindingAwareModelMap model;
	private Authentication authenticationNoAccount;
	private Authentication authenticationDeactivated;
	private Authentication authenticationFailed;
	
	private AccessForbiddenController accessForbiddenController;

	/**
	 * This method sets up the test by creating 3 {@link Authentication} objects. One for a user that does not have an account,
	 * one for a user with a deactivated account, and one with neither of these roles.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		model = new BindingAwareModelMap();
		principal = new Principal() {
			@Override
			public String getName() {
				return "jdoe";
			}
		};

		// create user with role no account
		{
			List<GrantedAuthority> grantedAuthList = new ArrayList<GrantedAuthority>();
			grantedAuthList.add(new QuadrigaGrantedAuthority(
					RoleNames.ROLE_QUADRIGA_NOACCOUNT));
			UserDetails userDetails = new User(principal.getName(), "john",
					grantedAuthList);
			authenticationNoAccount = new TestingAuthenticationToken(
					userDetails, "password", grantedAuthList);
		}

		// create user with role deactivated
		{
			List<GrantedAuthority> grantedAuthList = new ArrayList<GrantedAuthority>();
			grantedAuthList.add(new QuadrigaGrantedAuthority(
					RoleNames.ROLE_QUADRIGA_DEACTIVATED));
			UserDetails userDetails = new User(principal.getName(), "john",
					grantedAuthList);
			authenticationDeactivated = new TestingAuthenticationToken(
					userDetails, "password", grantedAuthList);
		}

		// create user with account
		{
			List<GrantedAuthority> grantedAuthList = new ArrayList<GrantedAuthority>();
			grantedAuthList.add(new QuadrigaGrantedAuthority(
					"ROLE_QUADRIGA_STANDARD_USER"));
			UserDetails userDetails = new User(principal.getName(), "john",
					grantedAuthList);
			authenticationFailed = new TestingAuthenticationToken(
					userDetails, "password", grantedAuthList);
		}

		accessForbiddenController = new AccessForbiddenController();
	}

	/**
	 * This method tests if the {@link AccessForbiddenController} returns the
	 * appropriate paths for users with a deactivated or no account.
	 */
	@Test
	public void testGetInactiveUserPage() {

		SecurityContextHolder.getContext().setAuthentication(authenticationNoAccount);
		assertEquals(accessForbiddenController.getInactiveUserPage(model, principal), "nouser");
		SecurityContextHolder.clearContext();
		
		SecurityContextHolder.getContext().setAuthentication(authenticationDeactivated);
		assertEquals(accessForbiddenController.getInactiveUserPage(model, principal), "inactiveuser");
		SecurityContextHolder.clearContext();
		
		SecurityContextHolder.getContext().setAuthentication(authenticationFailed);
		assertEquals(accessForbiddenController.getInactiveUserPage(model, principal), "forbidden");
		SecurityContextHolder.clearContext();
	}
}
