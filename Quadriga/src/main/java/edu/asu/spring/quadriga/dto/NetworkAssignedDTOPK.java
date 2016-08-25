/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *This class represents primary key column mappings 
 *for network assigned table
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
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;

    public NetworkAssignedDTOPK() {
    }

    public NetworkAssignedDTOPK(String networkid, String assigneduser,Date date) {
        this.networkid = networkid;
        this.assigneduser = assigneduser;
        this.createddate = date;
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
    
    public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (networkid != null ? networkid.hashCode() : 0);
        hash += (assigneduser != null ? assigneduser.hashCode() : 0);
        hash += (createddate != null ? createddate.hashCode() : 0);
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
        if ((this.createddate == null && other.createddate != null) || (this.createddate != null && !this.createddate.equals(other.createddate))) {
            return false;
        }
        return true;
    }
}
