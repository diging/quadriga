package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class RequestAccountController {

	@Autowired
	private IUserManager userManager;

	
	@RequestMapping(value = "requests/requestAccount", method = RequestMethod.GET)
	public String requestAccount(Model model, Principal principal) {
		String sUserId = principal.getName();		
		model.addAttribute("username", sUserId);
		
		return "requests/accountRequest";
	}
	
	@RequestMapping(value = "requests/submitAccountRequest", method = RequestMethod.POST)
	public String submitAccountRequest(Model model, Principal principal) {
		String userId = principal.getName();
		
		if (userId == null || userId.trim().isEmpty())
			return "error";
		
		userManager.addAccountRequest(userId);

		return "requests/accountRequested";
	}
}
