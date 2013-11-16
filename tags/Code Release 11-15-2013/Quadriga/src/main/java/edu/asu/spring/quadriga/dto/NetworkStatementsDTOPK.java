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
public class NetworkStatementsDTOPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "networkid")
    private String networkid;
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    public NetworkStatementsDTOPK() {
    }

    public NetworkStatementsDTOPK(String networkid, String id) {
        this.networkid = networkid;
        this.id = id;
    }

    public String getNetworkid() {
        return networkid;
    }

    public void setNetworkid(String networkid) {
        this.networkid = networkid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (networkid != null ? networkid.hashCode() : 0);
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NetworkStatementsDTOPK)) {
            return false;
        }
        NetworkStatementsDTOPK other = (NetworkStatementsDTOPK) object;
        if ((this.networkid == null && other.networkid != null) || (this.networkid != null && !this.networkid.equals(other.networkid))) {
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.NetworkStatementsDTOPK[ networkid=" + networkid + ", id=" + id + " ]";
    }
    
}
