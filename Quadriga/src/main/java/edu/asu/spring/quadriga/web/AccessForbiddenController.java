package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AccessForbiddenController {

	@RequestMapping(value="forbidden", method = RequestMethod.GET)
	public String getInactiveUserPage(ModelMap model, Principal principal) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		for (GrantedAuthority ga : authorities) {
			if (ga.getAuthority().equals(RoleNames.ROLE_QUADRIGA_DEACTIVATED)) {
				return "inactiveuser";
			}
			if (ga.getAuthority().equals(RoleNames.ROLE_QUADRIGA_NOACCOUNT)) {
				return "nouser";
			}
		}

		//An authenticated user can try to access other resources. So send them back to home page.
		String sUserId = principal.getName();
		model.addAttribute("username", sUserId);
		return "auth/home";
	}
}
