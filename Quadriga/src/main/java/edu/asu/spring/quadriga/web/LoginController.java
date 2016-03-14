package edu.asu.spring.quadriga.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.NoAuthorizationCheck;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * The controller to manage the login step performed by every user in Quadriga Database
 * 
 * @author Ram Kumar Kumaresan
 */
@Controller
public class LoginController {

	@Autowired
	IUserManager userManager;
	IUser user;

	/**
	 * A valid authenticated user is redirected to the home page.
	 * 
	 * @return 		Returned to the home page of Quadriga.
	 */
	@NoAuthorizationCheck
	@RequestMapping(value = "auth/welcome", method = RequestMethod.GET)
	public String validUserHandle(ModelMap model, Principal principal,
			Authentication authentication) {

		// Get the LDAP-authenticated userid
		String sUserId = principal.getName();		
		model.addAttribute("username", sUserId);
		
		return "auth/home";

	}

	/**
	 * User requests a login page
	 * 
	 * @return		Redirected to the login page
	 */
	@NoAuthorizationCheck
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {

		return "login";

	}

	/**
	 * Authentication failed. User credentials mismatch causes this request.
	 * 
	 * @return		Redirected to login page
	 */
	@NoAuthorizationCheck
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "true");
		return "login";

	}

}