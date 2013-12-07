/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ram Kumar Kumaresan
 */
@Entity
@Table(name = "tbl_network_annotations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworksAnnotationsDTO.findAll", query = "SELECT n FROM NetworksAnnotationsDTO n"),
    @NamedQuery(name = "NetworksAnnotationsDTO.findByNetworkid", query = "SELECT n FROM NetworksAnnotationsDTO n WHERE n.networkid = :networkid"),
    @NamedQuery(name = "NetworksAnnotationsDTO.findByid", query = "SELECT n FROM NetworksAnnotationsDTO n WHERE n.id = :id"),
    @NamedQuery(name = "NetworksAnnotationsDTO.findByAnnotationText", query = "SELECT n FROM NetworksAnnotationsDTO n WHERE n.annotationtext = :annotationtext"),
    @NamedQuery(name = "NetworksAnnotationsDTO.findByAnnotationId", query = "SELECT n FROM NetworksAnnotationsDTO n WHERE n.annotationid = :annotationid"),
    @NamedQuery(name = "NetworksAnnotationsDTO.findByUsername", query = "SELECT n FROM NetworksAnnotationsDTO n WHERE n.username = :username"),
    @NamedQuery(name = "NetworksAnnotationsDTO.findByObjectType", query = "SELECT n FROM NetworksAnnotationsDTO n WHERE n.objecttype = :objecttype"),
    @NamedQuery(name = "NetworksAnnotationsDTO.findByUpdatedby", query = "SELECT n FROM NetworksAnnotationsDTO n WHERE n.updatedby = :updatedby"),
    @NamedQuery(name = "NetworksAnnotationsDTO.findByUpdateddate", query = "SELECT n FROM NetworksAnnotationsDTO n WHERE n.updateddate = :updateddate"),
    @NamedQuery(name = "NetworksAnnotationsDTO.findByCreatedby", query = "SELECT n FROM NetworksAnnotationsDTO n WHERE n.createdby = :createdby"),
    @NamedQuery(name = "NetworksAnnotationsDTO.findByCreateddate", query = "SELECT n FROM NetworksAnnotationsDTO n WHERE n.createddate = :createddate")})
public class NetworksAnnotationsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "networkid")
    private String networkid;
    
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    
    @Basic(optional = false)
    @Column(name = "annotationtext")
    private String annotationtext;
    
    @Basic(optional = false)
    @Column(name = "annotationid")
    private String annotationid;
   
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    
    @Basic(optional = false)
    @Column(name = "objecttype")
    private String objecttype;
    
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

    public NetworksAnnotationsDTO() {
    }

    public NetworksAnnotationsDTO(String networkid) {
        this.networkid = networkid;
    }

    public NetworksAnnotationsDTO(String networkid, String id, String annotationtext, String annotationid, String username, String objecttype, String updatedby, Date updateddate, String createdby, Date createddate) {
        this.networkid = networkid;
        this.id = id;
        this.annotationtext = annotationtext;
        this.annotationid = annotationid;
        this.username = username;
        this.objecttype = objecttype;
        this.updatedby = updatedby;
        this.updateddate = updateddate;
        this.createdby = createdby;
        this.createddate = createddate;
    }


    public String getNetworkid() {
		return networkid;
	}

	public void setNetworkid(String networkid) {
		this.networkid = networkid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnnotationtext() {
		return annotationtext;
	}

	public void setAnnotationtext(String annotationtext) {
		this.annotationtext = annotationtext;
	}

	public String getAnnotationid() {
		return annotationid;
	}

	public void setAnnotationid(String annotationid) {
		this.annotationid = annotationid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getObjecttype() {
		return objecttype;
	}

	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (networkid != null ? networkid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NetworksAnnotationsDTO)) {
            return false;
        }
        NetworksAnnotationsDTO other = (NetworksAnnotationsDTO) object;
        if ((this.networkid == null && other.networkid != null) || (this.networkid != null && !this.networkid.equals(other.networkid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hpsdtogeneration.NetworksAnnotationsDTO[ networkid=" + networkid + " ]";
    }
    
}
