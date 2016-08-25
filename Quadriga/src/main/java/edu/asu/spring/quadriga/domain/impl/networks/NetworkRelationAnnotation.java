package edu.asu.spring.quadriga.domain.impl.networks;

import java.util.Date;

import edu.asu.spring.quadriga.domain.network.INetworkRelationAnnotation;

public class NetworkRelationAnnotation implements INetworkRelationAnnotation 
{
	private String annotationId;
	private String annotationText;
	private String networkId;
	private String userName;
	private String predicateId;
	private String predicateName;
	private String subjectId;
	private String subjectName;
	private String objectId;
	private String objectName;
	private String objectType;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	

	@Override
	public String getAnnotationId() {
		return annotationId;
	}

	@Override
	public void setAnnotationId(String annotationId) {
        this.annotationId = annotationId;
	}

	@Override
	public String getAnnotationText() {
		return annotationText;
	}

	@Override
	public void setAnnotationText(String annotationText) {
          this.annotationText = annotationText;
	}

	@Override
	public String getNetworkId() {
		return networkId;
	}

	@Override
	public void setNetworkId(String networkId) {
         this.networkId = networkId;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
         this.userName = userName;
	}
	
	@Override
	public String getObjectType() {
		return objectType;
	}

	@Override
	public void setObjectType(String objectType) {
        this.objectType = objectType;		
	}

	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
	}

	@Override
	public Date getCreatedDate() {
 		return createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
         this.createdDate = createdDate;
	}

	@Override
	public String getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
         this.updatedBy = updatedBy;
	}

	@Override
	public Date getUpdatedDate() {
		return updatedDate;
	}

	@Override
	public void setUpdatedDate(Date updatedDate) {
         this.updatedDate = updatedDate;
	}

	@Override
	public String getPredicateId() {
		return predicateId;
	}

	@Override
	public void setPredicateId(String predicateId) {
         this.predicateId = predicateId;
	}

	@Override
	public String getPredicateName() {
		return predicateName;
	}

	@Override
	public void setPredicateName(String predicateName) {
        this.predicateName = predicateName;
	}

	@Override
	public String getSubjectId() {
		return subjectId;
	}

	@Override
	public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
	}

	@Override
	public String getSubjectName() {
		return subjectName;
	}

	@Override
	public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
	}

	@Override
	public String getObjectId() {
		return objectId;
	}

	@Override
	public void setObjectId(String objectId) {
        this.objectId = objectId;
	}

	@Override
	public String getObjectName() {
		return objectName;
	}

	@Override
	public void setObjectName(String objectName) {
        this.objectName = objectName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((annotationId == null) ? 0 : annotationId.hashCode());
		result = prime * result
				+ ((annotationText == null) ? 0 : annotationText.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((networkId == null) ? 0 : networkId.hashCode());
		result = prime * result
				+ ((objectId == null) ? 0 : objectId.hashCode());
		result = prime * result
				+ ((objectName == null) ? 0 : objectName.hashCode());
		result = prime * result
				+ ((objectType == null) ? 0 : objectType.hashCode());
		result = prime * result
				+ ((predicateId == null) ? 0 : predicateId.hashCode());
		result = prime * result
				+ ((predicateName == null) ? 0 : predicateName.hashCode());
		result = prime * result
				+ ((subjectId == null) ? 0 : subjectId.hashCode());
		result = prime * result
				+ ((subjectName == null) ? 0 : subjectName.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		NetworkRelationAnnotation other = (NetworkRelationAnnotation) obj;
		if (annotationId == null) {
			if (other.annotationId != null)
				return false;
		} else if (!annotationId.equals(other.annotationId))
			return false;
		if (annotationText == null) {
			if (other.annotationText != null)
				return false;
		} else if (!annotationText.equals(other.annotationText))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (networkId == null) {
			if (other.networkId != null)
				return false;
		} else if (!networkId.equals(other.networkId))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		if (objectName == null) {
			if (other.objectName != null)
				return false;
		} else if (!objectName.equals(other.objectName))
			return false;
		if (objectType == null) {
			if (other.objectType != null)
				return false;
		} else if (!objectType.equals(other.objectType))
			return false;
		if (predicateId == null) {
			if (other.predicateId != null)
				return false;
		} else if (!predicateId.equals(other.predicateId))
			return false;
		if (predicateName == null) {
			if (other.predicateName != null)
				return false;
		} else if (!predicateName.equals(other.predicateName))
			return false;
		if (subjectId == null) {
			if (other.subjectId != null)
				return false;
		} else if (!subjectId.equals(other.subjectId))
			return false;
		if (subjectName == null) {
			if (other.subjectName != null)
				return false;
		} else if (!subjectName.equals(other.subjectName))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
}
