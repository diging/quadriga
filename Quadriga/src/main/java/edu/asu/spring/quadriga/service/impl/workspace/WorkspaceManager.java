package edu.asu.spring.quadriga.service.impl.workspace;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IProjectWorkspaceDAO;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItemEntity;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDeepMapper;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * Class to display the active, archived and
 * deactivated workspace associated with project.
 * 
 * @implements IListWSManager
 * @author Kiran Kumar Batna, Julia Damerow
 */
@Service
public class WorkspaceManager extends BaseWSManager implements IWorkspaceManager {

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceManager.class);

    @Autowired
    private IProjectWorkspaceDAO projectWorkspaceDao;

    @Autowired
    private IDspaceManager dspaceManager;
    
    @Autowired
    private IWorkspaceDeepMapper workspaceDeepMapper;


    /**
     * This method display the workspace details for the workspace submitted.
     * 
     * @param workspaceId
     * @return IWorkSpace - workspace object
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     */
    @Override
    @Transactional
    public IWorkSpace getWorkspaceDetails(String workspaceId, String username)
            throws QuadrigaStorageException, QuadrigaAccessException {
        return workspaceDeepMapper.getWorkSpaceDetails(workspaceId, username);
    }

    /**
     * This method display the workspace details for the workspace submitted.
     * 
     * @param workspaceId
     * @return IWorkSpace - workspace object
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     */
    @Override
    @Transactional
    public IWorkSpace getWorkspaceDetails(String workspaceId) throws QuadrigaStorageException, QuadrigaAccessException {
        return workspaceDeepMapper.getWorkSpaceDetails(workspaceId);
    }

    /**
     * This method get the workspace name for the workspace id.
     * 
     * @param workspaceId
     * @return workspacename - String object
     * @throws QuadrigaStorageException
     * @author Lohith Dwaraka
     * @throws QuadrigaAccessException
     */
    @Override
    @Transactional
    public String getWorkspaceName(String workspaceId) throws QuadrigaStorageException {
        return workspaceDeepMapper.getWorkSpaceDetails(workspaceId).getWorkspaceName();
    }

    @Override
    @Transactional
    public List<IWorkspaceNetwork> getWorkspaceNetworkList(String workspaceid) throws QuadrigaStorageException {

        IWorkSpace workspace = workspaceDeepMapper.getWorkSpaceDetails(workspaceid);
        List<IWorkspaceNetwork> workspaceNetworkList = null;
        if (workspace != null) {
            workspaceNetworkList = workspace.getWorkspaceNetworks();
        }

        return workspaceNetworkList;
    }

    @Override
    @Transactional
    public List<IWorkspaceNetwork> getWorkspaceRejectedNetworkList(String workspaceid) throws QuadrigaStorageException {

        IWorkSpace workspace = workspaceDeepMapper.getWorkSpaceDetails(workspaceid);
        if (workspace == null) {
            return null;
        }
        List<IWorkspaceNetwork> workspaceNetworkList = workspace.getWorkspaceNetworks();
        if (workspaceNetworkList == null) {
            return null;
        }

        List<IWorkspaceNetwork> removeList = new ArrayList<IWorkspaceNetwork>();
        for (IWorkspaceNetwork workspaceNetwork : workspaceNetworkList) {
            INetwork network = workspaceNetwork.getNetwork();
            if (network == null || !network.getStatus().equals(INetworkStatus.REJECTED)) {
                removeList.add(workspaceNetwork);
            }
        }

        for (IWorkspaceNetwork workspaceNetwork : removeList) {
            workspaceNetworkList.remove(workspaceNetwork);
        }

        return workspaceNetworkList;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     */
    @Override
    @Transactional
    public List<IWorkspaceNetwork> getWorkspaceApprovedNetworkList(String workspaceid) throws QuadrigaStorageException {
        IWorkSpace workspace = workspaceDeepMapper.getWorkSpaceDetails(workspaceid);
        List<IWorkspaceNetwork> workspaceNetworkList = null;
        if (workspace != null) {
            workspaceNetworkList = workspace.getWorkspaceNetworks();
        }

        List<Integer> removeList = null;
        if (workspaceNetworkList != null) {
            for (int i = 0; i < workspaceNetworkList.size(); i++) {
                IWorkspaceNetwork workspaceNetwork = workspaceNetworkList.get(i);
                INetwork network = workspaceNetwork.getNetwork();
                if (network != null) {
                    if (network.getStatus().equals(INetworkStatus.APPROVED)) {
                        // do nothing
                    } else {
                        if (removeList == null) {
                            removeList = new ArrayList<Integer>();
                        }
                        removeList.add(i);
                    }
                } else {
                    if (removeList == null) {
                        removeList = new ArrayList<Integer>();
                    }
                    removeList.add(i);
                }
            }

            if(removeList!=null){
                for (Integer i : removeList) {
                    workspaceNetworkList.remove(i);
                }
            }
        }
        return workspaceNetworkList;
    }

    @Override
    public String getItemMetadataAsJson(String fileid, String dspaceUsername, String dspacePassword,
            IDspaceKeys dspaceKeys) throws NoSuchAlgorithmException, QuadrigaStorageException, JSONException {

        IDspaceMetadataItemEntity metaData = dspaceManager.getItemMetadata(fileid, dspaceUsername, dspacePassword,
                dspaceKeys);

        logger.info(metaData.getName() + metaData.getLastModifiedDate() + metaData.getSubmitter().getFullname());

        String itemData = "";
        JSONArray ja = new JSONArray();
        JSONObject ja1 = new JSONObject();
        JSONObject j = new JSONObject();

        if (metaData.getName() == null) {
            j.put("filename", "");
        } else {
            j.put("filename", metaData.getName());
        }

        if (metaData.getSubmitter().getFullname() == null) {
            j.put("submitter", "");
        } else {
            j.put("submitter", metaData.getSubmitter().getFullname());
        }

        if (metaData.getLastModifiedDate() == null) {
            j.put("modifieddate", "");
        } else {
            j.put("modifieddate", metaData.getLastModifiedDate());
        }

        ja.put(j);

        ja1.put("text", ja);

        itemData = ja1.toString();

        return itemData;

    }

    @Override
    public String getProjectIdFromWorkspaceId(String workspaceId) throws QuadrigaStorageException {
        return projectWorkspaceDao.getCorrespondingProjectID(workspaceId);
    }

    @Transactional
    public boolean getDeactiveStatus(String workspaceId) throws QuadrigaStorageException {
        WorkspaceDTO wsDto = workspaceDao.getWorkspaceDTO(workspaceId.trim());
        if (wsDto != null)
            return wsDto.getIsdeactivated();
        return false;
    }

    @Override
    @Transactional
    public boolean isWorkspaceArchived(String workspaceId) throws QuadrigaStorageException {
        WorkspaceDTO wsDTO = workspaceDao.getWorkspaceDTO(workspaceId.trim());
        return wsDTO != null && wsDTO.getIsarchived();
    }
}
