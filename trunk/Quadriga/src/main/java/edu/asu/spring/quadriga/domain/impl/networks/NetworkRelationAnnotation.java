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

}
