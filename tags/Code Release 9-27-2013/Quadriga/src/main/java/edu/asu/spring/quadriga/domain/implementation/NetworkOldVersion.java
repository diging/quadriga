package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.INetworkOldVersion;

public class NetworkOldVersion implements INetworkOldVersion {

	private String previousVersionAssignedUser;
	private String previousVersionStatus;

	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkOldVersion#getPreviousVersionStatus()
	 */
	@Override
	public String getPreviousVersionStatus() {
		return previousVersionStatus;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkOldVersion#setPreviousVersionStatus(java.lang.String)
	 */
	@Override
	public void setPreviousVersionStatus(String previousVersionStatus) {
		this.previousVersionStatus = previousVersionStatus;
	}
	

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkOldVersion#getPreviousVersionAssignedUser()
	 */
	@Override
	public String getPreviousVersionAssignedUser() {
		return previousVersionAssignedUser;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkOldVersion#setPreviousVersionAssignedUser(java.lang.String)
	 */
	@Override
	public void setPreviousVersionAssignedUser(String previousVersionAssignedUser) {
		this.previousVersionAssignedUser = previousVersionAssignedUser;
	}
}
