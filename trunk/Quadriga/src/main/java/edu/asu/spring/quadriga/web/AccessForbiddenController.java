package edu.asu.spring.quadriga.web;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.web.login.ActiveUserGrantedAuthority;
import edu.asu.spring.quadriga.web.login.InactiveUserGrantedAuthority;
import edu.asu.spring.quadriga.web.login.NoAccountGrantedAuthority;

@Controller
public class AccessForbiddenController {

	@RequestMapping(value="forbidden", method = RequestMethod.GET)
	public String getInactiveUserPage() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		for (GrantedAuthority ga : authorities) {
			if (ga.getAuthority().equals(InactiveUserGrantedAuthority.ROLE)) {
				return "inactiveuser";
			}
			if (ga.getAuthority().equals(NoAccountGrantedAuthority.ROLE)) {
				return "nouser";
			}
		}
		
		return "inactiveuser";
	}
}
