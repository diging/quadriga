package edu.asu.spring.quadriga.domain.impl.networks.jsonobject;

public class NodeObject {

	String relationEventId;
	String predicate;
	String subject;
	String object;
	
	public String getPredicate(){
		return predicate;
	}
	public void setPredicate(String predicate){
		this.predicate= predicate;
	}
	
	public String getSubject(){
		return subject;
	}
	public void setSubject(String subject){
		this.subject= subject;
	}
	
	public String getObject(){
		return object;
	}
	public void setObject(String object){
		this.object= object;
	}
	
	public String getRelationEventId(){
		return relationEventId;
	}
	public void setRelationEventId(String relationEventId){
		this.relationEventId= relationEventId;
	}
	
}
