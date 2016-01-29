package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dao.workspace.IListExternalWsDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.ExternalWorkspaceDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ProjectDTOMapper;
import edu.asu.spring.quadriga.service.workspace.mapper.IExternalWSManager;

@Service
public class ExternalWSManager implements IExternalWSManager {

    @Autowired
    private IListExternalWsDAO externalWorkspaceDAO;
    
    @Autowired
    private IProjectDAO projectDao;

    @Autowired
    private ProjectDTOMapper projectMapper;
    
    @Autowired
    private SessionFactory sessionFactory;

    public boolean isExternalWorkspaceExists(String externalWorkspaceId)
            throws QuadrigaStorageException, QuadrigaAccessException {
        return externalWorkspaceDAO.isExternalWorkspaceExists(externalWorkspaceId);
    }

    @Override
    public void createExternalWorkspace(String externalWorkspaceId,String externalWorkspaceName, String workspaceId,String projectId,IUser user) {
        
        ProjectDTO projectDto = projectDao.getProjectDTO(projectId);

        ExternalWorkspaceDTO workspaceDTO = new ExternalWorkspaceDTO();
        workspaceDTO.setWorkspacename(externalWorkspaceName);
        workspaceDTO.setDescription("External Workspace");

        Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserDTO.findByUsername");
        query.setParameter("username", user.getUserName());
        List<QuadrigaUserDTO> quadrigaUsers = query.list();
        workspaceDTO.setWorkspaceowner(quadrigaUsers.get(0));
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
