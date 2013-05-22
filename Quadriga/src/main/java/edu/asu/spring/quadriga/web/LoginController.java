package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	@RequestMapping(value = "auth/welcome", method = RequestMethod.GET)
	public String validUserHandle(ModelMap model, Principal principal,
			Authentication authentication) {

		// Get the LDAP-authenticated userid
		String sUserId = principal.getName();		
		model.addAttribute("username", sUserId);
		
		return "auth/loggedIn";

	}

	/**
	 * User requests a login page
	 * 
	 * @return		Redirected to the login page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {

		return "login";

	}

	/**
	 * Authentication failed. User credentials mismatch causes this request.
	 * 
	 * @return		Redirected to login page
	 */
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "true");
		return "login";

	}

	/**
	 * A authenticated user is logged out of the system.
	 * 
	 * @return		Redirect to login page
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {

		return "login";

	}

}