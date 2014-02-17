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
 *This is mapping class for concept collection collaborator table
 * @author Karthik
 */
@Entity
@Table(name = "tbl_conceptcollection_collaborator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptCollectionCollaboratorDTO.findAll", query = "SELECT c FROM ConceptCollectionCollaboratorDTO c"),
    @NamedQuery(name = "ConceptCollectionCollaboratorDTO.findByCollectionid", query = "SELECT c FROM ConceptCollectionCollaboratorDTO c WHERE c.conceptCollectionCollaboratorDTOPK.conceptcollectionid = :conceptcollectionid"),
    @NamedQuery(name = "ConceptCollectionCollaboratorDTO.findByCollaboratoruser", query = "SELECT c FROM ConceptCollectionCollaboratorDTO c WHERE c.conceptCollectionCollaboratorDTOPK.collaboratoruser = :collaboratoruser"),
    })
public class ConceptCollectionCollaboratorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConceptCollectionCollaboratorDTOPK conceptCollectionCollaboratorDTOPK;
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
    @JoinColumn(name = "collaboratoruser", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private QuadrigaUserDTO quadrigaUserDTO;
    @JoinColumn(name = "conceptcollectionid", referencedColumnName = "conceptcollectionid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConceptCollectionDTO conceptCollectionDTO;

    public ConceptCollectionCollaboratorDTO() {
    }

    public ConceptCollectionCollaboratorDTO(ConceptCollectionCollaboratorDTOPK conceptCollectionCollaboratorDTOPK) {
        this.conceptCollectionCollaboratorDTOPK = conceptCollectionCollaboratorDTOPK;
    }

    public ConceptCollectionCollaboratorDTO(ConceptCollectionCollaboratorDTOPK conceptCollectionCollaboratorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.conceptCollectionCollaboratorDTOPK = conceptCollectionCollaboratorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ConceptCollectionCollaboratorDTO(String conceptCollectionid, String collaboratoruser, String collaboratorrole) {
        this.conceptCollectionCollaboratorDTOPK = new ConceptCollectionCollaboratorDTOPK(conceptCollectionid, collaboratoruser, collaboratorrole);
    }


    public ConceptCollectionCollaboratorDTOPK getConceptCollectionCollaboratorDTOPK() {
		return conceptCollectionCollaboratorDTOPK;
	}

	public void setConceptCollectionCollaboratorDTOPK(
			ConceptCollectionCollaboratorDTOPK conceptCollectionCollaboratorDTOPK) {
		this.conceptCollectionCollaboratorDTOPK = conceptCollectionCollaboratorDTOPK;
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

    public QuadrigaUserDTO getQuadrigaUserDTO() {
        return quadrigaUserDTO;
    }

    public void setQuadrigaUserDTO(QuadrigaUserDTO quadrigaUserDTO) {
        this.quadrigaUserDTO = quadrigaUserDTO;
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
        hash += (conceptCollectionCollaboratorDTOPK != null ? conceptCollectionCollaboratorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConceptCollectionCollaboratorDTO)) {
            return false;
        }
        ConceptCollectionCollaboratorDTO other = (ConceptCollectionCollaboratorDTO) object;
        if ((this.conceptCollectionCollaboratorDTOPK == null && other.conceptCollectionCollaboratorDTOPK != null) || (this.conceptCollectionCollaboratorDTOPK != null && !this.conceptCollectionCollaboratorDTOPK.equals(other.conceptCollectionCollaboratorDTOPK))) {
            return false;
        }
        return true;
    }
}
