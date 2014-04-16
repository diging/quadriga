package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.INetworkNodeInfoFactory;
import edu.asu.spring.quadriga.domain.impl.dictionary.Dictionary;
import edu.asu.spring.quadriga.domain.impl.networks.NetworkNodeInfo;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;

/**
 * Factory class for creating {@link Dictionary}.
 * 
 * @author Lohith Dwaraka
 *
 */
@Service
public class NetworkNodeInfoFactory implements INetworkNodeInfoFactory  {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkNodeInfo createNetworkNodeInfoObject() {
		
		return new NetworkNodeInfo();
	}

}