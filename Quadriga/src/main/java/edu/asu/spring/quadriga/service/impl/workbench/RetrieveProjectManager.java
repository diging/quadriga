package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.aspects.PubicAccessAspect;
import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.proxy.ProjectProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDeepMapper;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectShallowMapper;

@Service
public class RetrieveProjectManager implements IRetrieveProjectManager {
    
    private static final Logger logger = LoggerFactory.getLogger(RetrieveProjectManager.class);
    
    @Autowired
    private IRetrieveProjectDAO projectDao;

    @Autowired
    private IProjectShallowMapper projectShallowMapper;

    @Autowired
    private IProjectDeepMapper projectDeepMapper;

    @Autowired
    private IProjectSecurityChecker projectSecurity;

    @Autowired
    private IProjectCollaboratorManager projectManager;

    @Autowired
    private Environment env;
    
    /**
     * This method returns the list of projects associated with the logged in
     * user. It uses the Project shallow mapper to give a {@link List} of
     * {@link IProject} of domain type {@link ProjectProxy}.
     * 
     * @param sUserName
     * @return List - list of projects.
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    @Transactional
    public List<IProject> getProjectList(String sUserName)
            throws QuadrigaStorageException {
        List<IProject> projectList;
        projectList = projectShallowMapper.getProjectList(sUserName);
        return projectList;
    }

    /**
     * This method retrieves the list of projects associated with the logged in
     * user as a collaborator. It uses the Project shallow mapper to give a
     * {@link List} of {@link IProject} of domain type {@link ProjectProxy}.
     * 
     * @param sUserName
     *            - logged in user name.
     * @return List<IProject> - list of projects associated with the logged in
     *         user as collaborator.
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IProject> getCollaboratorProjectList(String sUserName)
            throws QuadrigaStorageException {
        List<IProject> projectList;
        projectList = projectShallowMapper
                .getCollaboratorProjectListOfUser(sUserName);
        return projectList;
    }

    /**
     * This method retrieves the list of projects associated with the logged in
     * user as a owner of associated workspaces. It uses the Project shallow
     * mapper to give a {@link List} of {@link IProject} of domain type
     * {@link ProjectProxy}.
     * 
     * @param sUserName
     *            - logged in user name.
     * @return List<IProject> list of projects associated with the logged in
     *         user as one of its workspaces.
     * @throws QuadrigaStorageException
     * 
     */
    @Override
    @Transactional
    public List<IProject> getProjectListAsWorkspaceOwner(String sUserName)
            throws QuadrigaStorageException {
        List<IProject> projectList;
        projectList = projectShallowMapper
                .getProjectListAsWorkspaceOwner(sUserName);
        return projectList;
    }

    /**
     * This method retrieves the list of projects associated with the logged in
     * user as a collaborator of associated workspaces.
     * 
     * @param sUserName
     *            - logged in user name.
     * @return List<IProject> list of projects associated with the logged in
     *         user as one of its workspaces.
     * @throws QuadrigaStorageException
     * 
     */
    @Override
    @Transactional
    public List<IProject> getProjectListAsWorkspaceCollaborator(String sUserName)
            throws QuadrigaStorageException {
        List<IProject> projectList;
        projectList = projectShallowMapper
                .getProjectListAsWorkspaceCollaborator(sUserName);
        return projectList;
    }

    /**
     * This method retrieves the list of projects associated with the logged in
     * user as a collaborator of associated workspaces.
     * 
     * @param sUserName
     *            - logged in user name.
     * @return List<IProject> list of projects associated with the logged in
     *         user as one of its workspaces.
     * @throws QuadrigaStorageException
     * 
     */
    @Override
    @Transactional
    public List<IProject> getProjectListByCollaboratorRole(String sUserName,
            String role) throws QuadrigaStorageException {
        List<IProject> projectList;
        projectList = projectShallowMapper.getProjectListByCollaboratorRole(
                sUserName, role);
        return projectList;
    }

    /**
     * This method returns the project details for the supplied project.
     * 
     * @param projectId
     * @return Iproject
     * @throws QuadrigaStorageException
     * @author rohit pendbhaje
     */
    @Override
    @Transactional
    public IProject getProjectDetails(String projectId)
            throws QuadrigaStorageException {
        projectDao.getProjectDTO(projectId);
        
        return projectDeepMapper.getProjectDetails(projectId);
    }

    /**
     * This method retrieves the collaborators associated with the project
     * 
     * @param projectId
     *            - project id.
     * @return List<IProjectCollaborator> list of project collaborators
     *         associated with the project.
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IProjectCollaborator> getCollaboratingUsers(String projectId)
            throws QuadrigaStorageException {
        List<IProjectCollaborator> projectCollaboratingUsersList = projectDeepMapper
                .getCollaboratorsOfProject(projectId);
        return projectCollaboratingUsersList;
    }

    /**
     * Retrieves the project details by its unix name
     * 
     * @param unixName
     *            - unix name associated with the project.
     * @return IProject - project object containing the details.
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public IProject getProjectDetailsByUnixName(String unixName)
            throws QuadrigaStorageException {
        return projectDeepMapper.getProjectDetailsByUnixName(unixName);

    }

    @Override
    @Transactional
    public boolean getPublicProjectWebsiteAccessibility(String unixName)
            throws QuadrigaStorageException {
        IProject project = projectDeepMapper
                .getProjectDetailsByUnixName(unixName);
        String access = project.getProjectAccess().toString();
        if (access.equals("PUBLIC")) {
            return true;
        }
        return false;
    }

    /**
     * This method returns true if the user is either the project owner, or if
     * they are a collaborator on the project. Otherwise false.
     */
    @Override
    @Transactional
    public boolean canAccessProjectWebsite(String unixName, String user)
            throws QuadrigaStorageException {
        IProject project = projectDeepMapper
                .getProjectDetailsByUnixName(unixName);

        List<IProjectCollaborator> projectCollaborators = project
                .getProjectCollaborators();
        List<String> collaboratorNames = new ArrayList<String>();
        if (projectCollaborators != null) {
            Iterator<IProjectCollaborator> projectCollabIterator = projectCollaborators
                    .iterator();
            while (projectCollabIterator.hasNext()) {
                String collab = projectCollabIterator.next().getCollaborator()
                        .getUserObj().getUserName();
                collaboratorNames.add(collab.toLowerCase());
            }
        }
        logger.trace("Collaborators: " + collaboratorNames);
        
        String access = project.getProjectAccess().toString();

        if (access.equals("PRIVATE")) {
            if (project.getOwner().getUserName().toLowerCase().equals(user)
                    || collaboratorNames.contains(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns the list of recently updated top 5 projects
     * associated with the logged in user.
     * 
     * @param sUserName
     * @return List - list of recently updated projects.
     * @throws QuadrigaStorageException
     * @author Suraj Nilapwar
     */
    @Override
    @Transactional
    public List<IProject> getRecentProjectList(String sUserName)
            throws QuadrigaStorageException {
        List<IProject> projectsList = new ArrayList<IProject>();
        List<String> projectIds = new ArrayList<String>();

        List<IProject> projectListAsOwner;
        projectListAsOwner = projectShallowMapper.getProjectList(sUserName);
        if (projectListAsOwner != null) {
            for (IProject p : projectListAsOwner) {
                projectsList.add(getProjectDetails(p.getProjectId()));
                projectIds.add(p.getProjectId());
            }
        }
        List<IProject> projectListAsCollaborator;
        projectListAsCollaborator = projectShallowMapper
                .getCollaboratorProjectListOfUser(sUserName);
        ;
        if (projectListAsCollaborator != null) {
            for (IProject p : projectListAsCollaborator) {
                if (!projectIds.contains(p.getProjectId())) {
                    projectsList.add(getProjectDetails(p.getProjectId()));
                    projectIds.add(p.getProjectId());
                }
            }
        }
        Collections.sort(projectsList, new Comparator<IProject>() {
            @Override
            public int compare(IProject o1, IProject o2) {
                return o2.getUpdatedDate().compareTo(o1.getUpdatedDate());
            }
        });

        int count;
        String recentProjectCount = env
                .getProperty("project.recent.list.count");
        if (recentProjectCount != null) {
            count = Integer.parseInt(recentProjectCount);
        } else {
            count = projectsList.size();
        }

        List<IProject> recentProjectsList;
        if (projectsList != null && recentProjectCount != null
                && projectsList.size() > count) {
            recentProjectsList = new ArrayList<IProject>(projectsList.subList(
                    0, count));
        } else {
            recentProjectsList = new ArrayList<IProject>(projectsList);
        }

        return recentProjectsList;
    }

    /**
     * This method retrieves the list of projects associated with the
     * accessibility of the project. It uses the Project shallow mapper to give
     * a {@link List} of {@link IProject} of domain type {@link ProjectProxy}.
     * 
     * @param accessibility
     *            - accessibility of the project.
     * @return List<IProject> - list of projects associated with the
     *         accessibility of the project.
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IProject> getProjectListByAccessibility(String accessibility)
            throws QuadrigaStorageException {
        List<ProjectDTO> projectDTOList = projectDao
                .getAllProjectsDTOByAccessibility(accessibility);
        List<IProject> projectList = new ArrayList<IProject>();
        if (projectDTOList != null) {
            for (ProjectDTO projectDTO : projectDTOList) {
                projectList.add(projectShallowMapper
                        .getProjectDetails(projectDTO));
            }
        }
        return projectList;
    }

    /**
     * This method retrieves the list of projects associated with the search
     * Term and accessibility of the project. It uses the Project shallow mapper
     * to give a {@link List} of {@link IProject} of domain type
     * {@link ProjectProxy}.
     * 
     * @param searchTerm
     *            - The search term which is a text, searched in name and
     *            description of the project.
     * @param accessibility
     *            - accessibility of the project.
     * @return List<IProject> - list of projects associated with the given
     *         searchTerm and accessibility of the project.
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public List<IProject> getProjectListBySearchTermAndAccessiblity(
            String searchTerm, String accessibility)
            throws QuadrigaStorageException {
        List<ProjectDTO> projectDTOList = projectDao
                .getAllProjectsDTOBySearchTermAndAccessiblity(searchTerm,
                        accessibility);
        List<IProject> projectList = new ArrayList<IProject>();
        if (projectDTOList != null) {
            for (ProjectDTO projectDTO : projectDTOList) {
                projectList.add(searchLines(projectDTO, searchTerm));
            }
        }
        return projectList;
    }

    /**
     * Returns formated String for description and pattern
     * 
     * @param projectDTO
     *            projectDTO
     * @param pattern
     *            search string
     * @return IProject with formated description
     * @throws QuadrigaStorageException
     */
    private IProject searchLines(ProjectDTO projectDTO, String pattern)
            throws QuadrigaStorageException {
        IProject projectProxy = projectShallowMapper
                .getProjectDetails(projectDTO);

        String description = projectProxy.getDescription();
        String[] tempDescription = description.split("\\.");
        ArrayList<String> descriptionList = new ArrayList<String>();
        for (int i = 0; i < tempDescription.length; i++) {
            if (tempDescription[i].toLowerCase().matches(
                    ".*" + pattern.toLowerCase() + ".*")) {
                descriptionList.add(tempDescription[i]);
            }
        }
        String finalDescription = null;
        finalDescription = String.join(" [...]", descriptionList);
        projectProxy
                .setDescription(finalDescription.length() > 500 ? finalDescription
                        .substring(0, 500) : finalDescription);

        return projectProxy;
    }
}