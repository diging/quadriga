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
 * This class represents the column mapping for workspace dspace table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_workspace_dspace")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkspaceDspaceDTO.findAll", query = "SELECT w FROM WorkspaceDspaceDTO w"),
    @NamedQuery(name = "WorkspaceDspaceDTO.findByWorkspaceid", query = "SELECT w FROM WorkspaceDspaceDTO w WHERE w.workspaceDspaceDTOPK.workspaceid = :workspaceid"),
    @NamedQuery(name = "WorkspaceDspaceDTO.findByBitstreamid", query = "SELECT w FROM WorkspaceDspaceDTO w WHERE w.workspaceDspaceDTOPK.bitstreamid = :bitstreamid"),
    })
public class WorkspaceDspaceDTO implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    protected WorkspaceDspaceDTOPK workspaceDspaceDTOPK;
    
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

    public WorkspaceDspaceDTO() {
    }

    public WorkspaceDspaceDTO(WorkspaceDspaceDTOPK workspaceDspaceDTOPK, String createdby, Date createddate) {
        this.workspaceDspaceDTOPK = workspaceDspaceDTOPK;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceDspaceDTO(String workspaceid, String bitstreamid, String itemHandle, String createdby, Date createddate) {
        this.workspaceDspaceDTOPK = new WorkspaceDspaceDTOPK(workspaceid, bitstreamid, itemHandle);
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceDspaceDTOPK getWorkspaceDspaceDTOPK() {
        return workspaceDspaceDTOPK;
    }

    public void setWorkspaceDspaceDTOPK(WorkspaceDspaceDTOPK workspaceDspaceDTOPK) {
        this.workspaceDspaceDTOPK = workspaceDspaceDTOPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workspaceDspaceDTOPK != null ? workspaceDspaceDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkspaceDspaceDTO)) {
            return false;
        }
        WorkspaceDspaceDTO other = (WorkspaceDspaceDTO) object;
        if ((this.workspaceDspaceDTOPK == null && other.workspaceDspaceDTOPK != null) || (this.workspaceDspaceDTOPK != null && !this.workspaceDspaceDTOPK.equals(other.workspaceDspaceDTOPK))) {
            return false;
        }
        return true;
    }
}
