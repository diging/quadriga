/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *This is mapping class for concept collection collaborator table
 * @author Karthik
 */
@Entity
@Table(name = "tbl_conceptcollection_collaborator")
@NamedQueries({
    @NamedQuery(name = "ConceptCollectionCollaboratorDTO.findAll", query = "SELECT c FROM ConceptCollectionCollaboratorDTO c"),
    @NamedQuery(name = "ConceptCollectionCollaboratorDTO.findByCollectionid", query = "SELECT c FROM ConceptCollectionCollaboratorDTO c WHERE c.collaboratorDTOPK.conceptcollectionid = :conceptcollectionid"),
    @NamedQuery(name = "ConceptCollectionCollaboratorDTO.findByCollaboratoruser", query = "SELECT c FROM ConceptCollectionCollaboratorDTO c WHERE c.collaboratorDTOPK.collaboratoruser = :collaboratoruser"),
    })
public class ConceptCollectionCollaboratorDTO extends CollaboratorDTO<ConceptCollectionCollaboratorDTOPK, ConceptCollectionCollaboratorDTO> {
    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "conceptcollectionid", referencedColumnName = "conceptcollectionid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConceptCollectionDTO conceptCollectionDTO;

    public ConceptCollectionCollaboratorDTO() {
    }

    public ConceptCollectionCollaboratorDTO(ConceptCollectionCollaboratorDTOPK conceptCollectionCollaboratorDTOPK) {
        this.collaboratorDTOPK = conceptCollectionCollaboratorDTOPK;
    }

    public ConceptCollectionCollaboratorDTO(ConceptCollectionCollaboratorDTOPK conceptCollectionCollaboratorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.collaboratorDTOPK = conceptCollectionCollaboratorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ConceptCollectionCollaboratorDTO(String conceptCollectionid, String collaboratoruser, String collaboratorrole) {
        this.collaboratorDTOPK = new ConceptCollectionCollaboratorDTOPK(conceptCollectionid, collaboratoruser, collaboratorrole);
    }

    public ConceptCollectionDTO getConceptCollectionDTO() {
		return conceptCollectionDTO;
	}

	public void setConceptCollectionDTO(ConceptCollectionDTO conceptCollectionDTO) {
		this.conceptCollectionDTO = conceptCollectionDTO;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (collaboratorDTOPK != null ? collaboratorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConceptCollectionCollaboratorDTO)) {
            return false;
        }
        ConceptCollectionCollaboratorDTO other = (ConceptCollectionCollaboratorDTO) object;
        if ((this.collaboratorDTOPK == null && other.collaboratorDTOPK != null) || (this.collaboratorDTOPK != null && !this.collaboratorDTOPK.equals(other.collaboratorDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public void setRelatedDTO(
            CollaboratingDTO<ConceptCollectionCollaboratorDTOPK, ConceptCollectionCollaboratorDTO> relatedDto) {
        conceptCollectionDTO = (ConceptCollectionDTO) relatedDto;
    }
}
