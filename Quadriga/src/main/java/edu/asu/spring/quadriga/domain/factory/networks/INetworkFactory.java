package edu.asu.spring.quadriga.domain.factory.networks;

import edu.asu.spring.quadriga.domain.network.INetwork;

/**
 * Factory interface for Dictionary factories.
 * 
 */

public interface INetworkFactory {

	/**
	 * Create Network factory object
	 * @return INetwork
	 */
	public abstract INetwork createNetworkObject();
	
	public abstract INetwork cloneNetworkObject(INetwork network);

}
