package edu.asu.spring.quadriga.domain.impl.networks.jsonobject;

public class SubjectObject {

	Boolean isRelationEventObject;
	RelationEventObject relationEventObject;
	AppellationEventObject appellationEventObject;
	
	public void setRelationEventObject(RelationEventObject relationEventObject){
		this.relationEventObject = relationEventObject;
	}
	public RelationEventObject getRelationEventObject(){
		return relationEventObject;
	}
	
	
	public void setAppellationEventObject(AppellationEventObject appellationEventObject){
		this.appellationEventObject = appellationEventObject;
	}
	public AppellationEventObject getAppellationEventObject(){
		return appellationEventObject;
	}
	
	
	public void setIsRelationEventObject(Boolean isRelationEventObject){
		this.isRelationEventObject = isRelationEventObject;
	}
	public Boolean getIsRelationEventObject(){
		return isRelationEventObject;
	}
	
	public String getSubjectRelationPredictionAppellation(SubjectObject subjectObject){
		RelationEventObject relationEventObject = subjectObject.getRelationEventObject();
		PredicateObject predicateObject = relationEventObject.getPredicateObject();
		AppellationEventObject appellationEventObject = predicateObject.getAppellationEventObject();
		return appellationEventObject.getNode();
	}
}

