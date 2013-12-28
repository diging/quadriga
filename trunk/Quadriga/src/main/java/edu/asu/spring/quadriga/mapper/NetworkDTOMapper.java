package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.factories.INetworkNodeInfoFactory;
import edu.asu.spring.quadriga.domain.factories.INetworkOldVersionFactory;
import edu.asu.spring.quadriga.domain.factories.impl.NetworkFactory;
import edu.asu.spring.quadriga.domain.implementation.Network;
import edu.asu.spring.quadriga.domain.implementation.NetworkNodeInfo;
import edu.asu.spring.quadriga.domain.implementation.NetworkOldVersion;
import edu.asu.spring.quadriga.dto.NetworkAssignedDTO;
import edu.asu.spring.quadriga.dto.NetworkAssignedDTOPK;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * The purpose of this class is to map the DTO class objects to the domain objects
 * used in Quadriga
 * 
 * @author Ram Kumar Kumaresan
 */
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
	
	/**
	 * This method will return a new {@link NetworksDTO} object with the parameters set to the method input
	 * 
	 * @param networkid			The id of the network
	 * @param networkName		The name of the network
	 * @param username			The username to be associated with the network
	 * @param workspaceid		The id of the workspace that the network belongs to
	 * @return
	 */
	public NetworksDTO getNetworksDTO(String networkid, String networkName, String username, String workspaceid)
	{
		NetworksDTO networkDTO = new NetworksDTO(networkid, workspaceid, networkName, username, INetworkStatus.PENDING, username, new Date(), username, new Date());
		return networkDTO;
	}
	
	/**
	 * This method will return a new {@link NetworkStatementsDTO} object with the parameters set to the method input
	 * 
	 * @param rowid				The rowid of the NetworkStatement
	 * @param networkId			The id of the network
	 * @param id				The unique id of the record
	 * @param type				The type of the NetworkStatement
	 * @param isTop				The number indicating the isTop
	 * @param username			The username of the user associated with the network
	 * @return
	 */
	public NetworkStatementsDTO getNetworkStatementsDTO(String networkId,String id,String type,String isTop, String username)
	{
		NetworkStatementsDTO networkStatementsDTO = new NetworkStatementsDTO(networkId, id, Integer.parseInt(isTop), INetworkStatus.NOT_ARCHIVED, type, username, new Date(), username, new Date());
		return networkStatementsDTO;
	}
	
	/**
	 * This method will convert a {@link NetworksDTO} object to {@link Network} object.
	 * It will copy the network id, network name, workspaceid, status and owner object from the input 
	 * 
	 * @param networksDTO				The input record which is not null and contains the network details				
	 * @return							The {@link Network} object containing values copied from the input
	 * @throws QuadrigaStorageException
	 */
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
	
	/**
	 * This method will convert the list of {@link NetworksDTO} objects to a list of {@link Network} objects.
	 * For each object it will copy the network id, network name, workspaceid, status and owner object from the input.
	 * 
	 * @param networksDTO				The input list of networksDTO objects
	 * @return							The corresponding list of network objects. The input list order will be maintained
	 * @throws QuadrigaStorageException
	 */
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
	
	/**
	 * This method will convert the list of {@link NetworkStatementsDTO} objects to a list of {@link NetworkNodeInfo} objects.
	 * For each object it will copy the id and statement type.
	 * 
	 * @param networkStatementsDTOList	The input list of networkstatementsDTO objects
	 * @return
	 */
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
				networkNodeInfo.setId(networkStatementsDTO.getNetworkstatementsDTOPK().getStatementid());
				networkNodeInfo.setStatementType(networkStatementsDTO.getStatementtype());
				networkList.add(networkNodeInfo);
			}
		}
		return networkList;
	}
	
	/**
	 * This method will convert a {@link NetworkAssignedDTO} object to {@link NetworkOldVersion} object.
	 * It will copy the previousAssignedUser, previousVersionStatus and updateDate from the input object.
	 * 
	 * @param networkAssignedDTO	The networkAssignedDTO to be converted
	 * @return
	 */
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
	
	/**
	 * This method will create a {@link NetworkAssignedDTO} from the input parameters.
	 * 
	 * @param networkid				The id of the network
	 * @param assignedUsername		The username to be assigned.
	 * @param status				The status of the network
	 * @param archived				The archived status
	 * @return
	 */
	public NetworkAssignedDTO getNetworkAssignedDTO(String networkid, String assignedUsername, String status, int archived)
	{
		NetworkAssignedDTO networkAssignedDTO = new NetworkAssignedDTO();
		networkAssignedDTO.setNetworkAssignedDTOPK(new NetworkAssignedDTOPK(networkid, assignedUsername));
		networkAssignedDTO.setStatus(status);
		networkAssignedDTO.setCreatedby(assignedUsername);
		networkAssignedDTO.setUpdatedby(assignedUsername);
		networkAssignedDTO.setUpdateddate(new Date());
		networkAssignedDTO.setIsarchived(archived);
		
		return networkAssignedDTO;
	}
	
	/**
	 * This method will create {@link NetworksAnnotationsDTO} from the input parameters.
	 * 
	 * @param networkid			The id of the network
	 * @param id				The id of this unique record
	 * @param annotationtext	The annotation text
	 * @param annotationid		The annotation id
	 * @param username			The username of the user to be associated with annotation
	 * @param objecttype		The type of object
	 * @return
	 */
	public NetworksAnnotationsDTO getNetworkAnnotationDTO(String networkid, String id, String annotationtext, String annotationid, String username, String objecttype)
	{
		NetworksAnnotationsDTO networkAnnotationsDTO = new NetworksAnnotationsDTO();
		networkAnnotationsDTO.setNetworkid(networkid);
		networkAnnotationsDTO.setObjectid(id);
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
	
	/**
	 * This method will create list of networks from the input parameters.
	 * 
	 * @param networksDTOList	The list of networks DTO to be converted to Inetwork
	 * @return
	 */
	public List<INetwork> getNetworkList(List<NetworksDTO> networksDTOList) throws QuadrigaStorageException
	{
		List<INetwork> networkList = new ArrayList<INetwork>();
		if(networksDTOList != null && networksDTOList.size() > 0)
		{
			Iterator<NetworksDTO> nwDTOIterator = networksDTOList.iterator();
			while(nwDTOIterator.hasNext())
			{
				networkList.add(getNetwork(nwDTOIterator.next()));
			}
		}
		return networkList;
	}
}
