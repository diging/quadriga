package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceCollection;

/**
 * @description : Project class describing the properties 
 *                of a Project object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
@Service
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", namespace="http://www.digitalhps.org/quadriga", propOrder = {
	"name",
	"description",
	"id"
})
@XmlRootElement
public class Project implements IProject 
{
	@XmlElement(required = true, namespace="http://www.digitalhps.org/quadriga")
	private String name;
	@XmlElement(required = true, namespace="http://www.digitalhps.org/quadriga")
	private String description;
	@XmlElement(required = true, namespace="http://www.digitalhps.org/quadriga")
	private String id;
	@XmlTransient
	private int internalid;
	@XmlTransient
	private List<ICollaborator> collaborators;
	@XmlTransient
    private EProjectAccessibility projectAccess;
	@XmlTransient
    private ENetworkAccessibility networksDefaultAccess;
	@XmlTransient
    private IUser owner;

    /**
     * retrieves the name of the project
     */
	@Override
	public String getName() {
		return name;
	}
    /**
     * assigns the name of the project to the supplied 
     * variable.
     */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
    /**
     * retrieves the description of the project
     */
	@Override
	public String getDescription() {
		return description;
	}
	
	/**
	 * assigns the description of the project
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

    /**
     * retrieves the Unix ID of the project
     */
	@Override
	public String getId() {
		return id;
	}
	
    /**
     * assigns the Unix ID of the project
     */
	@Override
	public void setId(String id) {
		this.id = id;
	}

    /**
     * retrieves the internal id of the project
     */
	@Override
	public int getInternalid() {
		return internalid;
	}

    /**
     * assigns the internal id of the project
     */
	@Override
	public void setInternalid(int internalid) {
		this.internalid = internalid;
	}
	 /**
     * retrieves the owner of the project
     */
	@Override
	public IUser getOwner() {
		return owner;
	}
    
	/**
     * assigns the owner of the project
     */
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
	}

    /**
     * retrieves the collaborators of the project
     */
	@Override
	public List<ICollaborator> getCollaborators() {
		return collaborators;
	}

    /**
     * assigns the collaborators of the project
     */
	@Override
	public void setCollaborators(List<ICollaborator> collaborators) {
		this.collaborators = collaborators;
	}
	
    /**
     * retrieves the accessibility of the project
     */
	@Override
	public EProjectAccessibility getProjectAccess() {
		return projectAccess;
	}
	
    /**
     * assigns the accessibility of the project
     */
	@Override
	public void setProjectAccess(EProjectAccessibility projectAccess) {
		this.projectAccess = projectAccess;
	}
    /**
     * retrieves the networks access of the project
     */
	@Override
	public ENetworkAccessibility getNetworksDefaultAccess() {
		return networksDefaultAccess;
	}
    /**
     * assigns the network access of the project
     */
	@Override
	public void setNetworksDefaultAccess(ENetworkAccessibility networksDefaultAccess) {
		this.networksDefaultAccess = networksDefaultAccess;
	}
	
}
