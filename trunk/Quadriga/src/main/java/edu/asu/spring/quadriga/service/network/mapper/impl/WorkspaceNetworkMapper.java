package edu.asu.spring.quadriga.service.network.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceNetwork;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dto.NetworkWorkspaceDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.mapper.INetworkMapper;
import edu.asu.spring.quadriga.service.network.mapper.IWorkspaceNetworkMapper;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceShallowMapper;

public class WorkspaceNetworkMapper implements IWorkspaceNetworkMapper{

	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	IWorkspaceShallowMapper workspaceshallowmapper;
	
	@Autowired
	INetworkMapper networkmapper;
	
	@Override
	public IWorkspaceNetwork getNetworkWorkspaceByNetworkDTO(NetworksDTO networksDTO,
			INetwork network) throws QuadrigaStorageException {
		
		IWorkspaceNetwork networkworkspace = new WorkspaceNetwork();
		
		NetworkWorkspaceDTO networkworkspaceDTO = networksDTO.getNetworkWorkspace();
		if(networkworkspaceDTO!=null){
			IWorkSpace workspace = workspaceshallowmapper.getWorkSpaceDetails(networkworkspaceDTO.getWorkspaceDTO());
			networkworkspace.setWorkspace(workspace);
			networkworkspace.setNetwork(network);
			networkworkspace.setCreatedBy(networkworkspaceDTO.getCreatedby());
			networkworkspace.setCreatedDate(networkworkspaceDTO.getCreateddate());
			networkworkspace.setUpdatedBy(networkworkspaceDTO.getUpdatedby());
			networkworkspace.setUpdatedDate(networkworkspaceDTO.getUpdateddate());
		}
		return networkworkspace;
	}
	
	@Override
	public IWorkspaceNetwork getNetworkWorkspaceByWorkSpaceDTO(WorkspaceDTO workspaceDTO,
			IWorkSpace workspace) throws QuadrigaStorageException {
		
		IWorkspaceNetwork networkworkspace = new WorkspaceNetwork();
		
		NetworkWorkspaceDTO networkworkspaceDTO =workspaceDTO.getWorkspaceNetworkDTO();
		if(networkworkspaceDTO!=null){
			INetwork network = networkmapper.getNetworkShallowDetails(networkworkspaceDTO.getNetworksDTO());
			networkworkspace.setNetwork(network);
			networkworkspace.setWorkspace(workspace);
			networkworkspace.setCreatedBy(networkworkspaceDTO.getCreatedby());
			networkworkspace.setCreatedDate(networkworkspaceDTO.getCreateddate());
			networkworkspace.setUpdatedBy(networkworkspaceDTO.getUpdatedby());
			networkworkspace.setUpdatedDate(networkworkspaceDTO.getUpdateddate());
		}
		
		return networkworkspace;
	}

}
