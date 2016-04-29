package edu.asu.spring.quadriga.domain.factory.networks;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;

/**
 * Factory interface to create NetworkNodeInfo object
 * @author kiran batna
 *
 */
public interface INetworkNodeInfoFactory {

	public abstract INetworkNodeInfo createNetworkNodeInfoObject();
	
	public abstract INetworkNodeInfo cloneNetworkNodeInfoObject(INetworkNodeInfo networkNode);

}