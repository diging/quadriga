package edu.asu.spring.quadriga.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class CollaboratingDTO<U extends CollaboratorDTOPK, T extends CollaboratorDTO<U, T>> {
    
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

    public abstract List<T> getCollaboratorList();
    
    public abstract void setCollaboratorList(List<T> list);
    
    public abstract String getId();
    
    public abstract QuadrigaUserDTO getOwner();
    public abstract void setOwner(QuadrigaUserDTO owner);
    
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
}
