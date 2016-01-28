package edu.asu.spring.quadriga.service.network;

import java.util.List;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;

/**
 * This interface has methods needed to implement to parse network XML and generate JSon for D3
 * @author Lohith Dwaraka
 *
 */
public interface ID3NetworkManager {

	/**
	 * This method should help in getting the D3 JSon by parsing through the Network XML.
	 * 
	 * @param networkTopNodesList						{@link List} of {@link INetworkNodeInfo} for each statement in the network
	 * @return											Returns {@link INetworkJSon} object which contains the list of nodes and D3 JQuery JSon
	 */
	public abstract INetworkJSon parseNetworkForD3Jquery(
			List<INetworkNodeInfo> networkTopNodesList);

}