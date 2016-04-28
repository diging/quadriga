package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IDspaceKeysFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceKeys;

/**
 * Factory method to create DSpaceKey object
 * @author Ram Kumar Kumaresan
 *
 */
@Service
public class DspaceKeysFactory implements IDspaceKeysFactory {

	@Override
	public IDspaceKeys createDspaceKeysObject()
	{
		return new DspaceKeys();
	}
}
