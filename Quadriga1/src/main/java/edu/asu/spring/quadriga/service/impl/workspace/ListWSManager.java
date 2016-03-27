package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceShallowMapper;

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
    public List<IWorkSpace> listWorkspace(String projectid, String user) throws QuadrigaStorageException {
        return workspaceShallowMapper.getWorkSpaceList(projectid, user);
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
        return workspaceShallowMapper.listWorkspaceOfCollaborator(projectid, user);
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
    public List<IWorkSpace> listActiveWorkspace(String projectid, String user) throws QuadrigaStorageException {
        return workspaceShallowMapper.listActiveWorkspacesOfOwner(projectid, user);
    }

    @Override
    @Transactional
    public List<IWorkSpace> listActiveWorkspaceByCollaborator(String projectid, String user)
            throws QuadrigaStorageException {
        return workspaceShallowMapper.listActiveWorkspaceOfCollaborator(projectid, user);
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
    public List<IWorkSpace> listArchivedWorkspace(String projectid, String user) throws QuadrigaStorageException {
        return workspaceShallowMapper.listArchivedWorkspace(projectid, user);
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
    public List<IWorkSpace> listDeactivatedWorkspace(String projectid, String user) throws QuadrigaStorageException {
        return workspaceShallowMapper.listDeactivatedWorkspace(projectid, user);
    }

}
