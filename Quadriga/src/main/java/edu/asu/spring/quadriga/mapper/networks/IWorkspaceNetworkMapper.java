package edu.asu.spring.quadriga.mapper.networks;

import java.util.List;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceNetworkMapper {

    public List<INetwork> getNetworks(WorkspaceDTO workspaceDTO, IWorkspace workspace)
            throws QuadrigaStorageException;

    public IWorkspace getWorkspaces(NetworksDTO networksDTO, INetwork network)
            throws QuadrigaStorageException;
}
