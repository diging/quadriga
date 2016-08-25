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
 * This class represents the primary variable mappings
 * with the column names of dictionary items table.
 * @author Karthik
 */
@Embeddable
public class DictionaryItemsDTOPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "dictionaryid")
    private String dictionaryid;
    @Basic(optional = false)
    @Column(name = "termid")
    private String termid;

    public DictionaryItemsDTOPK() {
    }

    public DictionaryItemsDTOPK(String dictionaryid, String termid) {
        this.dictionaryid = dictionaryid;
        this.termid = termid;
    }


    public String getDictionaryid() {
		return dictionaryid;
	}

	public void setDictionaryid(String dictionaryid) {
		this.dictionaryid = dictionaryid;
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
        hash += (dictionaryid != null ? dictionaryid.hashCode() : 0);
        hash += (termid != null ? termid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DictionaryItemsDTOPK)) {
            return false;
        }
        DictionaryItemsDTOPK other = (DictionaryItemsDTOPK) object;
        if ((this.dictionaryid == null && other.dictionaryid != null) || (this.dictionaryid != null && !this.dictionaryid.equals(other.dictionaryid))) {
            return false;
        }
        if ((this.termid == null && other.termid != null) || (this.termid != null && !this.termid.equals(other.termid))) {
            return false;
        }
        return true;
    }
}
