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

/**
 * This class represents the column mappings for
 * workspace dictionary table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_workspace_dictionary")
@NamedQueries({
    @NamedQuery(name = "WorkspaceDictionaryDTO.findAll", query = "SELECT w FROM WorkspaceDictionaryDTO w"),
    @NamedQuery(name = "WorkspaceDictionaryDTO.findByWorkspaceid", query = "SELECT w FROM WorkspaceDictionaryDTO w WHERE w.workspaceDictionaryDTOPK.workspaceid = :workspaceid"),
    @NamedQuery(name = "WorkspaceDictionaryDTO.findByDictionaryid", query = "SELECT w FROM WorkspaceDictionaryDTO w WHERE w.workspaceDictionaryDTOPK.dictionaryid = :dictionaryid"),
    })
public class WorkspaceDictionaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkspaceDictionaryDTOPK workspaceDictionaryDTOPK;
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
    @JoinColumn(name = "dictionaryid", referencedColumnName = "dictionaryid", insertable = false , updatable = false)
    @ManyToOne(optional = false)
    private DictionaryDTO dictionaryDTO;
    
	public WorkspaceDictionaryDTO() {
    }

    public WorkspaceDictionaryDTO(WorkspaceDictionaryDTOPK workspaceDictionaryDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.workspaceDictionaryDTOPK = workspaceDictionaryDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceDictionaryDTO(String workspaceid, String dictionaryid,String updatedby, Date updateddate, String createdby, Date createddate) {
        this.workspaceDictionaryDTOPK = new WorkspaceDictionaryDTOPK(workspaceid, dictionaryid);
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceDictionaryDTOPK getWorkspaceDictionaryDTOPK() {
        return workspaceDictionaryDTOPK;
    }

    public void setWorkspaceDictionaryDTOPK(WorkspaceDictionaryDTOPK workspaceDictionaryDTOPK) {
        this.workspaceDictionaryDTOPK = workspaceDictionaryDTOPK;
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

	public DictionaryDTO getDictionaryDTO() {
		return dictionaryDTO;
	}

	public void setDictionaryDTO(DictionaryDTO dictionaryDTO) {
		this.dictionaryDTO = dictionaryDTO;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workspaceDictionaryDTOPK != null ? workspaceDictionaryDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkspaceDictionaryDTO)) {
            return false;
        }
        WorkspaceDictionaryDTO other = (WorkspaceDictionaryDTO) object;
        if ((this.workspaceDictionaryDTOPK == null && other.workspaceDictionaryDTOPK != null) || (this.workspaceDictionaryDTOPK != null && !this.workspaceDictionaryDTOPK.equals(other.workspaceDictionaryDTOPK))) {
            return false;
        }
        return true;
    }
}
