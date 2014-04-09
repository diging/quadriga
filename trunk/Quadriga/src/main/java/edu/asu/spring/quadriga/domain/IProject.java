package edu.asu.spring.quadriga.domain;

import java.util.List;

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

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract String getUnixName();

	public abstract void setUnixName(String unixName);
	
	public abstract EProjectAccessibility getProjectAccess();

	public abstract void setProjectAccess(EProjectAccessibility projectAccess);

	public abstract IUser getOwner();

	public abstract void setOwner(IUser owner);
	
	public abstract List<ICollaborator> getCollaborators();

	public abstract void setCollaborators(List<ICollaborator> collaborators);
	
	public abstract List<IWorkSpace> getWorkspaces();
	
	public abstract void setWorkspaces(List<IWorkSpace> workspaces);
	
	public abstract List<IConceptCollection> getConceptCollections();
	
	public abstract void setConceptCollections(List<IConceptCollection> conceptCollections);
	
	public abstract List<IDictionary> getDictionaries();
	
	public abstract void setDictionaries(List<IDictionary> dictionaries);
}