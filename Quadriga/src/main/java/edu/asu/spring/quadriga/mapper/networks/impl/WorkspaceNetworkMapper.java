package edu.asu.spring.quadriga.mapper.networks.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.NetworkWorkspaceDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.networks.INetworkMapper;
import edu.asu.spring.quadriga.mapper.networks.IWorkspaceNetworkMapper;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceShallowMapper;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

@Service
public class WorkspaceNetworkMapper implements IWorkspaceNetworkMapper {

    @Autowired
    IListWSManager wsManager;

    @Autowired
    IWorkspaceShallowMapper workspaceshallowmapper;

    @Autowired
    INetworkMapper networkmapper;

    @Override
    public IWorkspace getWorkspaces(NetworksDTO networksDTO, INetwork network)
            throws QuadrigaStorageException {
        NetworkWorkspaceDTO networkWorkspaceDTO = networksDTO.getNetworkWorkspace();
        if (networkWorkspaceDTO != null) {
            return workspaceshallowmapper.mapWorkspaceDTO(networkWorkspaceDTO.getWorkspaceDTO());
        }
        return null;
    }

    @Override
    public List<INetwork> getNetworks(WorkspaceDTO workspaceDTO, IWorkspace workspace)
            throws QuadrigaStorageException {

        List<INetwork> networks = new ArrayList<INetwork>();
        List<NetworkWorkspaceDTO> networkworkspaceDTOList = workspaceDTO.getWorkspaceNetworkDTOList();

        if (networkworkspaceDTOList != null) {
            for (NetworkWorkspaceDTO networkworkspaceDTO : networkworkspaceDTOList) {
                INetwork network = networkmapper.getNetworkShallowDetails(networkworkspaceDTO.getNetworksDTO());
                networks.add(network);
            }
        }

        return networks;
    }

}
