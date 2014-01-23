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
import javax.persistence.Lob;
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
    @NamedQuery(name = "ConceptCollectionItemsDTO.findById", query = "SELECT c FROM ConceptCollectionItemsDTO c WHERE c.conceptCollectionItemsDTOPK.conceptcollectionid = :conceptcollectionid"),
    @NamedQuery(name = "ConceptCollectionItemsDTO.findByLemma", query = "SELECT c FROM ConceptCollectionItemsDTO c WHERE c.lemma = :lemma"),
    @NamedQuery(name = "ConceptCollectionItemsDTO.findByItem", query = "SELECT c FROM ConceptCollectionItemsDTO c WHERE c.conceptCollectionItemsDTOPK.item = :item"),
    @NamedQuery(name = "ConceptCollectionItemsDTO.findByPos", query = "SELECT c FROM ConceptCollectionItemsDTO c WHERE c.pos = :pos"),
    })
public class ConceptCollectionItemsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK;
    @Basic(optional = false)
    @Column(name = "lemma")
    private String lemma;
    @Column(name = "pos")
    private String pos;
    @Lob
    @Column(name = "description")
    private String description;
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

    public ConceptCollectionItemsDTO() {
    }

    public ConceptCollectionItemsDTO(ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK) {
        this.conceptCollectionItemsDTOPK = conceptCollectionItemsDTOPK;
    }

    public ConceptCollectionItemsDTO(ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK, String lemma, Date updateddate, Date createddate) {
        this.conceptCollectionItemsDTOPK = conceptCollectionItemsDTOPK;
        this.lemma = lemma;
        this.updateddate = updateddate;
        this.createddate = createddate;
    }

    public ConceptCollectionItemsDTO(String conceptCollectionId, String item,String lemma, Date updateddate, Date createddate) {
        this.conceptCollectionItemsDTOPK = new ConceptCollectionItemsDTOPK(conceptCollectionId, item);
        this.lemma = lemma;
        this.updateddate = updateddate;
        this.createddate = createddate;
    }

    public ConceptCollectionItemsDTOPK getConceptcollectionsItemsDTOPK() {
        return conceptCollectionItemsDTOPK;
    }

    public void setConceptcollectionsItemsDTOPK(ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK) {
        this.conceptCollectionItemsDTOPK = conceptCollectionItemsDTOPK;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
