package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.IProfile;

/**
 * Factory interface to create Profile object
 * @author kiran batna
 *
 */
public interface IProfileFactory {

	public abstract IProfile createProfile();
}
