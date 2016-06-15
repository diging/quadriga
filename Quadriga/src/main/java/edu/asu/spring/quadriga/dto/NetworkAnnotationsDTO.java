package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_network_annotations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworkAnnotationsDTO.findAll", query = "SELECT n FROM NetworkAnnotationsDTO n"),
    @NamedQuery(name = "NetworkAnnotationsDTO.findByNetworkid", query = "SELECT n FROM NetworkAnnotationsDTO n WHERE n.networkId = :networkid"),
    @NamedQuery(name = "NetworkAnnotationsDTO.findByAnnotationText", query = "SELECT n FROM NetworkAnnotationsDTO n WHERE n.annotationText = :annotationtext"),
    @NamedQuery(name = "NetworkAnnotationsDTO.findByAnnotationId", query = "SELECT n FROM NetworkAnnotationsDTO n WHERE n.annotationId = :annotationid"),
    @NamedQuery(name = "NetworkAnnotationsDTO.findByUsername", query = "SELECT n FROM NetworkAnnotationsDTO n WHERE n.userName = :username"),
    @NamedQuery(name = "NetworkAnnotationsDTO.findByObjectType", query = "SELECT n FROM NetworkAnnotationsDTO n WHERE n.objectType = :objecttype"),
    })
public class NetworkAnnotationsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @Column(name = "annotationid")
    private String annotationId;
	
	@Basic(optional = false)
    @Column(name = "annotationtext")
    private String annotationText;
   
	@Basic(optional = false)
    @Column(name = "networkid")
    private String networkId;
	
	@Basic(optional = false)
    @Column(name = "username")
    private String userName;
    
    @Basic(optional = false)
    @Column(name = "objecttype")
    private String objectType;
    
    @Basic(optional = false)
    @Column(name = "createdby")
    private String createdBy;
    
    @Basic(optional = false)
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Basic(optional = false)    
    @Column(name = "updatedby")
    private String updatedBy;
    
    @Basic(optional = false)
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    
    @JoinColumn(name = "networkid",referencedColumnName = "networkid",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NetworksDTO networksDTO;
    
    @JoinColumn(name = "username", referencedColumnName = "username",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private QuadrigaUserDTO quadrigaUserDTO;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "annotationNodes")
    private NetworkNodeAnnotationsDTO networkNodeAnnotation;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "annotationEdges")
    private NetworkEdgeAnnotationsDTO networkEdgeAnnotation;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "annotationRelation")
    private NetworkRelationAnnotationsDTO networkRelationAnnotation;

    public NetworkAnnotationsDTO()
    {
    	
    }
    
	public String getAnnotationId() {
		return annotationId;
	}

	public void setAnnotationId(String annotationId) {
		this.annotationId = annotationId;
	}

	public String getAnnotationText() {
		return annotationText;
	}

	public void setAnnotationText(String annotationText) {
		this.annotationText = annotationText;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updateDdate) {
		this.updatedDate = updateDdate;
	}

	public NetworksDTO getNetworksDTO() {
		return networksDTO;
	}

	public void setNetworksDTO(NetworksDTO networksDTO) {
		this.networksDTO = networksDTO;
	}

	public QuadrigaUserDTO getQuadrigaUserDTO() {
		return quadrigaUserDTO;
	}

	public void setQuadrigaUserDTO(QuadrigaUserDTO quadrigaUserDTO) {
		this.quadrigaUserDTO = quadrigaUserDTO;
	}

	
	public NetworkNodeAnnotationsDTO getNetworkNodeAnnotation() {
		return networkNodeAnnotation;
	}

	public void setNetworkNodeAnnotation(
			NetworkNodeAnnotationsDTO networkNodeAnnotation) {
		this.networkNodeAnnotation = networkNodeAnnotation;
	}

	public NetworkEdgeAnnotationsDTO getNetworkEdgeAnnotation() {
		return networkEdgeAnnotation;
	}

	public void setNetworkEdgeAnnotation(
			NetworkEdgeAnnotationsDTO networkEdgeAnnotation) {
		this.networkEdgeAnnotation = networkEdgeAnnotation;
	}

	public NetworkRelationAnnotationsDTO getNetworkRelationAnnotation() {
		return networkRelationAnnotation;
	}

	public void setNetworkRelationAnnotation(
			NetworkRelationAnnotationsDTO networkRelationAnnotation) {
		this.networkRelationAnnotation = networkRelationAnnotation;
	}

	public NetworkAnnotationsDTO(String annotationId, String annotationText,
			String networkId, String userName, String objectType,
			String createdBy, Date createdDate, String updatedBy,
			Date updatedDate) {
		
		this.annotationId = annotationId;
		this.annotationText = annotationText;
		this.networkId = networkId;
		this.userName = userName;
		this.objectType = objectType;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((annotationId == null) ? 0 : annotationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NetworkAnnotationsDTO other = (NetworkAnnotationsDTO) obj;
		if (annotationId == null) {
			if (other.annotationId != null)
				return false;
		} else if (!annotationId.equals(other.annotationId))
			return false;
		return true;
	}

}
