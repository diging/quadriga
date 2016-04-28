package edu.asu.spring.quadriga.domain.network;

public interface INetworkRelationAnnotation extends INetworkAnnotation 
{
	public abstract String getPredicateId();
	
	public abstract void setPredicateId(String predicateId);
	
	public abstract String getPredicateName();
	
	public abstract void setPredicateName(String predicateName);
	
	public abstract String getSubjectId();
	
	public abstract void setSubjectId(String subjectId);
	
	public abstract String getSubjectName();
	
	public abstract void setSubjectName(String subjectName);
	
	public abstract String getObjectId();
	
	public abstract void setObjectId(String objectId);
	
	public abstract String getObjectName();
	
	public abstract void setObjectName(String objectName);

}
