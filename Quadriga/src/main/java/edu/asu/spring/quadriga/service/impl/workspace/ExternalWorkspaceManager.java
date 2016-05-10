package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.IUserDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dao.workspace.IExternalWorkspaceDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectBaseMapper;
import edu.asu.spring.quadriga.mapper.workspace.IWorkspaceDeepMapper;
import edu.asu.spring.quadriga.passthroughproject.constants.Constants;
import edu.asu.spring.quadriga.service.workspace.IExternalWorkspaceManager;

/**
 * 
 * This class has all the service layer functions to modify and get external
 * workspace information.
 * 
 *
 */
@Service
public class ExternalWorkspaceManager implements IExternalWorkspaceManager {

    @Autowired
    private IExternalWorkspaceDAO externalWorkspaceDAO;

    @Autowired
    private IProjectDAO projectDao;

    @Autowired
    @Qualifier("ProjectBaseMapper")
    private IProjectBaseMapper projectMapper;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IWorkspaceDAO workspaceDao;
    
    @Autowired
    private IWorkspaceDeepMapper workspaceMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public IWorkSpace getExternalWorkspace(String externalWorkspaceId, String projectId)
            throws QuadrigaStorageException, QuadrigaAccessException {
        List<ExternalWorkspaceDTO> externalWorkspaceDTOs = externalWorkspaceDAO.getExternalWorkspace(externalWorkspaceId);
        for (ExternalWorkspaceDTO wsDto : externalWorkspaceDTOs) {
            IWorkSpace workspace = new WorkSpace();
            workspaceMapper.fillWorkspace(wsDto, workspace);
            if (workspace.getProjectWorkspace().getProject().getProjectId().equals(projectId))
                return workspace;
        }
        
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createExternalWorkspace(String externalWorkspaceId, String externalWorkspaceName, String projectId,
            IUser user) {

        ProjectDTO projectDto = projectDao.getProjectDTO(projectId);

        ExternalWorkspaceDTO workspaceDTO = new ExternalWorkspaceDTO();
        workspaceDTO.setWorkspacename(externalWorkspaceName);
        workspaceDTO.setDescription(Constants.DEFAULT_WORKSPACE_DESCRIPTION);
        QuadrigaUserDTO owner = userDAO.getDTO(user.getUserName());
        workspaceDTO.setWorkspaceowner(owner);
        workspaceDTO.setIsarchived(false);
        workspaceDTO.setIsdeactivated(false);
        workspaceDTO.setUpdatedby(user.getName());
        workspaceDTO.setUpdateddate(new Date());
        workspaceDTO.setCreatedby(user.getName());
        workspaceDTO.setCreateddate(new Date());

        String workspaceId = workspaceDao.generateUniqueID();
        workspaceDTO.setWorkspaceid(workspaceId);

        workspaceDTO.setExternalWorkspaceid(externalWorkspaceId);

        ProjectWorkspaceDTO projectWorkspaceDTO = projectMapper.getProjectWorkspace(projectDto, workspaceDTO);
        workspaceDTO.setProjectWorkspaceDTO(projectWorkspaceDTO);

        externalWorkspaceDAO.saveNewDTO(workspaceDTO);
        projectDao.updateDTO(projectDto);

        return workspaceId;

    }

}
