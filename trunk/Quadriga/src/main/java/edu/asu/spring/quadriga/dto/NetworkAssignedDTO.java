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
@Table(name = "tbl_network_assigned")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworkAssignedDTO.findAll", query = "SELECT n FROM NetworkAssignedDTO n"),
    @NamedQuery(name = "NetworkAssignedDTO.findByNetworkid", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.networkid = :networkid"),
    @NamedQuery(name = "NetworkAssignedDTO.findByAssigneduser", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.assigneduser = :assigneduser"),
    @NamedQuery(name = "NetworkAssignedDTO.findByStatus", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.status = :status"),
    @NamedQuery(name = "NetworkAssignedDTO.findByIsarchived", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.isarchived = :isarchived")
    })
public class NetworkAssignedDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NetworkAssignedDTOPK networkAssignedDTOPK;
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
    @Column(name = "isarchived")
    private int isarchived;
    @JoinColumn(name = "networkid", referencedColumnName = "networkid",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NetworksDTO networksDTO;
    @JoinColumn(name = "assigneduser", referencedColumnName = "username",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private QuadrigaUserDTO quadrigaUserDTO;

	public NetworkAssignedDTO() {
    }

    public NetworkAssignedDTO(NetworkAssignedDTOPK networkAssignedDTOPK) {
        this.networkAssignedDTOPK = networkAssignedDTOPK;
    }

    public NetworkAssignedDTO(NetworkAssignedDTOPK networkAssignedDTOPK, String status, String updatedby, Date updateddate, String createdby, int isarchived) {
        this.networkAssignedDTOPK = networkAssignedDTOPK;
        this.status = status;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.isarchived = isarchived;
    }

    public NetworkAssignedDTO(String networkid, String assigneduser,Date date) {
        this.networkAssignedDTOPK = new NetworkAssignedDTOPK(networkid, assigneduser,date);
    }

    public NetworkAssignedDTOPK getNetworkAssignedDTOPK() {
        return networkAssignedDTOPK;
    }

    public void setNetworkAssignedDTOPK(NetworkAssignedDTOPK networkAssignedDTOPK) {
        this.networkAssignedDTOPK = networkAssignedDTOPK;
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

	public int getIsarchived() {
        return isarchived;
    }

    public void setIsarchived(int isarchived) {
        this.isarchived = isarchived;
    }
    
    public NetworksDTO getNetworksDTO() {
		return networksDTO;
	}

	public void setNetworksDTO(NetworksDTO networksDTO) {
		this.networksDTO = networksDTO;
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
        hash += (networkAssignedDTOPK != null ? networkAssignedDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NetworkAssignedDTO)) {
            return false;
        }
        NetworkAssignedDTO other = (NetworkAssignedDTO) object;
        if ((this.networkAssignedDTOPK == null && other.networkAssignedDTOPK != null) || (this.networkAssignedDTOPK != null && !this.networkAssignedDTOPK.equals(other.networkAssignedDTOPK))) {
            return false;
        }
        return true;
    }
}
