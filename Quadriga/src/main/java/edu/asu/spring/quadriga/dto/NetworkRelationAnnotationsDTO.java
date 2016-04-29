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
@Table(name = "tbl_network_annotations_relation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetworkRelationAnnotationsDTO.findAll", query = "SELECT n FROM NetworkRelationAnnotationsDTO n"),
    @NamedQuery(name = "NetworkRelationAnnotationsDTO.findBySubjectId", query = "SELECT n FROM NetworkRelationAnnotationsDTO n WHERE n.subjectId = :subjectid"),
    @NamedQuery(name = "NetworkRelationAnnotationsDTO.findBySubjectName", query = "SELECT n FROM NetworkRelationAnnotationsDTO n WHERE n.subjectName = :subjectname"),
    @NamedQuery(name = "NetworkRelationAnnotationsDTO.findByObjectId", query = "SELECT n FROM NetworkRelationAnnotationsDTO n WHERE n.objectId = :objectid"),
    @NamedQuery(name = "NetworkRelationAnnotationsDTO.findByObjectName", query = "SELECT n FROM NetworkRelationAnnotationsDTO n WHERE n.objectName = :objectname"),
    @NamedQuery(name = "NetworkRelationAnnotationsDTO.findByPredicateId", query = "SELECT n FROM NetworkRelationAnnotationsDTO n WHERE n.predicateId = :predicateid"),
    @NamedQuery(name = "NetworkRelationAnnotationsDTO.findByPredicateName", query = "SELECT n FROM NetworkRelationAnnotationsDTO n WHERE n.predicateName = :predicatename"),
    })
public class NetworkRelationAnnotationsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @Column(name = "relationannotationid")
    private String realtionAnnotationId;
	@Basic(optional = false)
    @Column(name = "predicateid")
    private String predicateId;
	@Basic(optional = false)
    @Column(name = "predicatename")
    private String predicateName;
	@Basic(optional = false)
    @Column(name = "subjectid")
    private String subjectId;
	@Basic(optional = false)
    @Column(name = "subjectname")
    private String subjectName;
	@Basic(optional = false)
    @Column(name = "objectid")
    private String objectId;
	@Basic(optional = false)
    @Column(name = "objectname")
    private String objectName;
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
    private Date updateDdate;
	
	@JoinColumn(name = "relationannotationid", referencedColumnName = "annotationid",insertable = false, updatable = false)
    @OneToOne(optional = false)
    private NetworkAnnotationsDTO annotationRelation;


	public String getRealtionAnnotationId() {
		return realtionAnnotationId;
	}

	public void setRealtionAnnotationId(String realtionAnnotationId) {
		this.realtionAnnotationId = realtionAnnotationId;
	}

	public String getPredicateId() {
		return predicateId;
	}

	public void setPredicateId(String predicateId) {
		this.predicateId = predicateId;
	}

	public String getPredicateName() {
		return predicateName;
	}

	public void setPredicateName(String predicateName) {
		this.predicateName = predicateName;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
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

	public Date getUpdateDdate() {
		return updateDdate;
	}

	public void setUpdateDdate(Date updateDdate) {
		this.updateDdate = updateDdate;
	}

	public NetworkAnnotationsDTO getAnnotationRelation() {
		return annotationRelation;
	}

	public void setAnnotationRelation(NetworkAnnotationsDTO annotationRelation) {
		this.annotationRelation = annotationRelation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((realtionAnnotationId == null) ? 0 : realtionAnnotationId
						.hashCode());
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
		NetworkRelationAnnotationsDTO other = (NetworkRelationAnnotationsDTO) obj;
		if (realtionAnnotationId == null) {
			if (other.realtionAnnotationId != null)
				return false;
		} else if (!realtionAnnotationId.equals(other.realtionAnnotationId))
			return false;
		return true;
	}
}
