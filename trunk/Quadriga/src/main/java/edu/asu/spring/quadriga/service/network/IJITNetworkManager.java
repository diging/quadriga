package edu.asu.spring.quadriga.service.network;

import java.util.List;

import edu.asu.spring.quadriga.domain.INetworkNodeInfo;

public interface IJITNetworkManager {

	public abstract String parseNetworkForJITJquery(
			List<INetworkNodeInfo> networkTopNodesList);

}