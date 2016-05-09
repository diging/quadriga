package edu.asu.spring.quadriga.service.impl.passthroughproject;

import java.util.Properties;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.dao.workbench.passthroughproject.IPassThroughProjectDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factory.passthroughproject.IPassThroughProjectFactory;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProject;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.XMLInfo;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.PassThroughProjectDTOMapper;
import edu.asu.spring.quadriga.service.impl.BaseManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;
import edu.asu.spring.quadriga.service.workspace.IExternalWorkspaceManager;

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
    private IExternalWorkspaceManager externalWSManager;

    @Autowired
    private IPassThroughProjectDAO projectDao;

    @Autowired
    private IProjectSecurityChecker projectSecurityChecker;
    
    @Autowired
    private IPassThroughProjectFactory projectFactory;


    @Autowired
    @Qualifier("passThroughProjectDTOMapper")
    private PassThroughProjectDTOMapper projectMapper;

    @Resource(name = "projectconstants")
    private Properties messages;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public String addPassThroughProject(IUser user, IPassThroughProject project) throws QuadrigaStorageException {
        String projectId = projectDao.generateUniqueID();
        project.setCreatedBy(user.getUserName());
        project.setUpdatedBy(user.getUserName());
        PassThroughProjectDTO projectDTO = projectMapper.getPassThroughProjectDTO(project, user);

        projectDTO.setProjectid(projectId);
        projectDao.saveNewDTO(projectDTO);

        return projectId;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public String getInternalProjectId(String externalProjectid, String client)
            throws QuadrigaStorageException, NoSuchRoleException {

        PassThroughProjectDTO projectDTO = projectDao.getExternalProject(externalProjectid, client);

        if (projectDTO != null)
            return projectDTO.getId();
        
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public String createWorkspaceForExternalProject(XMLInfo passThroughProjectInfo, String projectId,
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
     * This method will create a {@link PassThroughProject} instance from the
     * provided {@link XMLInfo}.
     * 
     * @param passThroughProjectInfo
     *            The {@link XMLInfo} object.
     * @return The {@link PassThroughProject} object.
     */
    @Override
    public IPassThroughProject getPassThroughProject(XMLInfo passThroughProjectInfo) {
        IPassThroughProject project = projectFactory.createPassThroughProjectObject();
        project.setExternalProjectid(passThroughProjectInfo.getProjectId());
        project.setExternalUserName(passThroughProjectInfo.getExternalUserName());
        project.setExternalUserId(passThroughProjectInfo.getExternalUserId());
        project.setProjectName(passThroughProjectInfo.getName());
        project.setDescription(passThroughProjectInfo.getDescription());
        project.setClient(passThroughProjectInfo.getSender());
        project.setProjectAccess(EProjectAccessibility.PUBLIC);
        // Since we are not passing unix name in REST request, we are creating
        // the unix name out of the project name
        project.setUnixName(passThroughProjectInfo.getName().replace(" ", "-"));
        return project;
    }

}
