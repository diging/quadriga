package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.network.INetworkRelationAnnotation;

public class NetworkRelationAnnotation implements INetworkRelationAnnotation 
{
	String annotationId;
	String annotationText;
	String networkId;
	String userName;
	String predicateId;
	String predicateName;
	String subjectId;
	String subjectName;
	String objectId;
	String objectName;
	String objectType;
	
	@Override
	public String getAnnotationId() 
	{
		return annotationId;
	}

	@Override
	public void setAnnotationId(String annotationId) 
	{
		this.annotationId = annotationId;
	}

	@Override
	public String getAnnotationText()
	{
		return annotationText;
	}

	@Override
	public void setAnnotationText(String annotationText) 
	{
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
	public String getUserName() 
	{
		return userName;
	}

	@Override
	public void setUserName(String userName)
	{
        this.userName = userName;
	}

	@Override
	public String getPredicateId() 
	{
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
	public void setSubjectId(String subjectId) 
	{
        this.subjectId = subjectId;
	}

	@Override
	public String getSubjectName() {
		return subjectName;
	}

	@Override
	public void setSubjectName(String subjectName) 
	{
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
	public void setObjectName(String objectName) 
	{
        this.objectName = objectName;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

}
