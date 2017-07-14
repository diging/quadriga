package edu.asu.spring.quadriga.domain.dictionary;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
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
	
	public abstract List<IDictionaryCollaborator> getDictionaryCollaborators() throws QuadrigaStorageException;
	
	public abstract void setDictionaryCollaborators(List<IDictionaryCollaborator> dictionaryCollaborators) throws QuadrigaStorageException;
	
	public abstract List<IDictionaryItems> getDictionaryItems() throws QuadrigaStorageException;
		
	public abstract void setDictionaryItems(List<IDictionaryItems> dictionaryItems) throws QuadrigaStorageException;
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

    void setWorkspaces(List<IWorkspace> workspaces);

    List<IWorkspace> getWorkspaces();

    void setProjects(List<IProject> projects);

    List<IProject> getProjects();
}
