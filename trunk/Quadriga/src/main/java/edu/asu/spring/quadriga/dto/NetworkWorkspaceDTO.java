package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_network_workspace")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworkWorkspaceDTO.findAll", query = "SELECT n FROM NetworkWorkspaceDTO n"),
    @NamedQuery(name = "NetworkWorkspaceDTO.findByNetworkid", query = "SELECT n FROM NetworkWorkspaceDTO n WHERE n.NetworkWorkspaceDTOPK.networkid = :networkid"),
   @NamedQuery(name = "NetworkWorkspaceDTO.findByWorkspaceid", query = "SELECT n FROM NetworkWorkspaceDTO n WHERE n.NetworkWorkspaceDTOPK.workspaceid = :workspaceid"),
    })
public class NetworkWorkspaceDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    protected NetworkWorkspaceDTOPK networkWorkspaceDTOPK;
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
    @OneToOne(optional = false)
    private WorkspaceDTO workspaceDTO;
    @JoinColumn(name = "networkid", referencedColumnName = "networkid", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private NetworksDTO networksDTO;
    
    public NetworkWorkspaceDTO(NetworkWorkspaceDTOPK networkWorkspaceDTOPK,String createdBy,Date createdDate,String updatedBy,Date updatedDate) {
		this.networkWorkspaceDTOPK = networkWorkspaceDTOPK;
		this.createdby = createdBy;
		this.createddate = createdDate;
		this.updatedby = updatedBy;
		this.updateddate = updatedDate;
	}
    
    public NetworkWorkspaceDTO(String networkid, String workspaceid,String createdBy,Date createdDate,String updatedBy,Date updatedDate) {
    	this.networkWorkspaceDTOPK = new NetworkWorkspaceDTOPK(networkid, workspaceid);
		this.createdby = createdBy;
		this.createddate = createdDate;
		this.updatedby = updatedBy;
		this.updateddate = updatedDate;
	}
    
	public NetworkWorkspaceDTOPK getNetworkWorkspaceDTOPK() {
		return networkWorkspaceDTOPK;
	}
	public void setNetworkWorkspaceDTOPK(NetworkWorkspaceDTOPK networkWorkspaceDTOPK) {
		this.networkWorkspaceDTOPK = networkWorkspaceDTOPK;
	}
	public WorkspaceDTO getWorkspaceDTO() {
		return workspaceDTO;
	}
	public void setWorkspaceDTO(WorkspaceDTO workspaceDTO) {
		this.workspaceDTO = workspaceDTO;
	}
	public NetworksDTO getNetworksDTO() {
		return networksDTO;
	}
	public void setNetworksDTO(NetworksDTO networksDTO) {
		this.networksDTO = networksDTO;
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
        hash += (networkWorkspaceDTOPK != null ? networkWorkspaceDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NetworkWorkspaceDTO)) {
            return false;
        }
        NetworkWorkspaceDTO other = (NetworkWorkspaceDTO) object;
        if ((this.networkWorkspaceDTOPK == null && other.networkWorkspaceDTOPK != null) || (this.networkWorkspaceDTOPK != null && !this.networkWorkspaceDTOPK.equals(other.networkWorkspaceDTOPK))) {
            return false;
        }
        return true;
    }
}
