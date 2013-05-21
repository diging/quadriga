package edu.asu.spring.quadriga.domain;

import java.util.List;

import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;

/**
 * @description   : interface to implement Project class.
 * 
 * @author        : Kiran
 *
 */
public interface IProject {

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getInternalid();

	public abstract void setInternalid(String internalid);

	public abstract IUser getOwner();

	public abstract void setOwner(IUser owner);

	public abstract List<ICollaborator> getCollaborators();

	public abstract void setCollaborators(List<ICollaborator> collaborators);

	public abstract EProjectAccessibility getProjectAccess();

	public abstract void setProjectAccess(EProjectAccessibility projectAccess);

	public abstract ENetworkAccessibility getNetworksDefaultAccess();

	public abstract void setNetworksDefaultAccess(
			ENetworkAccessibility networksDefaultAccess);

}