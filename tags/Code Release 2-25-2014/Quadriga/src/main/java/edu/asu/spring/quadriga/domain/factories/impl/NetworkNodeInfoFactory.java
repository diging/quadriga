package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.factories.INetworkNodeInfoFactory;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.NetworkNodeInfo;

/**
 * Factory class for creating {@link Dictionary}.
 * 
 * @author Lohith Dwaraka
 *
 */
@Service
public class NetworkNodeInfoFactory implements INetworkNodeInfoFactory  {
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.factories.impl.INetworkNodeInfoFactory#createNetworkNodeInfoObject()
	 */
	@Override
	public INetworkNodeInfo createNetworkNodeInfoObject() {
		
		return new NetworkNodeInfo();
	}

}
