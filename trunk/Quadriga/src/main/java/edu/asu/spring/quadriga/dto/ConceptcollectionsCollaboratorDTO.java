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
 *
 * @author Karthik
 */
@Entity
@Table(name = "tbl_conceptcollections_collaborator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptcollectionsCollaboratorDTO.findAll", query = "SELECT c FROM ConceptcollectionsCollaboratorDTO c"),
    @NamedQuery(name = "ConceptcollectionsCollaboratorDTO.findByCollectionid", query = "SELECT c FROM ConceptcollectionsCollaboratorDTO c WHERE c.conceptcollectionsCollaboratorDTOPK.collectionid = :collectionid"),
    @NamedQuery(name = "ConceptcollectionsCollaboratorDTO.findByCollaboratoruser", query = "SELECT c FROM ConceptcollectionsCollaboratorDTO c WHERE c.conceptcollectionsCollaboratorDTOPK.collaboratoruser = :collaboratoruser"),
    @NamedQuery(name = "ConceptcollectionsCollaboratorDTO.findByCollaboratorrole", query = "SELECT c FROM ConceptcollectionsCollaboratorDTO c WHERE c.conceptcollectionsCollaboratorDTOPK.collaboratorrole = :collaboratorrole"),
    @NamedQuery(name = "ConceptcollectionsCollaboratorDTO.findByUpdatedby", query = "SELECT c FROM ConceptcollectionsCollaboratorDTO c WHERE c.updatedby = :updatedby"),
    @NamedQuery(name = "ConceptcollectionsCollaboratorDTO.findByUpdateddate", query = "SELECT c FROM ConceptcollectionsCollaboratorDTO c WHERE c.updateddate = :updateddate"),
    @NamedQuery(name = "ConceptcollectionsCollaboratorDTO.findByCreatedby", query = "SELECT c FROM ConceptcollectionsCollaboratorDTO c WHERE c.createdby = :createdby"),
    @NamedQuery(name = "ConceptcollectionsCollaboratorDTO.findByCreateddate", query = "SELECT c FROM ConceptcollectionsCollaboratorDTO c WHERE c.createddate = :createddate")})
public class ConceptcollectionsCollaboratorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConceptcollectionsCollaboratorDTOPK conceptcollectionsCollaboratorDTOPK;
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
    @JoinColumn(name = "collectionid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConceptCollectionDTO conceptcollectionsDTO;

    public ConceptcollectionsCollaboratorDTO() {
    }

    public ConceptcollectionsCollaboratorDTO(ConceptcollectionsCollaboratorDTOPK conceptcollectionsCollaboratorDTOPK) {
        this.conceptcollectionsCollaboratorDTOPK = conceptcollectionsCollaboratorDTOPK;
    }

    public ConceptcollectionsCollaboratorDTO(ConceptcollectionsCollaboratorDTOPK conceptcollectionsCollaboratorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.conceptcollectionsCollaboratorDTOPK = conceptcollectionsCollaboratorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public ConceptcollectionsCollaboratorDTO(String collectionid, String collaboratoruser, String collaboratorrole) {
        this.conceptcollectionsCollaboratorDTOPK = new ConceptcollectionsCollaboratorDTOPK(collectionid, collaboratoruser, collaboratorrole);
    }

    public ConceptcollectionsCollaboratorDTOPK getConceptcollectionsCollaboratorDTOPK() {
        return conceptcollectionsCollaboratorDTOPK;
    }

    public void setConceptcollectionsCollaboratorDTOPK(ConceptcollectionsCollaboratorDTOPK conceptcollectionsCollaboratorDTOPK) {
        this.conceptcollectionsCollaboratorDTOPK = conceptcollectionsCollaboratorDTOPK;
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

    public ConceptCollectionDTO getConceptcollectionsDTO() {
        return conceptcollectionsDTO;
    }

    public void setConceptcollectionsDTO(ConceptCollectionDTO conceptcollectionsDTO) {
        this.conceptcollectionsDTO = conceptcollectionsDTO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptcollectionsCollaboratorDTOPK != null ? conceptcollectionsCollaboratorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptcollectionsCollaboratorDTO)) {
            return false;
        }
        ConceptcollectionsCollaboratorDTO other = (ConceptcollectionsCollaboratorDTO) object;
        if ((this.conceptcollectionsCollaboratorDTOPK == null && other.conceptcollectionsCollaboratorDTOPK != null) || (this.conceptcollectionsCollaboratorDTOPK != null && !this.conceptcollectionsCollaboratorDTOPK.equals(other.conceptcollectionsCollaboratorDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.ConceptcollectionsCollaboratorDTO[ conceptcollectionsCollaboratorDTOPK=" + conceptcollectionsCollaboratorDTOPK + " ]";
    }
    
}
