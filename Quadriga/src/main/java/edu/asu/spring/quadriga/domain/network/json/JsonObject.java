package edu.asu.spring.quadriga.domain.network.json;

public class JsonObject {

	RelationEventObject relationEventObject;
	AppellationEventObject appellationEventObject;
	Boolean isRelationEventObject;
	
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
}
