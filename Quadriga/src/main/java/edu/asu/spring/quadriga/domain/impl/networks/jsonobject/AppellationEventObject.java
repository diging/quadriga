package edu.asu.spring.quadriga.domain.impl.networks.jsonobject;

/**
 * Class representing appellation event
 * @author Lohith Dwaraka
 *
 */
public class AppellationEventObject {

	String node;
	String termId;
	
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public void setNode(String node){
		this.node = node;
	}
	public String getNode(){
		return node;
	}
	
}
