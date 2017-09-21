package edu.asu.spring.quadriga.service.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IProjectWorkspaceDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDeepMapper;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * Class to display the active, archived and deactivated workspace associated
 * with project.
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
    private IWorkspaceDeepMapper workspaceDeepMapper;

    @Autowired
    private IWorkspaceDAO wsDao;

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
    public IWorkspace getWorkspaceDetails(String workspaceId, String username)
            throws QuadrigaStorageException, QuadrigaAccessException {
        WorkspaceDTO wsDto = wsDao.getDTO(workspaceId);
        return workspaceDeepMapper.mapWorkspaceDTO(wsDto);
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
    public IWorkspace getWorkspaceDetails(String workspaceId) throws QuadrigaStorageException, QuadrigaAccessException {
        WorkspaceDTO wsDto = wsDao.getDTO(workspaceId);
        return workspaceDeepMapper.mapWorkspaceDTO(wsDto);
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
        WorkspaceDTO wsDto = wsDao.getDTO(workspaceId);
        return workspaceDeepMapper.mapWorkspaceDTO(wsDto).getWorkspaceName();
    }

    @Override
    @Transactional
    public List<INetwork> getNetworks(String workspaceId) throws QuadrigaStorageException {
        WorkspaceDTO wsDto = wsDao.getDTO(workspaceId);
        IWorkspace workspace = workspaceDeepMapper.mapWorkspaceDTO(wsDto);
        List<INetwork> networks = null;
        if (workspace != null) {
            networks = workspace.getNetworks();
        }

        return networks;
    }

    @Override
    @Transactional
    public List<INetwork> getWorkspaceRejectedNetworkList(String workspaceId) throws QuadrigaStorageException {
        WorkspaceDTO wsDto = wsDao.getDTO(workspaceId);
        IWorkspace workspace = workspaceDeepMapper.mapWorkspaceDTO(wsDto);

        if (workspace == null) {
            return null;
        }
        List<INetwork> networks = workspace.getNetworks();
        if (networks == null) {
            return null;
        }

        List<INetwork> removeList = new ArrayList<INetwork>();
        for (INetwork network : networks) {
            if (network == null || !network.getStatus().equals(INetworkStatus.REJECTED)) {
                removeList.add(network);
            }
        }

        for (INetwork network : removeList) {
            networks.remove(network);
        }

        return networks;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     */
    @Override
    @Transactional
    public List<INetwork> getWorkspaceApprovedNetworkList(String workspaceId) throws QuadrigaStorageException {

        WorkspaceDTO wsDto = wsDao.getDTO(workspaceId);
        IWorkspace workspace = workspaceDeepMapper.mapWorkspaceDTO(wsDto);

        List<INetwork> networks = null;
        if (workspace != null) {
            networks = workspace.getNetworks();
        }

        List<INetwork> removeList = new ArrayList<>();
        if (networks != null) {
            for (INetwork network : networks) {
                if (network != null && !network.getStatus().equals(INetworkStatus.APPROVED)) {
                    removeList.add(network);
                }
            }

            for (INetwork network : removeList) {
                networks.remove(network);
            }
        }
        return networks;
    }

    @Override
    public String getProjectIdFromWorkspaceId(String workspaceId) throws QuadrigaStorageException {
        return projectWorkspaceDao.getCorrespondingProjectID(workspaceId);
    }

    @Transactional
    public boolean getDeactiveStatus(String workspaceId) throws QuadrigaStorageException {
        WorkspaceDTO wsDto = workspaceDao.getDTO(workspaceId.trim());
        if (wsDto != null)
            return wsDto.getIsdeactivated();
        return false;
    }

    @Override
    @Transactional
    public boolean isWorkspaceArchived(String workspaceId) throws QuadrigaStorageException {
        WorkspaceDTO wsDTO = workspaceDao.getDTO(workspaceId.trim());
        return wsDTO != null && wsDTO.getIsarchived();
    }
}
