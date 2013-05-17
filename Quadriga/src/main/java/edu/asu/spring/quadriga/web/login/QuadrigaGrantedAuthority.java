package edu.asu.spring.quadriga.web.login;

import org.springframework.security.core.GrantedAuthority;

public class QuadrigaGrantedAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5486412986430678772L;
	
	private String authority;
	
	public QuadrigaGrantedAuthority() {
		
	}

	public QuadrigaGrantedAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
