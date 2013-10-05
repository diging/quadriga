package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.factories.INetworkOldVersionFactory;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.NetworkOldVersion;

/**
 * Factory class for creating {@link Dictionary}.
 * 
 * @author Lohith Dwaraka
 *
 */
@Service
public class NetworkOldVersionFactory implements INetworkOldVersionFactory  {

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.factories.impl.INetworkOldVersionFactory#createNetworkOldVersionObject()
	 */
	@Override
	public INetworkOldVersion createNetworkOldVersionObject() {
		
		return new NetworkOldVersion();
	}

}
