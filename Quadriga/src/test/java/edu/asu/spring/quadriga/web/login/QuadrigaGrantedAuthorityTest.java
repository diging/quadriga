package edu.asu.spring.quadriga.web.login;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests {@link QuadrigaGrantedAuthority}.
 * @author Julia Damerow
 *
 */
public class QuadrigaGrantedAuthorityTest {

	private QuadrigaGrantedAuthority authority;
	private String authorityRole;
	
	@Before
	public void setUp() throws Exception {
		authorityRole = "ROLE_QUADRIGA";
	}
	
	@Test
	public void testGetSetAuthority() {
		
		authority = new QuadrigaGrantedAuthority();
		authority.setAuthority(null);
		assertNull(authority.getAuthority());
			
		authority.setAuthority(authorityRole);
		assertEquals(authority.getAuthority(), authorityRole);
		
		authority = new QuadrigaGrantedAuthority(authorityRole);
		assertEquals(authority.getAuthority(), authorityRole);
	}

}
