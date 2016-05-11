package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.domain.proxy.WorkSpaceProxy;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceShallowMapper;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

/**
 * Class implements {@link IListWSManager} to display the active,archived and
 * deactivated workspace associated with project.
 * 
 * @implements IListWSManager
 * @author Kiran Kumar Batna
 */
@Service
public class ListWSManager implements IListWSManager {

    @Autowired
    private IWorkspaceShallowMapper workspaceShallowMapper;
    
    @Autowired
    private IWorkspaceDAO wsDao;
    

    /**
     * This will list all the workspaces associated with the project.
     * 
     * @param projectid
     * @return List<IWorkSpace> - list of workspaces associated with the
     *         project.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     */
    @Override
    @Transactional
    public List<IWorkSpace> listWorkspace(String projectId, String user) throws QuadrigaStorageException {
        List<WorkspaceDTO> workspaceDTOList = wsDao.listWorkspaceDTO(projectId, user);
        return mapWorkspaceDTOs(workspaceDTOList);
    }

    /**
     * This method retrieves all the workspace associated with the given having
     * the user as a collaborator.
     * 
     * @param projectid
     * @return List<IWorkSpace> - list of workspaces associated with the
     *         project.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     */
    @Override
    @Transactional
    public List<IWorkSpace> listWorkspaceOfCollaborator(String projectid, String user) throws QuadrigaStorageException {
        List<WorkspaceDTO> workspaceDTOList = wsDao.listWorkspaceDTOofCollaborator(projectid, user);
        return mapWorkspaceDTOs(workspaceDTOList);
    }

    /**
     * This will list all the active workspaces associated with the project.
     * 
     * @param projectid
     * @return List<IWorkSpace> - list of active workspaces associated with the
     *         project.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     */
    @Override
    @Transactional
    public List<IWorkSpace> listActiveWorkspace(String projectId, String user) throws QuadrigaStorageException {
        List<WorkspaceDTO> workspaceDTOList = wsDao.listActiveWorkspaceDTOofOwner(projectId, user);
        return mapWorkspaceDTOs(workspaceDTOList);
    }

    @Override
    @Transactional
    public List<IWorkSpace> listActiveWorkspaceByCollaborator(String projectId, String user)
            throws QuadrigaStorageException {
        List<WorkspaceDTO> workspaceDTOList = wsDao.listActiveWorkspaceDTOofCollaborator(projectId, user);
        return mapWorkspaceDTOs(workspaceDTOList);
    }

    /**
     * This will list all the archived workspaces associated with the project.
     * 
     * @param projectid
     * @return List<IWorkSpace> - list of archived workspaces associated with
     *         the project.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     */
    @Override
    @Transactional
    public List<IWorkSpace> listArchivedWorkspace(String projectId, String user) throws QuadrigaStorageException {
        List<WorkspaceDTO> workspaceDTOList = wsDao.listArchivedWorkspaceDTO(projectId, user);
        return mapWorkspaceDTOs(workspaceDTOList);
    }

    /**
     * This will list all the deactivated workspaces associated with the
     * project.
     * 
     * @param projectid
     * @return List<IWorkSpace> - list of archived workspaces associated with
     *         the project.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     */
    @Override
    @Transactional
    public List<IWorkSpace> listDeactivatedWorkspace(String projectId, String user) throws QuadrigaStorageException {
        List<WorkspaceDTO> workspaceDTOList = wsDao.listDeactivatedWorkspaceDTO(projectId, user);
        return mapWorkspaceDTOs(workspaceDTOList);
    }

    /**
     * Method that takes a list of workspace DTOs and then calls the {@link IWorkspaceShallowMapper}
     * to map the DTOs to a list of workspace proxies.
     * 
     * @param workspaceDTOList The list of workspace DTOs to be mapped.
     * @return A list of Workspace proxies ({@link WorkSpaceProxy}).
     * @throws QuadrigaStorageException
     */
    private List<IWorkSpace> mapWorkspaceDTOs(List<WorkspaceDTO> workspaceDTOList) throws QuadrigaStorageException {
        List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();;
        if(workspaceDTOList != null){   
            for(WorkspaceDTO workspaceDTO : workspaceDTOList){
                workspaceList.add(workspaceShallowMapper.mapWorkspaceDTO(workspaceDTO));
            }
        }
        return workspaceList;
    }
}
