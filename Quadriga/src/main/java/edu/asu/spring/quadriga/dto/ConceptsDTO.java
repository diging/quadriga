/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *This is the hibernate mapping class for concept items.
 * @author Sowjanya Ambati
 */
@Entity
@Table(name = "tbl_concepts")
@NamedQueries({
    @NamedQuery(name = "ConceptsDTO.findAll", query = "SELECT c FROM ConceptsDTO c"),
    @NamedQuery(name = "ConceptsDTO.findByLemma", query = "SELECT c FROM ConceptsDTO c WHERE c.lemma = :lemma"),
    @NamedQuery(name = "ConceptCsDTO.findByItem", query = "SELECT c FROM ConceptsDTO c WHERE c.item = :item"),
    @NamedQuery(name = "ConceptsDTO.findByPos", query = "SELECT c FROM ConceptsDTO c WHERE c.pos = :pos"),
    })
public class ConceptsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "item")
    private String item;
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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptDTO")
    private List<ConceptCollectionItemsDTO> conceptItemsDTOList;

    public ConceptsDTO() {
    }

    public ConceptsDTO(String item) {
    	this.item = item;
    }

    public List<ConceptCollectionItemsDTO> getConceptItemsDTOList() {
		return conceptItemsDTOList;
	}

	public void setConceptItemsDTOList(List<ConceptCollectionItemsDTO> conceptItemsDTOList) {
		this.conceptItemsDTOList = conceptItemsDTOList;
	}

	public ConceptsDTO(String item,String lemma, Date updateddate, Date createddate) {
        this.item = item;
        this.lemma = lemma;
        this.updateddate = updateddate;
        this.createddate = createddate;
    }

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (item != null ? item.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConceptsDTO)) {
            return false;
        }
        ConceptsDTO other = (ConceptsDTO) object;
        if ((this.item == null && other.item != null) || (this.item != null && !this.item.equals(other.item))) {
            return false;
        }
        return true;
    }
}
