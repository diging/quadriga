package edu.asu.spring.quadriga.web.manageusers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.UsernameExistsException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.manageusers.beans.AccountRequest;
import edu.asu.spring.quadriga.web.manageusers.beans.NewUserAccountValidator;

@Controller
public class RegisterUserController {

	@Autowired
	private IUserManager usermanager;

	@InitBinder("accountRequest")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new NewUserAccountValidator());
	}

	@RequestMapping(value = "register")
	public String initRegisterPage(ModelMap model) {
		model.addAttribute("request", new AccountRequest());
		return "register";
	}

	@RequestMapping(value = "register-user")
	public String registerUser(ModelMap model, @Valid @ModelAttribute AccountRequest request, BindingResult result)
			throws QuadrigaStorageException {
		if (result.hasErrors()) {
			model.addAttribute("request", request);
			model.addAttribute("errors", result);
			return "register";
		}

		boolean success = false;
		String username = request.getUsername();
		try {
			request.setUsername(username.toLowerCase());
			success = usermanager.addNewUser(request);
		} catch (UsernameExistsException e) {
			model.addAttribute("errormsg_username_in_use", "Username already in use.");
			request.setUsername(username);
			request.setPassword("");
			request.setRepeatedPassword("");
			model.addAttribute("request", request);
			return "register";
		} catch (QuadrigaStorageException e) {
			throw e;
		}

		if (!success) {
			model.addAttribute("errormsg_failure", "Sorry, user could not be added.");
			request.setPassword("");
			request.setRepeatedPassword("");
			model.addAttribute("request", request);
			return "register";
		}

		model.addAttribute("successmsg", "User account request created. An administrator will review your request.");
		return "redirect:/login";
	}
}
