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
@Table(name = "tbl_workspace_editor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkspaceEditorDTO.findAll", query = "SELECT w FROM WorkspaceEditorDTO w"),
    @NamedQuery(name = "WorkspaceEditorDTO.findByWorkspaceid", query = "SELECT w FROM WorkspaceEditorDTO w WHERE w.workspaceEditorDTOPK.workspaceid = :workspaceid"),
    @NamedQuery(name = "WorkspaceEditorDTO.findByOwner", query = "SELECT w FROM WorkspaceEditorDTO w WHERE w.workspaceEditorDTOPK.owner = :owner"),
    @NamedQuery(name = "WorkspaceEditorDTO.findByUpdatedby", query = "SELECT w FROM WorkspaceEditorDTO w WHERE w.updatedby = :updatedby"),
    @NamedQuery(name = "WorkspaceEditorDTO.findByUpdateddate", query = "SELECT w FROM WorkspaceEditorDTO w WHERE w.updateddate = :updateddate"),
    @NamedQuery(name = "WorkspaceEditorDTO.findByCreatedby", query = "SELECT w FROM WorkspaceEditorDTO w WHERE w.createdby = :createdby"),
    @NamedQuery(name = "WorkspaceEditorDTO.findByCreateddate", query = "SELECT w FROM WorkspaceEditorDTO w WHERE w.createddate = :createddate")})
public class WorkspaceEditorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkspaceEditorDTOPK workspaceEditorDTOPK;
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

    public WorkspaceEditorDTO() {
    }

    public WorkspaceEditorDTO(WorkspaceEditorDTOPK workspaceEditorDTOPK) {
        this.workspaceEditorDTOPK = workspaceEditorDTOPK;
    }

    public WorkspaceEditorDTO(WorkspaceEditorDTOPK workspaceEditorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.workspaceEditorDTOPK = workspaceEditorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceEditorDTO(String workspaceid, String owner) {
        this.workspaceEditorDTOPK = new WorkspaceEditorDTOPK(workspaceid, owner);
    }

    public WorkspaceEditorDTOPK getWorkspaceEditorDTOPK() {
        return workspaceEditorDTOPK;
    }

    public void setWorkspaceEditorDTOPK(WorkspaceEditorDTOPK workspaceEditorDTOPK) {
        this.workspaceEditorDTOPK = workspaceEditorDTOPK;
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
        hash += (workspaceEditorDTOPK != null ? workspaceEditorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkspaceEditorDTO)) {
            return false;
        }
        WorkspaceEditorDTO other = (WorkspaceEditorDTO) object;
        if ((this.workspaceEditorDTOPK == null && other.workspaceEditorDTOPK != null) || (this.workspaceEditorDTOPK != null && !this.workspaceEditorDTOPK.equals(other.workspaceEditorDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.WorkspaceEditorDTO[ workspaceEditorDTOPK=" + workspaceEditorDTOPK + " ]";
    }
    
}
