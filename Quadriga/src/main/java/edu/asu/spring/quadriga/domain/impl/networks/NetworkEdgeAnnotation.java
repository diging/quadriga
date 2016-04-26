package edu.asu.spring.quadriga.domain.impl.networks;

import java.util.Date;

import edu.asu.spring.quadriga.domain.network.INetworkEdgeAnnotation;

public class NetworkEdgeAnnotation implements INetworkEdgeAnnotation 
{
	private String annotationId;
	private String annotationText;
	private String networkId;
	private String userName;
	private String sourceId;
	private String sourceName;
	private String targetId;
	private String targetName;
	private String targetNodeType;
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
	public String getSourceId() {
		return sourceId;
	}

	@Override
	public void setSourceId(String sourceId) {
         this.sourceId = sourceId;
	}

	@Override
	public String getSourceName() {
		return sourceName;
	}

	@Override
	public void setSourceName(String sourceName) {
          this.sourceName = sourceName;
	}

	@Override
	public String getTargetId() {
		return targetId;
	}

	@Override
	public void setTargetId(String targetId) {
        this.targetId = targetId;
	}
	
	@Override
	public String getTargetName() {
		return targetName;
	}

	@Override
	public void setTargetName(String targetName) {
        this.targetName = targetName;		
	}

	@Override
	public String getTargetNodeType() {
		return targetNodeType;
	}

	@Override
	public void setTargetNodeType(String targetNodeType) {
          this.targetNodeType = targetNodeType;
	}
	
	@Override
	public String getObjectType() {
		return objectType;
	}

	@Override
	public void setObjectType(String objectType)
	{
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
				+ ((objectType == null) ? 0 : objectType.hashCode());
		result = prime * result
				+ ((sourceId == null) ? 0 : sourceId.hashCode());
		result = prime * result
				+ ((sourceName == null) ? 0 : sourceName.hashCode());
		result = prime * result
				+ ((targetId == null) ? 0 : targetId.hashCode());
		result = prime * result
				+ ((targetName == null) ? 0 : targetName.hashCode());
		result = prime * result
				+ ((targetNodeType == null) ? 0 : targetNodeType.hashCode());
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
		NetworkEdgeAnnotation other = (NetworkEdgeAnnotation) obj;
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
		if (objectType == null) {
			if (other.objectType != null)
				return false;
		} else if (!objectType.equals(other.objectType))
			return false;
		if (sourceId == null) {
			if (other.sourceId != null)
				return false;
		} else if (!sourceId.equals(other.sourceId))
			return false;
		if (sourceName == null) {
			if (other.sourceName != null)
				return false;
		} else if (!sourceName.equals(other.sourceName))
			return false;
		if (targetId == null) {
			if (other.targetId != null)
				return false;
		} else if (!targetId.equals(other.targetId))
			return false;
		if (targetName == null) {
			if (other.targetName != null)
				return false;
		} else if (!targetName.equals(other.targetName))
			return false;
		if (targetNodeType == null) {
			if (other.targetNodeType != null)
				return false;
		} else if (!targetNodeType.equals(other.targetNodeType))
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
