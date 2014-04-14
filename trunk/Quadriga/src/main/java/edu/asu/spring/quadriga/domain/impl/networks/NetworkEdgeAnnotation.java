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
	private String targetNodeType;
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
	public String getTargetNodeType() {
		return targetNodeType;
	}

	@Override
	public void setTargetNodeType(String targetNodeType) {
          this.targetNodeType = targetNodeType;
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

}
