package edu.asu.spring.quadriga.mapper.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.domain.impl.workbench.ProjectWorkspace;
import edu.asu.spring.quadriga.domain.proxy.WorkSpaceProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDeepMapper;
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
    private IWorkspaceDeepMapper workspaceDeepMapper;

    @Autowired
    private IUserDeepMapper userDeepManager;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public IWorkSpace mapWorkspaceDTO(WorkspaceDTO workspaceDTO) throws QuadrigaStorageException {
        IWorkSpace workspaceProxy = null;

        if (workspaceDTO != null) {
            workspaceProxy = createWorkspaceProxy(workspaceDTO);
        }

        return workspaceProxy;
    }

    @Override
    public List<IProjectWorkspace> getProjectWorkspaceList(IProject project, List<ProjectWorkspaceDTO> projectWorkspaceDTOList) throws QuadrigaStorageException {
        List<IProjectWorkspace> projectWorkspaceList = null;
        if (project != null) {
            if (projectWorkspaceDTOList != null) {
                projectWorkspaceList = new ArrayList<IProjectWorkspace>();
                
                for (ProjectWorkspaceDTO projectWorkspaceDTO : projectWorkspaceDTOList) {
                    IWorkSpace workspaceProxy = createWorkspaceProxy(projectWorkspaceDTO.getWorkspaceDTO());
                    IProjectWorkspace projectWorkspace = createProjectWorkspace(project, projectWorkspaceDTO, workspaceProxy);
                    
                    projectWorkspaceList.add(projectWorkspace);
                }

            }
        }

        return projectWorkspaceList;
    }

    /**
     * Method to create a new {@link ProjectWorkspace} object from a {@link ProjectWorkspaceDTO}.
     * @param project
     * @param projectWorkspaceDTO
     * @param workspaceProxy
     * @return
     */
    private IProjectWorkspace createProjectWorkspace(IProject project, ProjectWorkspaceDTO projectWorkspaceDTO,
            IWorkSpace workspaceProxy) {
        IProjectWorkspace projectWorkspace = new ProjectWorkspace();
        projectWorkspace.setProject(project);
        projectWorkspace.setWorkspace(workspaceProxy);
        projectWorkspace.setCreatedBy(projectWorkspaceDTO.getCreatedby());
        projectWorkspace.setCreatedDate(projectWorkspaceDTO.getCreateddate());
        projectWorkspace.setUpdatedBy(projectWorkspaceDTO.getUpdatedby());
        projectWorkspace.setUpdatedDate(projectWorkspaceDTO.getUpdateddate());
        return projectWorkspace;
    }

    /**
     * Method to create a new {@link WorkSpaceProxy} from a {@link WorkspaceDTO}.
     * 
     * @param workspaceDTO
     * @return
     * @throws QuadrigaStorageException
     */
    protected IWorkSpace createWorkspaceProxy(WorkspaceDTO workspaceDTO) throws QuadrigaStorageException {
        IWorkSpace workspaceProxy = new WorkSpaceProxy(wsManager);
        workspaceProxy.setWorkspaceId(workspaceDTO.getWorkspaceid());
        workspaceProxy.setWorkspaceName(workspaceDTO.getWorkspacename());
        workspaceProxy.setDescription(workspaceDTO.getDescription());
        workspaceProxy.setOwner(userDeepManager.getUser(workspaceDTO.getWorkspaceowner().getUsername()));
        workspaceProxy.setCreatedBy(workspaceDTO.getCreatedby());
        workspaceProxy.setCreatedDate(workspaceDTO.getCreateddate());
        workspaceProxy.setUpdatedBy(workspaceDTO.getUpdatedby());
        workspaceProxy.setUpdatedDate(workspaceDTO.getUpdateddate());
        workspaceProxy.setProjectWorkspace(getProjectWorkspaceOfWorkspace(workspaceProxy,
                workspaceDTO));
        return workspaceProxy;
    }
}
