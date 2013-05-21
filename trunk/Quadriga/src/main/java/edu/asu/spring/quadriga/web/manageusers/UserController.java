package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class UserController {

	@RequestMapping(value = "auth/users/manage", method = RequestMethod.GET)
	public String manageUsers(ModelMap model, Principal principal,
			Authentication authentication)
	{
		// Get the LDAP-authenticated userid
		String sUserId = principal.getName();

		model.addAttribute("username", sUserId);
		return "auth/users/manage";
	}
	
	@RequestMapping(value = "auth/users/requests", method = RequestMethod.GET)
	public String login(ModelMap model, Principal principal,
			Authentication authentication)
	{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>...");
		return "auth/users/requests";
	}
}