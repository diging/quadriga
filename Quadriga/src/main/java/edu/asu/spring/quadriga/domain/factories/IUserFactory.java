package edu.asu.spring.quadriga.domain.factories;

import org.apache.commons.lang.NotImplementedException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.User;

/**
 * Factory interface for user factories.
 * @author Julia Damerow
 *
 */
public interface IUserFactory {

	/**
	 * Factory method for creating {@link User} objects.
	 * @return
	 */
	public abstract IUser createUserObject();

	/**
	 * Method for cloning a {@link IUser} object. Note that this will produce a shallow clone, meaning that the QuadrigaRoles
	 * will simply be put into a new list for the clone, but the QuadrigaRole objects themselves will be the same.
	 * @param user the user object to be cloned.
	 * @return a clone of the given user object that contains the exact same information as the original object.
	 * @throws NotImplementedException
	 */
	public abstract IUser cloneUserObject(IUser user);

}