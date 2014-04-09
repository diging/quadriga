package edu.asu.spring.quadriga.domain.proxy;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;

public class ProjectProxy implements IProject {

	private String name;
	private String description;
	private String unixName;
	private String internalid;
	private List<ICollaborator> collaborators;
	private EProjectAccessibility projectAccess;
	private ENetworkAccessibility networksDefaultAccess;
	private IUser owner;
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;

	}

	@Override
	public String getUnixName() {
		return this.unixName;
	}

	@Override
	public void setUnixName(String unixName) {
		this.unixName  = unixName;

	}

	@Override
	public IUser getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;

	}

	@Override
	public List<ICollaborator> getCollaborators() {
		return this.collaborators;
	}

	@Override
	public void setCollaborators(List<ICollaborator> collaborators) {
		this.collaborators = collaborators;

	}

	@Override
	public EProjectAccessibility getProjectAccess() {
		return this.projectAccess;
	}

	@Override
	public void setProjectAccess(EProjectAccessibility projectAccess) {
		this.projectAccess = projectAccess;

	}

	@Override
	public ENetworkAccessibility getNetworksDefaultAccess() {
		return this.networksDefaultAccess;
	}

	@Override
	public void setNetworksDefaultAccess(
			ENetworkAccessibility networksDefaultAccess) {
		this.networksDefaultAccess = networksDefaultAccess;

	}

	@Override
	public void setProjectId(String internalid) {
		this.internalid = internalid;

	}

	@Override
	public String getProjectId() {
		return this.internalid;
	}

}
