package edu.asu.spring.quadriga.domain.impl.dictionarylist;

import javax.xml.bind.annotation.XmlElement;
/**
 * Class for individual dictionary item
 * @author Ashwin Prabhu Verleker
 *
 */
public class DictionaryItem {
	
	private String uri;
	private String term;
	private String pos;
		
	public String getUri() {
		return uri;
	}
	@XmlElement(namespace="http://www.digitalhps.org/Quadriga")
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTerm() {
		return term;
	}
	@XmlElement(namespace="http://www.digitalhps.org/Quadriga")
	public void setTerm(String term) {
		this.term = term;
	}
	public String getPos() {
		return pos;
	}
	@XmlElement(namespace="http://www.digitalhps.org/Quadriga")
	public void setPos(String pos) {
		this.pos = pos;
	}
}