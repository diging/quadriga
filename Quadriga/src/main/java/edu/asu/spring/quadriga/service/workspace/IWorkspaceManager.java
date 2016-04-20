package edu.asu.spring.quadriga.service.workspace;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;

import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceManager {

    IWorkSpace getWorkspaceDetails(String workspaceId, String username)
            throws QuadrigaStorageException, QuadrigaAccessException;

    List<IWorkspaceNetwork> getWorkspaceNetworkList(String workspaceid)
            throws QuadrigaStorageException;

    String getWorkspaceName(String workspaceId) throws QuadrigaStorageException;

    List<IWorkspaceNetwork> getWorkspaceRejectedNetworkList(String workspaceid)
            throws QuadrigaStorageException;
    
    List<IWorkspaceNetwork> getWorkspaceApprovedNetworkList(String workspaceid)
            throws QuadrigaStorageException;

    IWorkSpace getWorkspaceDetails(String workspaceId)
            throws QuadrigaStorageException, QuadrigaAccessException;

    String getItemMetadataAsJson(String fileid, String dspaceUsername,
            String dspacePassword, IDspaceKeys dspaceKeys)
            throws NoSuchAlgorithmException, QuadrigaStorageException,
            JSONException;

    String getProjectIdFromWorkspaceId(String workspaceId)
            throws QuadrigaStorageException;
    
    boolean getDeactiveStatus(String workspaceId) throws QuadrigaStorageException;

    boolean isWorkspaceArchived(String workspaceId) throws QuadrigaStorageException;
}