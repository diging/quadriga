package edu.asu.spring.quadriga.domain.impl.networks.jsonobject;

public class ObjectTypeObject {
	Boolean isRelationEventObject;
	RelationEventObject relationEventObject;
	AppellationEventObject appellationEventObject;
	String remoteStatementId;
	boolean isRemoteLink = false;
	
	
	public String getRemoteStatementId() {
		return remoteStatementId;
	}
	public void setRemoteStatementId(String remoteStatementId) {
		this.remoteStatementId = remoteStatementId;
	}
	
	
	public boolean isRemoteLink() {
		return isRemoteLink;
	}
	public void setRemoteLink(boolean isRemoteLink) {
		this.isRemoteLink = isRemoteLink;
	}
	
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
	
	public String getObjectRelationPredictionAppellation(ObjectTypeObject objectTypeObject){
		RelationEventObject relationEventObject = objectTypeObject.getRelationEventObject();
		PredicateObject predicateObject = relationEventObject.getPredicateObject();
		AppellationEventObject appellationEventObject = predicateObject.getAppellationEventObject();
		return appellationEventObject.getNode();
	}
	public String getObjectRelationPredictionAppellationTermId(ObjectTypeObject objectTypeObject){
		RelationEventObject relationEventObject = objectTypeObject.getRelationEventObject();
		PredicateObject predicateObject = relationEventObject.getPredicateObject();
		AppellationEventObject appellationEventObject = predicateObject.getAppellationEventObject();
		return appellationEventObject.getTermId();
	}
}
