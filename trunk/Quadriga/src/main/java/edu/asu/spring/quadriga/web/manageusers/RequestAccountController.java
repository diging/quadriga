package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.service.IUserManager;

/**
 * This class handles account requests by logged in users that don't have an account yet.
 * 
 * @author Julia Damerow
 *
 */
@Controller
public class RequestAccountController {

	@Autowired
	private IUserManager userManager;

	/**
	 * This simply fills in the username for the request account page.
	 * @param model Model for jsp page.
	 * @param principal Information about logged in user.
	 * @return path to request account page.
	 */
	@RequestMapping(value = "requests/requestAccount", method = RequestMethod.GET)
	public String requestAccount(Model model, Principal principal) {
		String sUserId = principal.getName();		
		model.addAttribute("username", sUserId);
		
		return "requests/accountRequest";
	}
	
	/**
	 * This sends a request for a user account to the {@link IUserManager}.
	 * @param model Model for jsp page.
	 * @param principal Information about logged in user.
	 * @return result page 
	 */
	@RequestMapping(value = "requests/submitAccountRequest", method = RequestMethod.POST)
	public String submitAccountRequest(Model model, Principal principal) {
		String userId = principal.getName();
		
		if (userId == null || userId.trim().isEmpty())
			return "requests/error";
		
		userManager.addAccountRequest(userId);

		return "requests/accountRequested";
	}
}
