package edu.asu.spring.quadriga.service.network.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.factories.impl.NetworkFactory;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.mapper.INetworMapper;

public class NetworkMapper implements INetworMapper{
	
	@Autowired
	private IDBConnectionNetworkManager dbconnect;
	
	@Autowired
	private NetworkFactory networkFactory;
	
	@Autowired
	private IUserManager userManager;
	
	@Override
	public INetwork getNetwork(String networkId) throws QuadrigaStorageException
	{
		INetwork network = null;
		NetworksDTO networksDTO = dbconnect.getNetworksDTO(networkId);
		if(networksDTO != null)
		{
			network = networkFactory.createNetworkObject();
			network.setNetworkId(networksDTO.getNetworkid());
			network.setNetworkName(networksDTO.getNetworkname());
			//network.setNetworkWorkspaces(networksDTO.getn)
			//network.setWorkspaceid(networksDTO.getWorkspaceid());
			network.setStatus(networksDTO.getStatus());
			if(networksDTO.getNetworkowner() != null)
				network.setCreator(userManager.getUserDetails(networksDTO.getNetworkowner()));
		}
		return network;
	}
	
}
