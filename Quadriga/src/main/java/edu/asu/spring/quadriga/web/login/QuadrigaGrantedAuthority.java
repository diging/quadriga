package edu.asu.spring.quadriga.web.login;

import org.springframework.security.core.GrantedAuthority;

/**
 * This class represents roles available in Quadriga.
 * 
 * @author jdamerow
 *
 */
public class QuadrigaGrantedAuthority implements GrantedAuthority {
	
	private static final long serialVersionUID = -5486412986430678772L;
	
	private String authority;
	
	public QuadrigaGrantedAuthority() {
		
	}

	public QuadrigaGrantedAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * Returns the "role name" e.g. ROLE_QUADRIGA_ADMIN.
	 * @return name of the represented role.
	 */
	@Override
	public String getAuthority() {
		return authority;
	}

	/**
	 * Sets the role name.
	 * @param authority
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
