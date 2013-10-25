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
public class WorkspaceDspaceDTOPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "workspaceid")
    private String workspaceid;
    @Basic(optional = false)
    @Column(name = "bitstreamid")
    private String bitstreamid;

    public WorkspaceDspaceDTOPK() {
    }

    public WorkspaceDspaceDTOPK(String workspaceid, String bitstreamid) {
        this.workspaceid = workspaceid;
        this.bitstreamid = bitstreamid;
    }

    public String getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(String workspaceid) {
        this.workspaceid = workspaceid;
    }

    public String getBitstreamid() {
        return bitstreamid;
    }

    public void setBitstreamid(String bitstreamid) {
        this.bitstreamid = bitstreamid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workspaceid != null ? workspaceid.hashCode() : 0);
        hash += (bitstreamid != null ? bitstreamid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkspaceDspaceDTOPK)) {
            return false;
        }
        WorkspaceDspaceDTOPK other = (WorkspaceDspaceDTOPK) object;
        if ((this.workspaceid == null && other.workspaceid != null) || (this.workspaceid != null && !this.workspaceid.equals(other.workspaceid))) {
            return false;
        }
        if ((this.bitstreamid == null && other.bitstreamid != null) || (this.bitstreamid != null && !this.bitstreamid.equals(other.bitstreamid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.WorkspaceDspaceDTOPK[ workspaceid=" + workspaceid + ", bitstreamid=" + bitstreamid + " ]";
    }
    
}
