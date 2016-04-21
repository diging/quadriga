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
 *This class represents the primary key column mappings for
 *quadriga user profile table.
 * @author Karthik
 */
@Embeddable
public class QuadrigaUserprofileDTOPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "serviceid")
    private String serviceid;
    @Basic(optional = false)
    @Column(name = "profileid")
    private String profileid;

    public QuadrigaUserprofileDTOPK() {
    }

    public QuadrigaUserprofileDTOPK(String username, String serviceid, String profileid) {
        this.username = username;
        this.serviceid = serviceid;
        this.profileid = profileid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getProfileid() {
		return profileid;
	}

	public void setProfileid(String profileid) {
		this.profileid = profileid;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        hash += (serviceid != null ? serviceid.hashCode() : 0);
        hash += (profileid != null ? profileid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof QuadrigaUserprofileDTOPK)) {
            return false;
        }
        QuadrigaUserprofileDTOPK other = (QuadrigaUserprofileDTOPK) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        if ((this.serviceid == null && other.serviceid != null) || (this.serviceid != null && !this.serviceid.equals(other.serviceid))) {
            return false;
        }
        if ((this.profileid == null && other.profileid != null) || (this.profileid != null && !this.profileid.equals(other.profileid))) {
            return false;
        }
        return true;
    }
}
