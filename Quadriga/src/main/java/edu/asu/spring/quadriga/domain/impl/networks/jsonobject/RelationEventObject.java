package edu.asu.spring.quadriga.domain.impl.networks.jsonobject;

public class RelationEventObject {

	SubjectObject subjectObject;
	ObjectTypeObject objectTypeObject;
	PredicateObject predicateObject;
	
	public void setSubjectObject(SubjectObject subjectObject){
		this.subjectObject = subjectObject;
	}
	public SubjectObject getSubjectObject(){
		return subjectObject;
	}
	
	
	public void setObjectTypeObject(ObjectTypeObject objectTypeObject){
		this.objectTypeObject = objectTypeObject;
	}
	public ObjectTypeObject getObjectTypeObject(){
		return objectTypeObject;
	}
	
	
	public void setPredicateObject(PredicateObject predicateObject){
		this.predicateObject = predicateObject;
	}
	public PredicateObject getPredicateObject(){
		return predicateObject;
	}
	
}
