package edu.asu.spring.quadriga.domain.workspace;

import java.util.Date;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;

/**
 * @description   : interface to implement WorkSpace class.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface IWorkSpace 
{

	public abstract void setWorkspaceId(String workspaceId);

	public abstract String getWorkspaceId();
	
	public abstract void setWorkspaceName(String workspaceName);

	public abstract String getWorkspaceName();
	
	public abstract void setDescription(String description);

	public abstract String getDescription();
	
	public abstract void setOwner(IUser owner);

	public abstract IUser getOwner();
	
	public abstract IWorkspaceCollaborator getWorkspaceCollaborators();
	
	public abstract void setWorkspaceCollaborators(IWorkspaceCollaborator workspaceCollaborator);
	
	public abstract IProject getProject();
	
	public abstract void setProject(IProject project);
	
	public abstract IWorkspaceBitStream getWorkspaceBitStreams();
	
	public abstract void setWorkspaceBitStreams(IWorkspaceBitStream workspaceBitStream);
	
    public abstract IWorkspaceConceptCollection getWorkspaceConceptCollections();
    
    public abstract void setWorkspaceConceptCollections(IWorkspaceConceptCollection workspaceConceptCollection);
	
    public abstract  IWorkspaceDictionary getWorkspaceDictinaries();
    
    public abstract void setWorkspaceDictionaries(IWorkspaceDictionary workspaceDictionary);
    
    public abstract IWorkspaceNetwork getWorkspaceNetworks();
    
    public abstract void setWorkspaceNetworks(IWorkspaceNetwork workspaceNetwork);
    
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

}
