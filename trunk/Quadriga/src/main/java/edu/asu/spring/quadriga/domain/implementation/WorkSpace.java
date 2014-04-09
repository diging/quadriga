package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;

/**
 * @description : WorkSpace class describing the properties 
 *                of a WorkSpace object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class WorkSpace implements IWorkSpace 
{
	private String workspaceId;
	private String workspaceName;
	private String description;
	private IUser owner;
	private List<ICollaborator> collaborators;
	private IProject project;
	private List<IBitStream> bitStreams;
	private List<IConceptCollection> conceptCollections;
	private List<IDictionary> dictionaries;
	private List<INetwork> networks;
	
	@Override
	public String getWorkspaceId() {
		return workspaceId;
	}
	@Override
	public void setWorkspaceId(String id) {
		this.workspaceId = id;
	}
	
	@Override
	public String getWorkspaceName() {
		return workspaceName;
	}
	@Override
	public void setWorkspaceName(String name) {
		this.workspaceName = name;
	}
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public IUser getOwner() {
		return owner;
	}
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
	}
	@Override
	public List<ICollaborator> getCollaborators() {
		return collaborators;
	}
	@Override
	public void setCollaborators(List<ICollaborator> collaborators) {
		this.collaborators = collaborators;
	}
	
	@Override
	public IProject getProject() {
		return project;
	}
	@Override
	public void setProject(IProject project) {
		this.project = project;
	}
	
	@Override
	public List<IBitStream> getBitstreams() {
		return bitStreams;
	}
	@Override
	public void setBitstreams(List<IBitStream> bitstreams) {
		this.bitStreams = bitstreams;
	}

	@Override
	public List<IConceptCollection> getConceptCollections() {
		return conceptCollections;
	}
	@Override
	public void setConceptCollections(
			List<IConceptCollection> conceptCollections) {
		this.conceptCollections = conceptCollections;
	}
	@Override
	public List<IDictionary> getDictionaries() {
		return dictionaries;
	}
	@Override
	public void setDictionaries(List<IDictionary> dictionaries) {
		this.dictionaries = dictionaries;
	}
	@Override
	public List<INetwork> getNetworks() {
		return networks;
	}
	@Override
	public void setNetworks(List<INetwork> networks) {
		this.networks = networks;
	}
	
}
