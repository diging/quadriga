package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.INetworkOldVersion;

public class NetworkOldVersion implements INetworkOldVersion {

	private String previousVersionAssignedUser;
	private String previousVersionStatus;
	private String updateDate;

	
	/**
	 * This method retrieves the previous version status
	 * @author Lohith Dwaraka
	 */
	@Override
	public String getPreviousVersionStatus() {
		return previousVersionStatus;
	}

	/**
	 * This method assigns the previous version status
	 * @author Lohith Dwaraka
	 */
	@Override
	public void setPreviousVersionStatus(String previousVersionStatus) {
		this.previousVersionStatus = previousVersionStatus;
	}
	
	
	/**
	 * The method retrieves the update date
	 * @author Lohith Dwaraka
	 */
	@Override
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * This method assigns the update date
	 * @author Lohith Dwaraka
	 */
	@Override
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * This method retrieve the assigned user of previous network version
	 * @author Lohith Dwaraka
	 */
	@Override
	public String getPreviousVersionAssignedUser() {
		return previousVersionAssignedUser;
	}

	/**
	 * This method assigns the user to the previous network version
	 * @author Lohith Dwaraka
	 */
	@Override
	public void setPreviousVersionAssignedUser(String previousVersionAssignedUser) {
		this.previousVersionAssignedUser = previousVersionAssignedUser;
	}
}
