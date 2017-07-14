package edu.asu.spring.quadriga.domain.workspace.impl;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;

/**
 * @description : WorkSpace class describing the properties of a WorkSpace
 *              object
 * 
 * @author : Kiran Kumar Batna
 *
 */
public class Workspace implements IWorkspace {
    private String workspaceId;
    private String workspaceName;
    private String description;
    private IUser owner;
    private IProject project;
    private List<IWorkspaceCollaborator> workspaceCollaborators;
    private List<IConceptCollection> conceptCollections;
    private List<IDictionary> dictionaries;
    private List<INetwork> networks;
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
    public List<IConceptCollection> getConceptCollections() {
        return conceptCollections;
    }

    @Override
    public void setConceptCollections(List<IConceptCollection> conceptCollections) {
        this.conceptCollections = conceptCollections;
    }

    @Override
    public List<IDictionary> getDictionaries() {
        return dictionaries;
    }

    @Override
    public void setDictionaries(List<IDictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    @Override
    public List<INetwork> getNetworks() {
        return networks;
    }

    @Override
    public void setNetworks(List<INetwork> networks) {
        this.networks = networks;
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
    public IProject getProject() {
        return project;
    }

    @Override
    public void setProject(IProject project) {
        this.project = project;
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
        result = prime * result + ((workspaceId == null) ? 0 : workspaceId.hashCode());
        result = prime * result + ((workspaceName == null) ? 0 : workspaceName.hashCode());
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
        Workspace other = (Workspace) obj;
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
        return true;
    }

}
