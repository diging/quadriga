package edu.asu.spring.quadriga.web.login;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.PersonContextMapper;

import edu.asu.spring.quadriga.domain.IQuadrigaRoles;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.IUserManager;

public class QuadrigaUserRoleMapper extends PersonContextMapper {

	IUserManager userManager;

	public IUserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx,
			String username, Collection<? extends GrantedAuthority> authorities) {

		// authorities.add(new ActiveUserAuthority());

		List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
		authorityList.addAll(authorities);

		// Check the status of the user in the Quad DB
		IUser user = null;
		try {
			user = userManager.getUserDetails(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (IQuadrigaRoles role : user.getQuadrigaRoles()) {
			authorityList.add(new QuadrigaGrantedAuthority(role.getId()));
		}

		// No such user present in Quad DB
		// if (user != null && user.getName() != null) {
		// authorityList.add(new ActiveUserGrantedAuthority());
		// }
		// else if (user != null && user.getName() == null) {
		// authorityList.add(new InactiveUserGrantedAuthority());
		// }
		// else {
		// authorityList.add(new NoAccountGrantedAuthority());
		// }

		UserDetails details = super.mapUserFromContext(ctx, username,
				authorityList);

		return details;
	}

}
