package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.factories.IProfileFactory;
import edu.asu.spring.quadriga.domain.implementation.Profile;

@Service
public class ProfileFactory implements IProfileFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProfile createProfileObject() {
		
		return new Profile();
	}

}