package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.INetworkVersions;

public class NetworkVersions implements INetworkVersions {
	
	private String networkname;
	private int versionnumber;
	private String status;
	private String assigneduser;
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkVersions#getNetworkname()
	 */
	@Override
	public String getNetworkname() {
		return networkname;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkVersions#setNetworkname(java.lang.String)
	 */
	@Override
	public void setNetworkname(String networkname) {
		this.networkname = networkname;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkVersions#getVersionnumber()
	 */
	@Override
	public int getVersionnumber() {
		return versionnumber;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkVersions#setVersionnumber(int)
	 */
	@Override
	public void setVersionnumber(int versionnumber) {
		this.versionnumber = versionnumber;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkVersions#getStatus()
	 */
	@Override
	public String getStatus() {
		return status;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkVersions#setStatus(java.lang.String)
	 */
	@Override
	public void setStatus(String status) {
		this.status = status;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkVersions#getAssigneduser()
	 */
	@Override
	public String getAssigneduser() {
		return assigneduser;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.INetworkVersions#setAssigneduser(java.lang.String)
	 */
	@Override
	public void setAssigneduser(String assigneduser) {
		this.assigneduser = assigneduser;
	}
	

}
