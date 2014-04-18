package edu.asu.spring.quadriga.service.network.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceNetwork;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dto.NetworkWorkspaceDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.mapper.IWorkspaceNetworkMapper;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceShallowMapper;

public class WorkspaceNetworkMapper implements IWorkspaceNetworkMapper{

	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	IWorkspaceShallowMapper workspaceshallowmapper;
	
	@Override
	public IWorkspaceNetwork getNetworkWorkspace(NetworksDTO networksDTO,
			INetwork network) throws QuadrigaStorageException {
		
		IWorkspaceNetwork networkworkspace = new WorkspaceNetwork();
		
		NetworkWorkspaceDTO networkworkspaceDTO = networksDTO.getNetworkWorkspaceDTO();
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

}
