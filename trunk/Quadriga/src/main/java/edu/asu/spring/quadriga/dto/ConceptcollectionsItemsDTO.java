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
 *
 * @author Karthik
 */
@Entity
@Table(name = "tbl_conceptcollections_items")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptcollectionsItemsDTO.findAll", query = "SELECT c FROM ConceptcollectionsItemsDTO c"),
    @NamedQuery(name = "ConceptcollectionsItemsDTO.findById", query = "SELECT c FROM ConceptcollectionsItemsDTO c WHERE c.conceptcollectionsItemsDTOPK.id = :id"),
    @NamedQuery(name = "ConceptcollectionsItemsDTO.findByLemma", query = "SELECT c FROM ConceptcollectionsItemsDTO c WHERE c.lemma = :lemma"),
    @NamedQuery(name = "ConceptcollectionsItemsDTO.findByItem", query = "SELECT c FROM ConceptcollectionsItemsDTO c WHERE c.conceptcollectionsItemsDTOPK.item = :item"),
    @NamedQuery(name = "ConceptcollectionsItemsDTO.findByPos", query = "SELECT c FROM ConceptcollectionsItemsDTO c WHERE c.pos = :pos"),
    @NamedQuery(name = "ConceptcollectionsItemsDTO.findByUpdateddate", query = "SELECT c FROM ConceptcollectionsItemsDTO c WHERE c.updateddate = :updateddate"),
    @NamedQuery(name = "ConceptcollectionsItemsDTO.findByCreateddate", query = "SELECT c FROM ConceptcollectionsItemsDTO c WHERE c.createddate = :createddate")})
public class ConceptcollectionsItemsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConceptcollectionsItemsDTOPK conceptcollectionsItemsDTOPK;
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
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConceptCollectionDTO conceptcollectionsDTO;

    public ConceptcollectionsItemsDTO() {
    }

    public ConceptcollectionsItemsDTO(ConceptcollectionsItemsDTOPK conceptcollectionsItemsDTOPK) {
        this.conceptcollectionsItemsDTOPK = conceptcollectionsItemsDTOPK;
    }

    public ConceptcollectionsItemsDTO(ConceptcollectionsItemsDTOPK conceptcollectionsItemsDTOPK, String lemma, Date updateddate, Date createddate) {
        this.conceptcollectionsItemsDTOPK = conceptcollectionsItemsDTOPK;
        this.lemma = lemma;
        this.updateddate = updateddate;
        this.createddate = createddate;
    }

    public ConceptcollectionsItemsDTO(String id, String item) {
        this.conceptcollectionsItemsDTOPK = new ConceptcollectionsItemsDTOPK(id, item);
    }

    public ConceptcollectionsItemsDTOPK getConceptcollectionsItemsDTOPK() {
        return conceptcollectionsItemsDTOPK;
    }

    public void setConceptcollectionsItemsDTOPK(ConceptcollectionsItemsDTOPK conceptcollectionsItemsDTOPK) {
        this.conceptcollectionsItemsDTOPK = conceptcollectionsItemsDTOPK;
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
        return conceptcollectionsDTO;
    }

    public void setConceptcollectionsDTO(ConceptCollectionDTO conceptcollectionsDTO) {
        this.conceptcollectionsDTO = conceptcollectionsDTO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptcollectionsItemsDTOPK != null ? conceptcollectionsItemsDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptcollectionsItemsDTO)) {
            return false;
        }
        ConceptcollectionsItemsDTO other = (ConceptcollectionsItemsDTO) object;
        if ((this.conceptcollectionsItemsDTOPK == null && other.conceptcollectionsItemsDTOPK != null) || (this.conceptcollectionsItemsDTOPK != null && !this.conceptcollectionsItemsDTOPK.equals(other.conceptcollectionsItemsDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.ConceptcollectionsItemsDTO[ conceptcollectionsItemsDTOPK=" + conceptcollectionsItemsDTOPK + " ]";
    }
    
}
