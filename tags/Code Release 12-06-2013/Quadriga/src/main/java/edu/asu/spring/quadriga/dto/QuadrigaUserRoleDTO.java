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
@Table(name = "tbl_quadriga_user_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuadrigaUserRoleDTO.findAll", query = "SELECT q FROM QuadrigaUserRoleDTO q"),
    @NamedQuery(name = "QuadrigaUserRoleDTO.findByUsername", query = "SELECT q FROM QuadrigaUserRoleDTO q WHERE q.quadrigaUserRoleDTOPK.username = :username"),
    @NamedQuery(name = "QuadrigaUserRoleDTO.findByQuadrigarole", query = "SELECT q FROM QuadrigaUserRoleDTO q WHERE q.quadrigaUserRoleDTOPK.quadrigarole = :quadrigarole"),
    @NamedQuery(name = "QuadrigaUserRoleDTO.findByUpdatedby", query = "SELECT q FROM QuadrigaUserRoleDTO q WHERE q.updatedby = :updatedby"),
    @NamedQuery(name = "QuadrigaUserRoleDTO.findByUpdateddate", query = "SELECT q FROM QuadrigaUserRoleDTO q WHERE q.updateddate = :updateddate"),
    @NamedQuery(name = "QuadrigaUserRoleDTO.findByCreatedby", query = "SELECT q FROM QuadrigaUserRoleDTO q WHERE q.createdby = :createdby"),
    @NamedQuery(name = "QuadrigaUserRoleDTO.findByCreateddate", query = "SELECT q FROM QuadrigaUserRoleDTO q WHERE q.createddate = :createddate")})
public class QuadrigaUserRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected QuadrigaUserRoleDTOPK quadrigaUserRoleDTOPK;
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

    public QuadrigaUserRoleDTO() {
    }

    public QuadrigaUserRoleDTO(QuadrigaUserRoleDTOPK quadrigaUserRoleDTOPK) {
        this.quadrigaUserRoleDTOPK = quadrigaUserRoleDTOPK;
    }

    public QuadrigaUserRoleDTO(QuadrigaUserRoleDTOPK quadrigaUserRoleDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.quadrigaUserRoleDTOPK = quadrigaUserRoleDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public QuadrigaUserRoleDTO(String username, String quadrigarole) {
        this.quadrigaUserRoleDTOPK = new QuadrigaUserRoleDTOPK(username, quadrigarole);
    }

    public QuadrigaUserRoleDTOPK getQuadrigaUserRoleDTOPK() {
        return quadrigaUserRoleDTOPK;
    }

    public void setQuadrigaUserRoleDTOPK(QuadrigaUserRoleDTOPK quadrigaUserRoleDTOPK) {
        this.quadrigaUserRoleDTOPK = quadrigaUserRoleDTOPK;
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
        hash += (quadrigaUserRoleDTOPK != null ? quadrigaUserRoleDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuadrigaUserRoleDTO)) {
            return false;
        }
        QuadrigaUserRoleDTO other = (QuadrigaUserRoleDTO) object;
        if ((this.quadrigaUserRoleDTOPK == null && other.quadrigaUserRoleDTOPK != null) || (this.quadrigaUserRoleDTOPK != null && !this.quadrigaUserRoleDTOPK.equals(other.quadrigaUserRoleDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.QuadrigaUserRoleDTO[ quadrigaUserRoleDTOPK=" + quadrigaUserRoleDTOPK + " ]";
    }
    
}