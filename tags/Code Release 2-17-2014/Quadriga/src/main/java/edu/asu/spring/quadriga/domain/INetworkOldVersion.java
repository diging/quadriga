package edu.asu.spring.quadriga.domain;

/**
 * Interface to represent the version management in network
 *
 */
public interface INetworkOldVersion {

	public abstract String getPreviousVersionStatus();

	public abstract void setPreviousVersionStatus(String previousVersionStatus);

	public abstract String getPreviousVersionAssignedUser();

	public abstract void setPreviousVersionAssignedUser(
			String previousVersionAssignedUser);

	public abstract String getUpdateDate();

	public abstract void setUpdateDate(String updateDate);

}