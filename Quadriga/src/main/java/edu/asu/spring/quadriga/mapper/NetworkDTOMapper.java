package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.factories.INetworkNodeInfoFactory;
import edu.asu.spring.quadriga.domain.factories.INetworkOldVersionFactory;
import edu.asu.spring.quadriga.domain.factories.impl.NetworkFactory;
import edu.asu.spring.quadriga.domain.implementation.NetworkAnnotation;
import edu.asu.spring.quadriga.dto.NetworkAssignedDTO;
import edu.asu.spring.quadriga.dto.NetworkAssignedDTOPK;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;


@Service
public class NetworkDTOMapper {

	@Autowired
	private INetworkNodeInfoFactory networkNodeInfoFactory;
	
	@Autowired
	private INetworkOldVersionFactory networkOldVersionFactory;
	
	@Autowired
	private NetworkFactory networkFactory;
	
	@Autowired
	private IUserManager userManager;
	
	
	public NetworksDTO getNetworksDTO(String networkid, String networkName, String username, String workspaceid)
	{
		NetworksDTO networkDTO = new NetworksDTO(networkid, workspaceid, networkName, username, INetworkStatus.PENDING, username, new Date(), username, new Date());
		return networkDTO;
	}
	
	public NetworkStatementsDTO getNetworkStatementsDTO(String rowid, String networkId,String id,String type,String isTop, String username)
	{
		NetworkStatementsDTO networkStatementsDTO = new NetworkStatementsDTO(rowid, networkId, id, Integer.parseInt(isTop), INetworkStatus.NOT_ARCHIVED, type, username, new Date(), username, new Date());
		return networkStatementsDTO;
	}
	
	public INetwork getNetwork(NetworksDTO networksDTO) throws QuadrigaStorageException
	{
		INetwork network = null;
		if(networksDTO != null)
		{
			network = networkFactory.createNetworkObject();
			network.setId(networksDTO.getNetworkid());
			network.setName(networksDTO.getNetworkname());
			network.setWorkspaceid(networksDTO.getWorkspaceid());
			network.setStatus(networksDTO.getStatus());
			if(networksDTO.getNetworkowner() != null)
				network.setCreator(userManager.getUserDetails(networksDTO.getNetworkowner()));
		}
		return network;
	}
	
	public List<INetwork> getListOfNetworks(List<NetworksDTO> networksDTO) throws QuadrigaStorageException
	{
		List<INetwork> networkList = null;
		if(networksDTO != null)
		{
			networkList = new ArrayList<INetwork>();
			INetwork network = null;
			for(NetworksDTO networkDTO: networksDTO)
			{
				network = networkFactory.createNetworkObject();
				network.setId(networkDTO.getNetworkid());
				network.setName(networkDTO.getNetworkname());
				network.setWorkspaceid(networkDTO.getWorkspaceid());
				network.setStatus(networkDTO.getStatus());
				if(networkDTO.getNetworkowner() != null)
					network.setCreator(userManager.getUserDetails(networkDTO.getNetworkowner()));
				
				networkList.add(network);
			}
		}		
		return networkList;
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
				networkNodeInfo.setId(networkStatementsDTO.getId());
				networkNodeInfo.setStatementType(networkStatementsDTO.getStatementtype());
				networkList.add(networkNodeInfo);
			}
		}
		return networkList;
	}
	
	public INetworkOldVersion getNetworkOldVersion(NetworkAssignedDTO networkAssignedDTO)
	{
		INetworkOldVersion networkOldVersion =null;
		if(networkAssignedDTO != null)
		{
			networkOldVersion = networkOldVersionFactory.createNetworkOldVersionObject();
			networkOldVersion.setPreviousVersionAssignedUser(networkAssignedDTO.getNetworkAssignedDTOPK().getAssigneduser());
			networkOldVersion.setPreviousVersionStatus(networkAssignedDTO.getStatus());
			networkOldVersion.setUpdateDate(networkAssignedDTO.getUpdateddate().toString());
		}
		
		return networkOldVersion;
	}
	
	public NetworkAssignedDTO getNetworkAssignedDTO(String networkid, String assignedUsername, String status, int archived)
	{
		NetworkAssignedDTO networkAssignedDTO = new NetworkAssignedDTO();
		networkAssignedDTO.setNetworkAssignedDTOPK(new NetworkAssignedDTOPK(networkid, assignedUsername, new Date()));
		networkAssignedDTO.setStatus(status);
		networkAssignedDTO.setCreatedby(assignedUsername);
		networkAssignedDTO.setUpdatedby(assignedUsername);
		networkAssignedDTO.setUpdateddate(new Date());
		networkAssignedDTO.setIsarchived(archived);
		
		return networkAssignedDTO;
	}
	
	public NetworksAnnotationsDTO getNetworkAnnotationDTO(String networkid, String id, String annotationtext, String annotationid, String username, String objecttype)
	{
		NetworksAnnotationsDTO networkAnnotationsDTO = new NetworksAnnotationsDTO();
		networkAnnotationsDTO.setNetworkid(networkid);
		networkAnnotationsDTO.setId(id);
		networkAnnotationsDTO.setAnnotationtext(annotationtext);
		networkAnnotationsDTO.setAnnotationid(annotationid);
		networkAnnotationsDTO.setObjecttype(objecttype);
		networkAnnotationsDTO.setUsername(username);
		networkAnnotationsDTO.setCreatedby(username);
		networkAnnotationsDTO.setCreateddate(new Date());
		networkAnnotationsDTO.setUpdatedby(username);
		networkAnnotationsDTO.setUpdateddate(new Date());
		
		return networkAnnotationsDTO;
	}
}
