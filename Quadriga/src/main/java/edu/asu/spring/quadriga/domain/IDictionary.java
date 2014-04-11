package edu.asu.spring.quadriga.domain;

import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
/**
 * Interface to implement Dictionary.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface IDictionary 
{

	public abstract void setDictionaryId(String id);

	public abstract String getDictionaryId();
	
	public abstract void setDictionaryName(String dictionaryName);

	public abstract String getDictionaryName();
	
	public abstract void setDescription(String description);

	public abstract String getDescription();
	
	public abstract void setOwner(IUser owner);

	public abstract IUser getOwner();
	
	public abstract void setCollaborators(List<ICollaborator> collaborators);

	public abstract List<ICollaborator> getCollaborators();
	
	public abstract List<IDictionaryItem> getDictionaryItems();
	
	public abstract void setDictionaryItems(List<IDictionaryItem> dictionaryItems);
	
	public abstract List<IProject> getProjects();
	
	public abstract void setProjects(List<IProject> projects);
	
	public abstract List<IWorkSpace> getWorkspaces();
	
	public abstract void setWorkspaces(List<IWorkSpace> workspaces);
}
