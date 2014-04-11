package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;

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
	private List<ICollaborator> collaborators;
	private List<IConcept> concepts;
	private List<IProject> projects;
	private List<IWorkSpace> workspaces;
	
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
	public List<ICollaborator> getCollaborators() {
		return collaborators;
	}
	@Override
	public void setCollaborators(List<ICollaborator> collaborators) {
		this.collaborators = collaborators;
	}
	@Override
	public List<IConcept> getConcepts() {
		return concepts;
	}
	
	@Override
	public void setConcepts(List<IConcept> concepts)
	{
		this.concepts = concepts;
	}
	
	@Override
	public List<IProject> getProjects() 
	{
		return projects;
	}
	@Override
	public void setProjects(List<IProject> projects) {
		this.projects = projects;
	}
	@Override
	public List<IWorkSpace> getWorkspaces() {
		return workspaces;
	}
	@Override
	public void setWorkspaces(List<IWorkSpace> workspaces) {
		this.workspaces = workspaces;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((collaborators == null) ? 0 : collaborators.hashCode());
		result = prime
				* result
				+ ((conceptCollectionId == null) ? 0 : conceptCollectionId
						.hashCode());
		result = prime
				* result
				+ ((conceptCollectionName == null) ? 0 : conceptCollectionName
						.hashCode());
		result = prime * result
				+ ((concepts == null) ? 0 : concepts.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result
				+ ((projects == null) ? 0 : projects.hashCode());
		result = prime * result
				+ ((workspaces == null) ? 0 : workspaces.hashCode());
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
		if (collaborators == null) {
			if (other.collaborators != null)
				return false;
		} else if (!collaborators.equals(other.collaborators))
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
		if (concepts == null) {
			if (other.concepts != null)
				return false;
		} else if (!concepts.equals(other.concepts))
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
		if (projects == null) {
			if (other.projects != null)
				return false;
		} else if (!projects.equals(other.projects))
			return false;
		if (workspaces == null) {
			if (other.workspaces != null)
				return false;
		} else if (!workspaces.equals(other.workspaces))
			return false;
		return true;
	} 
}
