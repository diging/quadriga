package edu.asu.spring.quadriga.web.login;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.develop.MockupUserManager;

@ContextConfiguration(locations = {
		"file:src/test/resources/quadriga-roles.xml",
		"file:src/test/resources/root-context.xml",
		"file:src/test/resources/spring-security.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class QuadrigaUserRoleMapperTest {

	@Autowired
	private IUserManager userManager;
	private DirContextOperations ctx;

	private AuthenticationProvider provider;
	
	@Autowired
	private QuadrigaUserRoleMapper mapper;

	@Before
	public void setUp() throws Exception {
		provider = new TestingAuthenticationProvider();
		ctx = new DirContextAdapter();
		mapper.setUserManager(userManager);
	}

	@Test
	public void testMapUserFromContext() {
//		List<GrantedAuthority> grantedAuthList = new ArrayList<GrantedAuthority>();
//		grantedAuthList.add(new QuadrigaGrantedAuthority(
//				RoleNames.ROLE_QUADRIGA_NOACCOUNT));
//		mapper.mapUserFromContext(ctx, "jdoe", grantedAuthList);
		fail("not yet implemented");
		
	}

}
