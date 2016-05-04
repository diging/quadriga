package edu.asu.spring.quadriga.service.impl.passthroughproject;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.dao.IUserDAO;
import edu.asu.spring.quadriga.dao.workbench.passthroughproject.IPassThroughProjectDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factory.passthroughproject.IPassThroughProjectFactory;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProjectInfo;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.PassThroughProjectDTOMapper;
import edu.asu.spring.quadriga.service.impl.BaseManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;
import edu.asu.spring.quadriga.service.workspace.IExternalWSManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * 
 * This class has all the Pass through project service layer functions. It
 * includes the handling DB and controller services.
 * 
 *
 */
@Service
public class PassThroughProjectManager extends BaseManager implements IPassThroughProjectManager {

    @Autowired
    private IExternalWSManager externalWSManager;

    @Autowired
    private IPassThroughProjectDAO projectDao;

    @Autowired
    private IProjectSecurityChecker projectSecurityChecker;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private PassThroughProjectDTOMapper projectMapper;

    @Autowired
    private IPassThroughProjectFactory passthrprojfactory;

    @Resource(name = "projectconstants")
    private Properties messages;

    private String addPassThroughProject(IUser user, IPassThroughProject project) throws QuadrigaStorageException {
        QuadrigaUserDTO owner = userDAO.getDTO(user.getUserName());
        PassThroughProjectDTO projectDTO = projectMapper.getPassThroughProjectDTO(project, user);
        projectDTO.setProjectowner(owner);

        projectDao.saveNewDTO(projectDTO);

        return projectDTO.getProjectid();
    }

    private String getInternalProjectId(String externalProjectid, String userid)
            throws QuadrigaStorageException, NoSuchRoleException {

        List<PassThroughProjectDTO> projectDTOs = projectDao.getExternalProjects(externalProjectid);

        for (PassThroughProjectDTO projectDTO : projectDTOs) {
            boolean isOwner = projectSecurityChecker.isProjectOwner(userid, projectDTO.getProjectid());
            if (isOwner) {
                return projectDTO.getProjectid();
            }
            boolean isCollaborator = projectSecurityChecker.isCollaborator(userid,
                    RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN, projectDTO.getProjectid());
            if (isCollaborator) {
                return projectDTO.getProjectid();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public String createWorkspaceForExternalProject(PassThroughProjectInfo passThroughProjectInfo, String projectId,
            IUser user) throws JAXBException, QuadrigaStorageException, QuadrigaAccessException {

        String externalWorkspaceId = passThroughProjectInfo.getExternalWorkspaceId();
        String externalWorkspaceName = passThroughProjectInfo.getWorkspaceName();

        boolean isExternalWorkspaceExists = externalWSManager.isExternalWorkspaceExists(externalWorkspaceId);
        String workspaceId = null;
        if (!isExternalWorkspaceExists) {
            // External workspace does not exists so insert the values into
            // externalWorkspace table
            // Create a new externalWorkspaceId and InternalWorkspaceId and then
            // call storeNetworkDetails
            workspaceId = externalWSManager.createExternalWorkspace(externalWorkspaceId, externalWorkspaceName,
                    projectId, user);
        } else {
            // Get the workspace Id related to the external workspace Id
            workspaceId = externalWSManager.getInternalWorkspaceId(externalWorkspaceId);
        }

        // After creating the values save the values by calling store network
        // details. If already available. Call network details for updation

        return workspaceId;

    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public String savePassThroughProject(IUser user, IPassThroughProject project)
            throws QuadrigaStorageException, NoSuchRoleException {

        String internalProjetid = getInternalProjectId(project.getExternalProjectid(), user.getUserName());

        if (StringUtils.isEmpty(internalProjetid)) {
            return addPassThroughProject(user, project);
        }
        return internalProjetid;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public IPassThroughProject getPassThroughProject(PassThroughProjectInfo passThroughProjectInfo) {
        String projectId = projectDao.generateUniqueID();
        IPassThroughProject project = passthrprojfactory.createPassThroughProjectObject();
        project.setExternalProjectid(passThroughProjectInfo.getExternalProjectId());
        project.setExternalUserName(passThroughProjectInfo.getExternalUserName());
        project.setExternalUserId(passThroughProjectInfo.getExternalUserId());
        project.setProjectName(passThroughProjectInfo.getName());
        project.setDescription(passThroughProjectInfo.getDescription());
        project.setClient(passThroughProjectInfo.getSender());
        project.setProjectAccess(EProjectAccessibility.PUBLIC);
        project.setProjectId(projectId);
        return project;
    }

}
