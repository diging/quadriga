/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class represents the column mappings for networks table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_networks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworksDTO.findAll", query = "SELECT n FROM NetworksDTO n"),
    @NamedQuery(name = "NetworksDTO.findByNetworkid", query = "SELECT n FROM NetworksDTO n WHERE n.networkid = :networkid"),
    @NamedQuery(name = "NetworksDTO.findByNetworkname", query = "SELECT n FROM NetworksDTO n WHERE n.networkname = :networkname"),
    @NamedQuery(name = "NetworksDTO.findByNetworkowner", query = "SELECT n FROM NetworksDTO n WHERE n.networkowner = :networkowner"),
    @NamedQuery(name = "NetworksDTO.findByStatus", query = "SELECT n FROM NetworksDTO n WHERE n.status = :status"),
    })
public class NetworksDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "networkid")
    private String networkid;
    @Basic(optional = false)
    @Column(name = "networkname")
    private String networkname;
    @Basic(optional = false)
    @Column(name = "networkowner")
    private String networkowner;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
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
    
    private String externalUserId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "networksDTO")
    private List<NetworkAssignedDTO> networksAssignedDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "networksDTO")
    private List<NetworkAnnotationsDTO> networksAnnotationsDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "networkDTO")
    private List<NetworkStatementsDTO> networkStamentesDTOList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "networksDTO")
    private NetworkWorkspaceDTO networkWorkspace;
    
    @JoinColumn(name = "networkowner",referencedColumnName = "username",insertable=false ,updatable=false)
    @ManyToOne(optional = false)
    private QuadrigaUserDTO quadrigaUserDTO;
   
	public NetworksDTO() {
    }
						
    public NetworksDTO(String networkid,String networkname, String networkowner, String status, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.networkid = networkid;
        this.networkname = networkname;
        this.networkowner = networkowner;
        this.status = status;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    @XmlTransient
    public List<NetworkAssignedDTO> getNetworksAssignedDTOList() {
		return networksAssignedDTOList;
	}

	public void setNetworksAssignedDTOList(
			List<NetworkAssignedDTO> networksAssignedDTOList) {
		this.networksAssignedDTOList = networksAssignedDTOList;
	}

	@XmlTransient
	public List<NetworkAnnotationsDTO> getNetworksAnnotationsDTOList() {
		return networksAnnotationsDTOList;
	}

	public void setNetworksAnnotationsDTOList(
			List<NetworkAnnotationsDTO> networksAnnotationsDTOList) {
		this.networksAnnotationsDTOList = networksAnnotationsDTOList;
	}

	@XmlTransient
	public List<NetworkStatementsDTO> getNetworkStamentesDTOList() {
		return networkStamentesDTOList;
	}

	public void setNetworkStamentesDTOList(
			List<NetworkStatementsDTO> networkStamentesDTOList) {
		this.networkStamentesDTOList = networkStamentesDTOList;
	}
	
	public NetworkWorkspaceDTO getNetworkWorkspace() {
		return networkWorkspace;
	}

	public void setNetworkWorkspace(NetworkWorkspaceDTO networkWorkspace) {
		this.networkWorkspace = networkWorkspace;
	}

	public QuadrigaUserDTO getQuadrigaUserDTO() {
		return quadrigaUserDTO;
	}

	public void setQuadrigaUserDTO(QuadrigaUserDTO quadrigaUserDTO) {
		this.quadrigaUserDTO = quadrigaUserDTO;
	}
	
    public String getNetworkid() {
        return networkid;
    }

    public void setNetworkid(String networkid) {
        this.networkid = networkid;
    }

    public String getNetworkname() {
        return networkname;
    }

    public void setNetworkname(String networkname) {
        this.networkname = networkname;
    }

    public String getNetworkowner() {
        return networkowner;
    }

    public void setNetworkowner(String networkowner) {
        this.networkowner = networkowner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        hash += (networkid != null ? networkid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NetworksDTO)) {
            return false;
        }
        NetworksDTO other = (NetworksDTO) object;
        if ((this.networkid == null && other.networkid != null) || (this.networkid != null && !this.networkid.equals(other.networkid))) {
            return false;
        }
        return true;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }
}
