/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * This class represents the primary key variables for 
 * ConceptCollectionCollaborator table
 * @author Karthik
 */
@Embeddable
public class ConceptCollectionCollaboratorDTOPK extends CollaboratorDTOPK {
	private static final long serialVersionUID = 1L;
	
    @Basic(optional = false)
    @Column(name = "conceptcollectionid")
    private String conceptcollectionid;

    public ConceptCollectionCollaboratorDTOPK() {
    }

    public ConceptCollectionCollaboratorDTOPK(String conceptcollectionid, String collaboratoruser, String collaboratorrole) {
        this.conceptcollectionid = conceptcollectionid;
        this.collaboratoruser = collaboratoruser;
        this.collaboratorrole = collaboratorrole;
    }


    public String getConceptcollectionid() {
		return conceptcollectionid;
	}

	public void setConceptcollectionid(String conceptcollectionid) {
		this.conceptcollectionid = conceptcollectionid;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptcollectionid != null ? conceptcollectionid.hashCode() : 0);
        hash += (collaboratoruser != null ? collaboratoruser.hashCode() : 0);
        hash += (collaboratorrole != null ? collaboratorrole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConceptCollectionCollaboratorDTOPK)) {
            return false;
        }
        ConceptCollectionCollaboratorDTOPK other = (ConceptCollectionCollaboratorDTOPK) object;
        if ((this.conceptcollectionid == null && other.conceptcollectionid != null) || (this.conceptcollectionid != null && !this.conceptcollectionid.equals(other.conceptcollectionid))) {
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
}
