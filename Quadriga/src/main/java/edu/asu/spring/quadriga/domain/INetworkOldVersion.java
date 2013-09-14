package edu.asu.spring.quadriga.domain;

public interface INetworkOldVersion {

	public abstract String getPreviousVersionStatus();

	public abstract void setPreviousVersionStatus(String previousVersionStatus);

	public abstract String getPreviousVersionAssignedUser();

	public abstract void setPreviousVersionAssignedUser(
			String previousVersionAssignedUser);

}