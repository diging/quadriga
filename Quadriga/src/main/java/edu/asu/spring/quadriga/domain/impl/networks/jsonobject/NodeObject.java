package edu.asu.spring.quadriga.domain.impl.networks.jsonobject;

/**
 * Class representing individual node object
 * @author Lohith Dwaraka
 *
 */
public class NodeObject {

	String relationEventId;
	String predicate;
	String predicateId;
	String subject;
	String subjectId;
	boolean isSubjectRemoteLink=false;
	String object;
	String objectId;
	boolean isObjectRemoteLink=false;
	
	public boolean isSubjectRemoteLink() {
		return isSubjectRemoteLink;
	}
	public void setSubjectRemoteLink(boolean isSubjectRemoteLink) {
		this.isSubjectRemoteLink = isSubjectRemoteLink;
	}
	public boolean isObjectRemoteLink() {
		return isObjectRemoteLink;
	}
	public void setObjectRemoteLink(boolean isObjectRemoteLink) {
		this.isObjectRemoteLink = isObjectRemoteLink;
	}
	public String getPredicateId() {
		return predicateId;
	}
	public void setPredicateId(String predicateId) {
		this.predicateId = predicateId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
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
