package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    protected NetworkWorkspaceDTOPK networkWorkspaceDTOPK;
	@JoinColumn(name = "workspaceid", referencedColumnName = "workspaceid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private WorkspaceDTO workspaceDTO;
    @JoinColumn(name = "networkid", referencedColumnName = "networkid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NetworksDTO networksDTO;
    
    
    
    public NetworkWorkspaceDTO(NetworkWorkspaceDTOPK networkWorkspaceDTOPK) {
		this.networkWorkspaceDTOPK = networkWorkspaceDTOPK;
	}
    
    public NetworkWorkspaceDTO(String networkid, String workspaceid) {
    	this.networkWorkspaceDTOPK = new NetworkWorkspaceDTOPK(networkid, workspaceid);
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
