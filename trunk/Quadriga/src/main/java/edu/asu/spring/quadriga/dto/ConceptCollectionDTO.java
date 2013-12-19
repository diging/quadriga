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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Karthik
 */
@Entity
@Table(name = "tbl_conceptcollections")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptcollectionsDTO.findAll", query = "SELECT c FROM ConceptcollectionsDTO c"),
    @NamedQuery(name = "ConceptcollectionsDTO.findByCollectionname", query = "SELECT c FROM ConceptcollectionsDTO c WHERE c.collectionname = :collectionname"),
    @NamedQuery(name = "ConceptcollectionsDTO.findById", query = "SELECT c FROM ConceptcollectionsDTO c WHERE c.id = :id"),
    @NamedQuery(name = "ConceptcollectionsDTO.findByAccessibility", query = "SELECT c FROM ConceptcollectionsDTO c WHERE c.accessibility = :accessibility"),
    @NamedQuery(name = "ConceptcollectionsDTO.findByUpdatedby", query = "SELECT c FROM ConceptcollectionsDTO c WHERE c.updatedby = :updatedby"),
    @NamedQuery(name = "ConceptcollectionsDTO.findByUpdateddate", query = "SELECT c FROM ConceptcollectionsDTO c WHERE c.updateddate = :updateddate"),
    @NamedQuery(name = "ConceptcollectionsDTO.findByCreatedby", query = "SELECT c FROM ConceptcollectionsDTO c WHERE c.createdby = :createdby"),
    @NamedQuery(name = "ConceptcollectionsDTO.findByCreateddate", query = "SELECT c FROM ConceptcollectionsDTO c WHERE c.createddate = :createddate")})
public class ConceptCollectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "collectionname")
    private String collectionname;
    @Lob
    @Column(name = "description")
    private String description;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "accessibility")
    private Boolean accessibility;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptcollectionsDTO")
    private List<ConceptcollectionsCollaboratorDTO> conceptcollectionsCollaboratorDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptcollectionsDTO")
    private List<ConceptcollectionsItemsDTO> conceptcollectionsItemsDTOList;
    @JoinColumn(name = "collectionowner", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private QuadrigaUserDTO collectionowner;

    public ConceptCollectionDTO() {
    }

    public ConceptCollectionDTO(String id) {
        this.id = id;
    }

    public ConceptCollectionDTO(String id, String collectionname, Boolean accessibility, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.id = id;
        this.collectionname = collectionname;
        this.accessibility = accessibility;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public String getCollectionname() {
        return collectionname;
    }

    public void setCollectionname(String collectionname) {
        this.collectionname = collectionname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Boolean accessibility) {
        this.accessibility = accessibility;
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

    @XmlTransient
    public List<ConceptcollectionsCollaboratorDTO> getConceptcollectionsCollaboratorDTOList() {
        return conceptcollectionsCollaboratorDTOList;
    }

    public void setConceptcollectionsCollaboratorDTOList(List<ConceptcollectionsCollaboratorDTO> conceptcollectionsCollaboratorDTOList) {
        this.conceptcollectionsCollaboratorDTOList = conceptcollectionsCollaboratorDTOList;
    }

    @XmlTransient
    public List<ConceptcollectionsItemsDTO> getConceptcollectionsItemsDTOList() {
        return conceptcollectionsItemsDTOList;
    }

    public void setConceptcollectionsItemsDTOList(List<ConceptcollectionsItemsDTO> conceptcollectionsItemsDTOList) {
        this.conceptcollectionsItemsDTOList = conceptcollectionsItemsDTOList;
    }

    public QuadrigaUserDTO getCollectionowner() {
        return collectionowner;
    }

    public void setCollectionowner(QuadrigaUserDTO collectionowner) {
        this.collectionowner = collectionowner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptCollectionDTO)) {
            return false;
        }
        ConceptCollectionDTO other = (ConceptCollectionDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.ConceptcollectionsDTO[ id=" + id + " ]";
    }
    
}
