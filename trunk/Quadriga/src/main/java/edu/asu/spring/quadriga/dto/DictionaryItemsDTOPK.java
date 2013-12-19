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
public class DictionaryItemsDTOPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "termid")
    private String termid;

    public DictionaryItemsDTOPK() {
    }

    public DictionaryItemsDTOPK(String id, String termid) {
        this.id = id;
        this.termid = termid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (termid != null ? termid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DictionaryItemsDTOPK)) {
            return false;
        }
        DictionaryItemsDTOPK other = (DictionaryItemsDTOPK) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.termid == null && other.termid != null) || (this.termid != null && !this.termid.equals(other.termid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.DictionaryItemsDTOPK[ id=" + id + ", termid=" + termid + " ]";
    }
    
}
