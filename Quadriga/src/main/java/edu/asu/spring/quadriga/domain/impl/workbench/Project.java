package edu.asu.spring.quadriga.domain.impl.workbench;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;

/**
 * @description : Project class describing the properties of a Project object
 * 
 * @author : Kiran Kumar Batna
 * 
 */
@Service
public class Project implements IProject {
	private String projectId;
	private String projectName;
	private String description;
	private String unixName;
	private EProjectAccessibility projectAccess;
	private IUser owner;
	private List<IProjectCollaborator> projectCollaborators;
	private List<IProjectWorkspace> projectWorkspaces;
	private List<IProjectConceptCollection> projectConceptCollections;
	private List<IProjectDictionary> projectDictionaries;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private IProjectHandleResolver resolver;
	
	/**
	 * retrieves the internal id of the project
	 */
	@Override
	public String getProjectId() {
		return projectId;
	}

	/**
	 * assigns the internal id of the project
	 */
	@Override
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
	/**
	 * retrieves the name of the project
	 */
	@Override
	public String getProjectName() {
		return projectName;
	}

	/**
	 * assigns the name of the project to the supplied variable.
	 */
	@Override
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * retrieves the description of the project
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * assigns the description of the project
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * retrieves the Unix ID of the project
	 */
	@Override
	public String getUnixName() {
		return unixName;
	}

	/**
	 * assigns the Unix ID of the project
	 */
	@Override
	public void setUnixName(String unixName) {
		this.unixName = unixName;
	}

	/**
	 * retrieves the accessibility of the project
	 */
	@Override
	public EProjectAccessibility getProjectAccess() {
		return projectAccess;
	}

	/**
	 * assigns the accessibility of the project
	 */
	@Override
	public void setProjectAccess(EProjectAccessibility projectAccess) {
		this.projectAccess = projectAccess;
	}

	/**
	 * retrieves the owner of the project
	 */
	@Override
	public IUser getOwner() {
		return owner;
	}

	/**
	 * assigns the owner of the project
	 */
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
	}

	@Override
	public void setProjectCollaborators(
			List<IProjectCollaborator> projectCollaborators) {
         this.projectCollaborators = projectCollaborators;		
	}
	
	@Override
	public List<IProjectCollaborator> getProjectCollaborators() {
		return projectCollaborators;
	}

	@Override
	public void setProjectWorkspaces(List<IProjectWorkspace> projectWorkspaces) {
         this.projectWorkspaces = projectWorkspaces;		
	}
	
	@Override
	public List<IProjectWorkspace> getProjectWorkspaces() {
		return projectWorkspaces;
	}

	@Override
	public void setProjectDictionaries(
			List<IProjectDictionary> projectDictionaries) {
         this.projectDictionaries = projectDictionaries;		
	}
	
	@Override
	public List<IProjectDictionary> getProjectDictionaries() {
		return projectDictionaries;
	}

	@Override
	public List<IProjectConceptCollection> getProjectConceptCollections() {
		return projectConceptCollections;
	}

	@Override
	public void setProjectConceptCollections(
			List<IProjectConceptCollection> projectConceptCollections) {
        this.projectConceptCollections = projectConceptCollections;		
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
    public IProjectHandleResolver getResolver() {
        return resolver;
    }

    @Override
    public void setResolver(IProjectHandleResolver resolver) {
        this.resolver = resolver;
    }


//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result
//				+ ((createdBy == null) ? 0 : createdBy.hashCode());
//		result = prime * result
//				+ ((createdDate == null) ? 0 : createdDate.hashCode());
//		result = prime * result
//				+ ((description == null) ? 0 : description.hashCode());
//		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
//		result = prime * result
//				+ ((projectAccess == null) ? 0 : projectAccess.hashCode());
//		result = prime
//				* result
//				+ ((projectCollaborators == null) ? 0 : projectCollaborators
//						.hashCode());
//		result = prime
//				* result
//				+ ((projectConceptCollections == null) ? 0
//						: projectConceptCollections.hashCode());
//		result = prime
//				* result
//				+ ((projectDictionaries == null) ? 0 : projectDictionaries
//						.hashCode());
//		result = prime * result
//				+ ((projectId == null) ? 0 : projectId.hashCode());
//		result = prime * result
//				+ ((projectName == null) ? 0 : projectName.hashCode());
//		result = prime
//				* result
//				+ ((projectWorkspaces == null) ? 0 : projectWorkspaces
//						.hashCode());
//		result = prime * result
//				+ ((unixName == null) ? 0 : unixName.hashCode());
//		result = prime * result
//				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
//		result = prime * result
//				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
//		return result;
//	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Project other = (Project) obj;
//		if (createdBy == null) {
//			if (other.createdBy != null)
//				return false;
//		} else if (!createdBy.equals(other.createdBy))
//			return false;
//		if (createdDate == null) {
//			if (other.createdDate != null)
//				return false;
//		} else if (!createdDate.equals(other.createdDate))
//			return false;
//		if (description == null) {
//			if (other.description != null)
//				return false;
//		} else if (!description.equals(other.description))
//			return false;
//		if (owner == null) {
//			if (other.owner != null)
//				return false;
//		} else if (!owner.equals(other.owner))
//			return false;
//		if (projectAccess != other.projectAccess)
//			return false;
//		if (projectCollaborators == null) {
//			if (other.projectCollaborators != null)
//				return false;
//		} else if (!projectCollaborators.equals(other.projectCollaborators))
//			return false;
//		if (projectConceptCollections == null) {
//			if (other.projectConceptCollections != null)
//				return false;
//		} else if (!projectConceptCollections
//				.equals(other.projectConceptCollections))
//			return false;
//		if (projectDictionaries == null) {
//			if (other.projectDictionaries != null)
//				return false;
//		} else if (!projectDictionaries.equals(other.projectDictionaries))
//			return false;
//		if (projectId == null) {
//			if (other.projectId != null)
//				return false;
//		} else if (!projectId.equals(other.projectId))
//			return false;
//		if (projectName == null) {
//			if (other.projectName != null)
//				return false;
//		} else if (!projectName.equals(other.projectName))
//			return false;
//		if (projectWorkspaces == null) {
//			if (other.projectWorkspaces != null)
//				return false;
//		} else if (!projectWorkspaces.equals(other.projectWorkspaces))
//			return false;
//		if (unixName == null) {
//			if (other.unixName != null)
//				return false;
//		} else if (!unixName.equals(other.unixName))
//			return false;
//		if (updatedBy == null) {
//			if (other.updatedBy != null)
//				return false;
//		} else if (!updatedBy.equals(other.updatedBy))
//			return false;
//		if (updatedDate == null) {
//			if (other.updatedDate != null)
//				return false;
//		} else if (!updatedDate.equals(other.updatedDate))
//			return false;
//		return true;
//	}

}
