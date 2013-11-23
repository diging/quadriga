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
public class WorkspaceConceptcollectionDTOPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "workspaceid")
    private String workspaceid;
    @Basic(optional = false)
    @Column(name = "conceptcollectionid")
    private String conceptcollectionid;

    public WorkspaceConceptcollectionDTOPK() {
    }

    public WorkspaceConceptcollectionDTOPK(String workspaceid, String conceptcollectionid) {
        this.workspaceid = workspaceid;
        this.conceptcollectionid = conceptcollectionid;
    }

    public String getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(String workspaceid) {
        this.workspaceid = workspaceid;
    }

    public String getConceptcollectionid() {
        return conceptcollectionid;
    }

    public void setConceptcollectionid(String conceptcollectionid) {
        this.conceptcollectionid = conceptcollectionid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workspaceid != null ? workspaceid.hashCode() : 0);
        hash += (conceptcollectionid != null ? conceptcollectionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkspaceConceptcollectionDTOPK)) {
            return false;
        }
        WorkspaceConceptcollectionDTOPK other = (WorkspaceConceptcollectionDTOPK) object;
        if ((this.workspaceid == null && other.workspaceid != null) || (this.workspaceid != null && !this.workspaceid.equals(other.workspaceid))) {
            return false;
        }
        if ((this.conceptcollectionid == null && other.conceptcollectionid != null) || (this.conceptcollectionid != null && !this.conceptcollectionid.equals(other.conceptcollectionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.WorkspaceConceptcollectionDTOPK[ workspaceid=" + workspaceid + ", conceptcollectionid=" + conceptcollectionid + " ]";
    }
    
}
