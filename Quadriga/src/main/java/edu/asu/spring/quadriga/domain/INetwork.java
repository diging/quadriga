package edu.asu.spring.quadriga.domain;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;

/**
 * @description   : interface to implement Network class.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface INetwork 
{

	public abstract void setTextUrl(String textUrl);

	public abstract String getTextUrl();

	public abstract void setCreationTime(Date creationTime);

	public abstract Date getCreationTime();

	public abstract void setCreator(IUser creator);

	public abstract IUser getCreator();

	public abstract void setNetworksAccess(ENetworkAccessibility networksAccess);

	public abstract ENetworkAccessibility getNetworksAccess();

	public abstract void setRelationIds(List<String> relationIds);

	public abstract List<String> getRelationIds();

	public abstract void setAppellationIds(List<String> appellationIds);

	public abstract List<String> getAppellationIds();

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getWorkspaceid();

	public abstract void setWorkspaceid(String workspaceid);

	public abstract String getProjectid();

	public abstract void setProjectid(String projectid);

	public abstract String getWorkspaceName();

	public abstract void setWorkspaceName(String workspaceName);

	public abstract String getProjectName();

	public abstract void setProjectName(String projectName);

	public abstract String getAssignedUser();

	public abstract void setAssignedUser(String assignedUser);


}
