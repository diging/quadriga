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
 * This class represents the column mapping for workspace editor table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_workspace_editor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkspaceEditorDTO.findAll", query = "SELECT w FROM WorkspaceEditorDTO w"),
    @NamedQuery(name = "WorkspaceEditorDTO.findByWorkspaceid", query = "SELECT w FROM WorkspaceEditorDTO w WHERE w.workspaceEditorDTOPK.workspaceid = :workspaceid"),
    @NamedQuery(name = "WorkspaceEditorDTO.findByEditor", query = "SELECT w FROM WorkspaceEditorDTO w WHERE w.workspaceEditorDTOPK.editor = :editor"),
    })
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
    @JoinColumn(name = "workspaceid", referencedColumnName = "workspaceid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private WorkspaceDTO workspaceDTO;
    @JoinColumn(name = "editor", referencedColumnName = "username",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private QuadrigaUserDTO quadrigaUserDTO;

	public WorkspaceEditorDTO() {
    }

    public WorkspaceEditorDTO(WorkspaceEditorDTOPK workspaceEditorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.workspaceEditorDTOPK = workspaceEditorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceEditorDTO(String workspaceid, String owner,String updatedby, Date updateddate, String createdby, Date createddate) {
        this.workspaceEditorDTOPK = new WorkspaceEditorDTOPK(workspaceid, owner);
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
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
        hash += (workspaceEditorDTOPK != null ? workspaceEditorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WorkspaceEditorDTO)) {
            return false;
        }
        WorkspaceEditorDTO other = (WorkspaceEditorDTO) object;
        if ((this.workspaceEditorDTOPK == null && other.workspaceEditorDTOPK != null) || (this.workspaceEditorDTOPK != null && !this.workspaceEditorDTOPK.equals(other.workspaceEditorDTOPK))) {
            return false;
        }
        return true;
    }
}
