package edu.asu.spring.quadriga.domain.implementation;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;

/**
 * @description : Network class describing the properties 
 *                of a Network object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class Network implements INetwork 
{
	private String id;
	private IUser creator;
	private Date creationTime;
	private String textUrl;
	private String workspaceid;
	private String name;
	private String status;
	private String assignedUser;
	private List<String> appellationIds;
	private List<String> relationIds;
	private ENetworkAccessibility networksAccess;
	private IProject project;
	private IWorkSpace workspace;
	private int versionNumber;
	
	public int getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
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
	public IWorkSpace getWorkspace() {
		return workspace;
	}
	@Override
	public void setWorkspace(IWorkSpace workspace) {
		this.workspace = workspace;
	}
	@Override
	public List<String> getAppellationIds() {
		return appellationIds;
	}
	@Override
	public void setAppellationIds(List<String> appellationIds) {
		this.appellationIds = appellationIds;
	}
	@Override
	public List<String> getRelationIds() {
		return relationIds;
	}
	@Override
	public void setRelationIds(List<String> relationIds) {
		this.relationIds = relationIds;
	}
	@Override
	public ENetworkAccessibility getNetworksAccess() {
		return networksAccess;
	}
	@Override
	public void setNetworksAccess(ENetworkAccessibility networksAccess) {
		this.networksAccess = networksAccess;
	}
	
	@Override
	public IUser getCreator() {
		return creator;
	}
	@Override
	public void setCreator(IUser creator) {
		this.creator = creator;
	}

	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getStatus() {
		return status;
	}
	@Override
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public Date getCreationTime() {
		return creationTime;
	}
	@Override
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	@Override
	public String getTextUrl() {
		return textUrl;
	}
	@Override
	public void setTextUrl(String textUrl) {
		this.textUrl = textUrl;
	}
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getWorkspaceid() {
		return workspaceid;
	}
	@Override
	public void setWorkspaceid(String workspaceid) {
		this.workspaceid = workspaceid;
	}
	
	@Override
	public String getAssignedUser() {
		return assignedUser;
	}
	@Override
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	
	
	
}
