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

/**
 * This class represents the column mapping for concept collection table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_conceptcollection")
@NamedQueries({
    @NamedQuery(name = "ConceptCollectionDTO.findAll", query = "SELECT c FROM ConceptCollectionDTO c"),
    @NamedQuery(name = "ConceptCollectionDTO.findByCollectionname", query = "SELECT c FROM ConceptCollectionDTO c WHERE c.collectionname = :collectionname"),
    @NamedQuery(name = "ConceptCollectionDTO.findById", query = "SELECT c FROM ConceptCollectionDTO c WHERE c.conceptCollectionid = :conceptCollectionid"),
    })
public class ConceptCollectionDTO extends CollaboratingDTO<ConceptCollectionCollaboratorDTOPK, ConceptCollectionCollaboratorDTO> implements Serializable {
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptCollectionDTO")
    private List<ConceptCollectionCollaboratorDTO> conceptCollectionCollaboratorDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptCollectionDTO")
    private List<ConceptCollectionItemsDTO> conceptCollectionItemsDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptCollection")
    private List<ProjectConceptCollectionDTO> projConceptCollectionDTOList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptCollectionDTO")
    private List<WorkspaceConceptcollectionDTO> wsConceptCollectionDTOList;
	@JoinColumn(name = "collectionowner", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private QuadrigaUserDTO collectionowner;

    public ConceptCollectionDTO() {
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
    
    public List<ProjectConceptCollectionDTO> getProjConceptCollectionDTOList() {
		return projConceptCollectionDTOList;
	}

	public void setProjConceptCollectionDTOList(
			List<ProjectConceptCollectionDTO> projConceptCollectionDTOList) {
		this.projConceptCollectionDTOList = projConceptCollectionDTOList;
	}

	public List<WorkspaceConceptcollectionDTO> getWsConceptCollectionDTOList() {
 		return wsConceptCollectionDTOList;
 	}

 	public void setWsConceptCollectionDTOList(
 			List<WorkspaceConceptcollectionDTO> wsConceptCollectionDTOList) {
 		this.wsConceptCollectionDTOList = wsConceptCollectionDTOList;
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


    public List<ConceptCollectionCollaboratorDTO> getConceptCollectionCollaboratorDTOList() {
		return conceptCollectionCollaboratorDTOList;
	}

	public void setConceptCollectionCollaboratorDTOList(
			List<ConceptCollectionCollaboratorDTO> conceptCollectionCollaboratorDTOList) {
		this.conceptCollectionCollaboratorDTOList = conceptCollectionCollaboratorDTOList;
	}

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

    @Override
    public List<ConceptCollectionCollaboratorDTO> getCollaboratorList() {
       return conceptCollectionCollaboratorDTOList;
    }

    @Override
    public void setCollaboratorList(List<ConceptCollectionCollaboratorDTO> list) {
        conceptCollectionCollaboratorDTOList = list;
    }

    @Override
    public String getId() {
        return conceptCollectionid;
    }

    @Override
    public void setOwner(QuadrigaUserDTO owner) {
        setCollectionowner(owner);
    }

    @Override
    public QuadrigaUserDTO getOwner() {
        return collectionowner;
    }
}
