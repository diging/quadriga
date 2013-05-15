package edu.asu.spring.quadriga;

import java.security.Principal;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.implementation.User;
import edu.asu.spring.quadriga.domain.implementation.UserManager;
 
@Controller
public class LoginController {
 
	UserManager userManager;
	User user;
	
	public LoginController() {
		userManager = new UserManager();
		user = new User();
	}
	
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String validUserHandle(ModelMap model, Principal principal,Authentication authentication) {
 
		//Get the LDAP-authenticated userid
		String sUserId = principal.getName();
		String sUserStatus = null;
		
		//Check the status of the user in the Quad DB
		user = userManager.getUserDetails(sUserId);
		
		//No such user present in Quad DB
		if(user == null)
		{
			return "nouser";
		}
		//User is present in Quad DB
		else
		{
			model.addAttribute("username", sUserId);
			if(user.isActive())
			{
				model.addAttribute("role","Active");
				sUserStatus = "hello";
			}
			else
			{
				model.addAttribute("role","InActive");
				sUserStatus = "inactiveuser";
			}
		}		
		return sUserStatus;
 
	}
 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {

		return "login";
 
	}
 
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
 
		model.addAttribute("error", "true");
		return "login";
 
	}
 
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
 
		return "login";
 
	}
 
}