/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Karthik
 */
@Embeddable
public class QuadrigaUserprofileDTOPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "servicename")
    private String servicename;
    @Basic(optional = false)
    @Column(name = "uri")
    private String uri;

    public QuadrigaUserprofileDTOPK() {
    }

    public QuadrigaUserprofileDTOPK(String username, String servicename, String uri) {
        this.username = username;
        this.servicename = servicename;
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        hash += (servicename != null ? servicename.hashCode() : 0);
        hash += (uri != null ? uri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof QuadrigaUserprofileDTOPK)) {
            return false;
        }
        QuadrigaUserprofileDTOPK other = (QuadrigaUserprofileDTOPK) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        if ((this.servicename == null && other.servicename != null) || (this.servicename != null && !this.servicename.equals(other.servicename))) {
            return false;
        }
        if ((this.uri == null && other.uri != null) || (this.uri != null && !this.uri.equals(other.uri))) {
            return false;
        }
        return true;
    }
}
