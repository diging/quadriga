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

/**
 *This class represents the column mappings for quadriga user requests table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_quadriga_user_requests")
@NamedQueries({
    @NamedQuery(name = "QuadrigaUserRequestsDTO.findAll", query = "SELECT q FROM QuadrigaUserRequestsDTO q"),
    @NamedQuery(name = "QuadrigaUserRequestsDTO.findByFullname", query = "SELECT q FROM QuadrigaUserRequestsDTO q WHERE q.fullname = :fullname"),
    @NamedQuery(name = "QuadrigaUserRequestsDTO.findByUsername", query = "SELECT q FROM QuadrigaUserRequestsDTO q WHERE q.username = :username"),
    @NamedQuery(name = "QuadrigaUserRequestsDTO.findByEmail", query = "SELECT q FROM QuadrigaUserRequestsDTO q WHERE q.email = :email"),
    })
public class QuadrigaUserRequestsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "fullname")
    private String fullname;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Column(name = "passwd")
    private String passwd;
    @Column(name = "email")
    private String email;
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
    @Column(name = "provider")
    private String provider;
    @Column(name = "provideruserid")
    private String userIdOfProvider;
    

    public QuadrigaUserRequestsDTO() {
    }

    public QuadrigaUserRequestsDTO(String username, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.username = username;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }
    

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUserIdOfProvider() {
        return userIdOfProvider;
    }

    public void setUserIdOfProvider(String userIdOfProvider) {
        this.userIdOfProvider = userIdOfProvider;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof QuadrigaUserRequestsDTO)) {
            return false;
        }
        QuadrigaUserRequestsDTO other = (QuadrigaUserRequestsDTO) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }
}
