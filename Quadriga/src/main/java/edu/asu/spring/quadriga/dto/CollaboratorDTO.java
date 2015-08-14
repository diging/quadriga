package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class CollaboratorDTO<T extends CollaboratorDTOPK, V extends CollaboratorDTO<T, V>> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4418739052537728672L;

    @Basic(optional = false)
    @Column(name = "updatedby")
    protected String updatedby;
    
    @Basic(optional = false)
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updateddate;
    
    @Basic(optional = false)
    @Column(name = "createdby")
    protected String createdby;
    
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createddate;
    
    @JoinColumn(name = "collaboratoruser", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    protected QuadrigaUserDTO quadrigaUserDTO;
    
    @EmbeddedId
    protected T collaboratorDTOPK;
    
    public T getCollaboratorDTOPK() {
        return collaboratorDTOPK;
    }

    public void setCollaboratorDTOPK(T projectCollaboratorDTOPK) {
        this.collaboratorDTOPK = projectCollaboratorDTOPK;
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
    
    public abstract void setRelatedDTO(CollaboratingDTO<T, V> relatedDto);
}
