package edu.asu.spring.quadriga.service.workspace.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceEditorDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceEditorDTO;
import edu.asu.spring.quadriga.email.IEmailNotificationManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.WorkspaceCollaboratorDTOMapper;
import edu.asu.spring.quadriga.mapper.WorkspaceDTOMapper;
import edu.asu.spring.quadriga.mapper.workbench.IProjectBaseMapper;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;

/**
 * Class implements {@link IModifyWSManager} to add/update/delete workspace
 * associated with project.
 * 
 * @implements IListWSManager
 * @author Kiran Kumar Batna
 */
@Service
@Transactional(rollbackFor = { Exception.class })
public class ModifyWSManager implements IModifyWSManager {
    @Autowired
    private IEmailNotificationManager emailManager;

    @Autowired
    private IWorkspaceDAO workspaceDao;

    @Autowired
    private IProjectDAO projectDao;

    @Autowired
    private IWorkspaceEditorDAO workspaceEditorDao;

    @Autowired
    private WorkspaceDTOMapper workspaceDTOMapper;

    @Autowired
    private WorkspaceCollaboratorDTOMapper collaboratorMapper;
    
    @Autowired
    private IUserManager userManager;

    @Autowired
    @Qualifier("ProjectBaseMapper")
    private IProjectBaseMapper projectMapper;

    /**
     * This inserts a workspace for a project into database.
     * 
     * @param workspace
     * @param projectId
     * @return String errmsg - blank on success and error message on failure
     * @throws QuadrigaStorageException
     * @author Julia Damerow, kiranbatna
     */
    @Override
    public void addWorkspaceToProject(IWorkSpace workspace, String projectId, String username) throws QuadrigaStorageException {
        IUser user = userManager.getUser(username);
        workspace.setOwner(user);
        
        ProjectDTO projectDto = projectDao.getProjectDTO(projectId);
        WorkspaceDTO workspaceDTO = workspaceDTOMapper.getWorkspaceDTO(workspace);
        workspaceDTO.setWorkspaceid(workspaceDao.generateUniqueID());

        ProjectWorkspaceDTO projectWorkspaceDTO = projectMapper.getProjectWorkspace(projectDto, workspaceDTO);
        workspaceDTO.setProjectWorkspaceDTO(projectWorkspaceDTO);
        workspaceDao.saveNewDTO(workspaceDTO);
        projectDao.updateDTO(projectDto);
    }

    /**
     * This method deletes the requested workspace.
     * 
     * @param workspaceIdList
     *            - comma separated list of workspace ids to delete
     * @return boolean - return true if delete was successful, otherwise false
     * @author Julia Damerow, kiranbatna
     */
    @Override
    public boolean deleteWorkspace(String wsId) {
        return workspaceDao.deleteWorkspace(wsId);
    }

    /**
     * This method updates the workspace
     * 
     * @param workspace
     * @return String - errmsg blank on success and error message on failure
     * @throws QuadrigaStorageException
     * @author Julia Damerow, kiranbatna
     */
    @Override
    @Transactional
    public void updateWorkspace(IWorkSpace workspace) throws QuadrigaStorageException {
        WorkspaceDTO workspaceDTO = workspaceDao.getDTO(workspace.getWorkspaceId());
        workspaceDTO.setWorkspacename(workspace.getWorkspaceName());
        workspaceDTO.setDescription(workspace.getDescription());
        workspaceDTO.setUpdateddate(new Date());
        workspaceDTO.setUpdatedby(workspace.getOwner().getName());

        workspaceDao.updateDTO(workspaceDTO);
    }

    /**
     * Method to assign the editor role to a user.
     * 
     * @param workspaceId
     * @param userName
     * @return
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void assignEditorRole(String workspaceId, String userName) throws QuadrigaStorageException {
        WorkspaceDTO workspaceDto = workspaceDao.getDTO(workspaceId);
        WorkspaceEditorDTO workspaceEditorDTO = workspaceDTOMapper.getWorkspaceEditor(workspaceDto, userName);
        workspaceEditorDao.saveNewDTO(workspaceEditorDTO);
    }

    /**
     * Manager for Assigning editor roles to owner for workspace level
     * 
     * @param workspaceId
     * @param userName
     *            username of the user that should be deleted as editor
     * @return true if editor role was removed, otherwise false
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public boolean deleteEditorRole(String workspaceId, String userName) {
        WorkspaceEditorDTO workspaceEditorDTO = workspaceEditorDao.getWorkspaceEditorDTO(workspaceId, userName);
        if (workspaceEditorDTO != null) {
            workspaceEditorDao.deleteWorkspaceEditorDTO(workspaceEditorDTO);
            return true;
        } else {
            return false;
        }
    }

}
