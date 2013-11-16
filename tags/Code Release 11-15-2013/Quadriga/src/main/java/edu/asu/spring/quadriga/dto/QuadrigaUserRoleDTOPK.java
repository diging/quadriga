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
public class QuadrigaUserRoleDTOPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "quadrigarole")
    private String quadrigarole;

    public QuadrigaUserRoleDTOPK() {
    }

    public QuadrigaUserRoleDTOPK(String username, String quadrigarole) {
        this.username = username;
        this.quadrigarole = quadrigarole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuadrigarole() {
        return quadrigarole;
    }

    public void setQuadrigarole(String quadrigarole) {
        this.quadrigarole = quadrigarole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        hash += (quadrigarole != null ? quadrigarole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuadrigaUserRoleDTOPK)) {
            return false;
        }
        QuadrigaUserRoleDTOPK other = (QuadrigaUserRoleDTOPK) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        if ((this.quadrigarole == null && other.quadrigarole != null) || (this.quadrigarole != null && !this.quadrigarole.equals(other.quadrigarole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.QuadrigaUserRoleDTOPK[ username=" + username + ", quadrigarole=" + quadrigarole + " ]";
    }
    
}
