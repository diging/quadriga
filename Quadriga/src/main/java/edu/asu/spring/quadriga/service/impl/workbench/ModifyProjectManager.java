package edu.asu.spring.quadriga.service.impl.workbench;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.dao.workbench.IProjectCollaboratorDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectConceptCollectionDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDictionaryDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectEditorDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectWorkspaceDAO;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectDictionaryDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTO;
import edu.asu.spring.quadriga.dto.ProjectEditorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectDeepMapper;
import edu.asu.spring.quadriga.service.impl.BaseManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;

/**
 * This class add/update/delete a project
 * 
 * @author kbatna, Julia Damerow
 */
@Service
public class ModifyProjectManager extends BaseManager implements IModifyProjectManager {
    @Autowired
    private IProjectSecurityChecker projectSecurity;

    @Autowired
    private IProjectDAO projectDao;

    @Autowired
    private IProjectDeepMapper projectDTOMapper;

    @Autowired
    private IProjectWorkspaceDAO projectWorkspaceDao;

    @Autowired
    private IProjectDictionaryDAO projectDictionaryDao;

    @Autowired
    private IProjectConceptCollectionDAO projectCCDao;

    @Autowired
    private IProjectCollaboratorDAO projectCollabDao;

    @Autowired
    private IProjectEditorDAO projectEditorDao;

    @Resource(name = "projectconstants")
    private Properties messages;

    private static final Logger logger = LoggerFactory.getLogger(ModifyProjectManager.class);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addNewProject(IProject project, String userName) {
        project.setProjectId(projectDao.generateUniqueID());
        project.setCreatedBy(userName);
        project.setUpdatedBy(userName);
        ProjectDTO projectDTO = projectDTOMapper.getProjectDTO(project);
        projectDao.saveNewDTO(projectDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateProject(String projID, String projName, String projDesc, String projectAccess, String userName)
            throws QuadrigaStorageException {
        ProjectDTO projectDTO = projectDao.getProjectDTO(projID);
        projectDTO.setProjectname(projName);
        projectDTO.setDescription(projDesc);
        projectDTO.setUpdatedby(userName);
        projectDTO.setAccessibility(projectAccess);
        projectDTO.setUpdateddate(new Date());
        projectDao.updateDTO(projectDTO);
    }

    @Override
    @Transactional
    public void updateProjectURL(String projID, String unixName, String userName) throws QuadrigaStorageException {
        ProjectDTO projectDTO = projectDao.getProjectDTO(projID);
        projectDTO.setUnixname(unixName);
        projectDTO.setUpdatedby(userName);
        projectDTO.setUpdateddate(new Date());

        projectDao.updateDTO(projectDTO);
    }

    /**
     * This method deletes a project into the database.
     * 
     * @param project
     * @return String - error message blank on success and contains error on
     *         failure.
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman, Julia Damerow
     */
    @Override
    @Transactional
    public void deleteProjectRequest(List<String> projectIdList, Principal principal) throws QuadrigaStorageException {
        for (String projectId : projectIdList) {
            boolean checkAuthorization = projectSecurity.isProjectOwner(principal.getName(), projectId);
            if (checkAuthorization) {
                ProjectDTO project = projectDao.getProjectDTO(projectId);
                // retrieve all the foreign key tables associated with them
                // delete the project workspace DTO
                deleteProjectWorkspaceMapping(project);
                deleteProjectDictionaryMapping(project);
                deleteProjectConceptCollectionMapping(project);
                deleteProjectCollaboratorMapping(project);
                deleteProjectEditorMapping(project);

                projectDao.updateDTO(project);
                projectDao.deleteDTO(project);
            }
        }
    }

    /**
     * This method will provide owner editor role to the project
     * 
     * @param projectId
     * @param owner
     * @throws QuadrigaStorageException
     * @author Lohith Dwaraka
     */
    @Override
    @Transactional
    public void assignEditorRole(String projectId, String editor) throws QuadrigaStorageException {
        ProjectEditorDTOPK projectEditorKey = new ProjectEditorDTOPK(projectId, editor);
        ProjectEditorDTO projectEditor = projectEditorDao.getProjectEditorDTO(projectEditorKey);

        // if user is not editor yet, make him editor
        if (projectEditor == null) {
            ProjectDTO project = projectDao.getProjectDTO(projectId);
            projectEditor = projectDTOMapper.getProjectEditor(project, editor);

            projectEditorDao.saveNewDTO(projectEditor);
        }
    }

    /**
     * This method will remove owner editor role from the project
     * 
     * @param projectId
     * @param owner
     * @throws QuadrigaStorageException
     * @author Lohith Dwaraka
     */
    @Override
    @Transactional
    public void removeEditorRole(String projectId, String editor) throws QuadrigaStorageException {
        ProjectEditorDTOPK projectEditorKey = new ProjectEditorDTOPK(projectId, editor);
        ProjectEditorDTO projectEditor = projectEditorDao.getProjectEditorDTO(projectEditorKey);
        if (projectEditor != null) {
            projectEditorDao.deleteDTO(projectEditor);
        }
    }

    /*
     * ================================================== Private Methods
     * ==================================================
     */
    private void deleteProjectWorkspaceMapping(ProjectDTO project) {
        List<ProjectWorkspaceDTO> projectWorkspaceList = project.getProjectWorkspaceDTOList();
        if (projectWorkspaceList != null) {
            for (ProjectWorkspaceDTO projectWorkspace : projectWorkspaceList) {
                projectWorkspaceDao.deleteDTO(projectWorkspace);
            }
        }
        project.setProjectWorkspaceDTOList(null);
    }

    private void deleteProjectDictionaryMapping(ProjectDTO project) {
        List<ProjectDictionaryDTO> projectDictionaryList = project.getProjectDictionaryDTOList();
        if (projectDictionaryList != null) {
            for (ProjectDictionaryDTO projectDictionary : projectDictionaryList) {
                projectDictionaryDao.deleteDTO(projectDictionary);
                ;
            }
        }
        project.setProjectDictionaryDTOList(null);
    }

    private void deleteProjectConceptCollectionMapping(ProjectDTO project) {
        List<ProjectConceptCollectionDTO> projectConceptCollectionList = project.getProjectConceptCollectionDTOList();
        if (projectConceptCollectionList != null) {
            for (ProjectConceptCollectionDTO projectConceptCollection : projectConceptCollectionList) {
                projectCCDao.deleteDTO(projectConceptCollection);
            }
        }
        project.setProjectConceptCollectionDTOList(null);
    }

    private void deleteProjectCollaboratorMapping(ProjectDTO project) {
        List<ProjectCollaboratorDTO> projectCollaboratorList = project.getProjectCollaboratorDTOList();
        if (projectCollaboratorList != null) {
            for (ProjectCollaboratorDTO projectCollaborator : projectCollaboratorList) {
                projectCollabDao.deleteDTO(projectCollaborator);
            }
        }
        project.setProjectConceptCollectionDTOList(null);
    }

    private void deleteProjectEditorMapping(ProjectDTO project) {
        List<ProjectEditorDTO> projectEditorList = project.getProjectEditorDTOList();
        if (projectEditorList != null) {
            for (ProjectEditorDTO projectEditor : projectEditorList) {
                projectEditorDao.deleteDTO(projectEditor);
            }
        }

        project.setProjectEditorDTOList(null);
    }
}
