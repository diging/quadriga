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
@Table(name = "tbl_workspace_dspace")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkspaceDspaceDTO.findAll", query = "SELECT w FROM WorkspaceDspaceDTO w"),
    @NamedQuery(name = "WorkspaceDspaceDTO.findByWorkspaceid", query = "SELECT w FROM WorkspaceDspaceDTO w WHERE w.workspaceDspaceDTOPK.workspaceid = :workspaceid"),
    @NamedQuery(name = "WorkspaceDspaceDTO.findByCommunityid", query = "SELECT w FROM WorkspaceDspaceDTO w WHERE w.communityid = :communityid"),
    @NamedQuery(name = "WorkspaceDspaceDTO.findByCollectionid", query = "SELECT w FROM WorkspaceDspaceDTO w WHERE w.collectionid = :collectionid"),
    @NamedQuery(name = "WorkspaceDspaceDTO.findByItemid", query = "SELECT w FROM WorkspaceDspaceDTO w WHERE w.itemid = :itemid"),
    @NamedQuery(name = "WorkspaceDspaceDTO.findByBitstreamid", query = "SELECT w FROM WorkspaceDspaceDTO w WHERE w.workspaceDspaceDTOPK.bitstreamid = :bitstreamid"),
    @NamedQuery(name = "WorkspaceDspaceDTO.findByCreatedby", query = "SELECT w FROM WorkspaceDspaceDTO w WHERE w.createdby = :createdby"),
    @NamedQuery(name = "WorkspaceDspaceDTO.findByCreateddate", query = "SELECT w FROM WorkspaceDspaceDTO w WHERE w.createddate = :createddate")})
public class WorkspaceDspaceDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkspaceDspaceDTOPK workspaceDspaceDTOPK;
    @Basic(optional = false)
    @Column(name = "communityid")
    private String communityid;
    @Basic(optional = false)
    @Column(name = "collectionid")
    private String collectionid;
    @Basic(optional = false)
    @Column(name = "itemid")
    private String itemid;
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

    public WorkspaceDspaceDTO(WorkspaceDspaceDTOPK workspaceDspaceDTOPK) {
        this.workspaceDspaceDTOPK = workspaceDspaceDTOPK;
    }

    public WorkspaceDspaceDTO(WorkspaceDspaceDTOPK workspaceDspaceDTOPK, String communityid, String collectionid, String itemid, String createdby, Date createddate) {
        this.workspaceDspaceDTOPK = workspaceDspaceDTOPK;
        this.communityid = communityid;
        this.collectionid = collectionid;
        this.itemid = itemid;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public WorkspaceDspaceDTO(String workspaceid, String bitstreamid) {
        this.workspaceDspaceDTOPK = new WorkspaceDspaceDTOPK(workspaceid, bitstreamid);
    }

    public WorkspaceDspaceDTOPK getWorkspaceDspaceDTOPK() {
        return workspaceDspaceDTOPK;
    }

    public void setWorkspaceDspaceDTOPK(WorkspaceDspaceDTOPK workspaceDspaceDTOPK) {
        this.workspaceDspaceDTOPK = workspaceDspaceDTOPK;
    }

    public String getCommunityid() {
        return communityid;
    }

    public void setCommunityid(String communityid) {
        this.communityid = communityid;
    }

    public String getCollectionid() {
        return collectionid;
    }

    public void setCollectionid(String collectionid) {
        this.collectionid = collectionid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkspaceDspaceDTO)) {
            return false;
        }
        WorkspaceDspaceDTO other = (WorkspaceDspaceDTO) object;
        if ((this.workspaceDspaceDTOPK == null && other.workspaceDspaceDTOPK != null) || (this.workspaceDspaceDTOPK != null && !this.workspaceDspaceDTOPK.equals(other.workspaceDspaceDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.WorkspaceDspaceDTO[ workspaceDspaceDTOPK=" + workspaceDspaceDTOPK + " ]";
    }
    
}
