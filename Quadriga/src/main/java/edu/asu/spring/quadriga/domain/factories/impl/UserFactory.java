package edu.asu.spring.quadriga.domain.factories.impl;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.User;

/**
 * Factory class for creating users.
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
		// FIXME: implementation needed
		throw new NotImplementedException("Clone user is not yet implemented.");
	}
}
