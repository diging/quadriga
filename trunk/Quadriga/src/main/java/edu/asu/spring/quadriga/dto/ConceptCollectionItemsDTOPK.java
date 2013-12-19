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
public class ConceptCollectionItemsDTOPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "conceptcollectionid")
    private String conceptcollectionid;
    @Basic(optional = false)
    @Column(name = "item")
    private String item;

    public ConceptCollectionItemsDTOPK() {
    }

    public ConceptCollectionItemsDTOPK(String conceptcollectionid, String item) {
        this.conceptcollectionid = conceptcollectionid;
        this.item = item;
    }

    public String getConceptcollectionid() {
		return conceptcollectionid;
	}

	public void setConceptcollectionid(String conceptcollectionid) {
		this.conceptcollectionid = conceptcollectionid;
	}

	public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptcollectionid != null ? conceptcollectionid.hashCode() : 0);
        hash += (item != null ? item.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConceptCollectionItemsDTOPK)) {
            return false;
        }
        ConceptCollectionItemsDTOPK other = (ConceptCollectionItemsDTOPK) object;
        if ((this.conceptcollectionid == null && other.conceptcollectionid != null) || (this.conceptcollectionid != null && !this.conceptcollectionid.equals(other.conceptcollectionid))) {
            return false;
        }
        if ((this.item == null && other.item != null) || (this.item != null && !this.item.equals(other.item))) {
            return false;
        }
        return true;
    }
}
