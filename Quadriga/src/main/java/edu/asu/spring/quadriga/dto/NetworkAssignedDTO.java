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
@Table(name = "tbl_network_assigned")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworkAssignedDTO.findAll", query = "SELECT n FROM NetworkAssignedDTO n"),
    @NamedQuery(name = "NetworkAssignedDTO.findByNetworkid", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.networkid = :networkid"),
    @NamedQuery(name = "NetworkAssignedDTO.findByAssigneduser", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.assigneduser = :assigneduser"),
    @NamedQuery(name = "NetworkAssignedDTO.findByStatus", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.status = :status"),
    @NamedQuery(name = "NetworkAssignedDTO.findByUpdatedby", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.updatedby = :updatedby"),
    @NamedQuery(name = "NetworkAssignedDTO.findByUpdateddate", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.updateddate = :updateddate"),
    @NamedQuery(name = "NetworkAssignedDTO.findByCreatedby", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.createdby = :createdby"),
    @NamedQuery(name = "NetworkAssignedDTO.findByCreateddate", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.networkAssignedDTOPK.createddate = :createddate"),
    @NamedQuery(name = "NetworkAssignedDTO.findByIsarchived", query = "SELECT n FROM NetworkAssignedDTO n WHERE n.isarchived = :isarchived")})
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
    private String isarchived;

    public NetworkAssignedDTO() {
    }

    public NetworkAssignedDTO(NetworkAssignedDTOPK networkAssignedDTOPK) {
        this.networkAssignedDTOPK = networkAssignedDTOPK;
    }

    public NetworkAssignedDTO(NetworkAssignedDTOPK networkAssignedDTOPK, String status, String updatedby, Date updateddate, String createdby, String isarchived) {
        this.networkAssignedDTOPK = networkAssignedDTOPK;
        this.status = status;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.isarchived = isarchived;
    }

    public NetworkAssignedDTO(String networkid, String assigneduser, Date createddate) {
        this.networkAssignedDTOPK = new NetworkAssignedDTOPK(networkid, assigneduser, createddate);
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

    public String getIsarchived() {
        return isarchived;
    }

    public void setIsarchived(String isarchived) {
        this.isarchived = isarchived;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (networkAssignedDTOPK != null ? networkAssignedDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NetworkAssignedDTO)) {
            return false;
        }
        NetworkAssignedDTO other = (NetworkAssignedDTO) object;
        if ((this.networkAssignedDTOPK == null && other.networkAssignedDTOPK != null) || (this.networkAssignedDTOPK != null && !this.networkAssignedDTOPK.equals(other.networkAssignedDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.NetworkAssignedDTO[ networkAssignedDTOPK=" + networkAssignedDTOPK + " ]";
    }
    
}
