package edu.asu.spring.quadriga.mapper.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.domain.proxy.WorkSpaceProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceShallowMapper;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;

/**
 * This class maps {@link WorkspaceDTO}s to {@link WorkSpaceProxy}.
 * 
 * @author Julia Damerow
 *
 */
@Service
public class WorkspaceShallowMapper extends BaseWorkspaceMapper implements IWorkspaceShallowMapper {

    @Autowired
    private IWorkspaceManager wsManager;

    @Autowired
    private IUserDeepMapper userDeepManager; 

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public IWorkspace mapWorkspaceDTO(WorkspaceDTO workspaceDTO) throws QuadrigaStorageException {
        IWorkspace workspaceProxy = null;

        if (workspaceDTO != null) {
            workspaceProxy = createWorkspaceProxy(workspaceDTO);
        }

        return workspaceProxy;
    }

    @Override
    public List<IWorkspace> getProjectWorkspaceList(IProject project, List<ProjectWorkspaceDTO> projectWorkspaceDTOList)
            throws QuadrigaStorageException {
        List<IWorkspace> workspaces = new ArrayList<IWorkspace>();
        if (project != null) {
            if (projectWorkspaceDTOList != null) {
                for (ProjectWorkspaceDTO projectWorkspaceDTO : projectWorkspaceDTOList) {
                    IWorkspace workspaceProxy = createWorkspaceProxy(projectWorkspaceDTO.getWorkspaceDTO());
                    workspaces.add(workspaceProxy);
                }
            }
        }

        return workspaces;
    }

    /**
     * Method to create a new {@link WorkSpaceProxy} from a {@link WorkspaceDTO}
     * .
     * 
     * @param workspaceDTO
     * @return
     * @throws QuadrigaStorageException
     */
    protected IWorkspace createWorkspaceProxy(WorkspaceDTO workspaceDTO) throws QuadrigaStorageException {
        IWorkspace workspaceProxy = new WorkSpaceProxy(wsManager);
        workspaceProxy.setWorkspaceId(workspaceDTO.getWorkspaceid());
        workspaceProxy.setWorkspaceName(workspaceDTO.getWorkspacename());
        workspaceProxy.setDescription(workspaceDTO.getDescription());
        workspaceProxy.setOwner(userDeepManager.getUser(workspaceDTO.getWorkspaceowner().getUsername()));
        workspaceProxy.setCreatedBy(workspaceDTO.getCreatedby());
        workspaceProxy.setCreatedDate(workspaceDTO.getCreateddate());
        workspaceProxy.setUpdatedBy(workspaceDTO.getUpdatedby());
        workspaceProxy.setUpdatedDate(workspaceDTO.getUpdateddate());
        workspaceProxy.setProject(getProjectWorkspaceOfWorkspace(workspaceProxy, workspaceDTO));
        return workspaceProxy;
    }
}
