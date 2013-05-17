package edu.asu.spring.quadriga.web.login;

import org.springframework.security.core.GrantedAuthority;

public class InactiveUserGrantedAuthority implements GrantedAuthority {

	public final static String ROLE = "ROLE_INACTIVE_USER";

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
