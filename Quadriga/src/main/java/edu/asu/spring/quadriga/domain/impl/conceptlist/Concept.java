package edu.asu.spring.quadriga.domain.impl.conceptlist;

import javax.xml.bind.annotation.XmlElement;

/**
 * Class representing individual concept
 * @author Lohith Dwaraka
 *
 */
public class Concept {

	private String name;
	private String pos;
	private String description;
	private String uri;
	
	public String getName() {
		return name;
	}
	@XmlElement(namespace = XMLConstants.QUADRIGA_NAMESPACE)
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPos() {
		return pos;
	}
	@XmlElement(namespace = XMLConstants.QUADRIGA_NAMESPACE)
	public void setPos(String pos) {
		this.pos = pos;
	}
	
	public String getDescription() {
		return description;
	}
	@XmlElement(namespace = XMLConstants.QUADRIGA_NAMESPACE)
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUri() {
		return uri;
	}
	@XmlElement(namespace = XMLConstants.QUADRIGA_NAMESPACE)
	public void setUri(String uri) {
		this.uri = uri;
	}
	
    @Override
    public String toString() {
        return "Concept [arg Name :" + name + ", arg Pos :" + pos + ", arg descrtiption :" + description + ", arg URI :" + uri + "]";
    }
	
	
}
