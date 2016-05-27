package edu.asu.spring.quadriga.domain.impl.workspace;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;

/**
 * @description : WorkSpace class describing the properties of a WorkSpace
 *              object
 * 
 * @author : Kiran Kumar Batna
 *
 */
public class WorkSpace implements IWorkSpace {
    private String workspaceId;
    private String workspaceName;
    private String description;
    private IUser owner;
    private IProjectWorkspace workspaceProject;
    private IProject project;
    private List<IWorkspaceCollaborator> workspaceCollaborators;
    private List<IWorkspaceConceptCollection> workspaceConceptCollections;
    private List<IWorkspaceDictionary> workspaceDictionaries;
    private List<IWorkspaceNetwork> workspaceNetworks;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String externalWorkspaceId;

    @Override
    public String getWorkspaceId() {
        return workspaceId;
    }

    @Override
    public void setWorkspaceId(String id) {
        this.workspaceId = id;
    }

    @Override
    public String getWorkspaceName() {
        return workspaceName;
    }

    @Override
    public void setWorkspaceName(String name) {
        this.workspaceName = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public IUser getOwner() {
        return owner;
    }

    @Override
    public void setOwner(IUser owner) {
        this.owner = owner;
    }

    @Override
    public List<IWorkspaceCollaborator> getWorkspaceCollaborators() {
        return workspaceCollaborators;
    }

    @Override
    public void setWorkspaceCollaborators(List<IWorkspaceCollaborator> workspaceCollaborators) {
        this.workspaceCollaborators = workspaceCollaborators;
    }

    @Override
    public IProjectWorkspace getProjectWorkspace() {
        return workspaceProject;
    }

    @Override
    public void setProjectWorkspace(IProjectWorkspace projectWorkspace) {
        workspaceProject = projectWorkspace;
    }

    @Override
    public List<IWorkspaceConceptCollection> getWorkspaceConceptCollections() {
        return workspaceConceptCollections;
    }

    @Override
    public void setWorkspaceConceptCollections(List<IWorkspaceConceptCollection> workspaceConceptCollections) {
        this.workspaceConceptCollections = workspaceConceptCollections;
    }

    @Override
    public List<IWorkspaceDictionary> getWorkspaceDictinaries() {
        return workspaceDictionaries;
    }

    @Override
    public void setWorkspaceDictionaries(List<IWorkspaceDictionary> workspaceDictionaries) {
        this.workspaceDictionaries = workspaceDictionaries;
    }

    @Override
    public List<IWorkspaceNetwork> getWorkspaceNetworks() {
        return workspaceNetworks;
    }

    @Override
    public void setWorkspaceNetworks(List<IWorkspaceNetwork> workspaceNetworks) {
        this.workspaceNetworks = workspaceNetworks;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String getExternalWorkspaceId() {
        return externalWorkspaceId;
    }

    @Override
    public void setExternalWorkspaceId(String externalWorkspaceId) {
        this.externalWorkspaceId = externalWorkspaceId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
        result = prime * result + ((workspaceCollaborators == null) ? 0 : workspaceCollaborators.hashCode());
        result = prime * result + ((workspaceConceptCollections == null) ? 0 : workspaceConceptCollections.hashCode());
        result = prime * result + ((workspaceDictionaries == null) ? 0 : workspaceDictionaries.hashCode());
        result = prime * result + ((workspaceId == null) ? 0 : workspaceId.hashCode());
        result = prime * result + ((workspaceName == null) ? 0 : workspaceName.hashCode());
        result = prime * result + ((workspaceNetworks == null) ? 0 : workspaceNetworks.hashCode());
        result = prime * result + ((workspaceProject == null) ? 0 : workspaceProject.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WorkSpace other = (WorkSpace) obj;
        if (createdBy == null) {
            if (other.createdBy != null)
                return false;
        } else if (!createdBy.equals(other.createdBy))
            return false;
        if (createdDate == null) {
            if (other.createdDate != null)
                return false;
        } else if (!createdDate.equals(other.createdDate))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        if (updatedBy == null) {
            if (other.updatedBy != null)
                return false;
        } else if (!updatedBy.equals(other.updatedBy))
            return false;
        if (updatedDate == null) {
            if (other.updatedDate != null)
                return false;
        } else if (!updatedDate.equals(other.updatedDate))
            return false;
        if (workspaceCollaborators == null) {
            if (other.workspaceCollaborators != null)
                return false;
        } else if (!workspaceCollaborators.equals(other.workspaceCollaborators))
            return false;
        if (workspaceConceptCollections == null) {
            if (other.workspaceConceptCollections != null)
                return false;
        } else if (!workspaceConceptCollections.equals(other.workspaceConceptCollections))
            return false;
        if (workspaceDictionaries == null) {
            if (other.workspaceDictionaries != null)
                return false;
        } else if (!workspaceDictionaries.equals(other.workspaceDictionaries))
            return false;
        if (workspaceId == null) {
            if (other.workspaceId != null)
                return false;
        } else if (!workspaceId.equals(other.workspaceId))
            return false;
        if (workspaceName == null) {
            if (other.workspaceName != null)
                return false;
        } else if (!workspaceName.equals(other.workspaceName))
            return false;
        if (workspaceNetworks == null) {
            if (other.workspaceNetworks != null)
                return false;
        } else if (!workspaceNetworks.equals(other.workspaceNetworks))
            return false;
        if (workspaceProject == null) {
            if (other.workspaceProject != null)
                return false;
        } else if (!workspaceProject.equals(other.workspaceProject))
            return false;
        return true;
    }

    @Override
    public IProject getProject() {
        return project;
    }

    @Override
    public void setProject(IProject project) {
        this.project = project;
    }

}
