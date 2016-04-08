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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *This is the hibernate mapping class for concept 
 *collection items.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_conceptcollection_items")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptCollectionItemsDTO.findAll", query = "SELECT c FROM ConceptCollectionItemsDTO c"),
    @NamedQuery(name = "ConceptCollectionItemsDTO.findById", query = "SELECT c FROM ConceptCollectionItemsDTO c WHERE c.conceptCollectionItemsDTOPK.concept = :concept"),
    })
public class ConceptCollectionItemsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK;
    @Basic(optional = false)
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate;
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
	@JoinColumn(name = "conceptcollectionid", referencedColumnName = "conceptcollectionid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConceptCollectionDTO conceptCollectionDTO;
    @JoinColumn(name = "concept", referencedColumnName = "item", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConceptsDTO conceptDTO;
    
    public ConceptCollectionItemsDTO() {
    }

    public ConceptCollectionItemsDTO(ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK) {
        this.conceptCollectionItemsDTOPK = conceptCollectionItemsDTOPK;
    }

    public ConceptCollectionItemsDTO(ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK,Date updateddate, Date createddate) {
        this.conceptCollectionItemsDTOPK = conceptCollectionItemsDTOPK;
        this.updateddate = updateddate;
        this.createddate = createddate;
    }

    public ConceptCollectionItemsDTO(String conceptCollectionId, String conceptId,Date updateddate, Date createddate) {
        this.conceptCollectionItemsDTOPK = new ConceptCollectionItemsDTOPK(conceptCollectionId, conceptId);
        this.updateddate = updateddate;
        this.createddate = createddate;
    }

    public ConceptCollectionItemsDTOPK getConceptcollectionsItemsDTOPK() {
        return conceptCollectionItemsDTOPK;
    }

    public void setConceptcollectionsItemsDTOPK(ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK) {
        this.conceptCollectionItemsDTOPK = conceptCollectionItemsDTOPK;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public ConceptCollectionDTO getConceptcollectionsDTO() {
        return conceptCollectionDTO;
    }

    public void setConceptcollectionsDTO(ConceptCollectionDTO conceptCollectionDTO) {
        this.conceptCollectionDTO = conceptCollectionDTO;
    }


	public ConceptCollectionItemsDTOPK getConceptCollectionItemsDTOPK() {
		return conceptCollectionItemsDTOPK;
	}

	public void setConceptCollectionItemsDTOPK(
			ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK) {
		this.conceptCollectionItemsDTOPK = conceptCollectionItemsDTOPK;
	}

	public ConceptCollectionDTO getConceptCollectionDTO() {
		return conceptCollectionDTO;
	}

	public void setConceptCollectionDTO(ConceptCollectionDTO conceptCollectionDTO) {
		this.conceptCollectionDTO = conceptCollectionDTO;
	}

	public ConceptsDTO getConceptDTO() {
		return conceptDTO;
	}

	public void setConceptDTO(ConceptsDTO conceptDTO) {
		this.conceptDTO = conceptDTO;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptCollectionItemsDTOPK != null ? conceptCollectionItemsDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConceptCollectionItemsDTO)) {
            return false;
        }
        ConceptCollectionItemsDTO other = (ConceptCollectionItemsDTO) object;
        if ((this.conceptCollectionItemsDTOPK == null && other.conceptCollectionItemsDTOPK != null) || (this.conceptCollectionItemsDTOPK != null && !this.conceptCollectionItemsDTOPK.equals(other.conceptCollectionItemsDTOPK))) {
            return false;
        }
        return true;
    }
}
