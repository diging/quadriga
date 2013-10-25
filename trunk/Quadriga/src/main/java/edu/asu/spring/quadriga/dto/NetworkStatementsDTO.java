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
@Table(name = "tbl_network_statements")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworkStatementsDTO.findAll", query = "SELECT n FROM NetworkStatementsDTO n"),
    @NamedQuery(name = "NetworkStatementsDTO.findByNetworkid", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.networkStatementsDTOPK.networkid = :networkid"),
    @NamedQuery(name = "NetworkStatementsDTO.findById", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.networkStatementsDTOPK.id = :id"),
    @NamedQuery(name = "NetworkStatementsDTO.findByStatementtype", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.statementtype = :statementtype"),
    @NamedQuery(name = "NetworkStatementsDTO.findByIstop", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.istop = :istop"),
    @NamedQuery(name = "NetworkStatementsDTO.findByIsarchived", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.isarchived = :isarchived"),
    @NamedQuery(name = "NetworkStatementsDTO.findByUpdatedby", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.updatedby = :updatedby"),
    @NamedQuery(name = "NetworkStatementsDTO.findByUpdateddate", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.updateddate = :updateddate"),
    @NamedQuery(name = "NetworkStatementsDTO.findByCreatedby", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.createdby = :createdby"),
    @NamedQuery(name = "NetworkStatementsDTO.findByCreateddate", query = "SELECT n FROM NetworkStatementsDTO n WHERE n.createddate = :createddate")})
public class NetworkStatementsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NetworkStatementsDTOPK networkStatementsDTOPK;
    @Basic(optional = false)
    @Column(name = "statementtype")
    private String statementtype;
    @Basic(optional = false)
    @Column(name = "istop")
    private int istop;
    @Basic(optional = false)
    @Column(name = "isarchived")
    private int isarchived;
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

    public NetworkStatementsDTO() {
    }

    public NetworkStatementsDTO(NetworkStatementsDTOPK networkStatementsDTOPK) {
        this.networkStatementsDTOPK = networkStatementsDTOPK;
    }

    public NetworkStatementsDTO(NetworkStatementsDTOPK networkStatementsDTOPK, String statementtype, int istop, int isarchived, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.networkStatementsDTOPK = networkStatementsDTOPK;
        this.statementtype = statementtype;
        this.istop = istop;
        this.isarchived = isarchived;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public NetworkStatementsDTO(String networkid, String id) {
        this.networkStatementsDTOPK = new NetworkStatementsDTOPK(networkid, id);
    }

    public NetworkStatementsDTOPK getNetworkStatementsDTOPK() {
        return networkStatementsDTOPK;
    }

    public void setNetworkStatementsDTOPK(NetworkStatementsDTOPK networkStatementsDTOPK) {
        this.networkStatementsDTOPK = networkStatementsDTOPK;
    }

    public String getStatementtype() {
        return statementtype;
    }

    public void setStatementtype(String statementtype) {
        this.statementtype = statementtype;
    }

    public int getIstop() {
        return istop;
    }

    public void setIstop(int istop) {
        this.istop = istop;
    }

    public int getIsarchived() {
        return isarchived;
    }

    public void setIsarchived(int isarchived) {
        this.isarchived = isarchived;
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
        hash += (networkStatementsDTOPK != null ? networkStatementsDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NetworkStatementsDTO)) {
            return false;
        }
        NetworkStatementsDTO other = (NetworkStatementsDTO) object;
        if ((this.networkStatementsDTOPK == null && other.networkStatementsDTOPK != null) || (this.networkStatementsDTOPK != null && !this.networkStatementsDTOPK.equals(other.networkStatementsDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.NetworkStatementsDTO[ networkStatementsDTOPK=" + networkStatementsDTOPK + " ]";
    }
    
}
