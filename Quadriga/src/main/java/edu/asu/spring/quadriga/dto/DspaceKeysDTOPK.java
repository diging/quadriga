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
 * This class represents the primary key
 * variable mappings for dspace keys table.
 * @author Karthik
 */
@Embeddable
public class DspaceKeysDTOPK implements Serializable {
	private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "publickey")
    private String publickey;
    @Basic(optional = false)
    @Column(name = "privatekey")
    private String privatekey;

    public DspaceKeysDTOPK() {
    }

    public DspaceKeysDTOPK(String username, String publickey, String privatekey) {
        this.username = username;
        this.publickey = publickey;
        this.privatekey = privatekey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        hash += (publickey != null ? publickey.hashCode() : 0);
        hash += (privatekey != null ? privatekey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DspaceKeysDTOPK)) {
            return false;
        }
        DspaceKeysDTOPK other = (DspaceKeysDTOPK) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        if ((this.publickey == null && other.publickey != null) || (this.publickey != null && !this.publickey.equals(other.publickey))) {
            return false;
        }
        if ((this.privatekey == null && other.privatekey != null) || (this.privatekey != null && !this.privatekey.equals(other.privatekey))) {
            return false;
        }
        return true;
    }
}
