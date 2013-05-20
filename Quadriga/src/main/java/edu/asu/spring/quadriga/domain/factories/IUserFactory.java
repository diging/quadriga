package edu.asu.spring.quadriga.domain.factories;

import org.apache.commons.lang.NotImplementedException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.User;

/**
 * Factory interface for user factories.
 * @author jdamerow
 *
 */
public interface IUserFactory {

	/**
	 * Factory method for creating {@link User} objects.
	 * @return
	 */
	public abstract IUser createUserObject();

	/**
	 * Method for cloning a {@link IUser} object.
	 * @param user the user object to be cloned.
	 * @return a clone of the given user object that contains the exact same information as the original object.
	 * @throws NotImplementedException
	 */
	public abstract IUser cloneUserObject(IUser user);

}