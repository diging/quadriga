package edu.asu.spring.quadriga.domain.workspace;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;

/**
 * @description   : interface to implement WorkSpace class.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface IWorkspace 
{

	public abstract void setWorkspaceId(String workspaceId);

	public abstract String getWorkspaceId();
	
	public abstract void setWorkspaceName(String workspaceName);

	public abstract String getWorkspaceName();
	
	public abstract void setDescription(String description);

	public abstract String getDescription();
	
	public abstract void setOwner(IUser owner);

	public abstract IUser getOwner();
	
	public abstract List<IWorkspaceCollaborator> getWorkspaceCollaborators();
	
	public abstract void setWorkspaceCollaborators(List<IWorkspaceCollaborator> workspaceCollaborators);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

    public abstract void setExternalWorkspaceId(String externalWorkspaceId);

    public abstract String getExternalWorkspaceId();

    public abstract void setProject(IProject project);

    public abstract IProject getProject();

    void setNetworks(List<INetwork> networks);

    List<INetwork> getNetworks();

    void setDictionaries(List<IDictionary> dictionaries);

    List<IDictionary> getDictionaries();

    void setConceptCollections(List<IConceptCollection> conceptCollections);

    List<IConceptCollection> getConceptCollections();

}
