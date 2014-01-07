/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Karthik
 */
@Embeddable
public class NetworkAssignedDTOPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "networkid")
    private String networkid;
    @Basic(optional = false)
    @Column(name = "assigneduser")
    private String assigneduser;

    public NetworkAssignedDTOPK() {
    }

    public NetworkAssignedDTOPK(String networkid, String assigneduser) {
        this.networkid = networkid;
        this.assigneduser = assigneduser;
    }

    public String getNetworkid() {
        return networkid;
    }

    public void setNetworkid(String networkid) {
        this.networkid = networkid;
    }

    public String getAssigneduser() {
        return assigneduser;
    }

    public void setAssigneduser(String assigneduser) {
        this.assigneduser = assigneduser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (networkid != null ? networkid.hashCode() : 0);
        hash += (assigneduser != null ? assigneduser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NetworkAssignedDTOPK)) {
            return false;
        }
        NetworkAssignedDTOPK other = (NetworkAssignedDTOPK) object;
        if ((this.networkid == null && other.networkid != null) || (this.networkid != null && !this.networkid.equals(other.networkid))) {
            return false;
        }
        if ((this.assigneduser == null && other.assigneduser != null) || (this.assigneduser != null && !this.assigneduser.equals(other.assigneduser))) {
            return false;
        }
        return true;
    }
}
