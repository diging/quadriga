package edu.asu.spring.quadriga.domain.factories;

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

}
