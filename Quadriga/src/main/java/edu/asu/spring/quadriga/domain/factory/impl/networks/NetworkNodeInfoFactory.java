package edu.asu.spring.quadriga.domain.factory.impl.networks;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.networks.INetworkNodeInfoFactory;
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

	@Override
	public INetworkNodeInfo cloneNetworkNodeInfoObject(INetworkNodeInfo networkNode) 
	{
		INetworkNodeInfo clone = createNetworkNodeInfoObject();
		clone.setId(networkNode.getId());
		clone.setIsTop(networkNode.getIsTop());
		clone.setStatementType(networkNode.getStatementType());
		clone.setVersion(networkNode.getVersion());
		return clone;
	}

}
