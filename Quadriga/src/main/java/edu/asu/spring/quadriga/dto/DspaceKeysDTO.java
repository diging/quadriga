/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the column mappings for
 * dspace keys table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_dspace_keys")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DspaceKeysDTO.findAll", query = "SELECT d FROM DspaceKeysDTO d"),
    @NamedQuery(name = "DspaceKeysDTO.findByUsername", query = "SELECT d FROM DspaceKeysDTO d WHERE d.dspaceKeysDTOPK.username = :username"),
    @NamedQuery(name = "DspaceKeysDTO.findByPublickey", query = "SELECT d FROM DspaceKeysDTO d WHERE d.dspaceKeysDTOPK.publickey = :publickey"),
    @NamedQuery(name = "DspaceKeysDTO.findByPrivatekey", query = "SELECT d FROM DspaceKeysDTO d WHERE d.dspaceKeysDTOPK.privatekey = :privatekey")})
public class DspaceKeysDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DspaceKeysDTOPK dspaceKeysDTOPK;
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private QuadrigaUserDTO quadrigaUserDTO;

    public DspaceKeysDTO() {
    }

    public DspaceKeysDTO(DspaceKeysDTOPK dspaceKeysDTOPK) {
        this.dspaceKeysDTOPK = dspaceKeysDTOPK;
    }

    public DspaceKeysDTO(String username, String publickey, String privatekey) {
        this.dspaceKeysDTOPK = new DspaceKeysDTOPK(username, publickey, privatekey);
    }

    public DspaceKeysDTOPK getDspaceKeysDTOPK() {
        return dspaceKeysDTOPK;
    }

    public void setDspaceKeysDTOPK(DspaceKeysDTOPK dspaceKeysDTOPK) {
        this.dspaceKeysDTOPK = dspaceKeysDTOPK;
    }

    public QuadrigaUserDTO getQuadrigaUserDTO() {
        return quadrigaUserDTO;
    }

    public void setQuadrigaUserDTO(QuadrigaUserDTO quadrigaUserDTO) {
        this.quadrigaUserDTO = quadrigaUserDTO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dspaceKeysDTOPK != null ? dspaceKeysDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DspaceKeysDTO)) {
            return false;
        }
        DspaceKeysDTO other = (DspaceKeysDTO) object;
        if ((this.dspaceKeysDTOPK == null && other.dspaceKeysDTOPK != null) || (this.dspaceKeysDTOPK != null && !this.dspaceKeysDTOPK.equals(other.dspaceKeysDTOPK))) {
            return false;
        }
        return true;
    }
}
