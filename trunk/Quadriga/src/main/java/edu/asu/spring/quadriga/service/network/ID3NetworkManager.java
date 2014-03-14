package edu.asu.spring.quadriga.service.network;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;

public interface ID3NetworkManager {

	public abstract INetworkJSon parseNetworkForD3Jquery(
			List<INetworkNodeInfo> networkTopNodesList);

}