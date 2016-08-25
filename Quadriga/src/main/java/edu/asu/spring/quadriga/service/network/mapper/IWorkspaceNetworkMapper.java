package edu.asu.spring.quadriga.service.network.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceNetworkMapper {

	
	public List<IWorkspaceNetwork> getNetworkWorkspaceByWorkSpaceDTO(
			WorkspaceDTO workspaceDTO, IWorkSpace workspace)
			throws QuadrigaStorageException;

	public IWorkspaceNetwork getNetworkWorkspaceByNetworkDTO(NetworksDTO networksDTO,
			INetwork network) throws QuadrigaStorageException;
}
