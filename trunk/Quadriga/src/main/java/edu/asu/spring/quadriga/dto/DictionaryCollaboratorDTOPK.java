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
public class DictionaryCollaboratorDTOPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
    @Basic(optional = false)
    @Column(name = "dictionaryid")
    private String dictionaryid;
	@Basic(optional = false)
    @Column(name = "collaboratoruser")
    private String collaboratoruser;
    @Basic(optional = false)
    @Column(name = "collaboratorrole")
    private String collaboratorrole;

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
        hash += (dictionaryid != null ? dictionaryid.hashCode() : 0);
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
