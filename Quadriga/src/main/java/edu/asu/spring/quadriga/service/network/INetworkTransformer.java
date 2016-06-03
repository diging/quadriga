package edu.asu.spring.quadriga.service.network;

import java.util.List;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

/**
 * This interface has methods needed to implement to parse network XML and generate JSon for D3
 * @author Lohith Dwaraka
 *
 */
public interface INetworkTransformer {

	/**
	 * This method retrieves the given statements and transforms them into nodes and links.
	 * 
	 * @param networkTopNodesList						{@link List} of {@link INetworkNodeInfo} for each statement in the network
	 * @return											Returns {@link ITransformedNetwork} object which contains the list of nodes and links
	 */
	public abstract ITransformedNetwork transformNetwork(
			List<INetworkNodeInfo> networkTopNodesList);

}