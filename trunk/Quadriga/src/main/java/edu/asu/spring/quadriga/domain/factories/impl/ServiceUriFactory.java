package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.factories.IServiceUriFactory;
import edu.asu.spring.quadriga.domain.implementation.Profile;

/**
 * Factory method to create Profile object
 * @author Kiran batna
 *
 */
@Service
public class ServiceUriFactory implements IServiceUriFactory {

	@Override
	public IProfile createServiceUriObject() {

		return new Profile();
	}
	
}
