package edu.asu.spring.quadriga.domain;

import java.util.List;

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
	
	public abstract void setCollaborators(List<ICollaborator> collaborators);

	public abstract List<ICollaborator> getCollaborators();
	
	public abstract IProject getProject();
	
	public abstract void setProject(IProject project);

	public abstract void setBitstreams(List<IBitStream> bitstreams);

	public abstract List<IBitStream> getBitstreams();
	
	public abstract List<IConceptCollection> getConceptCollections();
	
	public abstract void setConceptCollections(List<IConceptCollection> conceptCollections);
	
	public abstract List<IDictionary> getDictionaries();
	
	public abstract void setDictionaries(List<IDictionary> dictionaries);
	
	public abstract List<INetwork> getNetworks();
	
	public abstract void setNetworks(List<INetwork> networks);

}
