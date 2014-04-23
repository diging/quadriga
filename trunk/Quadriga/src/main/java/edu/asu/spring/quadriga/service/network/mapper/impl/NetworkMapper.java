package edu.asu.spring.quadriga.service.network.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.factory.impl.networks.NetworkFactory;
import edu.asu.spring.quadriga.domain.factory.networks.INetworkNodeInfoFactory;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.mapper.INetworkMapper;
import edu.asu.spring.quadriga.service.network.mapper.IWorkspaceNetworkMapper;

public class NetworkMapper implements INetworkMapper{
	
	@Autowired
	private IDBConnectionNetworkManager dbconnect;
	
	@Autowired
	private NetworkFactory networkFactory;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private IWorkspaceNetworkMapper networkworkspacemapper;
	
	@Autowired
	private INetworkNodeInfoFactory networkNodeInfoFactory;
	
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

			IWorkspaceNetwork networkworkspace = networkworkspacemapper.getNetworkWorkspaceByNetworkDTO(networksDTO, network);
			network.setNetworkWorkspace(networkworkspace);

			network.setStatus(networksDTO.getStatus());
			if(networksDTO.getNetworkowner() != null)
				network.setCreator(userManager.getUserDetails(networksDTO.getNetworkowner()));
			network.setCreatedBy(networksDTO.getCreatedby());
			network.setCreatedDate(networksDTO.getCreateddate());
			network.setUpdatedBy(networksDTO.getUpdatedby());
			network.setUpdatedDate(networksDTO.getUpdateddate());
			
			List<NetworkStatementsDTO> networkStatementsDTOList = networksDTO.getNetworkStamentesDTOList();
			List<INetworkNodeInfo> networkList = null;
			if(networkStatementsDTOList != null)
			{
				networkList = new ArrayList<INetworkNodeInfo>();
				INetworkNodeInfo networkNodeInfo = null;
				for(NetworkStatementsDTO networkStatementsDTO:networkStatementsDTOList)
				{
					networkNodeInfo = networkNodeInfoFactory.createNetworkNodeInfoObject();
					networkNodeInfo.setId(networkStatementsDTO.getStatementid());
					networkNodeInfo.setStatementType(networkStatementsDTO.getStatementtype());
					networkNodeInfo.setVersion(networkStatementsDTO.getVersion());
					networkNodeInfo.setIsTop(networkStatementsDTO.getIstop());
					networkList.add(networkNodeInfo);
				}
			}
			network.setNetworkNodes(networkList);
			
			//TODO : add version number and text url to network DTO
			//network.setAssignedUser(networksDTO.get)
			//network.setNetworkNodes(networksDTO.get)
			//network.setNetworksAccess(networksDTO.getn)
			//network.setTextUrl(textUrl)
			//network.setVersionNumber(networksDTO.get)
		}
		return network;
	}
	
	@Override
	public INetwork getNetworkShallowDetails(NetworksDTO networksDTO) throws QuadrigaStorageException{
		
		INetwork network = null;
		if(networksDTO != null)
		{
			network = networkFactory.createNetworkObject();
			network.setNetworkId(networksDTO.getNetworkid());
			network.setNetworkName(networksDTO.getNetworkname());

			network.setStatus(networksDTO.getStatus());
			if(networksDTO.getNetworkowner() != null)
				network.setCreator(userManager.getUserDetails(networksDTO.getNetworkowner()));
			network.setCreatedBy(networksDTO.getCreatedby());
			network.setCreatedDate(networksDTO.getCreateddate());
			network.setUpdatedBy(networksDTO.getUpdatedby());
			network.setUpdatedDate(networksDTO.getUpdateddate());
		}
		return network;
	}
	
}
