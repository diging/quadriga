package edu.asu.spring.quadriga.domain.workspace;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;

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
	
	public abstract List<IWorkspaceCollaborator> getWorkspaceCollaborators();
	
	public abstract void setWorkspaceCollaborators(List<IWorkspaceCollaborator> workspaceCollaborators);
	
	public abstract IProjectWorkspace getProjectWorkspace();
	
	public abstract void setProjectWorkspace(IProjectWorkspace projectWorkspace);
	
    public abstract List<IWorkspaceConceptCollection> getWorkspaceConceptCollections();
    
    public abstract void setWorkspaceConceptCollections(List<IWorkspaceConceptCollection> workspaceConceptCollections);
	
    public abstract  List<IWorkspaceDictionary> getWorkspaceDictinaries();
    
    public abstract void setWorkspaceDictionaries(List<IWorkspaceDictionary> workspaceDictionaries);
    
    public abstract List<IWorkspaceNetwork> getWorkspaceNetworks();
    
    public abstract void setWorkspaceNetworks(List<IWorkspaceNetwork> workspaceNetworks);
    
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

}
