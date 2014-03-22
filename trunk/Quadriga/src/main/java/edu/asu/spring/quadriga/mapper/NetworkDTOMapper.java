package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.factories.INetworkNodeInfoFactory;
import edu.asu.spring.quadriga.domain.factories.impl.NetworkFactory;
import edu.asu.spring.quadriga.domain.implementation.Network;
import edu.asu.spring.quadriga.domain.implementation.NetworkNodeInfo;
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
	
	private static final Logger logger = LoggerFactory.getLogger(NetworkDTOMapper.class);
	
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
	 * @return					Return a {@link NetworksDTO} object created using the input parameters
	 */
	public NetworksDTO getNetworksDTO(String networkid, String networkName, String username, String status, String workspaceid)
	{
		NetworksDTO networkDTO = new NetworksDTO(networkid, workspaceid, networkName, username, status, username, new Date(), username, new Date());
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
	 * @return					Return a {@link NetworkStatementsDTO} object created using the input parameters
	 */
	public NetworkStatementsDTO getNetworkStatementsDTO(String rowid,String networkId,String id,String type,String isTop, String username,int version)
	{
		NetworkStatementsDTO networkStatementsDTO = new NetworkStatementsDTO(rowid,networkId, id, Integer.parseInt(isTop), version, type, username, new Date(), username, new Date());
		return networkStatementsDTO;
	}
	
	/**
	 * This method will convert a {@link NetworksDTO} object to {@link Network} object.
	 * It will copy the network id, network name, workspaceid, status and owner object from the input 
	 * 
	 * @param networksDTO				The input record which is not null and contains the network details				
	 * @return							The {@link Network} object containing values copied from the input
	 * @throws QuadrigaStorageException Exception will be thrown when the input paramets do not satisfy the system/database constraints or due to database connection troubles.
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
	 * @throws QuadrigaStorageException Exception will be thrown when the input paramets do not satisfy the system/database constraints or due to database connection troubles.
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
	
	
	public List<INetwork> getListOfNetworks(List<NetworksDTO> networksDTO,String assignedUser) throws QuadrigaStorageException
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
				//network.setNetworkOldVersion(getNetworkOldVersion(getNetworkAssignedDTO(networkDTO.getNetworkid(), assignedUser, networkDTO.getStatus(), INetworkStatus.ARCHIVE_LEVEL_ONE )));
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
	 * @return Return a list of network nodes created from the input list. The list order will be maintained
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
				networkNodeInfo.setId(networkStatementsDTO.getStatementid());
				networkNodeInfo.setStatementType(networkStatementsDTO.getStatementtype());
				networkNodeInfo.setVersion(networkStatementsDTO.getVersion());
				networkNodeInfo.setIsTop(networkStatementsDTO.getIstop());
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
	 * @return	A {@link INetworkOldVersion} object will be created from the input parameters. 
	 */
	/*public INetworkOldVersion getNetworkOldVersion(NetworkAssignedDTO networkAssignedDTO)
	{
		INetworkOldVersion networkOldVersion =null;
		if(networkAssignedDTO != null)
		{
			networkOldVersion = networkOldVersionFactory.createNetworkOldVersionObject();
			networkOldVersion.setPreviousVersionAssignedUser(networkAssignedDTO.getNetworkAssignedDTOPK().getAssigneduser());
			networkOldVersion.setPreviousVersionStatus(networkAssignedDTO.getStatus());
			networkOldVersion.setUpdateDate(networkAssignedDTO.getUpdateddate().toString());
		}else{
			logger.info("network Assigned DTO is null");
		}
		if(networkOldVersion == null){
			logger.info("networkOldVersion is null");
			logger.info("networkOldVersion.getPreviousVersionAssignedUser()");
			logger.info("networkOldVersion.getPreviousVersionStatus()");
			logger.info("networkOldVersion.getUpdateDate()");
		}
		
		return networkOldVersion;
	}*/
	
	public List<INetwork> getNetworkVersions(List<NetworkAssignedDTO> networkAssignedDTOList){
		List<INetwork> networkVersions = null;
		if(networkAssignedDTOList!=null){
			networkVersions = new ArrayList<INetwork>();
			
			INetwork network = null;
			for(NetworkAssignedDTO networkAssignedDTO: networkAssignedDTOList)
			{
				//version = networkVersionsFactory.createNetworkVersionsObject();
				network = networkFactory.createNetworkObject();
				network.setName(networkAssignedDTO.getNetworkname());
				network.setStatus(networkAssignedDTO.getStatus());
				network.setAssignedUser(networkAssignedDTO.getUpdatedby());
				network.setVersionNumber(networkAssignedDTO.getVersion());
				
				
				networkVersions.add(network);
			}
			
		}
		return networkVersions;
	}
	
	/**
	 * This method will create a {@link NetworkAssignedDTO} from the input parameters.
	 * 
	 * @param networkid				The id of the network
	 * @param assignedUsername		The username to be assigned.
	 * @param status				The status of the network
	 * @param version				The version of network
	 * @return A {@link NetworkAssignedDTO} object will be created from the input parameters.
	 */
	public NetworkAssignedDTO getNetworkAssignedDTO(String networkid, String assignedUsername, String status, int version)
	{
		NetworkAssignedDTO networkAssignedDTO = new NetworkAssignedDTO();
		Date date = new Date();
		networkAssignedDTO.setNetworkAssignedDTOPK(new NetworkAssignedDTOPK(networkid, assignedUsername,date));
		networkAssignedDTO.setStatus(status);
		networkAssignedDTO.setCreatedby(assignedUsername);
		networkAssignedDTO.setUpdatedby(assignedUsername);
		networkAssignedDTO.setUpdateddate(date);
		networkAssignedDTO.setVersion(version);
		return networkAssignedDTO;
	}
	
	/**
	 * This method will create a {@link NetworkAssignedDTO} from the input parameters.
	 * 
	 * @param networkid				The id of the network
	 * @param assignedUsername		The username to be assigned.
	 * @param status				The status of the network
	 * @param version				The archived status
	 * @return A {@link NetworkAssignedDTO} object will be created from the input parameters.
	 */
	public NetworkAssignedDTO getNetworkAssignedDTOWithNetworkName(String networkid, String assignedUsername, String status, int version, String networkName)
	{
		NetworkAssignedDTO networkAssignedDTO = new NetworkAssignedDTO();
		Date date = new Date();
		networkAssignedDTO.setNetworkAssignedDTOPK(new NetworkAssignedDTOPK(networkid, assignedUsername,date));
		networkAssignedDTO.setStatus(status);
		networkAssignedDTO.setCreatedby(assignedUsername);
		networkAssignedDTO.setUpdatedby(assignedUsername);
		networkAssignedDTO.setUpdateddate(date);
		networkAssignedDTO.setNetworkname(networkName);
		networkAssignedDTO.setVersion(version);
		return networkAssignedDTO;
	}
	/**
	 * This method will create {@link NetworksAnnotationsDTO} from the input parameters.
	 * 
	 * @param networkid			The id of the network
	 * @param nodeId			The id of this unique record
	 * @param nodeName			Node name
	 * @param annotationtext	The annotation text
	 * @param annotationid		The annotation id
	 * @param username			The username of the user to be associated with annotation
	 * @param objecttype		The type of object
	 * @return A {@link NetworksAnnotationsDTO} object will be created from the input parameters; 
	 */
	public NetworksAnnotationsDTO getNetworkAnnotationDTO(String networkid, String nodeId, String nodeName, String annotationtext, String annotationid, String username, String objecttype)
	{
		NetworksAnnotationsDTO networkAnnotationsDTO = new NetworksAnnotationsDTO();
		networkAnnotationsDTO.setNetworkid(networkid);
		networkAnnotationsDTO.setNodeid(nodeId);
		networkAnnotationsDTO.setNodename(nodeName);
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
	 * @param networksDTOList	The list of networks DTO to be converted to {@link INetwork} 
	 * @return					List of {@link INetwork} which will be created from the input list. The list order will be maintained
	 * @throws QuadrigaStorageException Exception will be thrown when the input paramets do not satisfy the system/database constraints or due to database connection troubles.
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
