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
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IPassThroughProjectMapper;
import edu.asu.spring.quadriga.service.impl.BaseManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IExternalWorkspaceManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;

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
    private IWorkspaceManager workspaceManager;

    @Autowired
    private IPassThroughProjectDAO projectDao;

    @Autowired
    private IProjectSecurityChecker projectSecurityChecker;
    
    @Autowired
    private IPassThroughProjectFactory projectFactory;

    @Autowired
    @Qualifier("passThroughProjectDTOMapper")
    private IPassThroughProjectMapper projectMapper;
    
    @Autowired
    private IRetrieveProjectManager projectManager;

    @Resource(name = "projectconstants")
    private Properties messages;
    
    /**
     * This method tries to retrieve the project with the given id. If there doesn't exist a 
     * project with the id, it tries to find a project with the provided id as external id.
     * If that doesn't return a project either, it will create a new project that has the 
     * provided id as external id.
     * 
     * @param projectInfo
     * @param user
     * @return
     * @throws QuadrigaStorageException
     * @throws NoSuchRoleException
     */
    @Override
    public IProject retrieveOrCreateProject(XMLInfo projectInfo, IUser user) throws QuadrigaStorageException, NoSuchRoleException {
        IProject project = projectManager.getProjectDetails(projectInfo.getProjectId());
        
        // if the project id is not an internal id there is no project
        if (project == null) {
            
            String projectId = getInternalProjectId(projectInfo.getProjectId(), projectInfo.getSender());
            if (projectId != null) {
                /*
                 * if there exists a  project for the given external project id
                 * find project
                 */
                project = projectManager.getProjectDetails(projectId);  
            } else {
                /*
                 * Otherwise create a new project with the info
                 */
                project = getPassThroughProject(projectInfo);
                addPassThroughProject(user, project);
            }
            
        }
        
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public String addPassThroughProject(IUser user, IProject project) throws QuadrigaStorageException {
        String projectId = projectDao.generateUniqueID();
        project.setCreatedBy(user.getUserName());
        project.setUpdatedBy(user.getUserName());
        project.setProjectId(projectId);
        PassThroughProjectDTO projectDTO = (PassThroughProjectDTO) projectMapper.getProjectDTO(project);

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
    
    @Override
    @Transactional
    public IProject getPassthroughProject(String externalProjectId, String client) throws QuadrigaStorageException {
        PassThroughProjectDTO projectDTO = projectDao.getExternalProject(externalProjectId, client);
        return projectMapper.getProject(projectDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public String retrieveOrCreateWorkspace(XMLInfo passThroughProjectInfo, String projectId,
            IUser user) throws JAXBException, QuadrigaStorageException, QuadrigaAccessException {

        String workspaceId = passThroughProjectInfo.getExternalWorkspaceId();
        String externalWorkspaceName = passThroughProjectInfo.getWorkspaceName();
        
        IWorkSpace workspace = workspaceManager.getWorkspaceDetails(workspaceId);
        // is there a workspace in the project with that id?
        if (workspace != null && !workspace.getProjectWorkspace().getProject().getProjectId().equals(projectId)) {
            workspace = null;
        }
        
        if (workspace == null) {
            workspace = externalWSManager.getExternalWorkspace(workspaceId, projectId);
        }
        
        // is there a workspace with the given id as external ws id?
        if (workspace != null && !workspace.getProjectWorkspace().getProject().getProjectId().equals(projectId)) {
            workspace = null;
        }

        // if not we have to create a new one
        if (workspace == null) {
            // External workspace does not exists so insert the values into
            // externalWorkspace table
            // Create a new externalWorkspaceId and InternalWorkspaceId and then
            // call storeNetworkDetails
            workspaceId = externalWSManager.createExternalWorkspace(workspaceId, externalWorkspaceName,
                    projectId, user);
        } else {
            // Get the workspace Id related to the external workspace Id
            workspaceId = workspace.getWorkspaceId();
        }

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
        // the unix name out of the external id
        project.setUnixName(passThroughProjectInfo.getProjectId().replace(" ", "-"));
        return project;
    }

}
