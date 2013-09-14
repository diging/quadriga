package edu.asu.spring.quadriga.domain.implementation;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;

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
	private String projectid;
	private String workspaceName;
	private String projectName;
	private String name;
	private String status;
	private String assignedUser;
	private INetworkOldVersion networkOldVersion;
	private List<String> appellationIds;
	private List<String> relationIds;
	private ENetworkAccessibility networksAccess;
	
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
	public String getProjectid() {
		return projectid;
	}
	@Override
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	@Override
	public String getWorkspaceName() {
		return workspaceName;
	}
	@Override
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}
	
	@Override
	public String getProjectName() {
		return projectName;
	}
	@Override
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Override
	public String getAssignedUser() {
		return assignedUser;
	}
	@Override
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}
	
	@Override
	public INetworkOldVersion getNetworkOldVersion() {
		return networkOldVersion;
	}
	@Override
	public void setNetworkOldVersion(INetworkOldVersion networkOldVersion) {
		this.networkOldVersion = networkOldVersion;
	}
	
	
	
	
	
}
