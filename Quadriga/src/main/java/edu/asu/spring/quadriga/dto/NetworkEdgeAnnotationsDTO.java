package edu.asu.spring.quadriga.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_network_annotations_edge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworkEdgeAnnotationsDTO.findAll", query = "SELECT n FROM NetworkEdgeAnnotationsDTO n"),
    @NamedQuery(name = "NetworkEdgeAnnotationsDTO.findBySourceId", query = "SELECT n FROM NetworkEdgeAnnotationsDTO n WHERE n.sourceId = :sourceid"),
    @NamedQuery(name = "NetworkEdgeAnnotationsDTO.findBySourceName", query = "SELECT n FROM NetworkEdgeAnnotationsDTO n WHERE n.sourceName = :sourcename"),
    @NamedQuery(name = "NetworkEdgeAnnotationsDTO.findByTargetId", query = "SELECT n FROM NetworkEdgeAnnotationsDTO n WHERE n.targetId = :targetid"),
    @NamedQuery(name = "NetworkEdgeAnnotationsDTO.findByTargetName", query = "SELECT n FROM NetworkEdgeAnnotationsDTO n WHERE n.targetName = :targetname"),
    @NamedQuery(name = "NetworkEdgeAnnotationsDTO.findByTargetNodeType", query = "SELECT n FROM NetworkEdgeAnnotationsDTO n WHERE n.targetNodeType = :targetnodetype"),
    })
public class NetworkEdgeAnnotationsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @Column(name = "edgeannotationid")
    private String edgeAnnotationId;
	
	@Basic(optional = false)
    @Column(name = "sourceid")
    private String sourceId;
	@Basic(optional = false)
    @Column(name = "sourcename")
    private String sourceName;
	@Basic(optional = false)
    @Column(name = "targetid")
    private String targetId;
	@Basic(optional = false)
    @Column(name = "targetname")
    private String targetName;
	@Basic(optional = false)
    @Column(name = "targetnodetype")
    private String targetNodeType;
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
	
	@JoinColumn(name = "edgeannotationid", referencedColumnName = "annotationid",insertable = false, updatable = false)
    @OneToOne(optional = false)
    private NetworkAnnotationsDTO annotationEdges;


	public String getEdgeAnnotationId() {
		return edgeAnnotationId;
	}

	public void setEdgeAnnotationId(String edgeAnnotationId) {
		this.edgeAnnotationId = edgeAnnotationId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetNodeType() {
		return targetNodeType;
	}

	public void setTargetNodeType(String targetNodeType) {
		this.targetNodeType = targetNodeType;
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

	public NetworkAnnotationsDTO getAnnotationEdges() {
		return annotationEdges;
	}

	public void setAnnotationEdges(NetworkAnnotationsDTO annotationEdges) {
		this.annotationEdges = annotationEdges;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((edgeAnnotationId == null) ? 0 : edgeAnnotationId.hashCode());
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
		NetworkEdgeAnnotationsDTO other = (NetworkEdgeAnnotationsDTO) obj;
		if (edgeAnnotationId == null) {
			if (other.edgeAnnotationId != null)
				return false;
		} else if (!edgeAnnotationId.equals(other.edgeAnnotationId))
			return false;
		return true;
	}


    
}
