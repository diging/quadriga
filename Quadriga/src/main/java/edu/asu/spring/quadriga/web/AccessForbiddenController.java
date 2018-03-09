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

/**
 * This controller returns the appropriate page for a user who doesn't have the required
 * access rights to a page.
 * 
 * @author Julia Damerow
 *
 */
@Controller
public class AccessForbiddenController {

	/**
	 * This method answers requests to "forbidden". Inactive users and users without
	 * a account get redirected to pages corresponding to their role.
	 * 
	 * @param model This object holds the attributes of the request.
	 * @param principal This object holds the information about the user attempting to access a page.
	 * @return path to "forbidden" webpage.
	 */
	@RequestMapping(value="forbidden", method = RequestMethod.GET)
	public String getInactiveUserPage(ModelMap model, Principal principal) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		for (GrantedAuthority ga : authorities) {
			if (ga.getAuthority().equals(RoleNames.ROLE_QUADRIGA_DEACTIVATED)) {
				String sUserId = principal.getName();
				model.addAttribute("username", sUserId);
				return "inactiveuser";
			}
			if (ga.getAuthority().equals(RoleNames.ROLE_QUADRIGA_NOACCOUNT)) {
				String sUserId = principal.getName();
				model.addAttribute("username", sUserId);
				return "nouser";
			}
		}

		//An authenticated user can try to access other resources. So send them back to home page.
		String sUserId = principal.getName();
		model.addAttribute("username", sUserId);
		return "forbidden";
	}
}
