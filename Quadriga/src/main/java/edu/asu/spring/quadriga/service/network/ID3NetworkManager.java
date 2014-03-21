package edu.asu.spring.quadriga.service.network;

import java.util.List;

import edu.asu.spring.quadriga.d3.domain.ID3Link;
import edu.asu.spring.quadriga.d3.domain.ID3Node;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;

/**
 * This interface has methods needed to implement to parse network XML and generate JSon for D3
 * @author Lohith Dwaraka
 *
 */
public interface ID3NetworkManager {

	/**
	 * This method should help in getting the D3 JSon by parsing through the Network XML.
	 * It would parse the network XML and use {@link ID3Node} and {@link ID3Link} to form the links and nodes of Network.
	 * @param networkTopNodesList						{@link List} of {@link INetworkNodeInfo} for each statement in the network
	 * @return											Returns {@link INetworkJSon} object which contains the list of nodes and D3 JQuery JSon
	 */
	public abstract INetworkJSon parseNetworkForD3Jquery(
			List<INetworkNodeInfo> networkTopNodesList);

}