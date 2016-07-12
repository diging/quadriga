package edu.asu.spring.quadriga.domain.proxy;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This class acts a proxy to {@link Project} while fetching {@link List} of
 * {@link IProject}. {@link ProjectProxy} would load the {@link List} of
 * associated using lazy load concept.
 * 
 * @author Lohith Dwaraka
 *
 */
public class ProjectProxy implements IProject {
    private String projectId;
    private String projectName;
    private String description;
    private String unixName;
    private EProjectAccessibility projectAccess;
    private IUser owner;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private IProjectHandleResolver resolver;

    /**
     * Full project detail object. This would have object of type
     * {@link Project}
     */
    private IProject project;

    /**
     * Access to {@link IRetrieveProjectManager} to call manager methods to
     * update actual {@link Project} object.
     */
    private IRetrieveProjectManager projectManager;

    private static final Logger logger = LoggerFactory.getLogger(ProjectProxy.class);

    /**
     * Constructor to create {@link ProjectProxy} with
     * {@link IRetrieveProjectManager} manager object.
     * 
     * @param projectManager
     */
    public ProjectProxy(IRetrieveProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    /**
     * Getter class for {@link IRetrieveProjectManager} object
     * 
     * @return Returns {@link IRetrieveProjectManager} object.
     */
    public IRetrieveProjectManager getProjectManager() {
        return projectManager;
    }

    /**
     * Setter class for {@link IRetrieveProjectManager} manager object
     * 
     * @param projectManager
     *            {@link IRetrieveProjectManager} object to get access to
     *            project manager layer
     */
    public void setProjectManager(IRetrieveProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IProject} object if it is not
     * null
     */
    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
        if (this.project != null) {
            this.project.setProjectName(projectName);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IProject} object if it is not
     * null
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
        if (this.project != null) {
            this.project.setDescription(description);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getUnixName() {
        return this.unixName;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IProject} object if it is not
     * null
     */
    @Override
    public void setUnixName(String unixName) {
        this.unixName = unixName;
        if (this.project != null) {
            this.project.setUnixName(unixName);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public IUser getOwner() {
        return this.owner;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IProject} object if it is not
     * null
     */
    @Override
    public void setOwner(IUser owner) {
        this.owner = owner;
        if (this.project != null) {
            this.project.setOwner(owner);
        }
    }

    /**
     * {@inheritDoc} Also updates the local {@link IProject} object if it is not
     * null
     */
    @Override
    public void setProjectId(String projectId) {
        this.projectId = projectId;
        if (this.project != null) {
            this.project.setProjectId(projectId);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getProjectId() {
        return this.projectId;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public EProjectAccessibility getProjectAccess() {
        return this.projectAccess;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IProject} object if it is not
     * null
     */
    @Override
    public void setProjectAccess(EProjectAccessibility projectAccess) {
        this.projectAccess = projectAccess;
        if (this.project != null) {
            this.project.setProjectAccess(projectAccess);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IProject} object if it is not
     * null
     */
    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        if (this.project != null) {
            this.project.setCreatedBy(createdBy);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Date getCreatedDate() {
        return this.createdDate;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IProject} object if it is not
     * null
     */
    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        if (this.project != null) {
            this.project.setCreatedDate(createdDate);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IProject} object if it is not
     * null
     */
    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        if (this.project != null) {
            this.project.setUpdatedBy(updatedBy);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IProject} object if it is not
     * null
     */
    @Override
    public void setUpdatedDate(Date updateDate) {
        this.updatedDate = updateDate;
        if (this.project != null) {
            this.project.setUpdatedDate(updateDate);
        }
    }

    /**
     * This class helps in fetching the full project object using project
     * manager object. Also sets the values of variables in {@link ProjectProxy}
     * to local {@link Project} object.
     * 
     */
    private void setProjectDetails() {
        try {
            this.project = this.projectManager.getProjectDetails(this.projectId);
        } catch (QuadrigaStorageException e) {
            logger.error("Issue accessing database from project proxt", e);
        }
        if (this.project != null) {
            this.project.setProjectName(this.projectName);
            this.project.setProjectId(this.projectId);
            this.project.setDescription(this.description);
            this.project.setProjectAccess(this.projectAccess);
            this.project.setUpdatedBy(this.updatedBy);
            this.project.setUpdatedDate(this.updatedDate);
            this.project.setOwner(this.owner);
            this.project.setUnixName(this.unixName);
        }
    }

    /**
     * {@inheritDoc} This method checks if local {@link IProject} object is
     * null. If its null it would use project manager object to fetch full
     * project object and then return {@link List} of
     * {@link IProjectCollaborator} else if local {@link IProject} is not null,
     * just returns {@link List} of {@link IProjectCollaborator} from local
     * {@link IProject}
     */
    @Override
    public List<IProjectCollaborator> getProjectCollaborators() {
        if (this.project != null) {
            return this.project.getProjectCollaborators();
        } else {

            setProjectDetails();
            // We need to do this in case of Quadriga storage exception in
            // setProjectDetails() , this.project would be null
            if (this.project != null) {
                return this.project.getProjectCollaborators();
            } else {
                return null;
            }
        }

    }

    /**
     * {@inheritDoc} This method also checks if local {@link IProject} object is
     * null. If its null it would use manager object to fetch full project
     * object and then set {@link List} of {@link IProjectCollaborator} else if
     * local {@link IProject} is not null, just set {@link List} of
     * {@link IProjectCollaborator}
     */
    @Override
    public void setProjectCollaborators(List<IProjectCollaborator> projectCollaborators) {
        if (this.project != null) {
            this.project.setProjectCollaborators(projectCollaborators);
        } else {
            setProjectDetails();
            if (this.project != null) {
                this.project.setProjectCollaborators(projectCollaborators);
            } else {
                // Doing nothing this would be in case of Quadriga storage
                // exception in setProjectDetails()
            }
        }

    }

    /**
     * {@inheritDoc} This method checks if local {@link IProject} object is
     * null. If its null it would use project manager object to fetch full
     * project object and then return {@link List} of {@link IProjectWorkspace}
     * else if local {@link IProject} is not null, just returns {@link List} of
     * {@link IProjectWorkspace} from local {@link IProject}
     */
    @Override
    public List<IProjectWorkspace> getProjectWorkspaces() {
        if (this.project != null) {
            return this.project.getProjectWorkspaces();
        } else {
            setProjectDetails();
            // We need to do this in case of Quadriga storage exception in
            // setProjectDetails() , this.project would be null
            if (this.project != null) {
                return this.project.getProjectWorkspaces();
            } else {
                return null;
            }
        }

    }

    /**
     * {@inheritDoc} This method also checks if local {@link IProject} object is
     * null. If its null it would use manager object to fetch full project
     * object and then set {@link List} of {@link IProjectWorkspace} else if
     * local {@link IProject} is not null, just set {@link List} of
     * {@link IProjectWorkspace}
     */
    @Override
    public void setProjectWorkspaces(List<IProjectWorkspace> projectWorkspaces) {
        if (this.project != null) {
            this.project.setProjectWorkspaces(projectWorkspaces);
        } else {
            setProjectDetails();
            if (this.project != null) {
                this.project.setProjectWorkspaces(projectWorkspaces);
            } else {
                // Doing nothing this would be in case of Quadriga storage
                // exception in setProjectDetails()
            }
        }

    }

    /**
     * {@inheritDoc} This method checks if local {@link IProject} object is
     * null. If its null it would use project manager object to fetch full
     * project object and then return {@link List} of {@link IProjectDictionary}
     * else if local {@link IProject} is not null, just returns {@link List} of
     * {@link IProjectDictionary} from local {@link IProject}
     */
    @Override
    public List<IProjectDictionary> getProjectDictionaries() {
        if (this.project != null) {
            return this.project.getProjectDictionaries();
        } else {
            setProjectDetails();
            // We need to do this in case of Quadriga storage exception in
            // setProjectDetails() , this.project would be null
            if (this.project != null) {
                return this.project.getProjectDictionaries();
            } else {
                return null;
            }
        }

    }

    /**
     * {@inheritDoc} This method also checks if local {@link IProject} object is
     * null. If its null it would use manager object to fetch full project
     * object and then set {@link List} of {@link IProjectDictionary} else if
     * local {@link IProject} is not null, just set {@link List} of
     * {@link IProjectDictionary}
     */
    @Override
    public void setProjectDictionaries(List<IProjectDictionary> projectDictionaries) {
        if (this.project != null) {
            this.project.setProjectDictionaries(projectDictionaries);
        } else {
            setProjectDetails();
            if (this.project != null) {
                this.project.setProjectDictionaries(projectDictionaries);
            } else {
                // Doing nothing this would be in case of Quadriga storage
                // exception in setProjectDetails()
            }
        }

    }

    /**
     * {@inheritDoc} This method checks if local {@link IProject} object is
     * null. If its null it would use project manager object to fetch full
     * project object and then return {@link List} of
     * {@link IProjectConceptCollection} else if local {@link IProject} is not
     * null, just returns {@link List} of {@link IProjectConceptCollection} from
     * local {@link IProject}
     */
    @Override
    public List<IProjectConceptCollection> getProjectConceptCollections() {
        if (this.project != null) {
            return this.project.getProjectConceptCollections();
        } else {
            setProjectDetails();
            // We need to do this in case of Quadriga storage exception in
            // setProjectDetails() , this.project would be null
            if (this.project != null) {
                return this.project.getProjectConceptCollections();
            } else {
                return null;
            }
        }

    }

    /**
     * {@inheritDoc} This method also checks if local {@link IProject} object is
     * null. If its null it would use project manager object to fetch full
     * project object and then set {@link List} of
     * {@link IProjectConceptCollection} else if local {@link IProject} is not
     * null, just set {@link List} of {@link IProjectConceptCollection}
     * 
     */
    @Override
    public void setProjectConceptCollections(List<IProjectConceptCollection> projectConceptCollectionsList) {
        if (this.project != null) {
            this.project.setProjectConceptCollections(projectConceptCollectionsList);
        } else {
            setProjectDetails();
            if (this.project != null) {
                this.project.setProjectConceptCollections(projectConceptCollectionsList);
            } else {
                // Doing nothing this would be in case of Quadriga storage
                // exception in setProjectDetails()
            }
        }

    }

    @Override
    public void setResolver(IProjectHandleResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public IProjectHandleResolver getResolver() {
        return resolver;
    }

}
