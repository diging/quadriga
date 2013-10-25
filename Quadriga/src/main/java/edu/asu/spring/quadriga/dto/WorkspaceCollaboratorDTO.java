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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tbl_workspace_collaborator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findAll", query = "SELECT w FROM WorkspaceCollaboratorDTO w"),
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findByWorkspaceid", query = "SELECT w FROM WorkspaceCollaboratorDTO w WHERE w.workspaceCollaboratorDTOPK.workspaceid = :workspaceid"),
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findByUsername", query = "SELECT w FROM WorkspaceCollaboratorDTO w WHERE w.workspaceCollaboratorDTOPK.username = :username"),
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findByCollaboratorrole", query = "SELECT w FROM WorkspaceCollaboratorDTO w WHERE w.workspaceCollaboratorDTOPK.collaboratorrole = :collaboratorrole"),
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findByUpdatedby", query = "SELECT w FROM WorkspaceCollaboratorDTO w WHERE w.updatedby = :updatedby"),
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findByUpdateddate", query = "SELECT w FROM WorkspaceCollaboratorDTO w WHERE w.updateddate = :updateddate"),
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findByCreatedby", query = "SELECT w FROM WorkspaceCollaboratorDTO w WHERE w.createdby = :createdby"),
    @NamedQuery(name = "WorkspaceCollaboratorDTO.findByCreateddate", query = "SELECT w FROM WorkspaceCollaboratorDTO w WHERE w.createddate = :createddate")})
public class WorkspaceCollaboratorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkspaceCollaboratorDTOPK workspaceCollaboratorDTOPK;
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
    @JoinColumn(name = "workspaceid", referencedColumnName = "workspaceid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private WorkspaceDTO workspaceDTO;
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private QuadrigaUserDTO quadrigaUserDTO;

    public WorkspaceCollaboratorDTO() {
    }

    public WorkspaceCollaboratorDTO(WorkspaceCollaboratorDTOPK workspaceCollaboratorDTOPK) {
        this.workspaceCollaboratorDTOPK = workspaceCollaboratorDTOPK;
    }

    public WorkspaceCollaboratorDTO(WorkspaceCollaboratorDTOPK workspaceCollaboratorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.workspaceCollaboratorDTOPK = workspaceCollaboratorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceCollaboratorDTO(String workspaceid, String username, String collaboratorrole) {
        this.workspaceCollaboratorDTOPK = new WorkspaceCollaboratorDTOPK(workspaceid, username, collaboratorrole);
    }

    public WorkspaceCollaboratorDTOPK getWorkspaceCollaboratorDTOPK() {
        return workspaceCollaboratorDTOPK;
    }

    public void setWorkspaceCollaboratorDTOPK(WorkspaceCollaboratorDTOPK workspaceCollaboratorDTOPK) {
        this.workspaceCollaboratorDTOPK = workspaceCollaboratorDTOPK;
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

    public WorkspaceDTO getWorkspaceDTO() {
        return workspaceDTO;
    }

    public void setWorkspaceDTO(WorkspaceDTO workspaceDTO) {
        this.workspaceDTO = workspaceDTO;
    }

    public QuadrigaUserDTO getQuadrigaUserDTO() {
        return quadrigaUserDTO;
    }

    public void setQuadrigaUserDTO(QuadrigaUserDTO quadrigaUserDTO) {
        this.quadrigaUserDTO = quadrigaUserDTO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workspaceCollaboratorDTOPK != null ? workspaceCollaboratorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkspaceCollaboratorDTO)) {
            return false;
        }
        WorkspaceCollaboratorDTO other = (WorkspaceCollaboratorDTO) object;
        if ((this.workspaceCollaboratorDTOPK == null && other.workspaceCollaboratorDTOPK != null) || (this.workspaceCollaboratorDTOPK != null && !this.workspaceCollaboratorDTOPK.equals(other.workspaceCollaboratorDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.WorkspaceCollaboratorDTO[ workspaceCollaboratorDTOPK=" + workspaceCollaboratorDTOPK + " ]";
    }
    
}
