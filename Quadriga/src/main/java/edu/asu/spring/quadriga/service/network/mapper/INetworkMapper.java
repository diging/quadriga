package edu.asu.spring.quadriga.service.network.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface has methods to implement the mapping of DTO object to Domain objects for the service layer for Networks.
 * These methods need to map {@link NetworkDTO} to {@link Network} object.
 * 
 * @author Sayalee Mehendale
 *
 */
public interface INetworkMapper {

	public INetwork getNetwork(String networkId) throws QuadrigaStorageException;
	
	public INetwork getNetworkShallowDetails(NetworksDTO networksDTO)
			throws QuadrigaStorageException;

	List<INetwork> getListOfNetworks(IUser user)
			throws QuadrigaStorageException;

	List<INetwork> getNetworkList(String projectid)
			throws QuadrigaStorageException;	
	
}
