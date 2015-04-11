package edu.asu.spring.quadriga.domain.factories.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.impl.User;

/**
 * Factory class for creating {@link User}.
 * 
 * @author jdamerow
 *
 */
@Service
public class UserFactory implements IUserFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IUser createUserObject() {
		return new User();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IUser cloneUserObject(IUser user) {
		IUser clone = createUserObject();
		
		clone.setEmail(user.getEmail());
		clone.setName(user.getName());
		clone.setPassword(user.getPassword());
		clone.setUserName(user.getUserName());
		
		List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
		for (IQuadrigaRole role : user.getQuadrigaRoles()) {
			roles.add(role);
		}
		clone.setQuadrigaRoles(roles);
		
		return clone;
	}
}
