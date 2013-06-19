package edu.asu.spring.quadriga.domain.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	"name",
	"description",
	"id"
})

public class RestProject {

	@XmlElement(required = true, namespace="http://www.digitalhps.org/")
	private String name;
	@XmlElement(required = true, namespace="http://www.digitalhps.org/")
	private String description;
	@XmlElement(required = true, namespace="http://www.digitalhps.org/")
	private String id;
	
	/**
     * retrieves the name of the project
     */
	
	public String getName() {
		return name;
	}
    /**
     * assigns the name of the project to the supplied 
     * variable.
     */
	
	public void setName(String name) {
		this.name = name;
	}
	
    /**
     * retrieves the description of the project
     */
	
	public String getDescription() {
		return description;
	}
	
	/**
	 * assigns the description of the project
	 */

	public void setDescription(String description) {
		this.description = description;
	}

    /**
     * retrieves the Unix ID of the project
     */
	
	public String getId() {
		return id;
	}
	
    /**
     * assigns the Unix ID of the project
     */
	
	public void setId(String id) {
		this.id = id;
	}

}
