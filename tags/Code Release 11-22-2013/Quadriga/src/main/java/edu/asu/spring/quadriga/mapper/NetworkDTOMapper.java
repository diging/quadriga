package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.INetworkNodeInfoFactory;
import edu.asu.spring.quadriga.domain.implementation.Network;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTOPK;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.web.network.INetworkStatus;


@Service
public class NetworkDTOMapper {

	@Autowired
	INetworkNodeInfoFactory networkNodeInfoFactory;
	
	public NetworksDTO getNetworksDTO(String networkid, String networkName, String username, String workspaceid)
	{
		NetworksDTO networkDTO = new NetworksDTO(networkid, workspaceid, networkName, username, INetworkStatus.PENDING, username, new Date(), username, new Date());
		return networkDTO;
	}
	
	public NetworkStatementsDTO getNetworkStatementsDTO(String networkId,String id,String type,String isTop, String username)
	{
		NetworkStatementsDTO networkStatementsDTO = new NetworkStatementsDTO(new NetworkStatementsDTOPK(networkId, id), type, Integer.parseInt(isTop), INetworkStatus.NOT_ARCHIVED, username, new Date(), username, new Date());
		return networkStatementsDTO;
	}
	
	public INetwork getNetwork(NetworksDTO networksDTO)
	{
		INetwork network = null;
		if(networksDTO != null)
		{
			network.setId(networksDTO.getNetworkid());
			network.setName(networksDTO.getNetworkname());
			network.setWorkspaceid(networksDTO.getNetworkid());
			network.setStatus(networksDTO.getStatus());
		}
		return network;
	}
	
	public List<INetworkNodeInfo> getListOfNetworkNodeInfo(List<NetworkStatementsDTO> networkStatementsDTOList)
	{
		List<INetworkNodeInfo> networkList = null;
		if(networkStatementsDTOList != null)
		{
			networkList = new ArrayList<INetworkNodeInfo>();
			INetworkNodeInfo networkNodeInfo = null;
			for(NetworkStatementsDTO networkStatementsDTO:networkStatementsDTOList)
			{
				networkNodeInfo = networkNodeInfoFactory.createNetworkNodeInfoObject();
				networkNodeInfo.setId(networkStatementsDTO.getNetworkStatementsDTOPK().getId());
				networkNodeInfo.setStatementType(networkStatementsDTO.getStatementtype());
				networkList.add(networkNodeInfo);
			}
		}
		return networkList;
	}
}
