package edu.asu.spring.quadriga.domain.factories.impl;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.factories.IProfileFactory;
import edu.asu.spring.quadriga.domain.implementation.Profile;

public class ProfileFactory implements IProfileFactory {

	@Override
	public IProfile createProfileObject() {
		
		return new Profile();
	}

}
