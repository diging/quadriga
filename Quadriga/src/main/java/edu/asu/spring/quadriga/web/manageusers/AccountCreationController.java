package edu.asu.spring.quadriga.web.manageusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.manageusers.beans.AccountRequest;

@Controller
public class AccountCreationController {

	@Autowired
	private IUserManager userManager;
	
	@RequestMapping(value = "createAccount", method = RequestMethod.GET)
	public String prepareAccountRequestPage(Model model) {
		model.addAttribute("accountRequest", new AccountRequest());
		
		return "requestAccountPage";
	}
}
