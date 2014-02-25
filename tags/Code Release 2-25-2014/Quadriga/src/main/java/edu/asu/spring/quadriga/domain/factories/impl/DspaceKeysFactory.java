package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IDspaceKeysFactory;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceKeys;

@Service
public class DspaceKeysFactory implements IDspaceKeysFactory {

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.factories.impl.IDspaceKeysFactory#createDspaceKeysObject()
	 */
	@Override
	public IDspaceKeys createDspaceKeysObject()
	{
		return new DspaceKeys();
	}
}
