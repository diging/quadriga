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
public class ConceptcollectionsCollaboratorDTOPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "collectionid")
    private String collectionid;
    @Basic(optional = false)
    @Column(name = "collaboratoruser")
    private String collaboratoruser;
    @Basic(optional = false)
    @Column(name = "collaboratorrole")
    private String collaboratorrole;

    public ConceptcollectionsCollaboratorDTOPK() {
    }

    public ConceptcollectionsCollaboratorDTOPK(String collectionid, String collaboratoruser, String collaboratorrole) {
        this.collectionid = collectionid;
        this.collaboratoruser = collaboratoruser;
        this.collaboratorrole = collaboratorrole;
    }

    public String getCollectionid() {
        return collectionid;
    }

    public void setCollectionid(String collectionid) {
        this.collectionid = collectionid;
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
        hash += (collectionid != null ? collectionid.hashCode() : 0);
        hash += (collaboratoruser != null ? collaboratoruser.hashCode() : 0);
        hash += (collaboratorrole != null ? collaboratorrole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptcollectionsCollaboratorDTOPK)) {
            return false;
        }
        ConceptcollectionsCollaboratorDTOPK other = (ConceptcollectionsCollaboratorDTOPK) object;
        if ((this.collectionid == null && other.collectionid != null) || (this.collectionid != null && !this.collectionid.equals(other.collectionid))) {
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
        return "hpsdtogeneration.ConceptcollectionsCollaboratorDTOPK[ collectionid=" + collectionid + ", collaboratoruser=" + collaboratoruser + ", collaboratorrole=" + collaboratorrole + " ]";
    }
    
}
