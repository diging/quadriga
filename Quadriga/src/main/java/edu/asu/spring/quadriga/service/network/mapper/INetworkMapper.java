package edu.asu.spring.quadriga.service.network.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.networks.Network;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface has methods to implement the mapping of DTO object to Domain
 * objects for the service layer for Networks. These methods need to map
 * {@link NetworkDTO} to {@link Network} object.
 * 
 * @author Sayalee Mehendale
 *
 */
public interface INetworkMapper {

    public INetwork getNetwork(NetworksDTO networksDTO) throws QuadrigaStorageException;

    public INetwork getNetworkShallowDetails(NetworksDTO networksDTO) throws QuadrigaStorageException;

    public List<INetwork> getListOfNetworksForUser(IUser user) throws QuadrigaStorageException;

    public List<INetwork> getNetworkListForProject(String projectid) throws QuadrigaStorageException;

    public List<INetwork> getNetworksOfUserWithStatus(IUser user, String networkStatus) throws QuadrigaStorageException;

    public List<INetworkNodeInfo> getNetworkNodes(String networkId, int versionId) throws QuadrigaStorageException;

    public List<INetwork> getEditorNetworkList(IUser user) throws QuadrigaStorageException;
    
    public List<INetwork> getListOfApprovedNetworks() throws QuadrigaStorageException;

}
