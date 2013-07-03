package edu.asu.spring.quadriga.web.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.service.IUserManager;

@ContextConfiguration(locations = {
		"file:src/test/resources/root-context.xml",
		"file:src/test/resources/spring-security.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class QuadrigaUserRoleMapperTest {

	@Autowired
	@Qualifier("MockupUserManager")
	private IUserManager userManager;
	
	@Autowired
	private IUserFactory userFactory;
	@Autowired
	private IQuadrigaRoleFactory roleFactory;
	
	private DirContextOperations ctx;
	
	private AuthenticationProvider provider;
	
	@Autowired
	private QuadrigaUserRoleMapper mapper;
	
	private IUser jdoe;

	@Before
	public void setUp() throws Exception {
		provider = new TestingAuthenticationProvider();
		
		// set up DirContextOperations
		ctx = new DirContextAdapter();
		ctx.addAttributeValue("cn", "jdoe");
		ctx.addAttributeValue("sn", "Doe");
		ctx.addAttributeValue("description", "");
		ctx.addAttributeValue("telefonNumber", "");
		ctx.addAttributeValue("userPassword", "");
		
		// create testusers
		jdoe = userFactory.createUserObject();
		jdoe.setUserName("jdoe");
		jdoe.setName("John Doe");
		
		mapper.setUserManager(userManager);
	}

	@Test
	public void testMapUserFromContext() {
		List<String> userRoles = new ArrayList<String>();
		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
		
		jdoe.setQuadrigaRoles(roles);
//		userManager.addNewUser(jdoe);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		UserDetails details = mapper.mapUserFromContext(ctx, "jdoe", authorities);
		
		Collection<? extends GrantedAuthority> grantedAuthorities = details.getAuthorities();
		assertThat(grantedAuthorities, new AuthorityListMatcher(userRoles));
		
		// test if two roles get translated correctly
		userRoles = new ArrayList<String>();
		userRoles.add("ROLE_QUADRIGA_USER_ADMIN");
		userRoles.add("ROLE_QUADRIGA_USER_STANDARD");
		
		roles = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole role = roleFactory.createQuadrigaRoleObject();
		role.setId(userRoles.get(0));
		roles.add(role);

		role = roleFactory.createQuadrigaRoleObject();
		role.setId(userRoles.get(1));
		roles.add(role);

		jdoe.setQuadrigaRoles(roles);
//		userManager.addNewUser(jdoe);
		
		authorities = new ArrayList<GrantedAuthority>();
		details = mapper.mapUserFromContext(ctx, "jdoe", authorities);
		
		grantedAuthorities = details.getAuthorities();
		assertThat(grantedAuthorities, new AuthorityListMatcher(userRoles));
		
	}
	
	
	class AuthorityListMatcher implements Matcher<Collection<? extends GrantedAuthority>> {
		
		private List<String> needsToBePresent;
		
		public AuthorityListMatcher(List<String> needsToBePresent) {
			this.needsToBePresent = needsToBePresent;
		}

		@Override
		public void describeTo(Description arg0) {
			arg0.appendText("presence of ");
			for (String s : needsToBePresent) {
				arg0.appendText(s + ", ");
			}
		}

		@Override
		public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean matches(Object arg0) {
			if (!(arg0 instanceof Collection<?>))
				return false;
			
			AuthLoop: for (String auth : needsToBePresent) {
				for (Object obj : (Collection<?>) arg0) {
					if (!(obj instanceof GrantedAuthority))
						continue;
							
					if (((GrantedAuthority)obj).getAuthority().equals(auth))
						continue AuthLoop;
				}
				return false;
			}
			
			return true;
		}
		
	}

}
