package edu.asu.spring.quadriga.domain.network.json;

public class PredicateObject {

	AppellationEventObject appellationEventObject;
	String relationEventID;
	
	public void setAppellationEventObject(AppellationEventObject appellationEventObject){
		this.appellationEventObject = appellationEventObject;
	}
	public AppellationEventObject getAppellationEventObject(){
		return appellationEventObject;
	}
	
    public String getRelationEventID() {
        return relationEventID;
    }
    public void setRelationEventID(String relationEventID) {
        this.relationEventID = relationEventID;
    }
}
