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
@Table(name = "tbl_conceptcollection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptCollectionDTO.findAll", query = "SELECT c FROM ConceptCollectionDTO c"),
    @NamedQuery(name = "ConceptCollectionDTO.findByCollectionname", query = "SELECT c FROM ConceptCollectionDTO c WHERE c.collectionname = :collectionname"),
    @NamedQuery(name = "ConceptCollectionDTO.findById", query = "SELECT c FROM ConceptCollectionDTO c WHERE c.conceptCollectionid = :conceptCollectionid"),
    })
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
    @Column(name = "conceptcollectionid")
    private String conceptCollectionid;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptCollectionDTO")
    private List<ConceptCollectionCollaboratorDTO> conceptCollectionCollaboratorDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptCollectionDTO")
    private List<ConceptCollectionItemsDTO> conceptCollectionItemsDTOList;
    @JoinColumn(name = "collectionowner", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private QuadrigaUserDTO collectionowner;

    public ConceptCollectionDTO() {
    }

    public ConceptCollectionDTO(String conceptCollectionid) {
        this.conceptCollectionid = conceptCollectionid;
    }

    public ConceptCollectionDTO(String conceptCollectionid, String collectionname, Boolean accessibility, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.conceptCollectionid = conceptCollectionid;
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

    public String getConceptCollectionid() {
		return conceptCollectionid;
	}

	public void setConceptCollectionid(String conceptCollectionid) {
		this.conceptCollectionid = conceptCollectionid;
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
    public List<ConceptCollectionCollaboratorDTO> getConceptCollectionCollaboratorDTOList() {
		return conceptCollectionCollaboratorDTOList;
	}

	public void setConceptCollectionCollaboratorDTOList(
			List<ConceptCollectionCollaboratorDTO> conceptCollectionCollaboratorDTOList) {
		this.conceptCollectionCollaboratorDTOList = conceptCollectionCollaboratorDTOList;
	}

	@XmlTransient
	public List<ConceptCollectionItemsDTO> getConceptCollectionItemsDTOList() {
		return conceptCollectionItemsDTOList;
	}

	public void setConceptCollectionItemsDTOList(
			List<ConceptCollectionItemsDTO> conceptCollectionItemsDTOList) {
		this.conceptCollectionItemsDTOList = conceptCollectionItemsDTOList;
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
        hash += (conceptCollectionid != null ? conceptCollectionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConceptCollectionDTO)) {
            return false;
        }
        ConceptCollectionDTO other = (ConceptCollectionDTO) object;
        if ((this.conceptCollectionid == null && other.conceptCollectionid != null) || (this.conceptCollectionid != null && !this.conceptCollectionid.equals(other.conceptCollectionid))) {
            return false;
        }
        return true;
    }
}
