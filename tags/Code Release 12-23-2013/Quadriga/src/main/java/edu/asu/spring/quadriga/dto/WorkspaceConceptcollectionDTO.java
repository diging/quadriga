/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Karthik
 */
@Entity
@Table(name = "tbl_workspace_conceptcollection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkspaceConceptcollectionDTO.findAll", query = "SELECT w FROM WorkspaceConceptcollectionDTO w"),
    @NamedQuery(name = "WorkspaceConceptcollectionDTO.findByWorkspaceid", query = "SELECT w FROM WorkspaceConceptcollectionDTO w WHERE w.workspaceConceptcollectionDTOPK.workspaceid = :workspaceid"),
    @NamedQuery(name = "WorkspaceConceptcollectionDTO.findByConceptcollectionid", query = "SELECT w FROM WorkspaceConceptcollectionDTO w WHERE w.workspaceConceptcollectionDTOPK.conceptcollectionid = :conceptcollectionid"),
    @NamedQuery(name = "WorkspaceConceptcollectionDTO.findByUpdatedby", query = "SELECT w FROM WorkspaceConceptcollectionDTO w WHERE w.updatedby = :updatedby"),
    @NamedQuery(name = "WorkspaceConceptcollectionDTO.findByUpdateddate", query = "SELECT w FROM WorkspaceConceptcollectionDTO w WHERE w.updateddate = :updateddate"),
    @NamedQuery(name = "WorkspaceConceptcollectionDTO.findByCreatedby", query = "SELECT w FROM WorkspaceConceptcollectionDTO w WHERE w.createdby = :createdby"),
    @NamedQuery(name = "WorkspaceConceptcollectionDTO.findByCreateddate", query = "SELECT w FROM WorkspaceConceptcollectionDTO w WHERE w.createddate = :createddate")})
public class WorkspaceConceptcollectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkspaceConceptcollectionDTOPK workspaceConceptcollectionDTOPK;
    @Basic(optional = false)
    @Column(name = "updatedby")
    private String updatedby;
    @Basic(optional = false)
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate;
    @Basic(optional = false)
    @Column(name = "createdby")
    private String createdby;
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;

    public WorkspaceConceptcollectionDTO() {
    }

    public WorkspaceConceptcollectionDTO(WorkspaceConceptcollectionDTOPK workspaceConceptcollectionDTOPK) {
        this.workspaceConceptcollectionDTOPK = workspaceConceptcollectionDTOPK;
    }

    public WorkspaceConceptcollectionDTO(WorkspaceConceptcollectionDTOPK workspaceConceptcollectionDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.workspaceConceptcollectionDTOPK = workspaceConceptcollectionDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceConceptcollectionDTO(String workspaceid, String conceptcollectionid) {
        this.workspaceConceptcollectionDTOPK = new WorkspaceConceptcollectionDTOPK(workspaceid, conceptcollectionid);
    }

    public WorkspaceConceptcollectionDTOPK getWorkspaceConceptcollectionDTOPK() {
        return workspaceConceptcollectionDTOPK;
    }

    public void setWorkspaceConceptcollectionDTOPK(WorkspaceConceptcollectionDTOPK workspaceConceptcollectionDTOPK) {
        this.workspaceConceptcollectionDTOPK = workspaceConceptcollectionDTOPK;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
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
        hash += (workspaceConceptcollectionDTOPK != null ? workspaceConceptcollectionDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkspaceConceptcollectionDTO)) {
            return false;
        }
        WorkspaceConceptcollectionDTO other = (WorkspaceConceptcollectionDTO) object;
        if ((this.workspaceConceptcollectionDTOPK == null && other.workspaceConceptcollectionDTOPK != null) || (this.workspaceConceptcollectionDTOPK != null && !this.workspaceConceptcollectionDTOPK.equals(other.workspaceConceptcollectionDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.WorkspaceConceptcollectionDTO[ workspaceConceptcollectionDTOPK=" + workspaceConceptcollectionDTOPK + " ]";
    }
    
}
