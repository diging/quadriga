/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Karthik
 */
@Embeddable
public class DictionaryCollaboratorDTOPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "collaboratoruser")
    private String collaboratoruser;
    @Basic(optional = false)
    @Column(name = "collaboratorrole")
    private String collaboratorrole;

    public DictionaryCollaboratorDTOPK() {
    }

    public DictionaryCollaboratorDTOPK(String id, String collaboratoruser, String collaboratorrole) {
        this.id = id;
        this.collaboratoruser = collaboratoruser;
        this.collaboratorrole = collaboratorrole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (collaboratoruser != null ? collaboratoruser.hashCode() : 0);
        hash += (collaboratorrole != null ? collaboratorrole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DictionaryCollaboratorDTOPK)) {
            return false;
        }
        DictionaryCollaboratorDTOPK other = (DictionaryCollaboratorDTOPK) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.collaboratoruser == null && other.collaboratoruser != null) || (this.collaboratoruser != null && !this.collaboratoruser.equals(other.collaboratoruser))) {
            return false;
        }
        if ((this.collaboratorrole == null && other.collaboratorrole != null) || (this.collaboratorrole != null && !this.collaboratorrole.equals(other.collaboratorrole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.DictionaryCollaboratorDTOPK[ id=" + id + ", collaboratoruser=" + collaboratoruser + ", collaboratorrole=" + collaboratorrole + " ]";
    }
    
}
