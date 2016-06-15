package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CollaboratorDTOPK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7317542574800116063L;

    @Basic(optional = false)
    @Column(name = "collaboratoruser")
    protected String collaboratoruser;
    
    @Basic(optional = false)
    @Column(name = "collaboratorrole")
    protected String collaboratorrole;
    
    public String getCollaboratoruser() {
        return collaboratoruser;
    }

    public void setCollaboratoruser(String collaboratoruser) {
        this.collaboratoruser = collaboratoruser;
    }

    public String getCollaboratorrole() {
        return collaboratorrole;
    }

    public void setCollaboratorrole(String collaboratorrole) {
        this.collaboratorrole = collaboratorrole;
    }

}
