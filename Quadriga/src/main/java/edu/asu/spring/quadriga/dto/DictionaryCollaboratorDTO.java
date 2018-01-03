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
 *This contains the column mappings for 
 *dictionary collaborator table
 * @author Karthik
 */
@Entity
@Table(name = "tbl_dictionary_collaborator")
@NamedQueries({
    @NamedQuery(name = "DictionaryCollaboratorDTO.findAll", query = "SELECT d FROM DictionaryCollaboratorDTO d"),
    @NamedQuery(name = "DictionaryCollaboratorDTO.findById", query = "SELECT d FROM DictionaryCollaboratorDTO d WHERE d.collaboratorDTOPK.dictionaryid = :dictionaryid"),
    @NamedQuery(name = "DictionaryCollaboratorDTO.findByCollaboratoruser", query = "SELECT d FROM DictionaryCollaboratorDTO d WHERE d.collaboratorDTOPK.collaboratoruser = :collaboratoruser"),
    @NamedQuery(name = "DictionaryCollaboratorDTO.findByCollaboratorrole", query = "SELECT d FROM DictionaryCollaboratorDTO d WHERE d.collaboratorDTOPK.collaboratorrole = :collaboratorrole"),
    })
public class DictionaryCollaboratorDTO extends CollaboratorDTO<DictionaryCollaboratorDTOPK, DictionaryCollaboratorDTO> {
    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "dictionaryid", referencedColumnName = "dictionaryid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DictionaryDTO dictionaryDTO;

    public DictionaryCollaboratorDTO() {
    }

    public DictionaryCollaboratorDTO(DictionaryCollaboratorDTOPK dictionaryCollaboratorDTOPK) {
        this.collaboratorDTOPK = dictionaryCollaboratorDTOPK;
    }

    public DictionaryCollaboratorDTO(DictionaryCollaboratorDTOPK dictionaryCollaboratorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.collaboratorDTOPK = dictionaryCollaboratorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public DictionaryCollaboratorDTO(String dictionaryid, String collaboratoruser, String collaboratorrole,String updatedby, Date updateddate, String createdby, Date createddate) {
        this.collaboratorDTOPK = new DictionaryCollaboratorDTOPK(dictionaryid, collaboratoruser, collaboratorrole);
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public DictionaryCollaboratorDTOPK getDictionaryCollaboratorDTOPK() {
        return collaboratorDTOPK;
    }

    public void setDictionaryCollaboratorDTOPK(DictionaryCollaboratorDTOPK dictionaryCollaboratorDTOPK) {
        this.collaboratorDTOPK = dictionaryCollaboratorDTOPK;
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
        hash += (collaboratorDTOPK != null ? collaboratorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DictionaryCollaboratorDTO)) {
            return false;
        }
        DictionaryCollaboratorDTO other = (DictionaryCollaboratorDTO) object;
        if ((this.collaboratorDTOPK == null && other.collaboratorDTOPK != null) || (this.collaboratorDTOPK != null && !this.collaboratorDTOPK.equals(other.collaboratorDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public void setRelatedDTO(
            CollaboratingDTO<DictionaryCollaboratorDTOPK, DictionaryCollaboratorDTO> relatedDto) {
        dictionaryDTO = (DictionaryDTO) relatedDto;
    }
}
