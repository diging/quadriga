package edu.asu.spring.quadriga.domain.impl.workspacexml;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class representing individual workspace
 * @author Lohith Dwaraka
 *
 */
public class Workspace {

	private String name;
	private String description;
	private String uri;
	private String id;
	
	public String getId() {
		return id;
	}
	@XmlElement(namespace="http://www.digitalhps.org/Quadriga")
	public void setId(String id) {
		this.id = id;
	}
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
