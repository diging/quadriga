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
 *This class represents the column mappings for quadriga user profile table.
 * @author Karthik
 */
@Entity
@Table(name = "tbl_quadriga_userprofile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuadrigaUserprofileDTO.findAll", query = "SELECT q FROM QuadrigaUserprofileDTO q"),
    @NamedQuery(name = "QuadrigaUserprofileDTO.findByUsername", query = "SELECT q FROM QuadrigaUserprofileDTO q WHERE q.quadrigaUserprofileDTOPK.username = :username"),
    @NamedQuery(name = "QuadrigaUserprofileDTO.findByServiceid", query = "SELECT q FROM QuadrigaUserprofileDTO q WHERE q.quadrigaUserprofileDTOPK.serviceid = :serviceid"),
    @NamedQuery(name = "QuadrigaUserprofileDTO.findByProfileid", query = "SELECT q FROM QuadrigaUserprofileDTO q WHERE q.quadrigaUserprofileDTOPK.profileid = :profileid"),
    /*@NamedQuery(name = "QuadrigaUserprofileDTO.findByUsernameAndProfileid", query = "SELECT q FROM QuadrigaUserprofileDTO q WHERE q.quadrigaUserprofileDTOPK.profileid = :profileid "
    		+ " AND q.quadrigaUserprofileDTOPK.username = :username"),*/
    })
public class QuadrigaUserprofileDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected QuadrigaUserprofileDTOPK quadrigaUserprofileDTOPK;
    @Basic(optional = false)
    @Column(name = "profilename")
    private String profilename;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
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
    @JoinColumn(name = "username",referencedColumnName = "username",insertable = false,updatable = false)
    @ManyToOne(optional = false)
    private QuadrigaUserDTO quadrigaUserDTO;

	public QuadrigaUserprofileDTO() {
    }

    public QuadrigaUserprofileDTO(QuadrigaUserprofileDTOPK quadrigaUserprofileDTOPK,String profileName,String description, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.quadrigaUserprofileDTOPK = quadrigaUserprofileDTOPK;
        this.profilename = profileName;
        this.description = description;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public QuadrigaUserprofileDTO(String username, String serviceid, String profileid,String profileName,String description, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.quadrigaUserprofileDTOPK = new QuadrigaUserprofileDTOPK(username, serviceid,profileid);
        this.profilename = profileName;
        this.description = description;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }

    public QuadrigaUserprofileDTOPK getQuadrigaUserprofileDTOPK() {
        return quadrigaUserprofileDTOPK;
    }

    public void setQuadrigaUserprofileDTOPK(QuadrigaUserprofileDTOPK quadrigaUserprofileDTOPK) {
        this.quadrigaUserprofileDTOPK = quadrigaUserprofileDTOPK;
    }

    public String getProfilename() {
		return profilename;
	}

	public void setProfilename(String profilename) {
		this.profilename = profilename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quadrigaUserprofileDTOPK != null ? quadrigaUserprofileDTOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof QuadrigaUserprofileDTO)) {
            return false;
        }
        QuadrigaUserprofileDTO other = (QuadrigaUserprofileDTO) object;
        if ((this.quadrigaUserprofileDTOPK == null && other.quadrigaUserprofileDTOPK != null) || (this.quadrigaUserprofileDTOPK != null && !this.quadrigaUserprofileDTOPK.equals(other.quadrigaUserprofileDTOPK))) {
            return false;
        }
        return true;
    }
}
