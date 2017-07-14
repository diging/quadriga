package edu.asu.spring.quadriga.domain.proxy;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.impl.Workspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;

/**
 * This class acts a proxy to {@link Workspace} while fetching {@link List} of
 * {@link IWorkspace}. {@link WorkSpaceProxy} would load the {@link List} of
 * associated using lazy load concept.
 * 
 * @author Lohith Dwaraka
 *
 */
public class WorkSpaceProxy implements IWorkspace {

    private String workspaceId;
    private String workspaceName;
    private String description;
    private IUser owner;
    private IProject project;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    /**
     * Full workspace detail object. This would have object of type
     * {@link Workspace}
     */
    private IWorkspace workspace;
    /**
     * Access to {@link IListWSManager} to call manager methods to update actual
     * {@link Workspace} object.
     */
    private IWorkspaceManager wsManager;

    private static final Logger logger = LoggerFactory.getLogger(WorkSpaceProxy.class);

    /**
     * Constructor to create {@link WorkSpaceProxy} with {@link IListWSManager}
     * manager object.
     * 
     * @param wsManager
     *            {@link IListWSManager} object
     */
    public WorkSpaceProxy(IWorkspaceManager wsManager) {
        this.wsManager = wsManager;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IWorkspace} object if it is
     * not null
     */
    @Override
    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
        if (this.workspace != null) {
            this.workspace.setWorkspaceId(workspaceId);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getWorkspaceId() {
        return this.workspaceId;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IWorkspace} object if it is
     * not null
     */
    @Override
    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
        if (this.workspace != null) {
            this.workspace.setWorkspaceName(workspaceName);
        }

    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getWorkspaceName() {
        return this.workspaceName;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IWorkspace} object if it is
     * not null
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
        if (this.workspace != null) {
            this.workspace.setDescription(description);
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
     * {@inheritDoc} Also updates the local {@link IWorkspace} object if it is
     * not null
     */
    @Override
    public void setOwner(IUser owner) {
        this.owner = owner;
        if (this.workspace != null) {
            this.workspace.setOwner(owner);
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
     * {@inheritDoc} This method checks if local {@link IWorkspace} object is
     * null. If its null it would use workspace manager object to fetch full
     * workspace object and then return {@link List} of
     * {@link IWorkspaceCollaborator} else if local {@link IWorkspace} is not
     * null, just returns {@link List} of {@link IWorkspaceCollaborator} from
     * local {@link IWorkspace}
     */
    @Override
    public List<IWorkspaceCollaborator> getWorkspaceCollaborators() {
        if (this.workspace == null) {
            setWorkSpaceDetails();
        }
        return this.workspace.getWorkspaceCollaborators();
    }

    /**
     * {@inheritDoc} This method also checks if local {@link IWorkspace} object
     * is null. If its null it would use workspace manager object to fetch full
     * workspace object and then set {@link List} of
     * {@link IWorkspaceCollaborator} else if local {@link IWorkspace} is not
     * null, just set {@link List} of {@link IWorkspaceCollaborator}
     */
    @Override
    public void setWorkspaceCollaborators(List<IWorkspaceCollaborator> workspaceCollaborators) {
        if (this.workspace == null) {
            setWorkSpaceDetails();
        }
        this.workspace.setWorkspaceCollaborators(workspaceCollaborators);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public IProject getProject() {
        return this.project;
    }

    /**
     * {@inheritDoc} Also updates the local {@link IWorkspace} object if it is
     * not null
     */
    @Override
    public void setProject(IProject project) {
        this.project = project;
        if (this.workspace != null) {
            this.workspace.setProject(project);
        }
    }

    /**
     * {@inheritDoc} This method checks if local {@link IWorkspace} object is
     * null. If its null it would use workspace manager object to fetch full
     * workspace object and then return {@link List} of
     * {@link IWorkspaceConceptCollection} else if local {@link IWorkspace} is
     * not null, just returns {@link List} of
     * {@link IWorkspaceConceptCollection} from local {@link IWorkspace}
     */
    @Override
    public List<IConceptCollection> getConceptCollections() {
        if (this.workspace != null) {
            return this.workspace.getConceptCollections();
        } else {
            setWorkSpaceDetails();
            // We need to do this in case of Quadriga storage exception in
            // setWorkSpaceDetails(); , this.workspace would be null
            if (this.workspace != null) {
                return this.workspace.getConceptCollections();
            } else {
                return null;
            }
        }

    }

    /**
     * {@inheritDoc} This method also checks if local {@link IWorkspace} object
     * is null. If its null it would use workspace manager object to fetch full
     * workspace object and then set {@link List} of
     * {@link IWorkspaceConceptCollection} else if local {@link IWorkspace} is
     * not null, just set {@link List} of {@link IWorkspaceConceptCollection}
     */
    @Override
    public void setConceptCollections(List<IConceptCollection> conceptCollections) {
        if (this.workspace != null) {
            this.workspace.setConceptCollections(conceptCollections);
        } else {
            setWorkSpaceDetails();
            if (this.workspace != null) {
                this.workspace.setConceptCollections(conceptCollections);
            } else {
                // Doing nothing this would be in case of Quadriga storage
                // exception in setWorkSpaceDetails()
            }
        }

    }

    /**
     * {@inheritDoc} This method checks if local {@link IWorkspace} object is
     * null. If its null it would use workspace manager object to fetch full
     * workspace object and then return {@link List} of
     * {@link IWorkspaceDictionary} else if local {@link IWorkspace} is not
     * null, just returns {@link List} of {@link IWorkspaceDictionary} from
     * local {@link IWorkspace}
     */
    @Override
    public List<IDictionary> getDictionaries() {
        if (this.workspace != null) {
            return this.workspace.getDictionaries();
        } else {
            setWorkSpaceDetails();
            // We need to do this in case of Quadriga storage exception in
            // setWorkSpaceDetails(); , this.workspace would be null
            if (this.workspace != null) {
                return this.workspace.getDictionaries();
            } else {
                return null;
            }
        }
    }

    /**
     * {@inheritDoc} This method also checks if local {@link IWorkspace} object
     * is null. If its null it would use workspace manager object to fetch full
     * workspace object and then set {@link List} of
     * {@link IWorkspaceDictionary} else if local {@link IWorkspace} is not
     * null, just set {@link List} of {@link IWorkspaceDictionary}
     */
    @Override
    public void setDictionaries(List<IDictionary> dictionaries) {
        if (this.workspace != null) {
            this.workspace.setDictionaries(dictionaries);
        } else {
            setWorkSpaceDetails();
            if (this.workspace != null) {
                this.workspace.setDictionaries(dictionaries);
            } else {
                // Doing nothing this would be in case of Quadriga storage
                // exception in setWorkSpaceDetails()
            }
        }

    }

    /**
     * {@inheritDoc} This method checks if local {@link IWorkspace} object is
     * null. If its null it would use workspace manager object to fetch full
     * workspace object and then return {@link List} of
     * {@link IWorkspaceNetwork} else if local {@link IWorkspace} is not null,
     * just returns {@link List} of {@link IWorkspaceNetwork} from local
     * {@link IWorkspace}
     */
    @Override
    public List<INetwork> getNetworks() {
        if (this.workspace != null) {
            return this.workspace.getNetworks();
        } else {
            setWorkSpaceDetails();
            // We need to do this in case of Quadriga storage exception in
            // setWorkSpaceDetails(); , this.workspace would be null
            if (this.workspace != null) {
                return this.workspace.getNetworks();
            } else {
                return null;
            }
        }

    }

    /**
     * {@inheritDoc} This method also checks if local {@link IWorkspace} object
     * is null. If its null it would use workspace manager object to fetch full
     * workspace object and then set {@link List} of {@link IWorkspaceNetwork}
     * else if local {@link IWorkspace} is not null, just set {@link List} of
     * {@link IWorkspaceNetwork}
     */
    @Override
    public void setNetworks(List<INetwork> networks) {
        if (this.workspace != null) {
            this.workspace.setNetworks(networks);
        } else {
            setWorkSpaceDetails();
            if (this.workspace != null) {
                this.workspace.setNetworks(networks);
            } else {
                // Doing nothing this would be in case of Quadriga storage
                // exception in setWorkSpaceDetails()
            }
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
     * {@inheritDoc} Also updates the local {@link IWorkspace} object if it is
     * not null
     */
    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        if (this.workspace != null) {
            this.workspace.setCreatedBy(createdBy);
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
     * {@inheritDoc} Also updates the local {@link IWorkspace} object if it is
     * not null
     */
    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        if (this.workspace != null) {
            this.workspace.setCreatedDate(createdDate);
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
     * {@inheritDoc} Also updates the local {@link IWorkspace} object if it is
     * not null
     */
    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        if (this.workspace != null) {
            this.workspace.setUpdatedBy(updatedBy);
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
     * {@inheritDoc} Also updates the local {@link IWorkspace} object if it is
     * not null
     */
    @Override
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
        if (this.workspace != null) {
            this.workspace.setUpdatedDate(updatedDate);
        }
    }

    /**
     * This class helps in fetching the full workspace object using workspace
     * manager object. Also sets the values of variables in
     * {@link WorkSpaceProxy} to local {@link Workspace} object.
     */
    private void setWorkSpaceDetails() {
        try {
            this.workspace = this.wsManager.getWorkspaceDetails(this.workspaceId);
        } catch (QuadrigaStorageException e) {
            logger.error("Issue accessing database from project proxy", e);
        } catch (QuadrigaAccessException e) {
            logger.error("Issue user accessing project", e);
        }
        if (this.workspace != null) {
            this.workspace.setWorkspaceName(this.workspaceName);
            this.workspace.setWorkspaceId(this.workspaceId);
            this.workspace.setDescription(this.description);
            this.workspace.setUpdatedBy(this.updatedBy);
            this.workspace.setUpdatedDate(this.updatedDate);
            this.workspace.setOwner(this.owner);
            this.workspace.setProject(this.project);
        }

    }

    public void setExternalWorkspaceId(String externalWorkspaceId) {
    }

    public String getExternalWorkspaceId() {
        return null;
    }
}
