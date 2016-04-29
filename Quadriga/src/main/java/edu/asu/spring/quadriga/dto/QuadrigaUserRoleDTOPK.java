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
 *This class represents the primary key column mappings
 *for quadriga user role table
 * @author Karthik
 */
@Embeddable
public class QuadrigaUserRoleDTOPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
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
}
