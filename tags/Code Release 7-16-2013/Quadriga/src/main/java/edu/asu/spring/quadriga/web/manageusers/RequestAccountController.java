package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
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
	
	public IUserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * This sends a request for a user account to the {@link IUserManager}.
	 * @param model Model for jsp page.
	 * @param principal Information about logged in user.
	 * @return result page 
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value = "requests/submitAccountRequest", method = RequestMethod.POST)
	public String submitAccountRequest(Model model, Principal principal) throws QuadrigaStorageException {
		String userId = principal.getName();
		
		if (userId == null || userId.trim().isEmpty())
			return "requests/error";
		
		int iRequestStatus = userManager.addAccountRequest(userId);
		model.addAttribute("requestStatus", iRequestStatus);
		model.addAttribute("username", principal.getName());

		return "requests/accountRequested";
	}
}
