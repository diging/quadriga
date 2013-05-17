package edu.asu.spring.quadriga.web.login;

import org.springframework.security.core.GrantedAuthority;

public class ActiveUserGrantedAuthority implements GrantedAuthority {
	
	public final static String ROLE = "ROLE_ACTIVE_USER";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getAuthority() {
		return ROLE;
	}

	@Override
	public String toString() {
		return getAuthority();
	}

	
}
