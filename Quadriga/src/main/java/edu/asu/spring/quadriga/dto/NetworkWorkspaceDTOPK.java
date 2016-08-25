package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NetworkWorkspaceDTOPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @Column(name = "networkid")
    private String networkid;
	
	@Basic(optional = false)
    @Column(name = "workspaceid")
    private String workspaceid;
	
	public NetworkWorkspaceDTOPK(){
		
	}
	
	public NetworkWorkspaceDTOPK(String networkid, String workspaceid) {
		this.networkid = networkid;
		this.workspaceid = workspaceid;
	}
	public String getNetworkid() {
		return networkid;
	}
	public void setNetworkid(String networkid) {
		this.networkid = networkid;
	}
	public String getWorkspaceid() {
		return workspaceid;
	}
	public void setWorkspaceid(String workspaceid) {
		this.workspaceid = workspaceid;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((networkid == null) ? 0 : networkid.hashCode());
		result = prime * result
				+ ((workspaceid == null) ? 0 : workspaceid.hashCode());
		return result;
	}
	
	@Override
    public boolean equals(Object object) {
        if (!(object instanceof NetworkWorkspaceDTOPK)) {
            return false;
        }
        NetworkWorkspaceDTOPK other = (NetworkWorkspaceDTOPK) object;
        if ((this.networkid == null && other.networkid != null) || (this.networkid != null && !this.networkid.equals(other.networkid))) {
            return false;
        }
        if ((this.workspaceid == null && other.workspaceid != null) || (this.workspaceid != null && !this.workspaceid.equals(other.workspaceid))) {
            return false;
        }
        return true;
    }
}
