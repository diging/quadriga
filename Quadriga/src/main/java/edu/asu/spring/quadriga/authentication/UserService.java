package edu.asu.spring.quadriga.authentication;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.QuadrigaUserDetails;
import edu.asu.spring.quadriga.web.LoginController;
import edu.asu.spring.quadriga.web.login.QuadrigaGrantedAuthority;

@Service("userService")
public class UserService implements UserDetailsService {

	@Autowired
	private IUserManager userManager;
	
	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {
		IUser user;
		try {
			user = userManager.getUser(arg0);
		} catch (QuadrigaStorageException e) {
		    logger.error("Error while retrieving user details", e);
			throw new UsernameNotFoundException("Error getting user details.", e);
		}
		
		if (user == null){
		    throw new UsernameNotFoundException("Couldn't find username.");
		}

		List<QuadrigaGrantedAuthority> roles = new ArrayList<QuadrigaGrantedAuthority>();
		for (IQuadrigaRole role : user.getQuadrigaRoles()) {
			roles.add(new QuadrigaGrantedAuthority(role.getId()));
		}
		
		UserDetails details = new QuadrigaUserDetails(user.getUserName(), user.getName(), user.getPassword(), roles, user.getEmail());
		return details;
	}
}
