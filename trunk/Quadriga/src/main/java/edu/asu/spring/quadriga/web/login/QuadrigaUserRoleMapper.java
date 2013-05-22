package edu.asu.spring.quadriga.web.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.PersonContextMapper;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * This class is responsible for adding Quadriga specific roles to authenticated users.
 * 
 * @author Julia Damerow
 *
 */
public class QuadrigaUserRoleMapper extends PersonContextMapper {

	IUserManager userManager;

	public IUserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * This user is called with the username of the user that tries to login to Quadriga.
	 * It asks the {@link IUserManager} for the details about the user then creates and adds the 
	 * corresponding {@link QuadrigaGrantedAuthority}/ies.
	 */
	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx,
			String username, Collection<? extends GrantedAuthority> authorities) {

		// authorities.add(new ActiveUserAuthority());

		List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
		authorityList.addAll(authorities);
		
		// Check the status of the user in the Quad DB
		IUser user = null;
		user = userManager.getUserDetails(username);

		if(user.getQuadrigaRoles()!=null)
		{
		for (IQuadrigaRole role : user.getQuadrigaRoles()) {
			authorityList.add(new QuadrigaGrantedAuthority(role.getId()));
		}
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
