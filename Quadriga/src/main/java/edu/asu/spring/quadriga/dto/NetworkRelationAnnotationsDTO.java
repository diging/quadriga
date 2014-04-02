package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_network_annotations_relation")
@XmlRootElement
public class NetworkRelationAnnotationsDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @Column(name = "annotationid")
    private String annotationId;
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
	
	@JoinColumn(name = "networkannotation", referencedColumnName = "annotationid")
    @ManyToOne(optional = false)
    private NetworkAnnotationsDTO relation;

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

//	public NetworkAnnotationsDTO getNetworkannotations() {
//		return networkAnnotations;
//	}
//
//	public void setNetworkannotations(NetworkAnnotationsDTO networkannotations) {
//		this.networkAnnotations = networkannotations;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime
//				* result
//				+ ((networkAnnotations == null) ? 0 : networkAnnotations
//						.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		NetworkRelationAnnotationsDTO other = (NetworkRelationAnnotationsDTO) obj;
//		if (networkAnnotations == null) {
//			if (other.networkAnnotations != null)
//				return false;
//		} else if (!networkAnnotations.equals(other.networkAnnotations))
//			return false;
//		return true;
//	}

	
}
