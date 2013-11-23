package edu.asu.spring.quadriga.domain.impl.dictionarylist;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class representing individual dictionary
 * @author Ashwin Prabhu Verleker
 *
 */
public class Dictionary {

	private String name;
	private String description;
	private String uri;
	
	public String getName() {
		return name;
	}
	@XmlElement(namespace="http://www.digitalhps.org/Quadriga")
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	
	@XmlElement(namespace="http://www.digitalhps.org/Quadriga")
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUri() {
		return uri;
	}
	@XmlElement(namespace="http://www.digitalhps.org/Quadriga")
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
}
