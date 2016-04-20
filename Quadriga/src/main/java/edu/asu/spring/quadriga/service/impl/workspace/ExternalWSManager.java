package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.IUserDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dao.workspace.IExternalWsDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;
import edu.asu.spring.quadriga.service.workspace.IExternalWSManager;

@Service
public class ExternalWSManager implements IExternalWSManager {

    @Autowired
    private IExternalWsDAO externalWorkspaceDAO;

    @Autowired
    private IProjectDAO projectDao;

    @Autowired
    private ProjectDTOMapper projectMapper;

    @Autowired
    private IUserDAO userDAO;

    @Override
    public boolean isExternalWorkspaceExists(String externalWorkspaceId)
            throws QuadrigaStorageException, QuadrigaAccessException {
        ExternalWorkspaceDTO externalWorkspaceDTO = externalWorkspaceDAO.getExternalWorkspace(externalWorkspaceId);
        if (externalWorkspaceDTO != null) {
            return true;
        }
        return false;
    }

    @Override
    public void createExternalWorkspace(String externalWorkspaceId, String externalWorkspaceName, String workspaceId,
            String projectId, IUser user) {

        ProjectDTO projectDto = projectDao.getProjectDTO(projectId);

        ExternalWorkspaceDTO workspaceDTO = new ExternalWorkspaceDTO();
        workspaceDTO.setWorkspacename(externalWorkspaceName);
        workspaceDTO.setDescription("External Workspace");
        QuadrigaUserDTO owner = userDAO.getDTO(user.getUserName());
        workspaceDTO.setWorkspaceowner(owner);
        workspaceDTO.setIsarchived(false);
        workspaceDTO.setIsdeactivated(false);
        workspaceDTO.setUpdatedby(user.getName());
        workspaceDTO.setUpdateddate(new Date());
        workspaceDTO.setCreatedby(user.getName());
        workspaceDTO.setCreateddate(new Date());

        workspaceDTO.setWorkspaceid(workspaceId);

        workspaceDTO.setExternalWorkspaceid(externalWorkspaceId);

        ProjectWorkspaceDTO projectWorkspaceDTO = projectMapper.getProjectWorkspace(projectDto, workspaceDTO);
        workspaceDTO.setProjectWorkspaceDTO(projectWorkspaceDTO);

        externalWorkspaceDAO.saveNewDTO(workspaceDTO);
        projectDao.updateDTO(projectDto);

    }

    @Override
    public String getInternalWorkspaceId(String externalWorkspaceId) {
        return externalWorkspaceDAO.getInternalWorkspaceId(externalWorkspaceId);
    }

}
