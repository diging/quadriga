/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "tbl_networks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworksDTO.findAll", query = "SELECT n FROM NetworksDTO n"),
    @NamedQuery(name = "NetworksDTO.findByNetworkid", query = "SELECT n FROM NetworksDTO n WHERE n.networkid = :networkid"),
    @NamedQuery(name = "NetworksDTO.findByWorkspaceid", query = "SELECT n FROM NetworksDTO n WHERE n.workspaceid = :workspaceid"),
    @NamedQuery(name = "NetworksDTO.findByNetworkname", query = "SELECT n FROM NetworksDTO n WHERE n.networkname = :networkname"),
    @NamedQuery(name = "NetworksDTO.findByNetworkowner", query = "SELECT n FROM NetworksDTO n WHERE n.networkowner = :networkowner"),
    @NamedQuery(name = "NetworksDTO.findByAccessibility", query = "SELECT n FROM NetworksDTO n WHERE n.accessibility = :accessibility"),
    @NamedQuery(name = "NetworksDTO.findByStatus", query = "SELECT n FROM NetworksDTO n WHERE n.status = :status"),
    @NamedQuery(name = "NetworksDTO.findByUpdatedby", query = "SELECT n FROM NetworksDTO n WHERE n.updatedby = :updatedby"),
    @NamedQuery(name = "NetworksDTO.findByUpdateddate", query = "SELECT n FROM NetworksDTO n WHERE n.updateddate = :updateddate"),
    @NamedQuery(name = "NetworksDTO.findByCreatedby", query = "SELECT n FROM NetworksDTO n WHERE n.createdby = :createdby"),
    @NamedQuery(name = "NetworksDTO.findByCreateddate", query = "SELECT n FROM NetworksDTO n WHERE n.createddate = :createddate")})
public class NetworksDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "networkid")
    private String networkid;
    @Basic(optional = false)
    @Column(name = "workspaceid")
    private String workspaceid;
    @Basic(optional = false)
    @Column(name = "networkname")
    private String networkname;
    @Basic(optional = false)
    @Column(name = "networkowner")
    private String networkowner;
    @Basic(optional = false)
    @Column(name = "accessibility")
    private short accessibility;
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

    public NetworksDTO() {
    }

    public NetworksDTO(String networkid) {
        this.networkid = networkid;
    }

    public NetworksDTO(String networkid, String workspaceid, String networkname, String networkowner, short accessibility, String status, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.networkid = networkid;
        this.workspaceid = workspaceid;
        this.networkname = networkname;
        this.networkowner = networkowner;
        this.accessibility = accessibility;
        this.status = status;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public String getNetworkid() {
        return networkid;
    }

    public void setNetworkid(String networkid) {
        this.networkid = networkid;
    }

    public String getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(String workspaceid) {
        this.workspaceid = workspaceid;
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

    public short getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(short accessibility) {
        this.accessibility = accessibility;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NetworksDTO)) {
            return false;
        }
        NetworksDTO other = (NetworksDTO) object;
        if ((this.networkid == null && other.networkid != null) || (this.networkid != null && !this.networkid.equals(other.networkid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.NetworksDTO[ networkid=" + networkid + " ]";
    }
    
}
