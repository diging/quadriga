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
 *This class represents the mapping for the primary key 
 *variables for concept collection items.
 * @author Karthik
 */
@Embeddable
public class ConceptCollectionItemsDTOPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "conceptcollectionid")
    private String conceptcollectionid;
    @Basic(optional = false)
    @Column(name = "concept")
    private String concept;

    public ConceptCollectionItemsDTOPK() {
    }

    public ConceptCollectionItemsDTOPK(String conceptcollectionid, String concept) {
        this.conceptcollectionid = conceptcollectionid;
        this.concept = concept;
    }

    public String getConceptcollectionid() {
		return conceptcollectionid;
	}

	public void setConceptcollectionid(String conceptcollectionid) {
		this.conceptcollectionid = conceptcollectionid;
	}

    public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptcollectionid != null ? conceptcollectionid.hashCode() : 0);
        hash += (concept != null ? concept.hashCode() : 0);
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
        if ((this.concept == null && other.concept != null) || (this.concept != null && !this.concept.equals(other.concept))) {
            return false;
        }
        return true;
    }
}
