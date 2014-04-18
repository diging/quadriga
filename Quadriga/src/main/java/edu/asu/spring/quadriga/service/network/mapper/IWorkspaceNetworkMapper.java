package edu.asu.spring.quadriga.service.network.mapper;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceNetworkMapper {

	public IWorkspaceNetwork getNetworkWorkspace(NetworksDTO networksDTO, INetwork network) throws QuadrigaStorageException;
}
