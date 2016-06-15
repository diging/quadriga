/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *This class contains the primary key variables mapping
 *for dictionary collaborator.
 * @author Karthik
 */
@Embeddable
public class DictionaryCollaboratorDTOPK extends CollaboratorDTOPK {
	private static final long serialVersionUID = 1L;
	
    @Basic(optional = false)
    @Column(name = "dictionaryid")
    private String dictionaryid;
	

    public DictionaryCollaboratorDTOPK() {
    }

    public DictionaryCollaboratorDTOPK(String dictionaryid, String collaboratoruser, String collaboratorrole) {
        this.dictionaryid = dictionaryid;
        this.collaboratoruser = collaboratoruser;
        this.collaboratorrole = collaboratorrole;
    }

    public String getDictionaryid() {
		return dictionaryid;
	}

	public void setDictionaryid(String dictionaryid) {
		this.dictionaryid = dictionaryid;
	}
	

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dictionaryid != null ? dictionaryid.hashCode() : 0);
        hash += (collaboratoruser != null ? collaboratoruser.hashCode() : 0);
        hash += (collaboratorrole != null ? collaboratorrole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DictionaryCollaboratorDTOPK)) {
            return false;
        }
        DictionaryCollaboratorDTOPK other = (DictionaryCollaboratorDTOPK) object;
        if ((this.dictionaryid == null && other.dictionaryid != null) || (this.dictionaryid != null && !this.dictionaryid.equals(other.dictionaryid))) {
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
