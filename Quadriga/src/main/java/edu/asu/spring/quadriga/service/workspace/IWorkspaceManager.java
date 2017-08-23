package edu.asu.spring.quadriga.service.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceManager {

    IWorkspace getWorkspaceDetails(String workspaceId, String username)
            throws QuadrigaStorageException, QuadrigaAccessException;

    List<INetwork> getNetworks(String workspaceid)
            throws QuadrigaStorageException;

    String getWorkspaceName(String workspaceId) throws QuadrigaStorageException;

    List<INetwork> getWorkspaceRejectedNetworkList(String workspaceid)
            throws QuadrigaStorageException;
    
    List<INetwork> getWorkspaceApprovedNetworkList(String workspaceid)
            throws QuadrigaStorageException;

    IWorkspace getWorkspaceDetails(String workspaceId)
            throws QuadrigaStorageException, QuadrigaAccessException;

    String getProjectIdFromWorkspaceId(String workspaceId)
            throws QuadrigaStorageException;
    
    boolean getDeactiveStatus(String workspaceId) throws QuadrigaStorageException;

    boolean isWorkspaceArchived(String workspaceId) throws QuadrigaStorageException;
}