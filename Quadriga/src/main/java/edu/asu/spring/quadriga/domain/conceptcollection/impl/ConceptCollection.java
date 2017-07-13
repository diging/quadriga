package edu.asu.spring.quadriga.domain.conceptcollection.impl;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;

/**
 * @description : ConceptCollection class describing the properties 
 *                of a ConceptCollection object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class ConceptCollection implements IConceptCollection 
{
	private String conceptCollectionId;
	private String conceptCollectionName;
	private String description;
	private IUser owner;
	private List<IConceptCollectionCollaborator> conceptCollectionCollaborators;
	private List<IConcept> concepts;
	private List<IProject> projects;
	private List<IWorkspace> workspaces;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    
    
	@Override
	public String getConceptCollectionId() {
		return conceptCollectionId; 
	}
	@Override
	public void setConceptCollectionId(String id) {
		this.conceptCollectionId = id;
	}

	@Override
	public String getConceptCollectionName() {
		return conceptCollectionName;
	}
	@Override
	public void setConceptCollectionName(String conceptCollectionName) {
		this.conceptCollectionName = conceptCollectionName;
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
	public List<IConceptCollectionCollaborator> getConceptCollectionCollaborators() {
		return conceptCollectionCollaborators;
	}
	@Override
	public void setConceptCollectionCollaborators(
			List<IConceptCollectionCollaborator> conceptCollectionCollaborators) {
       this.conceptCollectionCollaborators = conceptCollectionCollaborators;		
	}
	@Override
    public List<IConcept> getConcepts() {
        return concepts;
    }
    @Override
    public void setConcepts(List<IConcept> concepts) {
        this.concepts = concepts;
    }
    @Override
    public List<IProject> getProjects() {
        return projects;
    }
    @Override
    public void setProjects(List<IProject> projects) {
        this.projects = projects;
    }
    @Override
    public List<IWorkspace> getWorkspaces() {
        return workspaces;
    }
    @Override
    public void setWorkspaces(List<IWorkspace> workspaces) {
        this.workspaces = workspaces;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((conceptCollectionCollaborators == null) ? 0
						: conceptCollectionCollaborators.hashCode());
		result = prime
				* result
				+ ((conceptCollectionId == null) ? 0 : conceptCollectionId
						.hashCode());
		result = prime
				* result
				+ ((conceptCollectionName == null) ? 0 : conceptCollectionName
						.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
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
		ConceptCollection other = (ConceptCollection) obj;
		if (conceptCollectionCollaborators == null) {
			if (other.conceptCollectionCollaborators != null)
				return false;
		} else if (!conceptCollectionCollaborators
				.equals(other.conceptCollectionCollaborators))
			return false;
		if (conceptCollectionId == null) {
			if (other.conceptCollectionId != null)
				return false;
		} else if (!conceptCollectionId.equals(other.conceptCollectionId))
			return false;
		if (conceptCollectionName == null) {
			if (other.conceptCollectionName != null)
				return false;
		} else if (!conceptCollectionName.equals(other.conceptCollectionName))
			return false;
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
		return true;
	}
	
	
	
}
