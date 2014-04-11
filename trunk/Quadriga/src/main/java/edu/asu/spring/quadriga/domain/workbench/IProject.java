package edu.asu.spring.quadriga.domain.workbench;

import java.util.Date;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;

/**
 * @description   : interface to implement Project class.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface IProject 
{
	public abstract void setProjectId(String projectId);

	public abstract String getProjectId();

	public abstract String getProjectName();

	public abstract void setProjectName(String projectName);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract String getUnixName();

	public abstract void setUnixName(String unixName);
	
	public abstract EProjectAccessibility getProjectAccess();

	public abstract void setProjectAccess(EProjectAccessibility projectAccess);

	public abstract IUser getOwner();

	public abstract void setOwner(IUser owner);
	
	public abstract IProjectCollaborator getProjectCollaborators();
	
	public abstract void setProjectCollaborators(IProjectCollaborator projectCollaborator);
	
	public abstract IProjectWorkspace getProjectWorkspaces();
	
	public abstract void setProjectWorkspaces(IProjectWorkspace projectWorkspace);
	
	public abstract IProjectDictionary getProjectDictionaries();
	
	public abstract void setProjectDictionaries(IProjectDictionary projectDictionary);
	
	public abstract IProjectConceptCollection getProjectConceptCollections();
	
	public abstract void setProjectConceptCollections(IProjectConceptCollection projectConceptCollection);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updateDate);
	
}