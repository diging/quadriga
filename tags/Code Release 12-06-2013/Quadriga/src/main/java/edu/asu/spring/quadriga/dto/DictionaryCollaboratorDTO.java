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
@Table(name = "tbl_dictionary_collaborator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DictionaryCollaboratorDTO.findAll", query = "SELECT d FROM DictionaryCollaboratorDTO d"),
    @NamedQuery(name = "DictionaryCollaboratorDTO.findById", query = "SELECT d FROM DictionaryCollaboratorDTO d WHERE d.dictionaryCollaboratorDTOPK.id = :id"),
    @NamedQuery(name = "DictionaryCollaboratorDTO.findByCollaboratoruser", query = "SELECT d FROM DictionaryCollaboratorDTO d WHERE d.dictionaryCollaboratorDTOPK.collaboratoruser = :collaboratoruser"),
    @NamedQuery(name = "DictionaryCollaboratorDTO.findByCollaboratorrole", query = "SELECT d FROM DictionaryCollaboratorDTO d WHERE d.dictionaryCollaboratorDTOPK.collaboratorrole = :collaboratorrole"),
    @NamedQuery(name = "DictionaryCollaboratorDTO.findByUpdatedby", query = "SELECT d FROM DictionaryCollaboratorDTO d WHERE d.updatedby = :updatedby"),
    @NamedQuery(name = "DictionaryCollaboratorDTO.findByUpdateddate", query = "SELECT d FROM DictionaryCollaboratorDTO d WHERE d.updateddate = :updateddate"),
    @NamedQuery(name = "DictionaryCollaboratorDTO.findByCreatedby", query = "SELECT d FROM DictionaryCollaboratorDTO d WHERE d.createdby = :createdby"),
    @NamedQuery(name = "DictionaryCollaboratorDTO.findByCreateddate", query = "SELECT d FROM DictionaryCollaboratorDTO d WHERE d.createddate = :createddate")})
public class DictionaryCollaboratorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DictionaryCollaboratorDTOPK dictionaryCollaboratorDTOPK;
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
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DictionaryDTO dictionaryDTO;

    public DictionaryCollaboratorDTO() {
    }

    public DictionaryCollaboratorDTO(DictionaryCollaboratorDTOPK dictionaryCollaboratorDTOPK) {
        this.dictionaryCollaboratorDTOPK = dictionaryCollaboratorDTOPK;
    }

    public DictionaryCollaboratorDTO(DictionaryCollaboratorDTOPK dictionaryCollaboratorDTOPK, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.dictionaryCollaboratorDTOPK = dictionaryCollaboratorDTOPK;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public DictionaryCollaboratorDTO(String id, String collaboratoruser, String collaboratorrole) {
        this.dictionaryCollaboratorDTOPK = new DictionaryCollaboratorDTOPK(id, collaboratoruser, collaboratorrole);
    }

    public DictionaryCollaboratorDTOPK getDictionaryCollaboratorDTOPK() {
        return dictionaryCollaboratorDTOPK;
    }

    public void setDictionaryCollaboratorDTOPK(DictionaryCollaboratorDTOPK dictionaryCollaboratorDTOPK) {
        this.dictionaryCollaboratorDTOPK = dictionaryCollaboratorDTOPK;
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

    public DictionaryDTO getDictionaryDTO() {
        return dictionaryDTO;
    }

    public void setDictionaryDTO(DictionaryDTO dictionaryDTO) {
        this.dictionaryDTO = dictionaryDTO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dictionaryCollaboratorDTOPK != null ? dictionaryCollaboratorDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DictionaryCollaboratorDTO)) {
            return false;
        }
        DictionaryCollaboratorDTO other = (DictionaryCollaboratorDTO) object;
        if ((this.dictionaryCollaboratorDTOPK == null && other.dictionaryCollaboratorDTOPK != null) || (this.dictionaryCollaboratorDTOPK != null && !this.dictionaryCollaboratorDTOPK.equals(other.dictionaryCollaboratorDTOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.DictionaryCollaboratorDTO[ dictionaryCollaboratorDTOPK=" + dictionaryCollaboratorDTOPK + " ]";
    }
    
}
