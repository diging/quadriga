package edu.asu.spring.quadriga.domain.proxy;

import java.util.List;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

public class WorkspaceProxy implements IWorkSpace {
	
	private String workspaceId;
	private String workspaceName;
	private String description;
	private IListWSManager wsManager;

	public IListWSManager getWsManager() {
		return wsManager;
	}

	public void setWsManager(IListWSManager wsManager) {
		this.wsManager = wsManager;
	}

	@Override
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	@Override
	public String getWorkspaceId() {
		return this.workspaceId;
	}

	@Override
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
		
	}

	@Override
	public String getWorkspaceName() {
		return this.workspaceName;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
		
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setOwner(IUser owner) {
		
	}

	@Override
	public IUser getOwner() {
		return null;
	}

	@Override
	public void setCollaborators(List<ICollaborator> collaborators) {
		
	}

	@Override
	public List<ICollaborator> getCollaborators() {
		return null;
	}

	@Override
	public IProject getProject() {
		return null;
	}

	@Override
	public void setProject(IProject project) {
		
	}

	@Override
	public void setBitstreams(List<IBitStream> bitstreams) {
		
	}

	@Override
	public List<IBitStream> getBitstreams() {
		return null;
	}

	@Override
	public List<IConceptCollection> getConceptCollections() {
		return null;
	}

	@Override
	public void setConceptCollections(
			List<IConceptCollection> conceptCollections) {
		
	}

	@Override
	public List<IDictionary> getDictionaries() {
		return null;
	}

	@Override
	public void setDictionaries(List<IDictionary> dictionaries) {
		
	}

	@Override
	public List<INetwork> getNetworks() {
		return null;
	}

	@Override
	public void setNetworks(List<INetwork> networks) {
		
	}

}
