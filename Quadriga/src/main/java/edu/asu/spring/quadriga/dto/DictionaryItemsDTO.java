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

/**
 *This class represents dictionary items table 
 *column mappings.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_dictionary_items")
@NamedQueries({
    @NamedQuery(name = "DictionaryItemsDTO.findAll", query = "SELECT d FROM DictionaryItemsDTO d"),
    @NamedQuery(name = "DictionaryItemsDTO.findById", query = "SELECT d FROM DictionaryItemsDTO d WHERE d.dictionaryItemsDTOPK.dictionaryid = :dictionaryid"),
    @NamedQuery(name = "DictionaryItemsDTO.findByTerm", query = "SELECT d FROM DictionaryItemsDTO d WHERE d.term = :term"),
    @NamedQuery(name = "DictionaryItemsDTO.findByTermid", query = "SELECT d FROM DictionaryItemsDTO d WHERE d.dictionaryItemsDTOPK.termid = :termid"),
    @NamedQuery(name = "DictionaryItemsDTO.findByPos", query = "SELECT d FROM DictionaryItemsDTO d WHERE d.pos = :pos"),
    })
public class DictionaryItemsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DictionaryItemsDTOPK dictionaryItemsDTOPK;
    @Basic(optional = false)
    @Column(name = "term")
    private String term;
    @Basic(optional = false)
    @Column(name = "pos")
    private String pos;
    @Basic(optional = false)
    @Column(name = "updatedby")
    private String updatedby;
    @Basic(optional = false)
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate;
    @Basic(optional = false)
    @Column(name = "createdby")
    private String createdby;
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @JoinColumn(name = "dictionaryid", referencedColumnName = "dictionaryid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DictionaryDTO dictionaryDTO;

    public DictionaryItemsDTO() {
    }

    public DictionaryItemsDTO(DictionaryItemsDTOPK dictionaryItemsDTOPK) {
        this.dictionaryItemsDTOPK = dictionaryItemsDTOPK;
    }

    public DictionaryItemsDTO(DictionaryItemsDTOPK dictionaryItemsDTOPK, String term, String pos, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.dictionaryItemsDTOPK = dictionaryItemsDTOPK;
        this.term = term;
        this.pos = pos;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public DictionaryItemsDTO(String id, String termid,String pos, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.dictionaryItemsDTOPK = new DictionaryItemsDTOPK(id, termid);
        this.pos = pos;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public DictionaryItemsDTOPK getDictionaryItemsDTOPK() {
        return dictionaryItemsDTOPK;
    }

    public void setDictionaryItemsDTOPK(DictionaryItemsDTOPK dictionaryItemsDTOPK) {
        this.dictionaryItemsDTOPK = dictionaryItemsDTOPK;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public DictionaryDTO getDictionaryDTO() {
        return dictionaryDTO;
    }

    public void setDictionaryDTO(DictionaryDTO dictionaryDTO) {
        this.dictionaryDTO = dictionaryDTO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dictionaryItemsDTOPK != null ? dictionaryItemsDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DictionaryItemsDTO)) {
            return false;
        }
        DictionaryItemsDTO other = (DictionaryItemsDTO) object;
        if ((this.dictionaryItemsDTOPK == null && other.dictionaryItemsDTOPK != null) || (this.dictionaryItemsDTOPK != null && !this.dictionaryItemsDTOPK.equals(other.dictionaryItemsDTOPK))) {
            return false;
        }
        return true;
    }
}
